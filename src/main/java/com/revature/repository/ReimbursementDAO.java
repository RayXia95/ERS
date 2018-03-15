package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementType;
import com.revature.util.ErsRepositoryUtil;

public class ReimbursementDAO implements ReimbursementRepository
{
    private static final Logger logger = Logger.getLogger(ReimbursementDAO.class);

    private ReimbursementDAO()
    {
    }

    @Override
    public boolean insert(Reimbursement reimbursement)
    {
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    logger.trace("Was able to get connection to insert");
	    final String SQL = "INSERT INTO USER_T VALUES(NULL,?,?,?,?,?,?)";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    int parameterIndex = 0;

	    statement.setString(++parameterIndex, employee.getFirstName().toUpperCase());
	    statement.setString(++parameterIndex, employee.getLastName().toUpperCase());
	    statement.setString(++parameterIndex, employee.getUsername().toLowerCase());
	    statement.setString(++parameterIndex, employee.getPassword());
	    statement.setString(++parameterIndex, employee.getEmail().toLowerCase());
	    statement.setInt(++parameterIndex, employee.getEmployeeRole().getId());

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
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Reimbursement select(int reimbursementId)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Reimbursement> selectPending(int employeeId)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Reimbursement> selectFinalized(int employeeId)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Reimbursement> selectAllPending()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Reimbursement> selectAllFinalized()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<ReimbursementType> selectTypes()
    {
	// TODO Auto-generated method stub
	return null;
    }

}
