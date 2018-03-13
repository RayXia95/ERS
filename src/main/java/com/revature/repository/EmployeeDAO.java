package com.revature.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import com.revature.model.Employee;
import com.revature.model.EmployeeToken;
import com.revature.util.ErsRepositoryUtil;

public class EmployeeDAO implements EmployeeRepository
{

    @Override
    public boolean insert(Employee employee)
    {
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{

	}
	catch (SQLException e)
	{

	}
	return false;
    }

    @Override
    public boolean update(Employee employee)
    {
	// TODO Auto-generated method stub
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

}
