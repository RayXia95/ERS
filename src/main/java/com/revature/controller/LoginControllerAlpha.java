package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.model.Employee;
import com.revature.service.EmployeeServiceImpl;

public class LoginControllerAlpha implements LoginController
{

    @Override
    public String login(HttpServletRequest request)
    {
	if ( request.getMethod().equals("GET") )
	{
	    return "login.html";
	}

	Employee credentials = new Employee();
	credentials.setUsername(request.getParameter("username"));
	credentials.setPassword(request.getParameter("password"));

	Employee employee = EmployeeServiceImpl.getEmployeeServiceImpl().authenticate(credentials);

	if ( employee == null )
	{
	    return "login.html";
	}
	request.getSession().setAttribute("loggedEmployee", employee);

	return employee.toString();
    }

    @Override
    public String logout(HttpServletRequest request)
    {
	request.getSession().invalidate();
	return "login.html";
    }

}
