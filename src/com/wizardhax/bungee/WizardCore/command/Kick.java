package com.wizardhax.bungee.WizardCore.command;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Kick extends Command {

	public Kick() {
		super("kick");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length >= 2) {
			ProxiedPlayer kick = BungeeCord.getInstance().getPlayer(args[0]);
			if(kick==null || !kick.isConnected()) {
				sender.sendMessage(new TextComponent("[BungeeCore] " + args[0] + " is not online."));
				return;
			}
			String reason = "Kicked by an Operator\n";
			for (int i = 1; i < args.length; i++) {
				reason += args[i];
			}
			//kick.disconnect(new TextComponent(reason));
			sender.sendMessage(new TextComponent("[BungeeCore] Kicked " + kick.getDisplayName() + " for \n\"" + reason + "\"\n"));
		} else {
			sender.sendMessage(new TextComponent("[BungeeCore] /kick <Player> <Reason>"));
		}
	}

	@Override
	public String getPermission() {
		return "wizardbungee.command.kick";
	}

}
