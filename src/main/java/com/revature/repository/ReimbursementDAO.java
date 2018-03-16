package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementType;
import com.revature.util.ErsRepositoryUtil;

public class ReimbursementDAO implements ReimbursementRepository
{
    private static final Logger logger = Logger.getLogger(ReimbursementDAO.class);

    private ReimbursementDAO()
    {
    }

    @Override
    public boolean insert(Reimbursement reimbursement)
    {
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    /**
	     * Id int null
	     * LOCALDATETIME Requested
	     * LOCALDATETIME Resolved
	     * amount double
	     * String description
	     * receipt object null
	     * Employee requester
	     * Employee approver
	     * ReimbursementStatus status
	     * ReimbursementType type
	     * 
	     * 	
	     */
	    logger.trace("Was able to get connection to insert");
	    final String SQL = "INSERT INTO REIMBURSEMENT_T VALUES(NULL,?,?,?,?, NULL,?,?,?,?)";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    int parameterIndex = 0;

	    statement.setTimestamp(++parameterIndex, Timestamp.valueOf(reimbursement.getRequested()));
	    statement.setTimestamp(++parameterIndex, Timestamp.valueOf(reimbursement.getResolved()));
	    statement.setDouble(++parameterIndex, reimbursement.getAmount());
	    statement.setString(++parameterIndex, reimbursement.getDescription());
	    //statement.setBlob(++parameterIndex, (Blob) reimbursement.getRequester());
	    statement.setInt(++parameterIndex, reimbursement.getRequester().getId());
	    statement.setInt(++parameterIndex, reimbursement.getApprover().getId()); // manager ID???
	    statement.setInt(++parameterIndex, reimbursement.getStatus().getId());
	    statement.setInt(++parameterIndex, reimbursement.getType().getId());

	    if ( statement.executeUpdate() != 0 )
	    {
		logger.trace("Was able to insert");
		return true;
	    }
	    else
	    {
		return false;
	    }

	}
	catch (SQLException e)
	{
	    logger.error("Unable register user, Probably unique id violated", e);
	}
	return false;
    }

    @Override
    public boolean update(Reimbursement reimbursement)
    {
	/**
	 * R_ID	NUMBER
	    R_REQUESTED	TIMESTAMP(6)
	    R_RESOLVED	TIMESTAMP(6)	//
	    R_AMOUNT	NUMBER(8,2)	//
	    R_DESCRIPTION	VARCHAR2(4000 BYTE) //
	    R_RECEIPT	BLOB
	    EMPLOYEE_ID	NUMBER
	    MANAGER_ID	NUMBER	//
	    RS_ID	NUMBER	//
	    RT_ID	NUMBER	//
	 */
	try (Connection connection = ErsRepositoryUtil.getErsRepositoryUtil().getConnection())
	{
	    final String SQL = "UPDATE REIMBURSEMENT SET R_RESOLVED = ?, R_AMOUNT = ?, R_DESCRIPTION = ?, MANAGER_ID = ?, RS_ID = ?, RT_ID = ?";
	    PreparedStatement statement = connection.prepareStatement(SQL);
	    int parameterIndex = 0;

	    statement.setTimestamp(++parameterIndex, Timestamp.valueOf(reimbursement.getResolved()));
	    statement.setDouble(++parameterIndex, reimbursement.getAmount());
	    statement.setString(++parameterIndex, reimbursement.getDescription());
	    statement.setInt(++parameterIndex, reimbursement.getApprover().getId());
	    statement.setInt(++parameterIndex, reimbursement.getStatus().getId());
	    statement.setInt(++parameterIndex, reimbursement.getType().getId());

	    if ( statement.executeUpdate() != 0 )
	    {
		return true;
	    }
	    else
	    {
		return false;
	    }
	}
	catch (SQLException e)
	{
	    logger.error("Was unable to update", e);
	}
	return false;
    }

    @Override
    public Reimbursement select(int reimbursementId)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Reimbursement> selectPending(int employeeId)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Reimbursement> selectFinalized(int employeeId)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Reimbursement> selectAllPending()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<Reimbursement> selectAllFinalized()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<ReimbursementType> selectTypes()
    {
	// TODO Auto-generated method stub
	return null;
    }

}
