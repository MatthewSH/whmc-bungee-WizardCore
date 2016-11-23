package com.wizardhax.bungee.WizardCore;

import java.util.List;
import java.util.Map;

import com.wizardhax.bungee.WizardCore.command.Ban;
import com.wizardhax.bungee.WizardCore.command.CommandLobby;
import com.wizardhax.bungee.WizardCore.command.Kick;
import com.wizardhax.bungee.WizardCore.command.Ping;
import com.wizardhax.bungee.WizardCore.command.RankReload;
import com.wizardhax.bungee.WizardCore.command.UnBan;
import com.wizardhax.bungee.WizardCore.events.BanEvents;
import com.wizardhax.bungee.WizardCore.events.CmdEvents;
import com.wizardhax.bungee.WizardCore.events.Events;
import com.wizardhax.bungee.WizardCore.misc.Config;
import com.wizardhax.bungee.WizardCore.misc.FileManager;
import com.wizardhax.bungee.WizardCore.misc.Group;
import com.wizardhax.bungee.WizardCore.misc.SQLLoader;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class WizardCore extends Plugin {

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

			fileManager = new FileManager(this);

			// Registry
			BungeeCord.getInstance().getPluginManager().registerListener(this, new Events());
			BungeeCord.getInstance().getPluginManager().registerListener(this, new BanEvents());
			BungeeCord.getInstance().getPluginManager().registerListener(this, new CmdEvents());

			// Commands
			BungeeCord.getInstance().getPluginManager().registerCommand(this, new CommandLobby());
			BungeeCord.getInstance().getPluginManager().registerCommand(this, new RankReload());
			BungeeCord.getInstance().getPluginManager().registerCommand(this, new Ban());
			BungeeCord.getInstance().getPluginManager().registerCommand(this, new UnBan());
			BungeeCord.getInstance().getPluginManager().registerCommand(this, new Kick());
			BungeeCord.getInstance().getPluginManager().registerCommand(this, new Ping());
			SQLLoader.loadfromServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {

	}

	public static WizardCore getPlugin() {
		return plugin;
	}

	// UUID permissions
	public Map<String, List<Integer>> userMap;

	// Group-id Group
	public Map<Integer, Group> groupMap;

	private static WizardCore plugin;

	public Config config;

	public FileManager fileManager;

}
