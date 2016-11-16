package com.wizardhax.bungee.WizardCore.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.wizardhax.bungee.WizardCore.WizardCore;
import com.wizardhax.bungee.WizardCore.utils.MySQL;

public class SQLLoader {
	public static void loadfromServer() {
		WizardCore plugin = WizardCore.getPlugin();

		plugin.userMap = new HashMap<>();
		plugin.groupMap = new HashMap<>();

		ResultSet groups = MySQL.requestQuery("SELECT * FROM `entities`");
		ResultSet memberships = MySQL.requestQuery("SELECT * FROM `memberships`");
		ResultSet inheritances = MySQL.requestQuery("SELECT * FROM `inheritances`");
		ResultSet permissions = MySQL.requestQuery("SELECT * FROM `entries`");

		try {
			// reading groups and putting in groupMap
			while (groups.next()) {
				int id = groups.getInt("id");
				int priority = groups.getInt("priority");

				// removed because it might be used to give perms to players
				// without a group
				// if (groups.getInt("is_group") == 1)
				plugin.groupMap.put(id, new Group(priority));
			}

			// adding all members to map
			while (memberships.next()) {
				String member = memberships.getString("member");
				int group_id = memberships.getInt("group_id");

				if (plugin.userMap.containsKey(member)) {
					// (n>1). Group
					plugin.userMap.get(member).add(group_id);
				} else {
					// 1. Group
					List<Integer> groupList = new ArrayList<>();
					groupList.add(group_id);
					// default-group
					groupList.add(1);
					plugin.userMap.put(member, groupList);
				}
			}

			// adding inheritances
			while (inheritances.next()) {
				int parent_id = inheritances.getInt("parent_id");
				int child_id = inheritances.getInt("child_id");

				for (String uuid : plugin.userMap.keySet()) {
					List<Integer> groupList = plugin.userMap.get(uuid);
					// process collection

					for (int i = 0; i < groupList.size(); i++) {
						Integer group = groupList.get(i);
						if (group == child_id && !groupList.contains(parent_id))
							groupList.add(parent_id);
					}
				}
			}

			// adding permissions (yeah)
			while (permissions.next()) {
				int id = permissions.getInt("entity_id");
				String perm = permissions.getString("permission");
				boolean positive = permissions.getInt("value") == 1;

				WizardCore.getPlugin().groupMap.get(id).getPermissions().add(new Permission(perm, positive));
			}

			// sort groups
			for (String uuid : plugin.userMap.keySet()) {
				List<Integer> entry = plugin.userMap.get(uuid);
				Collections.sort(entry, (Integer g1, Integer g2) -> {
					return g1 - g2;
				});
			}

		} catch (SQLException e) {
			System.err.println("Failed to read from MySQL");
			e.printStackTrace();
		}

		System.out.println("Reloaded Ranks");
		// sender.sendMessage(new TextComponent("[BungeeCore] reloaded"));
	}
}
