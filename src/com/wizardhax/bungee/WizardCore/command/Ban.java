package com.wizardhax.bungee.WizardCore.command;

import com.wizardhax.bungee.WizardCore.WizardCore;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Ban extends Command {

	public Ban() {
		super("ban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length >= 1) {
			ProxiedPlayer ban = BungeeCord.getInstance().getPlayer(args[0]);
			String reason = "";
			for (int i = 1; i < args.length; i++) {
				reason += args[i];
			}
			WizardCore.getPlugin().fileManager.banned.put(ban.getUniqueId().toString().replace("-", ""), reason);
			sender.sendMessage(new TextComponent("Banned " + ban.getDisplayName() + " for " + reason));
			WizardCore.getPlugin().fileManager.saveBanned();
		}
	}

	@Override
	public String getPermission() {
		return "wizardbungee.command.ban";
	}

}
