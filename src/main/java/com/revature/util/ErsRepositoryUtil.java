package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ErsRepositoryUtil
{
    private static final Logger logger = Logger.getLogger(ErsRepositoryUtil.class);
    private static ErsRepositoryUtil ersRepoUtil = new ErsRepositoryUtil();

    private ErsRepositoryUtil()
    {

    }

    public static ErsRepositoryUtil getErsRepositoryUtil()
    {
	return ersRepoUtil;
    }

    public Connection getConnection() throws SQLException
    {
	try
	{
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	}
	catch (ClassNotFoundException e)
	{
	    logger.error("Could not find my driver!", e);
	}

	final String CONNECTION_USERNAME = "REIMBURSEMENT_DB";
	final String CONNECTION_PASSWORD = "p4ssw0rd";
	final String URL = "jdbc:oracle:thin:@myrevaturerds.cyjl3lot33bp.us-east-1.rds.amazonaws.com:1521:ORCL";

	return DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
    }
}
