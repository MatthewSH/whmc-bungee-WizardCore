package com.wizardhax.bungee.WizardCore.command;

import java.net.URL;
import java.util.Scanner;

import com.wizardhax.bungee.WizardCore.WizardCore;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class UnBan extends Command {

	public UnBan() {
		super("unban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 1) {
			String uuid = "";
			try {
				URL url = new URL("https://mcapi.ca/uuid/player/" + args[0]);
				Scanner scanner = new Scanner(url.openStream());
				String line;
				while (!(line = scanner.nextLine()).contains("\"uuid\": \"")) {
				}
				uuid = line.split("\"uuid\": \"")[1].replace("-", "").replace("\",", "");
				scanner.close();
			} catch (Exception e) {
				sender.sendMessage(new TextComponent("Player " + args[0] + " not found."));
				return;
			}

			if (WizardCore.getPlugin().fileManager.banned.containsKey(uuid)) {
				WizardCore.getPlugin().fileManager.banned.remove(uuid);
				sender.sendMessage(new TextComponent("Unbanned " + args[0] + " [" + uuid + "]"));
			} else {
				sender.sendMessage(new TextComponent("Player " + args[0] + " not banned. [" + uuid + "]"));
			}
			WizardCore.getPlugin().fileManager.saveBanned();
		}
	}

	@Override
	public String getPermission() {
		return "wizardbungee.command.ban";
	}

}
