package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			String jdbcURL = "jdbc:sqlite:sqlite\\main.db?foreign_keys=true";
			Connection conn = DriverManager.getConnection(jdbcURL);
			
            System.out.println("Connection to SQLite has been established.");

			return conn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            System.out.println("Connection to SQLite is fail.");

			return null;
		}
	}
}
