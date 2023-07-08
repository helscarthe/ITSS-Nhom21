package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.UserEntity;
import service.SqliteConnection;

public class AccountController {
	
	public UserEntity logIn(String username, String password) {
		
		Connection conn = SqliteConnection.Connector();
    	String query = "select * from users where username='" + username + "' and password_hash='" + password + "'";
    	Statement sttm = null;
    	ResultSet rs = null;
    	UserEntity ans = null;

		try {
			sttm = conn.createStatement();
			
			rs = sttm.executeQuery(query);
			
			if (rs.getObject("username") == null) {
				return null;
			}
			
			ans = new UserEntity(rs.getInt("user_id"), rs.getString("username"),
					rs.getString("password_hash"), rs.getBoolean("is_admin"));
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ans;
	}

	public void signUp(String username, String pass) {

    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	String query = "insert into users (username, password_hash, is_admin) "
    			+ "values ('" + username + "', '" + pass + "', false);";
    	Statement sttm = null;
		
    	// execute query
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

	public void updatePassword(String username, String pass) {

    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	String query = "insert into users (username, password_hash, is_admin) "
    			+ "values ('" + username + "', '" + pass + "', false);";
    	Statement sttm = null;
    	query = "update users "
				+ "set password_hash = '" + pass + "'"
				+ "where username = '" + username + "';";
		
    	// execute query
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
