package service;

import java.sql.Connection;
import java.sql.DriverManager;

import org.sqlite.SQLiteConfig;

//import org.sqlite.SQLiteConfig;

public class SqliteConnection {
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			String jdbcURL = "jdbc:sqlite:sqlite\\main.db?foreign_keys=true";
			
			SQLiteConfig config = new SQLiteConfig();
			config.setBusyTimeout(5000);
			
			Connection conn = DriverManager.getConnection(jdbcURL, config.toProperties());
//			Connection conn = DriverManager.getConnection(jdbcURL);
			return conn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            System.out.println("Connection to SQLite is fail.");

			return null;
		}
	}
}
