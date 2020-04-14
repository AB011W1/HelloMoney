package com.barclays.hellomoney.client;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DBUtility {
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@wiid.wload.global:35610:SGDCRTD1";
	private static final String DB_USER = "SSASHM";
	private static final String DB_PASSWORD = "MASPC78#_L";
	static ReadExcel_C_INTEREST_RATE_MST inputExcelKeys = new ReadExcel_C_INTEREST_RATE_MST();
	static Connection dbConnection = null;
	static PreparedStatement preparedStatement = null;
    static void prepareDBConnection() throws SQLException{
    	dbConnection = getDBConnection();
    	dbConnection.setAutoCommit(false);
    }


	private static void executeAnySql(String selectSQL) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		  selectSQL = "SELECT  *  from C_MESSAGE_RES_MST a,C_MESSAGE_MST b WHERE a.system_id= b.system_id and a.business_id=b.business_id and a.message_key= b.message_key ";
		int count = 0;
		ResultSet rs = null;
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			// execute select SQL stetement
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String key = rs.getString(5);
				if (inputExcelKeys.keyMap.containsKey(key)) {
					//System.out.println((count++) + " :=" + key);
					inputExcelKeys.keyMap.remove(key);
				}

			}
			//System.out.println("does not contain" + inputExcelKeys.keyMap);
		} catch (SQLException e) {

			//System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}
			if(null != rs)
				rs.close();
		}

	}
	public  static void commit() throws SQLException {
  		 dbConnection.commit();
  		 }
	public  static void executeAnySqlInsert(String selectSQL) throws SQLException {
  		Statement statement = dbConnection.createStatement();

		try {
			//selectRecordsFromTable();
			// execute insert SQL stetement
			statement.executeUpdate(selectSQL);
			//System.out.println("  succeed  :"+selectSQL);


		} catch (SQLException e) {
			//System.out.println("==================================");
			//System.out.println("INSERT QUERY FAILED because "+e.getMessage());
			//System.out.println(selectSQL);
			//System.out.println("==================================");

		} finally {

			if (statement != null) {
				statement.close();
			}


		}

	}
	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			//System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
                             DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			//System.out.println(e.getMessage());

		}

		return dbConnection;

	}
}