package com.revature.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
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
		Double.parseDouble(request.getParameter("amount")), request.getParameter("description"), employee,
		manager, null, new ReimbursementType((int) Integer.parseInt(request.getParameter("typeId")),
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

	if ( request.getParameter("fetch") == null && employee.getEmployeeRole().getId() == UserRoles.EMPLOYEE_ROLE )
	{
	    return "reimbursement.html";
	}
	else if ( request.getParameter("fetch") == null
		&& employee.getEmployeeRole().getId() == UserRoles.MANAGER_ROLE )
	{
	    return "managerReimbursement.html";
	}
	else if ( request.getParameter("fetch").equals("pending")
		&& employee.getEmployeeRole().getId() == UserRoles.EMPLOYEE_ROLE )
	{
	    return ReimbursementServiceImpl.getReimbursementService().getUserPendingRequests(employee);
	}
	else if ( request.getParameter("fetch").equals("resolved")
		&& employee.getEmployeeRole().getId() == UserRoles.EMPLOYEE_ROLE )
	{
	    return ReimbursementServiceImpl.getReimbursementService().getUserFinalizedRequests(employee);
	}
	else if ( request.getParameter("fetch").equals("pending")
		&& employee.getEmployeeRole().getId() == UserRoles.MANAGER_ROLE )
	{
	    return ReimbursementServiceImpl.getReimbursementService().getAllPendingRequests();
	}
	else if ( request.getParameter("fetch").equals("resolved")
		&& employee.getEmployeeRole().getId() == UserRoles.MANAGER_ROLE )
	{
	    return ReimbursementServiceImpl.getReimbursementService().getAllResolvedRequests();
	}
	else if ( request.getParameter("fetch").equals("specific")
		&& employee.getEmployeeRole().getId() == UserRoles.MANAGER_ROLE )
	{
	    Set<Reimbursement> reimbursements = new HashSet<>();
	    Employee targetEmployee = new Employee();
	    targetEmployee.setId(Integer.parseInt(request.getParameter("id")));
	    reimbursements
		    .addAll(ReimbursementServiceImpl.getReimbursementService().getUserPendingRequests(targetEmployee));
	    reimbursements.addAll(
		    ReimbursementServiceImpl.getReimbursementService().getUserFinalizedRequests(targetEmployee));
	    return reimbursements;
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
	reimbursement.setId(Integer.parseInt(request.getParameter("id")));
	reimbursement.setStatus(new ReimbursementStatus());
	reimbursement.getStatus().setId(ReimbursementStatuses.PENDING);

	if ( employee.getEmployeeRole().getId() == UserRoles.MANAGER_ROLE )
	{
	    if ( request.getParameter("fetch").equals("approve") )
	    {
		reimbursement.getStatus().setId(ReimbursementStatuses.APPROVED);
		reimbursement.getStatus().setStatus("APPROVED");
		ReimbursementServiceImpl.getReimbursementService().finalizeRequest(reimbursement);
	    }
	    else if ( request.getParameter("fetch").equals("deny") )
	    {
		reimbursement.getStatus().setId(ReimbursementStatuses.DECLINED);
		reimbursement.getStatus().setStatus("DECLINED");
		ReimbursementServiceImpl.getReimbursementService().finalizeRequest(reimbursement);
	    }
	}

	return null;
    }

    @Override
    public Object getRequestTypes(HttpServletRequest request)
    {
	return null;
    }

}
