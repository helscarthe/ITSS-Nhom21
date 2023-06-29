package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:main.db");
			return conn;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
