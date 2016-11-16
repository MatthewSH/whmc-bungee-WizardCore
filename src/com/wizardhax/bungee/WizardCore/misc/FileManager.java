package com.wizardhax.bungee.WizardCore.misc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.wizardhax.bungee.WizardCore.WizardCore;
import com.wizardhax.bungee.WizardCore.utils.FileStorage;

public class FileManager {

	public FileManager(WizardCore plugin) {
		this.plugin = plugin;
		try {
			load();
		} catch (IllegalArgumentException | IOException e) {
			System.err.println("Failed to load Config:");
			e.printStackTrace();
		}
	}

	WizardCore plugin;
	FileStorage storage_banned;
	// uuid, reason
	public Map<String, String> banned;

	public void saveBanned() {
		storage_banned.store("banned", banned);
	}

	@SuppressWarnings("unchecked")
	public void load() throws IllegalArgumentException, IOException {
		banned = new HashMap<>();
		storage_banned = new FileStorage(new File(plugin.getDataFolder().getPath(), "banned.dat"));
		Object ban = storage_banned.get("banned");
		if (ban != null && ban instanceof Map)
			banned = (Map<String, String>) ban;
	}

}
