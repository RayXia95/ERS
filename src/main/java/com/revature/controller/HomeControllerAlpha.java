package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.model.Employee;
import com.revature.util.UserRoles;

public class HomeControllerAlpha implements HomeController
{
    private static HomeControllerAlpha homeController = new HomeControllerAlpha();

    private HomeControllerAlpha()
    {
    }

    public static HomeControllerAlpha getHomeController()
    {
	return homeController;
    }

    @Override
    public String showEmployeeHome(HttpServletRequest request)
    {
	Employee loggedEmployee = (Employee) request.getSession().getAttribute("loggedEmployee");

	if ( loggedEmployee == null )
	{
	    return "login.html";
	}

	if ( loggedEmployee.getEmployeeRole().getId() == UserRoles.EMPLOYEE_ROLE )
	{
	    return "home.html";
	}
	else
	{
	    return "managerHome.html";
	}
    }

}
