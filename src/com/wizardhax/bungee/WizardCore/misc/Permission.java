package com.wizardhax.bungee.WizardCore.misc;

public class Permission {
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
