package com.wizardhax.bungee.wizardcore.Commands;

import java.util.concurrent.TimeUnit;

import com.wizardhax.bungee.wizardcore.WizardCore;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyCommand extends Command {

	public LobbyCommand() {
		super("lobby", null, new String[] { "hub", "l" });
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		final Integer delay = WizardCore.getConfig().getInt("commands.lobby.delay");
		final ProxiedPlayer player = (ProxiedPlayer)sender;
		
		String message = WizardCore.getConfig().getString("commands.lobby.message");
		
		WizardCore.getInstance().getProxy().getScheduler().schedule(WizardCore.getInstance(), new Runnable() {

			@Override
			public void run() {	
				player.connect(ProxyServer.getInstance().getServerInfo("lobby"));
			}
			
		}, delay.longValue(), TimeUnit.SECONDS);
		
		player.sendMessage(new TextComponent(message.replace("&", "§").replace("{time}", delay.toString())));
	}

}
