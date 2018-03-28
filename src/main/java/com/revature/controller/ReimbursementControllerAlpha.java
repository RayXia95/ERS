package com.revature.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementType;
import com.revature.service.ReimbursementServiceImpl;

public class ReimbursementControllerAlpha implements ReimbursementController
{
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

	//int id, LocalDateTime requested, LocalDateTime resolved, double amount, String description,
	//Employee requester, Employee approver, ReimbursementStatus status, ReimbursementType type
	// amount param, 
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
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object finalizeRequest(HttpServletRequest request)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object getRequestTypes(HttpServletRequest request)
    {
	// TODO Auto-generated method stub
	return null;
    }

}
