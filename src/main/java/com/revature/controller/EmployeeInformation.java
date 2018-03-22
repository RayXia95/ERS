package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

public class EmployeeInformation implements EmployeeInformationController
{
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
	// TODO Auto-generated method stub
	return null;
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
