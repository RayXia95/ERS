package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.service.EmployeeServiceImpl;

public class LoginControllerAlpha implements LoginController
{
    private static LoginControllerAlpha loginController = new LoginControllerAlpha();

    private LoginControllerAlpha()
    {

    }

    public static LoginControllerAlpha getLoginController()
    {
	return loginController;
    }

    @Override
    public Object login(HttpServletRequest request)
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
	    return new ClientMessage("One or more of the fields is invalid");
	}

	request.getSession().setAttribute("loggedEmployee", employee);

	return employee;
    }

    @Override
    public String logout(HttpServletRequest request)
    {
	request.getSession().invalidate();
	return "login.html";
    }

}
