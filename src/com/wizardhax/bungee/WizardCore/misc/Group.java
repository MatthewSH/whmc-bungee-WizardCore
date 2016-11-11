package com.wizardhax.bungee.WizardCore.misc;

import java.util.ArrayList;
import java.util.List;

public class Group {
	public Group(int priority) {
		this.priority = priority;
		permissions = new ArrayList<>();
	}

	private int priority;
	private List<Permission> permissions;

	public List<Permission> getPermissions() {
		return permissions;
	}

	@Override
	public String toString() {
		return "{" + priority + ":" + getPermissions() + "}";
	}
}
