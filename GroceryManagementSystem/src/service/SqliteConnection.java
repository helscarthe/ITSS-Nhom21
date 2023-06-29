package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			String jdbcURL = "jdbc:sqlite:C:\\Data\\demo.db?foreign_keys=true";
			Connection conn = DriverManager.getConnection(jdbcURL);
			return conn;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
