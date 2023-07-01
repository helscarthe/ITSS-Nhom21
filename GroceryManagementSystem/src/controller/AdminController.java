package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import service.SqliteConnection;

public class AdminController {

	public void addUser(String username, String password, int isAdmin) {

    	
    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	String query = "insert into users (username, password_hash, is_admin) values "
    			+ "('" + username + "', '" + password + "', '" + isAdmin + "')";
    	Statement sttm = null;
		try {
			sttm = conn.createStatement();
			sttm.executeUpdate(query);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
