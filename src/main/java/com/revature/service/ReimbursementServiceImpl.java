package com.revature.service;

import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.exception.ReimbursementAmtLessThanZero;
import com.revature.exception.ReimbursementNotValidStatus;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementType;
import com.revature.repository.ReimbursementDAO;
import com.revature.util.ManangerIdUtil;
import com.revature.util.ReimbursementStatuses;

public class ReimbursementServiceImpl implements ReimbursementService
{

    private static final Logger logger = Logger.getLogger(ReimbursementServiceImpl.class);

    private static ReimbursementServiceImpl reimbursementService = new ReimbursementServiceImpl();

    private ReimbursementServiceImpl()
    {

    }

    public static ReimbursementServiceImpl getReimbursementService()
    {
	return reimbursementService;
    }

    @Override
    public boolean submitRequest(Reimbursement reimbursement)
    {
	try
	{
	    if ( reimbursement.getAmount() < 0 )
	    {
		throw new ReimbursementAmtLessThanZero();
	    }

	    if ( reimbursement.getApprover() == null )
	    {
		reimbursement.getApprover().setId(ManangerIdUtil.DEFAULT_MANAGER_ID);
	    }

	    return ReimbursementDAO.getReimbursementDAO().insert(reimbursement);
	}
	catch (ReimbursementAmtLessThanZero e)
	{
	    logger.error("Can not request Reimbursement of less than zero");
	}

	return false;
    }

    @Override
    public boolean finalizeRequest(Reimbursement reimbursement)
    {
	if ( reimbursement.getStatus().getId() < ReimbursementStatuses.PENDING
		& reimbursement.getStatus().getId() > ReimbursementStatuses.APPROVED )
	{
	    throw new ReimbursementNotValidStatus();
	}
	return ReimbursementDAO.getReimbursementDAO().update(reimbursement);
    }

    @Override
    public Reimbursement getSingleRequest(Reimbursement reimbursement)
    {
	return ReimbursementDAO.getReimbursementDAO().select(reimbursement.getId());
    }

    @Override
    public Set<Reimbursement> getUserPendingRequests(Employee employee)
    {
	return ReimbursementDAO.getReimbursementDAO().selectPending(employee.getId());
    }

    @Override
    public Set<Reimbursement> getUserFinalizedRequests(Employee employee)
    {
	return ReimbursementDAO.getReimbursementDAO().selectFinalized(employee.getId());
    }

    @Override
    public Set<Reimbursement> getAllPendingRequests()
    {
	return ReimbursementDAO.getReimbursementDAO().selectAllPending();
    }

    @Override
    public Set<Reimbursement> getAllResolvedRequests()
    {
	return ReimbursementDAO.getReimbursementDAO().selectAllFinalized();
    }

    @Override
    public Set<ReimbursementType> getReimbursementTypes()
    {
	return ReimbursementDAO.getReimbursementDAO().selectTypes();
    }

}
