package com.wizardhax.bungee.WizardCore.command;

import java.util.concurrent.TimeUnit;

import com.wizardhax.bungee.WizardCore.WizardCore;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandLobby extends Command {

	public CommandLobby() {
		super("lobby");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		final ProxiedPlayer p = (ProxiedPlayer) sender;
		BungeeCord.getInstance().getScheduler().schedule(WizardCore.getPlugin(), () -> {
			p.connect(ProxyServer.getInstance().getServerInfo("lobby"));
		}, WizardCore.getPlugin().config.lobby_delay, TimeUnit.SECONDS);
		p.sendMessage(new TextComponent(
				"You'll get teleportet in the lobby in " + WizardCore.getPlugin().config.lobby_delay + " Seconds."));
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
