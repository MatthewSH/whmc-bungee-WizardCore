package com.wizardhax.bungee.WizardCore.events;

import com.wizardhax.bungee.WizardCore.WizardCore;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BanEvents implements Listener {

	@EventHandler
	public void onLogin(LoginEvent event) {
		String uuid = event.getConnection().getUniqueId().toString().replace("-", "");
		if (WizardCore.getPlugin().fileManager.banned.containsKey(uuid)) {
			event.setCancelled(true);
			event.setCancelReason(
					"You are banned from this Server!\n" + WizardCore.getPlugin().fileManager.banned.get(uuid));
		}
	}
}
