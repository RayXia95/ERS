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

	Employee employee = (Employee) request.getSession().getAttribute("loggedEmployee");

	Reimbursement reimbursement = new Reimbursement(0, LocalDateTime.now(), null,
		(double) Double.parseDouble(request.getParameter("amount")), request.getParameter("description"),
		employee, null, null, new ReimbursementType((int) Integer.parseInt(request.getParameter("type_id")),
			request.getParameter("type")));

	if ( ReimbursementServiceImpl.getReimbursementService().submitRequest(reimbursement) )
	{
	    return new ClientMessage("Submission Successful");
	}
	else
	{
	    return new ClientMessage("Unable to Submmit");
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
