package com.wizardhax.bungee.wizardcore;

import java.io.File;
import java.io.IOException;

import com.wizardhax.bungee.wizardcore.Commands.CapesCommand;
import com.wizardhax.bungee.wizardcore.Commands.LobbyCommand;
import com.wizardhax.bungee.wizardcore.Utils.DatabaseUtils;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class WizardCore extends Plugin {
	private static WizardCore instance;
	private DatabaseUtils databaseUtils = null;
	
	public static WizardCore getInstance() {
		return instance;
	}
	
	public static Configuration getConfig() {
		try {
			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getInstance().getDataFolder().getPath(), "config.yml"));
		} catch (IOException e) {
			getInstance().getLogger().severe("Could not get the configuration file.");
			return null;
		}
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		this.saveConfig();
				
		this.getProxy().getPluginManager().registerCommand(getInstance(), new LobbyCommand());
		
		if(getConfig().getBoolean("use-capesapi")) {
			this.databaseUtils = new DatabaseUtils(getConfig().getString("capesapi.database.uri"), getConfig().getString("capesapi.database.username"), getConfig().getString("capesapi.database.password"));
			this.getProxy().getPluginManager().registerCommand(getInstance(), new CapesCommand());
		}
		
		// TODO: Add rules command for 1.2.0
				
		super.onEnable();
	}
	
	public DatabaseUtils getDatabase() {
		return this.databaseUtils;
	}
	
	
	private void saveConfig() {
		try {
			if(!this.getDataFolder().exists())
				this.getDataFolder().mkdir();
			
			File file = new File(this.getDataFolder().getPath(), "config.yml");
			
			if(!file.exists())
				file.createNewFile();
			
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			
			if(!config.contains("commands.lobby.delay"))
				config.set("commands.lobby.delay", 5);
			
			if(!config.contains("commands.lobby.message"))
				config.set("commands.lobby.message", "You will be teleported back to the lobby in {time} seconds.");
			
			if(!config.contains("use-capesapi"))
				config.set("use-capesapi", true);
			
			if(!config.contains("commands.capes.give-cape"))
				config.set("commands.capes.give-cape", false);
			
			if(!config.contains("commands.capes.cape-id"))
				config.set("commands.capes.cape-id", "id");
			
			if(!config.contains("capesapi.database.uri"))
				config.set("capesapi.database.uri", "localhost/database");
			
			if(!config.contains("capesapi.database.username"))
				config.set("capesapi.database.username", "username");
			
			if(!config.contains("capesapi.database.password"))
				config.set("capesapi.database.password", "password");
			
			if(!config.contains("debug"))
				config.set("debug", false);
			
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
			
		} catch (Exception e) {
			this.getLogger().severe("Configuration failed to save.");
			e.printStackTrace();
		}
	}
}
