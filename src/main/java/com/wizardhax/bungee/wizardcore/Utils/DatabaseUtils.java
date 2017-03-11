package com.wizardhax.bungee.wizardcore.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DatabaseUtils {
	public String uri, password, username;
	public Connection connection;
	
	public DatabaseUtils(String uri, String username, String password) {
		this.uri = uri;
		this.password = password;
		this.username = username;
		
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://" + uri + "?autoReconnect=true", username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection get() {
		return this.connection;
	}
	
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean createLogin(String code, String uuid, String username) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		try {
			PreparedStatement stmt = get().prepareStatement("INSERT INTO `mojang_login_codes` (`username`, `uuid`, `code`, `used`, `created_at`, `updated_at`) VALUES ('" + username + "', '" + uuid + "', '" + code + "', 0, '" + time + "', '" + time + "')");
			stmt.executeUpdate();
			stmt.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String createCode() {
		String random = RandomStringUtils.randomAlphanumeric(10);
		
		while (this.codeExists(random)) {
			random = RandomStringUtils.randomAlphanumeric(10);
		}
		
		return random;
	}
	
	public boolean codeExists(String code) {
		boolean exists = false;
		
		try {
			PreparedStatement stmt = get().prepareStatement("SELECT * FROM `mojang_login_codes` WHERE `code` = '" + code + "'");
			ResultSet set = stmt.executeQuery();
			
			if(set.first())
				exists = true;
			
			set.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exists;
	}
}
