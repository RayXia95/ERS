package com.revature.service;

import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.EmployeeToken;
import com.revature.repository.EmployeeDAO;

public class EmployeeServiceImpl implements EmployeeService
{
    private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);
    private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    private EmployeeServiceImpl()
    {
    }

    public static EmployeeServiceImpl getEmployeeServiceImpl()
    {
	return employeeService;
    }

    @Override
    public Employee authenticate(Employee employee)
    {
	Employee loggedEmployee = EmployeeDAO.getEmployeeDAO().select(employee.getUsername().toUpperCase());

	if ( loggedEmployee.getPassword().equals(EmployeeDAO.getEmployeeDAO().getPasswordHash(employee)) )
	{
	    logger.trace("Authenicated");
	    return loggedEmployee;
	}

	logger.trace("Not Authenicated");
	return null;
    }

    @Override
    public Employee getEmployeeInformation(Employee employee)
    {
	return EmployeeDAO.getEmployeeDAO().select(employee.getUsername());
    }

    @Override
    public Set<Employee> getAllEmployeesInformation()
    {
	return EmployeeDAO.getEmployeeDAO().selectAll();
    }

    @Override
    public boolean createEmployee(Employee employee)
    {
	return EmployeeDAO.getEmployeeDAO().insert(employee);
    }

    @Override
    public boolean updateEmployeeInformation(Employee employee)
    {
	return EmployeeDAO.getEmployeeDAO().update(employee);
    }

    @Override
    public boolean updatePassword(Employee employee)
    {
	return EmployeeDAO.getEmployeeDAO().update(employee);
    }

    @Override
    public boolean isUsernameTaken(Employee employee)
    {
	if ( EmployeeDAO.getEmployeeDAO().select(employee.getUsername()) != null )
	{
	    logger.error("Username is taken");
	    return true;
	}
	else
	{
	    logger.info("Username is not taken");
	    return false;
	}
    }

    @Override
    public boolean createPasswordToken(Employee employee)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean deletePasswordToken(EmployeeToken employeeToken)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isTokenExpired(EmployeeToken employeeToken)
    {
	// TODO Auto-generated method stub
	return false;
    }

}
