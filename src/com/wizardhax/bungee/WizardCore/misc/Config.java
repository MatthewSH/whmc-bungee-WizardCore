package com.wizardhax.bungee.WizardCore.misc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.wizardhax.bungee.WizardCore.WizardCore;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {
	WizardCore plugin;

	public Config(WizardCore plugin) {
		this.plugin = plugin;
	}

	public String driver = "com.mysql.jdbc.Driver";
	public String url = "jdbc:mysql://127.0.0.1:3306/default";
	public String username = "root";
	public String password = "root";
	public int lobby_delay = 5;
	public List<String> cmd_blackList = new ArrayList<>();
	public String wizard_core = "§r[§4WizardCore§r]";
	public boolean debug = false;

	public void loadConfig() {
		try {
			if (!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
			}
			File file = new File(plugin.getDataFolder().getPath(), "config.yml");
			if (!file.exists()) {
				file.createNewFile();
			}
			Configuration configFile = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

			String driver = "driver";
			String url = "url";
			String username = "username";
			String password = "password";
			String lobby_delay = "lobby_delay";
			String cmd_blackList = "cmd_blackList";
			String wizard_core = "wizard_core";
			String debug = "debug";

			if (configFile.contains(driver))
				this.driver = configFile.getString(driver);
			configFile.set(driver, this.driver);

			if (configFile.contains(url))
				this.url = configFile.getString(url);
			configFile.set(url, this.url);

			if (configFile.contains(username))
				this.username = configFile.getString(username);
			configFile.set(username, this.username);

			if (configFile.contains(password))
				this.password = configFile.getString(password);
			configFile.set(password, this.password);

			if (configFile.contains(lobby_delay))
				this.lobby_delay = configFile.getInt(lobby_delay);
			configFile.set(lobby_delay, this.lobby_delay);
			
			if (configFile.contains(cmd_blackList))
				this.cmd_blackList = configFile.getStringList(cmd_blackList);
			configFile.set(cmd_blackList, this.cmd_blackList);
			
			if (configFile.contains(wizard_core))
				this.wizard_core = configFile.getString(wizard_core);
			configFile.set(wizard_core, this.wizard_core);
			
			if (configFile.contains(debug))
				this.debug = configFile.getBoolean(debug);
			configFile.set(debug, this.debug);

			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configFile, file);

		} catch (Exception e) {
			System.err.println("Failed to loadConfig.");
			e.printStackTrace();
		}
	}
}
