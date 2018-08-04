package br.com.edr.cinema.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionFactory {
	
	public Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/dbjava2";
		
		try {
			return (Connection) DriverManager.getConnection(url, "root", "root");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
