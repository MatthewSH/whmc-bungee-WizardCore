package com.wizardhax.bungee.WizardCore.command;

import com.wizardhax.bungee.WizardCore.misc.SQLLoader;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class RankReload extends Command {

	public RankReload() {
		super("rankreload");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		SQLLoader.loadfromServer();
	}

	@Override
	public String getPermission() {
		return "wizardbungee.command.rankreload";
	}

}
