package com.wizardhax.bungee.WizardCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.wizardhax.bungee.WizardCore.PermissionEntry.Group;
import com.wizardhax.bungee.WizardCore.PermissionEntry.Permission;

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

	public static void loadfromServer() {
		System.out.println("loading Ranks1");
		WizardCore plugin = WizardCore.getPlugin();

		plugin.userPermissions = new HashMap<>();

		// ResultSet prefixes = MySQL.requestQuery("SELECT * FROM `metadata`");
		ResultSet memberships = MySQL.requestQuery("SELECT * FROM `memberships`");
		ResultSet inheritances = MySQL.requestQuery("SELECT * FROM `inheritances`");
		ResultSet permissions = MySQL.requestQuery("SELECT * FROM `entries`");
		System.out.println("loading Ranks2");
		try {
			// adding all members to map or adding already 2.group
			while (memberships.next()) {
				String member = memberships.getString("member");
				Group group_id = new Group(memberships.getInt("group_id"));

				if (plugin.userPermissions.containsKey(member)) {
					plugin.userPermissions.get(member).getGroups().add(group_id);
				} else {
					PermissionEntry entry = new PermissionEntry();
					entry.getGroups().add(group_id);
					plugin.userPermissions.put(member, entry);
				}
			}
			System.out.println("loading Ranks3");
			// adding n. Group
			while (inheritances.next()) {
				for (String uuid : plugin.userPermissions.keySet()) {
					List<Group> groups = plugin.userPermissions.get(uuid).getGroups();
					// process collection
					for (int i = 0; i < groups.size(); i++) {
						Group group = groups.get(i);
						if (group.getId() == inheritances.getInt("child_id"))
							// &&!plugin.userPermissions.get(uuid).getGroups().contains(inheritances.getInt("parent_id"))
							groups.add(new Group(inheritances.getInt("parent_id")));
					}
				}
			}
			System.out.println("loading Ranks4");
			// adding permissions (yeah)
			while (permissions.next()) {
				int id = permissions.getInt("Id");
				String perm = permissions.getString("permission");
				boolean positive = permissions.getInt("value") == 1;

				for (String uuid : plugin.userPermissions.keySet()) {
					PermissionEntry entry = plugin.userPermissions.get(uuid);
					for (Group group : entry.getGroups()) {
						if (group.getId() == id)
							group.getPermissions().add(new Permission(perm, positive));
					}
				}
			}
			
			System.out.println("loading Ranks5");

			// sort groups
			for (String uuid : plugin.userPermissions.keySet()) {
				PermissionEntry entry = plugin.userPermissions.get(uuid);
				Collections.sort(entry.getGroups(), (Group g1, Group g2) -> {
					return g1.getId() - g2.getId();
				});
			}

		} catch (SQLException e) {
			System.err.println("Failed to read from MySQL");
			e.printStackTrace();
		}

		System.out.println("Reloaded Ranks6");
		// sender.sendMessage(new TextComponent("[BungeeCore] reloaded"));
	}
}
