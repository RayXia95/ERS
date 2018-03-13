package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.model.EmployeeToken;
import com.revature.util.ErsRepositoryUtil;

public class EmployeeDAO implements EmployeeRepository
{
    private static final Logger logger = Logger.getLogger(EmployeeDAO.class);

    private static EmployeeDAO employeeDAO = new EmployeeDAO();

    private EmployeeDAO()
    {
    }

    public EmployeeDAO getEmployeeDAO()
    {
	return employeeDAO;
    }

    @Override
    public boolean insert(Employee employee)
    {
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    logger.trace("Was able to get connection to insert");
	    final String SQL = "INSERT INTO USER_T VALUES(NULL,?,?,?,?,?,?)";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    int parameterIndex = 0;

	    statement.setString(++parameterIndex, employee.getFirstName());
	    statement.setString(++parameterIndex, employee.getLastName());
	    statement.setString(++parameterIndex, employee.getUsername());
	    statement.setString(++parameterIndex, employee.getPassword());
	    statement.setString(++parameterIndex, employee.getEmail());
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
    public boolean update(Employee employee)
    {
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    /**
	    *	U_ID
	    	U_FIRSTNAME
	    	U_LASTNAME
	    	U_USERNAME
	    	U_PASSWORD
	    	U_EMAIL
	    	UR_ID 
	    */
	    final String SQL = "UPDATE USER_T SET U_FIRSTNAME = ?, U_LASTNAME = ?, U_USERNAME = ? , U_PASSWORD = ?, U_EMAIL = ?, UR_ID = ? WHERE U_ID =  ?";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    int parameterIndex = 0;

	    statement.setString(++parameterIndex, employee.getFirstName());
	    statement.setString(++parameterIndex, employee.getLastName());
	    statement.setString(++parameterIndex, employee.getUsername());
	    statement.setString(++parameterIndex, employee.getPassword());
	    statement.setString(++parameterIndex, employee.getEmail());
	    statement.setInt(++parameterIndex, employee.getEmployeeRole().getId());
	    statement.setInt(++parameterIndex, employee.getId());

	    if ( statement.executeUpdate() != 0 )
	    {
		logger.trace("Was able to update employee");
		return true;
	    }
	    else
	    {
		return false;
	    }
	}
	catch (SQLException e)
	{
	    logger.error("Was unable to update given employee", e);
	}
	return false;
    }

    @Override
    public Employee select(int employeeId)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Employee select(String username)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Employee> selectAll()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getPasswordHash(Employee employee)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean insertEmployeeToken(EmployeeToken employeeToken)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean deleteEmployeeToken(EmployeeToken employeeToken)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public EmployeeToken selectEmployeeToken(EmployeeToken employeeToken)
    {
	// TODO Auto-generated method stub
	return null;
    }

    public static void main(String[] args)
    {
	new EmployeeDAO().getEmployeeDAO().update(new Employee(21, "RAYMOND", "XIA", "RayXia95", "hello",
		"raymondxia95@gmail.com", new EmployeeRole(1, "EMPLOYEE")));
    }

}
