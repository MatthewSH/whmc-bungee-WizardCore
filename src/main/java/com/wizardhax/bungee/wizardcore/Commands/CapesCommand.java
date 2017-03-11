package com.wizardhax.bungee.wizardcore.Commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import com.wizardhax.bungee.wizardcore.WizardCore;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CapesCommand extends Command {
	TextComponent capesapiUrl;
	String message;
	Integer delay;
	String delayMessage;
	public HashMap<UUID, Long> ran;
	
	public CapesCommand() {
		super("capes", null, new String[] { "cape", "capeapi", "capesapi" });
		
		capesapiUrl = new TextComponent("CapesAPI Login - http://capesapi.com");
		capesapiUrl.setBold(true);
		capesapiUrl.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://capesapi.com/mojang/login"));
		capesapiUrl.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Goto the CapesAPI login page.").create() ) );
		message = "Your login code is " + ChatColor.BOLD + "" + ChatColor.AQUA +  "%code%" + ChatColor.RESET + ". This code can only be used once and is only active for 10 minutes.";
		delayMessage = "&bYou can only send a chat every &e%time% &bminutes.";
		delay = Integer.valueOf(5);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage("You must be a player to get a CapesAPI login.");
		}
		ProxiedPlayer p = (ProxiedPlayer) sender;
		Long timeNow = Long.valueOf(System.currentTimeMillis());
		
		if(this.ran.containsKey(p.getUniqueId())) {
			Long timeRan = this.ran.get(p.getUniqueId());
			
			if(timeRan.longValue() > timeNow.longValue()) {
				p.sendMessage(this.delayMessage.replaceAll("&", "§").replaceAll("%time%", this.delay.toString()));
				return;
			} else {
				this.ran.put(p.getUniqueId(), Long.valueOf(timeNow.longValue() + (this.delay.intValue() * 60000)));
			}
		} else {
			this.ran.put(p.getUniqueId(), Long.valueOf(timeNow.longValue() + (this.delay.intValue() * 60000)));
		}
		
		boolean giveCape = WizardCore.getConfig().getBoolean("commands.capes.give-cape");
		String capeId = WizardCore.getConfig().getString("commands.capes.cape-id");
		String username = p.getName();
		String uuid = p.getUniqueId().toString().replaceAll("-", "");
		String code = WizardCore.getInstance().getDatabase().createCode();
		//String message = "Your login code for the account %name% is %code%. This code can only be used once and is only active for 10 minutes.";
		
		if(WizardCore.getInstance().getDatabase().createLogin(code, uuid, username)) {
			String msg = message.replace("%name%", username).replace("%code%", code);
			
			p.sendMessage("----------");
			p.sendMessage(capesapiUrl);
			p.sendMessage(msg);
			p.sendMessage("----------");
			
			if(giveCape) {
				try {
					if(!this.hasCape(uuid)) {
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean hasCape(String uuid) throws MalformedURLException, IOException {
	    URL url = new URL("http://capesapi.com/api/v1/" + uuid + "/hasCape/8GuSVa");
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setRequestMethod("GET");
	    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    String inputLine;
	    StringBuffer response = new StringBuffer();
	
	    while ((inputLine = in.readLine()) != null) {
	      response.append(inputLine);
	    }
	    
	    in.close();
	    
	    int hasCape = Integer.parseInt(response.toString());
	    
	    return hasCape == 1 ? true : false;
	}
	
	private boolean addCape(String uuid, String capeId) {
		return true;
	}
}
