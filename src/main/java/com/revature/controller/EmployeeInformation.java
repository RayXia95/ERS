package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.model.Employee;
import com.revature.service.EmployeeServiceImpl;

public class EmployeeInformation implements EmployeeInformationController
{
    private static EmployeeInformation employeeInfo = new EmployeeInformation();

    private EmployeeInformation()
    {

    }

    public static EmployeeInformation getEmployeeInfoInstance()
    {
	return employeeInfo;
    }

    /**
     * Registers an employee.
     * 
     * This operation can only be performed by managers.
     * 
     * It should return a message stating whether the user was successfully
     * registered or not.
     */
    @Override
    public Object registerEmployee(HttpServletRequest request)
    {

	return null;
    }

    /**
     * Updates employee profile information.
     * 
     * It should return a message stating whether the user was successfully
     * updated or not.
     */
    @Override
    public Object updateEmployee(HttpServletRequest request)
    {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Returns information regarding a specific employee.
     * 
     * This operation can be performed by regular or manager employees.
     */
    @Override
    public Object viewEmployeeInformation(HttpServletRequest request)
    {
	Employee employee = (Employee) request.getSession().getAttribute("loggedEmployee");

	if ( employee == null )
	{
	    return "login.html";
	}

	if ( request.getParameter("fetch") == null )
	{
	    return "personal.html";
	}
	else
	{
	    return EmployeeServiceImpl.getEmployeeServiceImpl().getEmployeeInformation(employee);
	}
    }

    /**
     * Returns a collection of employees.
     * 
     * This operation can only be performed by managers.
     */
    @Override
    public Object viewAllEmployees(HttpServletRequest request)
    {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Returns a message stating if the username is available or not.
     */
    @Override
    public Object usernameExists(HttpServletRequest request)
    {
	// TODO Auto-generated method stub
	return null;
    }

}
