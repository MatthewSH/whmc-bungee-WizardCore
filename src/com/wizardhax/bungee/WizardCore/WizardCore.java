package com.wizardhax.bungee.WizardCore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import com.wizardhax.bungee.WizardCore.command.CommandLobby;
import com.wizardhax.bungee.WizardCore.command.RankReload;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class WizardCore extends Plugin {

	private static WizardCore plugin;

	public Config config;

	@Override
	public void onEnable() {
		try {
			plugin = this;

			try {
				// new LibManager(this, new String[] {"JDA.jar"});
			} catch (Throwable e) {
				e.printStackTrace();
			}

			config = new Config(this);
			config.loadConfig();

			// Registry
			BungeeCord.getInstance().getPluginManager().registerListener(this, new Events());

			// Commands
			BungeeCord.getInstance().getPluginManager().registerCommand(this, new CommandLobby());
			BungeeCord.getInstance().getPluginManager().registerCommand(this, new RankReload());

			SQLLoader.loadfromServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setFinalStatic(Field field, Object newValue, Object other) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(other, newValue);
	}

	public static WizardCore getPlugin() {
		return plugin;
	}

	public Map<String, PermissionEntry> userPermissions;
}
