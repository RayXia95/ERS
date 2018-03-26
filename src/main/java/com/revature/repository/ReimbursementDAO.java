package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.util.ErsRepositoryUtil;

public class ReimbursementDAO implements ReimbursementRepository
{
    private static final Logger logger = Logger.getLogger(ReimbursementDAO.class);
    private static ReimbursementDAO reimburseDAO = new ReimbursementDAO();

    private ReimbursementDAO()
    {
    }

    public static ReimbursementDAO getReimbursementDAO()
    {
	return reimburseDAO;
    }

    @Override
    public boolean insert(Reimbursement reimbursement)
    {
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    logger.trace("Was able to get connection to insert");
	    final String SQL = "INSERT INTO REIMBURSEMENT VALUES(NULL, CURRENT_TIMESTAMP, NULL, ?, ?, NULL,?,?,1,?)";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    int parameterIndex = 0;

	    statement.setDouble(++parameterIndex, reimbursement.getAmount());
	    statement.setString(++parameterIndex, reimbursement.getDescription());
	    statement.setInt(++parameterIndex, reimbursement.getRequester().getId());
	    statement.setInt(++parameterIndex, reimbursement.getApprover().getId());
	    statement.setInt(++parameterIndex, reimbursement.getType().getId());

	    if ( statement.executeUpdate() != 0 )
	    {
		logger.trace("Was able to insert");
		return true;
	    }
	    else
	    {
		return false;
	    }

	}
	catch (SQLException e)
	{
	    logger.error("Unable register user, Probably unique id violated", e);
	}
	return false;
    }

    @Override
    public boolean update(Reimbursement reimbursement)
    {
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    final String SQL = "UPDATE REIMBURSEMENT SET R_RESOLVED = CURRENT_TIMESTAMP, RS_ID = ? WHERE R_ID = ?";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    int parameterIndex = 0;

	    statement.setInt(++parameterIndex, reimbursement.getStatus().getId());
	    statement.setInt(++parameterIndex, reimbursement.getId());

	    if ( statement.executeUpdate() != 0 )
	    {
		return true;
	    }
	    else
	    {
		return false;
	    }
	}
	catch (SQLException e)
	{
	    logger.error("Was unable to update", e);
	}
	return false;
    }

    @Override
    public Reimbursement select(int reimbursementId)
    {
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    final String SQL = "SELECT RE.R_ID, RE.R_REQUESTED, RE.R_RESOLVED, RE.R_AMOUNT, RE.R_DESCRIPTION, RE.EMPLOYEE_ID, U.U_FIRSTNAME, U.U_LASTNAME, U.U_USERNAME, U.U_PASSWORD, U.U_EMAIL, U.UR_ID, UR.UR_TYPE, RE.MANAGER_ID, RE.RS_ID, RS.RS_STATUS, RE.RT_ID, RT.RT_TYPE FROM REIMBURSEMENT RE INNER JOIN USER_T U ON RE.EMPLOYEE_ID = U.U_ID INNER JOIN USER_ROLE UR ON U.UR_ID = UR.UR_ID INNER JOIN REIMBURSEMENT_STATUS RS ON RE.RS_ID = RS.RS_ID INNER JOIN REIMBURSEMENT_TYPE RT ON RE.RT_ID = RT.RT_ID WHERE RE.R_ID = ?";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    statement.setInt(1, reimbursementId);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next())
	    {
		Reimbursement reimbursement = new Reimbursement();
		Employee employee = new Employee();
		Employee manager = new Employee();
		EmployeeRole employeeRole = new EmployeeRole();
		ReimbursementStatus status = new ReimbursementStatus();
		ReimbursementType type = new ReimbursementType();

		reimbursement.setId(resultSet.getInt("R_ID"));
		reimbursement.setRequested(resultSet.getTimestamp("R_REQUESTED").toLocalDateTime());

		if ( resultSet.getTimestamp("R_RESOLVED") != null )
		{
		    reimbursement.setResolved(resultSet.getTimestamp("R_RESOLVED").toLocalDateTime());
		}
		reimbursement.setAmount(resultSet.getDouble("R_AMOUNT"));

		if ( resultSet.getString("R_DESCRIPTION") != null )
		{
		    reimbursement.setDescription(resultSet.getString("R_DESCRIPTION"));
		}
		employee.setId(resultSet.getInt("EMPLOYEE_ID"));
		employee.setFirstName(resultSet.getString("U_FIRSTNAME"));
		employee.setLastName(resultSet.getString("U_LASTNAME"));
		employee.setUsername(resultSet.getString("U_USERNAME"));
		employee.setPassword(resultSet.getString("U_PASSWORD"));
		employee.setEmail(resultSet.getString("U_EMAIL"));

		employeeRole.setId(resultSet.getInt("UR_ID"));
		employeeRole.setType(resultSet.getString("UR_TYPE"));

		employee.setEmployeeRole(employeeRole);
		reimbursement.setRequester(employee);

		if ( resultSet.getInt("MANAGER_ID") != 0 )
		{
		    manager.setId(resultSet.getInt("MANAGER_ID"));
		    reimbursement.setApprover(manager);
		}

		status.setId(resultSet.getInt("RS_ID"));
		status.setStatus(resultSet.getString("RS_STATUS"));
		reimbursement.setStatus(status);

		type.setId(resultSet.getInt("RT_ID"));
		type.setType(resultSet.getString("RT_TYPE"));
		reimbursement.setType(type);

		return reimbursement;
	    }

	}
	catch (SQLException e)
	{
	    logger.error("could not get reimbursement based on ID", e);
	}
	catch (NullPointerException d)
	{
	    logger.error("Resolved");
	}
	return null;
    }

    @Override
    public Set<Reimbursement> selectPending(int employeeId)
    {
	Set<Reimbursement> reimbursements = new HashSet<>();
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    final String SQL = "SELECT RE.R_ID, RE.R_REQUESTED, RE.R_RESOLVED, RE.R_AMOUNT, RE.R_DESCRIPTION, RE.EMPLOYEE_ID, U.U_FIRSTNAME, U.U_LASTNAME, U.U_USERNAME, U.U_PASSWORD, U.U_EMAIL, U.UR_ID, UR.UR_TYPE, RE.MANAGER_ID, RE.RS_ID, RS.RS_STATUS, RE.RT_ID, RT.RT_TYPE FROM REIMBURSEMENT RE INNER JOIN USER_T U ON RE.EMPLOYEE_ID = U.U_ID INNER JOIN USER_ROLE UR ON U.UR_ID = UR.UR_ID INNER JOIN REIMBURSEMENT_STATUS RS ON RE.RS_ID = RS.RS_ID INNER JOIN REIMBURSEMENT_TYPE RT ON RE.RT_ID = RT.RT_ID WHERE U.U_ID = ? AND RS.RS_STATUS = 'PENDING'";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    statement.setInt(1, employeeId);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next())
	    {
		Reimbursement reimbursement = new Reimbursement();
		Employee employee = new Employee();
		Employee manager = new Employee();
		EmployeeRole employeeRole = new EmployeeRole();
		ReimbursementStatus status = new ReimbursementStatus();
		ReimbursementType type = new ReimbursementType();

		reimbursement.setId(resultSet.getInt("R_ID"));
		reimbursement.setRequested(resultSet.getTimestamp("R_REQUESTED").toLocalDateTime());

		reimbursement.setAmount(resultSet.getDouble("R_AMOUNT"));

		if ( resultSet.getString("R_DESCRIPTION") != null )
		{
		    reimbursement.setDescription(resultSet.getString("R_DESCRIPTION"));
		}
		employee.setId(resultSet.getInt("EMPLOYEE_ID"));
		employee.setFirstName(resultSet.getString("U_FIRSTNAME"));
		employee.setLastName(resultSet.getString("U_LASTNAME"));
		employee.setUsername(resultSet.getString("U_USERNAME"));
		employee.setPassword(resultSet.getString("U_PASSWORD"));
		employee.setEmail(resultSet.getString("U_EMAIL"));

		employeeRole.setId(resultSet.getInt("UR_ID"));
		employeeRole.setType(resultSet.getString("UR_TYPE"));

		employee.setEmployeeRole(employeeRole);
		reimbursement.setRequester(employee);

		if ( resultSet.getInt("MANAGER_ID") != 0 )
		{
		    manager.setId(resultSet.getInt("MANAGER_ID"));
		    reimbursement.setApprover(manager);
		}

		status.setId(resultSet.getInt("RS_ID"));
		status.setStatus(resultSet.getString("RS_STATUS"));
		reimbursement.setStatus(status);

		type.setId(resultSet.getInt("RT_ID"));
		type.setType(resultSet.getString("RT_TYPE"));
		reimbursement.setType(type);

		reimbursements.add(reimbursement);
	    }
	    return reimbursements;

	}
	catch (SQLException e)
	{
	    logger.error("could not get reimbursements based on employee", e);
	}

	return new HashSet<>();
    }

    @Override
    public Set<Reimbursement> selectFinalized(int employeeId)
    {
	Set<Reimbursement> reimbursements = new HashSet<>();
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    final String SQL = "SELECT RE.R_ID, RE.R_REQUESTED, RE.R_RESOLVED, RE.R_AMOUNT, RE.R_DESCRIPTION, RE.EMPLOYEE_ID, U.U_FIRSTNAME, U.U_LASTNAME, U.U_USERNAME, U.U_PASSWORD, U.U_EMAIL, U.UR_ID, UR.UR_TYPE, RE.MANAGER_ID, RE.RS_ID, RS.RS_STATUS, RE.RT_ID, RT.RT_TYPE FROM REIMBURSEMENT RE INNER JOIN USER_T U ON RE.EMPLOYEE_ID = U.U_ID INNER JOIN USER_ROLE UR ON U.UR_ID = UR.UR_ID INNER JOIN REIMBURSEMENT_STATUS RS ON RE.RS_ID = RS.RS_ID INNER JOIN REIMBURSEMENT_TYPE RT ON RE.RT_ID = RT.RT_ID WHERE U.U_ID = ? AND RS.RS_STATUS = 'APPROVED' OR RS.RS_STATUS = 'DECLINED'";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    statement.setInt(1, employeeId);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next())
	    {
		Reimbursement reimbursement = new Reimbursement();
		Employee employee = new Employee();
		Employee manager = new Employee();
		EmployeeRole employeeRole = new EmployeeRole();
		ReimbursementStatus status = new ReimbursementStatus();
		ReimbursementType type = new ReimbursementType();

		reimbursement.setId(resultSet.getInt("R_ID"));
		reimbursement.setRequested(resultSet.getTimestamp("R_REQUESTED").toLocalDateTime());

		// all finalized should have resolved timestamp
		reimbursement.setResolved(resultSet.getTimestamp("R_RESOLVED").toLocalDateTime());
		reimbursement.setAmount(resultSet.getDouble("R_AMOUNT"));

		if ( resultSet.getString("R_DESCRIPTION") != null )
		{
		    reimbursement.setDescription(resultSet.getString("R_DESCRIPTION"));
		}
		employee.setId(resultSet.getInt("EMPLOYEE_ID"));
		employee.setFirstName(resultSet.getString("U_FIRSTNAME"));
		employee.setLastName(resultSet.getString("U_LASTNAME"));
		employee.setUsername(resultSet.getString("U_USERNAME"));
		employee.setPassword(resultSet.getString("U_PASSWORD"));
		employee.setEmail(resultSet.getString("U_EMAIL"));

		employeeRole.setId(resultSet.getInt("UR_ID"));
		employeeRole.setType(resultSet.getString("UR_TYPE"));

		employee.setEmployeeRole(employeeRole);
		reimbursement.setRequester(employee);

		//Resolved should have a manager ID
		manager.setId(resultSet.getInt("MANAGER_ID"));
		reimbursement.setApprover(manager);

		status.setId(resultSet.getInt("RS_ID"));
		status.setStatus(resultSet.getString("RS_STATUS"));
		reimbursement.setStatus(status);

		type.setId(resultSet.getInt("RT_ID"));
		type.setType(resultSet.getString("RT_TYPE"));
		reimbursement.setType(type);

		reimbursements.add(reimbursement);
	    }
	    return reimbursements;

	}
	catch (SQLException e)
	{
	    logger.error("could not get finalized reimbursements based on employeeID ", e);
	}

	return new HashSet<>();
    }

    @Override
    public Set<Reimbursement> selectAllPending()
    {
	Set<Reimbursement> reimbursements = new HashSet<>();
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    final String SQL = "SELECT RE.R_ID, RE.R_REQUESTED, RE.R_RESOLVED, RE.R_AMOUNT, RE.R_DESCRIPTION, RE.EMPLOYEE_ID, U.U_FIRSTNAME, U.U_LASTNAME, U.U_USERNAME, U.U_PASSWORD, U.U_EMAIL, U.UR_ID, UR.UR_TYPE, RE.MANAGER_ID, RE.RS_ID, RS.RS_STATUS, RE.RT_ID, RT.RT_TYPE FROM REIMBURSEMENT RE INNER JOIN USER_T U ON RE.EMPLOYEE_ID = U.U_ID INNER JOIN USER_ROLE UR ON U.UR_ID = UR.UR_ID INNER JOIN REIMBURSEMENT_STATUS RS ON RE.RS_ID = RS.RS_ID INNER JOIN REIMBURSEMENT_TYPE RT ON RE.RT_ID = RT.RT_ID WHERE RS.RS_STATUS = 'PENDING'";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next())
	    {
		Reimbursement reimbursement = new Reimbursement();
		Employee employee = new Employee();
		Employee manager = new Employee();
		EmployeeRole employeeRole = new EmployeeRole();
		ReimbursementStatus status = new ReimbursementStatus();
		ReimbursementType type = new ReimbursementType();

		reimbursement.setId(resultSet.getInt("R_ID"));
		reimbursement.setRequested(resultSet.getTimestamp("R_REQUESTED").toLocalDateTime());

		reimbursement.setAmount(resultSet.getDouble("R_AMOUNT"));

		if ( resultSet.getString("R_DESCRIPTION") != null )
		{
		    reimbursement.setDescription(resultSet.getString("R_DESCRIPTION"));
		}
		employee.setId(resultSet.getInt("EMPLOYEE_ID"));
		employee.setFirstName(resultSet.getString("U_FIRSTNAME"));
		employee.setLastName(resultSet.getString("U_LASTNAME"));
		employee.setUsername(resultSet.getString("U_USERNAME"));
		employee.setPassword(resultSet.getString("U_PASSWORD"));
		employee.setEmail(resultSet.getString("U_EMAIL"));

		employeeRole.setId(resultSet.getInt("UR_ID"));
		employeeRole.setType(resultSet.getString("UR_TYPE"));

		employee.setEmployeeRole(employeeRole);
		reimbursement.setRequester(employee);

		if ( resultSet.getInt("MANAGER_ID") != 0 )
		{
		    manager.setId(resultSet.getInt("MANAGER_ID"));
		    reimbursement.setApprover(manager);
		}

		status.setId(resultSet.getInt("RS_ID"));
		status.setStatus(resultSet.getString("RS_STATUS"));
		reimbursement.setStatus(status);

		type.setId(resultSet.getInt("RT_ID"));
		type.setType(resultSet.getString("RT_TYPE"));
		reimbursement.setType(type);

		reimbursements.add(reimbursement);
	    }
	    return reimbursements;

	}
	catch (SQLException e)
	{
	    logger.error("could not get pending reimbursements", e);
	}

	return new HashSet<>();
    }

    @Override
    public Set<Reimbursement> selectAllFinalized()
    {
	Set<Reimbursement> reimbursements = new HashSet<>();
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    final String SQL = "SELECT RE.R_ID, RE.R_REQUESTED, RE.R_RESOLVED, RE.R_AMOUNT, RE.R_DESCRIPTION, RE.EMPLOYEE_ID, U.U_FIRSTNAME, U.U_LASTNAME, U.U_USERNAME, U.U_PASSWORD, U.U_EMAIL, U.UR_ID, UR.UR_TYPE, RE.MANAGER_ID, RE.RS_ID, RS.RS_STATUS, RE.RT_ID, RT.RT_TYPE FROM REIMBURSEMENT RE INNER JOIN USER_T U ON RE.EMPLOYEE_ID = U.U_ID INNER JOIN USER_ROLE UR ON U.UR_ID = UR.UR_ID INNER JOIN REIMBURSEMENT_STATUS RS ON RE.RS_ID = RS.RS_ID INNER JOIN REIMBURSEMENT_TYPE RT ON RE.RT_ID = RT.RT_ID WHERE RS.RS_STATUS = 'APPROVED' OR RS.RS_STATUS = 'DECLINED'";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next())
	    {
		Reimbursement reimbursement = new Reimbursement();
		Employee employee = new Employee();
		Employee manager = new Employee();
		EmployeeRole employeeRole = new EmployeeRole();
		ReimbursementStatus status = new ReimbursementStatus();
		ReimbursementType type = new ReimbursementType();

		reimbursement.setId(resultSet.getInt("R_ID"));
		reimbursement.setRequested(resultSet.getTimestamp("R_REQUESTED").toLocalDateTime());

		// all finalized should have resolved timestamp
		reimbursement.setResolved(resultSet.getTimestamp("R_RESOLVED").toLocalDateTime());
		reimbursement.setAmount(resultSet.getDouble("R_AMOUNT"));

		if ( resultSet.getString("R_DESCRIPTION") != null )
		{
		    reimbursement.setDescription(resultSet.getString("R_DESCRIPTION"));
		}
		employee.setId(resultSet.getInt("EMPLOYEE_ID"));
		employee.setFirstName(resultSet.getString("U_FIRSTNAME"));
		employee.setLastName(resultSet.getString("U_LASTNAME"));
		employee.setUsername(resultSet.getString("U_USERNAME"));
		employee.setPassword(resultSet.getString("U_PASSWORD"));
		employee.setEmail(resultSet.getString("U_EMAIL"));

		employeeRole.setId(resultSet.getInt("UR_ID"));
		employeeRole.setType(resultSet.getString("UR_TYPE"));

		employee.setEmployeeRole(employeeRole);
		reimbursement.setRequester(employee);

		//Resolved should have a manager ID
		manager.setId(resultSet.getInt("MANAGER_ID"));
		reimbursement.setApprover(manager);

		status.setId(resultSet.getInt("RS_ID"));
		status.setStatus(resultSet.getString("RS_STATUS"));
		reimbursement.setStatus(status);

		type.setId(resultSet.getInt("RT_ID"));
		type.setType(resultSet.getString("RT_TYPE"));
		reimbursement.setType(type);

		reimbursements.add(reimbursement);
	    }

	    return reimbursements;

	}
	catch (SQLException e)
	{
	    logger.error("could not get finalized reimbursements", e);
	}

	return new HashSet<>();
    }

    @Override
    public Set<ReimbursementType> selectTypes()
    {
	Set<ReimbursementType> reimbursementTypes = new HashSet<>();
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    final String SQL = "SELECT * FROM REIMBURSEMENT_TYPE";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next())
	    {
		ReimbursementType reimbursementType = new ReimbursementType();
		reimbursementType.setId(resultSet.getInt("RT_ID"));
		reimbursementType.setType(resultSet.getString("RT_TYPE"));

		reimbursementTypes.add(reimbursementType);
	    }
	    return reimbursementTypes;
	}
	catch (SQLException e)
	{
	    logger.error("Could not get reimbursementType", e);
	}
	return new HashSet<>();
    }

}
