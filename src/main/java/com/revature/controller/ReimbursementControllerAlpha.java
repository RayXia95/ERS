package com.revature.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementType;
import com.revature.service.ReimbursementServiceImpl;
import com.revature.util.ReimbursementStatuses;
import com.revature.util.UserRoles;

public class ReimbursementControllerAlpha implements ReimbursementController
{
    private static final Logger logger = Logger.getLogger(ReimbursementControllerAlpha.class);
    private static ReimbursementControllerAlpha reimbursementController = new ReimbursementControllerAlpha();

    private ReimbursementControllerAlpha()
    {

    }

    public static ReimbursementControllerAlpha getReimbursementController()
    {
	return reimbursementController;
    }

    @Override
    public Object submitRequest(HttpServletRequest request)
    {
	if ( request.getMethod().equals("GET") )
	{
	    return "request.html";
	}

	Employee employee = (Employee) request.getSession().getAttribute("loggedEmployee");
	Employee manager = new Employee();
	manager.setId((int) Integer.parseInt(request.getParameter("manager")));

	Reimbursement reimbursement = new Reimbursement(0, LocalDateTime.now(), null,
		(double) Double.parseDouble(request.getParameter("amount")), request.getParameter("description"),
		employee, manager, null, new ReimbursementType((int) Integer.parseInt(request.getParameter("typeId")),
			request.getParameter("type").toUpperCase()));

	if ( ReimbursementServiceImpl.getReimbursementService().submitRequest(reimbursement) )
	{
	    return new ClientMessage("SUBMISSION SUCCESSFUL");
	}
	else
	{
	    return new ClientMessage("UNABLE TO SUBMIT");
	}
    }

    @Override
    public Object singleRequest(HttpServletRequest request)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object multipleRequests(HttpServletRequest request)
    {
	Employee employee = (Employee) request.getSession().getAttribute("loggedEmployee");

	if ( employee == null )
	{
	    return "login.html";
	}

	if ( request.getParameter("fetch") == null )
	{
	    return "reimbursement.html";
	}
	else if ( request.getParameter("fetch").equals("pending") )
	{
	    return ReimbursementServiceImpl.getReimbursementService().getUserPendingRequests(employee);
	}
	else if ( request.getParameter("fetch").equals("resolved") )
	{
	    return ReimbursementServiceImpl.getReimbursementService().getUserFinalizedRequests(employee);
	}

	return null;
    }

    @Override
    public Object finalizeRequest(HttpServletRequest request)
    {
	Employee employee = (Employee) request.getSession().getAttribute("loggedEmployee");

	if ( employee == null )
	{
	    return "login.html";
	}
	if ( request.getMethod() == "GET" )
	{
	    return "resolve.html";
	}

	Reimbursement reimbursement = new Reimbursement();

	if ( employee.getEmployeeRole().getId() == UserRoles.MANAGER_ROLE )
	{
	    if ( ReimbursementServiceImpl.getReimbursementService().finalizeRequest(reimbursement)
		    && reimbursement.getStatus().getStatus().equals(ReimbursementStatuses.APPROVED) )
	    {
		return new ClientMessage("Reimbursement has been approved");
	    }
	    else if ( ReimbursementServiceImpl.getReimbursementService().finalizeRequest(reimbursement)
		    && reimbursement.getStatus().getStatus().equals(ReimbursementStatuses.DECLINED) )
	    {
		return new ClientMessage("Reimbursement has been declined");
	    }
	}

	return new ClientMessage("Employee is not a manager");
    }

    @Override
    public Object getRequestTypes(HttpServletRequest request)
    {
	return null;
    }

}
