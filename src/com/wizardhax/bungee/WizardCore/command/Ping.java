package com.wizardhax.bungee.WizardCore.command;

import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Ping extends Command {

	public Ping() {
		super("ping");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(new TextComponent("[WizardCore] Your current Ping is " + ((UserConnection)sender).getPing()));
	}

	@Override
	public String getPermission() {
		return "wizardbungee.command.ping";
	}

}
