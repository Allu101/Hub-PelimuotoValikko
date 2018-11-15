package com.Allu.slinkoncraft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
 
public class Menu implements Listener {
 
	private ItemStack menuopenitem;
	
	private ItemStack katka, survival, blockmaster, creative, pumpkinHunt;
	
	public Menu() {
		
		blockmaster = createItemWithTitle(Material.PURPUR_BLOCK, ChatColor.LIGHT_PURPLE + "Blockmaster");
		creative = createItemWithTitle(Material.CRAFTING_TABLE, ChatColor.BLUE + "Creative");
		katka = createItemWithTitle(Material.GOLD_BLOCK, ChatColor.YELLOW + "Katsojien kaupunki");
		pumpkinHunt = createItemWithTitle(Material.JACK_O_LANTERN, ChatColor.GOLD + "Pumpkin Hunt");
		survival = createItemWithTitle(Material.GRASS, ChatColor.GREEN + "Survival");
		
		menuopenitem = createItemWithTitle(Material.COMPASS, ChatColor.DARK_PURPLE + "Avaa valikko");
	
	}
	
	private ItemStack createItemWithTitle(Material itemType, String title) {
		ItemStack is = new ItemStack(itemType, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(title);
		is.setItemMeta(meta);
		return is;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = (Player) event.getPlayer();
		p.getInventory().clear();
        p.getInventory().setItem(0, menuopenitem);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
		if (handItem.equals(menuopenitem) &&
				(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			openMenu(event.getPlayer());
		}
	}
	
	public void openMenu(Player player) {
		player.openInventory(createservermenu(player));	
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player player = (Player)event.getWhoClicked();
		handleInventoryClickEvent(item, player, null);
	}
	
	public void handleInventoryClickEvent(ItemStack is, Player player, PlayerInteractEvent event) {
		
		if (is.equals(katka)) {
			player.performCommand("katka");
		}
		else if (is.equals(pumpkinHunt)) {
			player.performCommand("pumpkinhunt");
		}
		else if (is.equals(survival)) {
			player.performCommand("survival");
		}
		else if (is.equals(blockmaster)) {
			player.performCommand("blockmaster");
		}
		else if (is.equals(creative)) {
			player.performCommand("creative");
		}
	}
	
    @EventHandler
    public void onInv(InventoryClickEvent e) {
	    e.getWhoClicked();
	    if(e.getCurrentItem().getType() == Material.COMPASS) {
	    	e.setCancelled(true);
	    }
    }
	
	private Inventory createservermenu(Player player) {
		Inventory inv = Slinkoncraftmenu.plugin.getServer().createInventory(null, 27, "Valitse pelimuoto");
		
		inv.setItem(11, katka);
		inv.setItem(13, pumpkinHunt);
		inv.setItem(15, survival);
		inv.setItem(19, blockmaster);
		inv.setItem(21, creative);
	
		return inv;
	}
	
	@EventHandler
	public void playerDropItem(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().getType() == Material.COMPASS) {
				event.setCancelled(true);
		}
	}
}