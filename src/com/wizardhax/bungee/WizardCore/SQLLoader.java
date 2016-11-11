package com.wizardhax.bungee.WizardCore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.wizardhax.bungee.WizardCore.PermissionEntry.Group;
import com.wizardhax.bungee.WizardCore.PermissionEntry.Permission;

public class SQLLoader {
	public static void loadfromServer() {
		System.out.println("loading Ranks1");
		WizardCore plugin = WizardCore.getPlugin();

		plugin.userPermissions = new HashMap<>();

		ResultSet memberships = MySQL.requestQuery("SELECT * FROM `memberships`");
		ResultSet inheritances = MySQL.requestQuery("SELECT * FROM `inheritances`");
		ResultSet permissions = MySQL.requestQuery("SELECT * FROM `entries`");
		
		System.out.println("loading Ranks2");
		try {
			// adding all members to map or adding already 2.group
			while (memberships.next()) {
				String member = memberships.getString("member");
				Group group_id = new Group(memberships.getInt("group_id"));
				//System.out.println("member:" + member + " group:" + group_id);
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
						int id = inheritances.getInt("parent_id");
						if (group.getId() == inheritances.getInt("child_id"))
							// &&!plugin.userPermissions.get(uuid).getGroups().contains(inheritances.getInt("parent_id"))
							groups.add(new Group(id));
					}
				}
			}
	
			System.out.println("loading Ranks4");
			// adding permissions (yeah)
			while (permissions.next()) {
				int id = permissions.getInt("entity_id");
				String perm = permissions.getString("permission");
				System.out.println(perm);
				boolean positive = permissions.getInt("value") == 1;

				for (String uuid : plugin.userPermissions.keySet()) {
					PermissionEntry entry = plugin.userPermissions.get(uuid);
					for (Group group : entry.getGroups()) {
						//System.out.println(group.getId() + " : " + id);
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
					return g2.getId() - g1.getId();
				});
			}
			
			for (String uuid : plugin.userPermissions.keySet()) {
				List<Group> groups = plugin.userPermissions.get(uuid).getGroups();
				System.out.println("uuid:" + uuid + " groups:" + groups);
			}
			
			

		} catch (SQLException e) {
			System.err.println("Failed to read from MySQL");
			e.printStackTrace();
		}

		System.out.println("Reloaded Ranks6");
		// sender.sendMessage(new TextComponent("[BungeeCore] reloaded"));
	}
}
