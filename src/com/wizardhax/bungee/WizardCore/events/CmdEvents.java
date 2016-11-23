package com.wizardhax.bungee.WizardCore.events;

import com.wizardhax.bungee.WizardCore.WizardCore;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CmdEvents implements Listener {

	@EventHandler
	public void onServerKickEvent(ChatEvent ev) {
		if(!((CommandSender) ev.getSender()).hasPermission("wizardbungee.bypass_cmd_blackList")) {
			if(WizardCore.getPlugin().config.cmd_blackList.contains(ev.getMessage())) {
				ev.setCancelled(true);
				((CommandSender)ev.getSender()).sendMessage(new TextComponent("You have no permission to use this command."));
			}
				
		}
	}
}
