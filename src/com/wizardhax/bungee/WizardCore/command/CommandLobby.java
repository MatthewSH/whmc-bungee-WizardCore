package com.wizardhax.bungee.WizardCore.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandLobby extends Command {

	public CommandLobby() {
		super("lobby");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
		p.connect(ProxyServer.getInstance().getServerInfo("lobby"));
	}

	@Override
	public String[] getAliases() {
		return new String[] { "hub", "l" };
	}
	
	@Override
	public String getPermission() {
		return "wizardbungee.command.lobby";
	}

}
