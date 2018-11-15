package com.Allu.slinkoncraft;

import org.bukkit.plugin.java.JavaPlugin;
 
public class Slinkoncraftmenu extends JavaPlugin {

	public static Slinkoncraftmenu plugin;
	
	Menu menu;
	
    public void onEnable() {
    	
    	Slinkoncraftmenu.plugin = this;
    	
    	menu = new Menu();
    	this.getServer().getPluginManager().registerEvents(menu, plugin);
    	
    	
    }	

        
}
