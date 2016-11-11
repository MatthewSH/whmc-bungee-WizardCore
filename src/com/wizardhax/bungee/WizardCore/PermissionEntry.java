package com.wizardhax.bungee.WizardCore;

import java.util.ArrayList;
import java.util.List;

public class PermissionEntry {

	public PermissionEntry() {
		groups = new ArrayList<>();
	}

	private List<Group> groups;

	public List<Group> getGroups() {
		return groups;
	}

	public static class Group {
		public Group(int id) {
			this.id = id;
			permissions = new ArrayList<>();
		}

		private int id;
		private List<Permission> permissions;

		public int getId() {
			return id;
		}

		public List<Permission> getPermissions() {
			return permissions;
		}

		@Override
		public String toString() {
			return "{" + id + ":" + getPermissions() + "}";
		}
	}

	public static class Permission {
		public Permission(String permission, boolean positive) {
			this.permission = permission;
			this.positive = positive;
		}

		@Override
		public String toString() {
			return (positive ? "+" : "-") + permission;
		}

		private String permission;
		private boolean positive;

		public String getPerm() {
			return permission;
		}

		public boolean isPositive() {
			return positive;
		}

	}

}
