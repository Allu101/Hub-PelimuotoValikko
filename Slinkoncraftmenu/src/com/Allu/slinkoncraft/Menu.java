package com.Allu.slinkoncraft;
//tässä näkyy erilaiset importit, jotka saa kun painaa "ctrl" + shift + o (ei nolla vaan o)
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
 
//--HUOM!!! huomatkaa, että tähän pitää lisätä tuo "implements Listener", jos sitä ei ole siinä.--
public class Menu implements Listener {
 
	private ItemStack katka, survival, blockmaster, creative, pumpkinHunt;
	private ItemStack menuopenitem;
	
	//Tässä siis määritetään aluksi mitä itemeitä on ja millaisia ne on, eli mikä blockki se on ja mikä nimi.
	public Menu() {
		blockmaster = createItemWithTitle(Material.PURPUR_BLOCK, ChatColor.LIGHT_PURPLE + "Blockmaster");
		creative = createItemWithTitle(Material.CRAFTING_TABLE, ChatColor.BLUE + "Creative");
		katka = createItemWithTitle(Material.GOLD_BLOCK, ChatColor.YELLOW + "Katsojien kaupunki");
		pumpkinHunt = createItemWithTitle(Material.JACK_O_LANTERN, ChatColor.GOLD + "Pumpkin Hunt");
		survival = createItemWithTitle(Material.GRASS, ChatColor.GREEN + "Survival");
		menuopenitem = createItemWithTitle(Material.COMPASS, ChatColor.DARK_PURPLE + "Avaa valikko");
	}
	
	//Tämä metodi luo ne itemit (tuossa ylempänä siis kerrotaan tällä metodille minkälaisia itemeitä pitää luoda.
	private ItemStack createItemWithTitle(Material itemType, String title) {
		ItemStack is = new ItemStack(itemType, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(title);
		is.setItemMeta(meta);
		return is;
	}
	
	//Tämä metodi suoritetaan automaattisesti, kun pelaaja liittyy serverille.
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = (Player) event.getPlayer();
		p.getInventory().clear();
       	p.getInventory().setItem(0, menuopenitem);
	}
	
	//Tämä metodi katsoo, jos pelaaja klikkaa tässä määritettyä valikonavaus itemiä ja sitten kutsuu *OpenMenu* metodia
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
		if (handItem.equals(menuopenitem) &&
			(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			openMenu(event.getPlayer());
		}
	}
	
	//Kun tätä metodia kutsutaan niin tämä kutsuu "createservermenu" metodia, joka luo sen ja tämä metodi avaan sen kyseiselle pelaajalle.
	public void openMenu(Player player) {
		player.openInventory(createservermenu(player));	
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		Player player = (Player)event.getWhoClicked();
		handleInventoryClickEvent(item, player, null);
	}
	
	//Tässä katsotaan, että mitä itemiä pelaaja on klikannut ja suoritetaan asiat sen mukaan.
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
	
	//Tämä metodi estää hotbaarissa olevan tietyn itemin siirtämisen.
    @EventHandler
    public void onInv(InventoryClickEvent e) {
        e.getWhoClicked();
        if(e.getCurrentItem().getType() == Material.COMPASS) {
            e.setCancelled(true);
        }
    }
	
	//Tässä luodaan se itse inventory, eli mikä itemi on missäkin kohtaa ja määritetään sen nimi ja koko.
	private Inventory createservermenu(Player player) {
		Inventory inv = Slinkoncraftmenu.plugin.getServer().createInventory(null, 27, "Valitse pelimuoto");
		
		inv.setItem(11, katka);
		inv.setItem(13, pumpkinHunt);
		inv.setItem(15, survival);
		inv.setItem(19, blockmaster);
		inv.setItem(21, creative);
	
		return inv;
	}
	
	//tämä metodi estää sen, että pelaaja ei pysty pudottamaan hotbaarissa olevaa tiettyä itemiä.
	@EventHandler
	public void playerDropItem(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().getType() == Material.COMPASS) {
			event.setCancelled(true);
		}
	}
}
