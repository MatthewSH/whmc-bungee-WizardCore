package com.wizardhax.bungee.WizardCore.events;

import java.util.List;

import com.wizardhax.bungee.WizardCore.WizardCore;
import com.wizardhax.bungee.WizardCore.misc.Permission;

import net.md_5.bungee.BungeeTitle;
import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Events implements Listener {

	@EventHandler
	public void onServerKickEvent(ServerKickEvent ev) {
		if (ev.getPlayer().hasPermission("wizardbungee.kicktolobby")) {
			WizardCore plugin = WizardCore.getPlugin();
			ServerInfo kickedFrom = null;
			if (ev.getPlayer().getServer() != null) {
				kickedFrom = ev.getPlayer().getServer().getInfo();
			} else if (plugin.getProxy().getReconnectHandler() != null) {
				kickedFrom = plugin.getProxy().getReconnectHandler().getServer(ev.getPlayer());
			} else {
				kickedFrom = AbstractReconnectHandler.getForcedHost(ev.getPlayer().getPendingConnection());
				if (kickedFrom == null) {
					kickedFrom = ProxyServer.getInstance().getServerInfo(
							ev.getPlayer().getPendingConnection().getListener().getServerPriority().get(0));
				}
			}
			ServerInfo kickTo = plugin.getProxy().getServerInfo("lobby");
			if ((kickedFrom != null) && (kickedFrom.equals(kickTo))) {
				return;
			}
			ev.setCancelled(true);
			ev.setCancelServer(kickTo);
			ev.getPlayer().sendMessage(new TextComponent("§4You have been connected to the lobby."));
			ev.getPlayer().sendMessage(new TextComponent(ev.getKickReasonComponent()));
			ev.getPlayer().sendTitle(new BungeeTitle().fadeIn(5).fadeOut(5).stay(40).subTitle(ev.getKickReasonComponent()));
		}
	}

	@EventHandler
	public void onPermissionCheckEvent(PermissionCheckEvent event) {
		if (!(event.getSender() instanceof ProxiedPlayer)) {
			return;
		}

		event.setHasPermission(false);

		if (!WizardCore.getPlugin().userMap
				.containsKey(((ProxiedPlayer) event.getSender()).getUniqueId().toString().replace("-", "")))
			return;

		String perm = event.getPermission();

		List<Integer> userperms = WizardCore.getPlugin().userMap
				.get(((ProxiedPlayer) event.getSender()).getUniqueId().toString().replace("-", ""));
		for (Integer group : userperms) {
			for (Permission permission : WizardCore.getPlugin().groupMap.get(group).getPermissions()) {
				//System.out.println((permission.isPositive() ? "+" : "-" + " ") + group + " " + permission.getPerm());
				if (permission.getPerm().equalsIgnoreCase(perm))
					event.setHasPermission(permission.isPositive());
			}
		}
		
		//TODO:  remove
		if(WizardCore.getPlugin().config.debug)
			event.setHasPermission(true);
	}
}
