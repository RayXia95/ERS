package com.revature.service;

import java.util.Set;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementType;
import com.revature.repository.ReimbursementDAO;

public class ReimbursementServiceImpl implements ReimbursementService
{
    private static ReimbursementServiceImpl reimbursementService = new ReimbursementServiceImpl();

    private ReimbursementServiceImpl()
    {

    }

    public ReimbursementServiceImpl getReimbursementService()
    {
	return reimbursementService;
    }

    @Override
    public boolean submitRequest(Reimbursement reimbursement)
    {
	return ReimbursementDAO.getReimbursementDAO().insert(reimbursement);
    }

    @Override
    public boolean finalizeRequest(Reimbursement reimbursement)
    {
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
