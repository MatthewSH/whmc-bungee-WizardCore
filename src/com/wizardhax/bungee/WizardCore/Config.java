package com.wizardhax.bungee.WizardCore;

import java.io.File;

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

			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configFile, file);

		} catch (Exception e) {
			System.err.println("Failed to loadConfig.");
			e.printStackTrace();
		}
	}
}
