package com.wizardhax.bungee.WizardCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL {

	private static Connection conn = getConnection();

	public static boolean request(String request) {
		try {
			PreparedStatement create = conn.prepareStatement(request);
			create.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static ResultSet requestQuery(String request) {
		try {
			PreparedStatement create = conn.prepareStatement(request);
			return create.executeQuery();
		} catch (Exception e) {
			return null;
		}
	}

	private static Connection getConnection() {
		try {
			Class.forName(WizardCore.getPlugin().config.driver);

			Connection conn = DriverManager.getConnection(WizardCore.getPlugin().config.url,
					WizardCore.getPlugin().config.username, WizardCore.getPlugin().config.password);
			System.out.println("MySQL has been connected!");
			System.out.println(conn);
			return conn;
		} catch (Exception e) {
			System.err.println("Failed to connect to SQL-Server. Check config or Internet-connection");
			e.printStackTrace();
		}

		return null;
	}
}
