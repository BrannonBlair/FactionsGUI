package toxxicjtag.com.github;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.Metrics;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;

public class FactionsGUI extends JavaPlugin implements Listener {
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new SpectateGUI(), this);
		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.AQUA + "[FactionsGUI 2.0] " + ChatColor.GREEN + "Successfully Started");
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[FactionsGUI 2.0] " + ChatColor.RED
				+ "This plugin is a work in progress! Please Post Bugs!");
		try {
			Metrics metrics = new Metrics();
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("fgui") || cmd.getName().equalsIgnoreCase("factionsgui")) {
			if (sender.hasPermission("factionsgui.gui")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					openGUI(player);
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You don't have access to this command.");
			}
		}
		return false;
	}

	public void openGUI(Player player) {
		ItemStack glassRed = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemMeta gRMeta = glassRed.getItemMeta();
		gRMeta.setDisplayName(" ");
		glassRed.setItemMeta(gRMeta);

		ItemStack glassBlack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta gBMeta = glassBlack.getItemMeta();
		gBMeta.setDisplayName(" ");
		glassBlack.setItemMeta(gBMeta);

		ItemStack information = new ItemStack(Material.ENCHANTMENT_TABLE);
		ItemMeta meta = information.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Information!");
		information.setItemMeta(meta);

		ItemStack status = new ItemStack(Material.BOOK);
		ItemMeta statusmeta = status.getItemMeta();
		statusmeta.setDisplayName(ChatColor.AQUA + "Status!");
		status.setItemMeta(statusmeta);

		ItemStack list = new ItemStack(Material.COMPASS);
		ItemMeta listmeta = list.getItemMeta();
		listmeta.setDisplayName(ChatColor.AQUA + "List Factions!");
		ArrayList<String> Lore66 = new ArrayList<String>();
		Lore66.add(ChatColor.RED + "Left Click For Chat Version");
		Lore66.add(ChatColor.YELLOW + "Right Click For GUI Menu");
		Lore66.add(ChatColor.BLUE + "  - Change Relations Inside");
		listmeta.setLore(Lore66);
		list.setItemMeta(listmeta);

		ItemStack home = new ItemStack(Material.BED);
		ItemMeta homemeta = home.getItemMeta();
		homemeta.setDisplayName(ChatColor.AQUA + "Home!");
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add("Left Click To Teleport.");
		Lore.add("Right Click For Home Menu.");
		homemeta.setLore(Lore);
		home.setItemMeta(homemeta);

		ItemStack map = new ItemStack(Material.MAP);
		ItemMeta mapmeta = map.getItemMeta();
		mapmeta.setDisplayName(ChatColor.AQUA + "Map!");
		ArrayList<String> Lore1 = new ArrayList<String>();
		Lore1.add("Left Click To View Map.");
		Lore1.add(ChatColor.DARK_PURPLE + "Right Click To Toggle Auto Map " + ChatColor.GREEN + "On " + ChatColor.WHITE
				+ "/ " + ChatColor.RED + "Off.");
		mapmeta.setLore(Lore1);
		map.setItemMeta(mapmeta);

		ItemStack invite = new ItemStack(Material.PAPER);
		ItemMeta invitemeta = invite.getItemMeta();
		invitemeta.setDisplayName(ChatColor.AQUA + "Membership: " + ChatColor.GREEN + "Offer " + ChatColor.WHITE + "/ "
				+ ChatColor.RED + "Revoke.");
		ArrayList<String> Lore2 = new ArrayList<String>();
		Lore2.add(ChatColor.GREEN + "Left Click To Invite A Player.");
		Lore2.add(ChatColor.DARK_RED + "Right Click To Revoke A Player's Membership.");
		invitemeta.setLore(Lore2);
		invite.setItemMeta(invitemeta);

		ItemStack kick = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta kickmeta = kick.getItemMeta();
		kickmeta.setDisplayName(ChatColor.DARK_RED + "Kick Member");
		ArrayList<String> Lore3 = new ArrayList<String>();
		kickmeta.setLore(Lore3);
		kick.setItemMeta(kickmeta);

		ItemStack perms = new ItemStack(Material.TNT);
		ItemMeta permsmeta = perms.getItemMeta();
		permsmeta.setDisplayName(ChatColor.YELLOW + "Permissions!");
		ArrayList<String> Lore11 = new ArrayList<String>();
		Lore11.add(ChatColor.GREEN + "Left Click To View Faction Permissions.");
		Lore11.add(ChatColor.DARK_RED + "Right Click To Edit Permissions.");
		permsmeta.setLore(Lore11);
		perms.setItemMeta(permsmeta);

		ItemStack playerinfo = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta playerinfometa = (SkullMeta) playerinfo.getItemMeta();
		playerinfometa.setOwner(player.getName());
		playerinfometa.setDisplayName(player.getName());
		playerinfo.setItemMeta(playerinfometa);
		playerinfometa.setDisplayName(ChatColor.RED + "Player Faction Stats");
		ArrayList<String> Lore12 = new ArrayList<String>();
		playerinfometa.setLore(Lore12);
		playerinfo.setItemMeta(playerinfometa);

		ItemStack prode = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta prodemeta = prode.getItemMeta();
		prodemeta.setDisplayName(ChatColor.YELLOW + "Promote/Demote Members");
		ArrayList<String> Lore13 = new ArrayList<String>();
		prodemeta.setLore(Lore13);
		prode.setItemMeta(prodemeta);

		ItemStack seechunks = new ItemStack(Material.WEB);
		ItemMeta seechunksmeta = seechunks.getItemMeta();
		seechunksmeta.setDisplayName(ChatColor.BLUE + "See Chunks");
		ArrayList<String> Lore77 = new ArrayList<String>();
		seechunksmeta.setLore(Lore77);
		seechunks.setItemMeta(seechunksmeta);

		ItemStack close = new ItemStack(Material.BARRIER);
		ItemMeta closemeta = close.getItemMeta();
		closemeta.setDisplayName(ChatColor.DARK_RED + "Close Menu!");
		ArrayList<String> Lore4 = new ArrayList<String>();
		closemeta.setLore(Lore4);
		close.setItemMeta(closemeta);

		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Factions GUI");
		inv.setItem(0, glassRed);
		inv.setItem(1, glassBlack);
		inv.setItem(2, glassRed);
		inv.setItem(3, glassBlack);
		inv.setItem(4, glassRed);
		inv.setItem(5, glassBlack);
		inv.setItem(6, glassRed);
		inv.setItem(7, glassBlack);
		inv.setItem(8, glassRed);
		inv.setItem(9, glassBlack);
		inv.setItem(10, information);
		inv.setItem(11, status);
		inv.setItem(12, list);
		inv.setItem(13, home);
		inv.setItem(14, map);
		inv.setItem(15, invite);
		inv.setItem(16, kick);
		inv.setItem(17, glassBlack);
		inv.setItem(18, glassRed);
		inv.setItem(19, perms);
		inv.setItem(20, playerinfo);
		inv.setItem(21, prode);
		inv.setItem(22, seechunks);
		inv.setItem(26, glassRed);
		inv.setItem(27, glassBlack);
		inv.setItem(34, close);
		inv.setItem(35, glassBlack);
		inv.setItem(36, glassRed);
		inv.setItem(37, glassBlack);
		inv.setItem(38, glassRed);
		inv.setItem(39, glassBlack);
		inv.setItem(40, glassRed);
		inv.setItem(41, glassBlack);
		inv.setItem(42, glassRed);
		inv.setItem(43, glassBlack);
		inv.setItem(44, glassRed);

		player.getPlayer().openInventory(inv);

	}

	public void openHomeGUI(Player player) {
		ItemStack glassBlue = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
		ItemMeta gBMeta = glassBlue.getItemMeta();
		gBMeta.setDisplayName("");
		glassBlue.setItemMeta(gBMeta);

		ItemStack glassBlack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta gBlMeta = glassBlack.getItemMeta();
		gBlMeta.setDisplayName("");
		glassBlack.setItemMeta(gBlMeta);

		ItemStack teleport = new ItemStack(Material.BED);
		ItemMeta tmeta = teleport.getItemMeta();
		tmeta.setDisplayName(ChatColor.AQUA + "Teleport!");
		teleport.setItemMeta(tmeta);

		ItemStack sethome = new ItemStack(Material.NETHER_STAR);
		ItemMeta sethomemeta = sethome.getItemMeta();
		sethomemeta.setDisplayName(ChatColor.AQUA + "Set Home!");
		sethome.setItemMeta(sethomemeta);

		ItemStack delhome = new ItemStack(Material.BARRIER);
		ItemMeta delhomemeta = delhome.getItemMeta();
		delhomemeta.setDisplayName(ChatColor.AQUA + "Delete Home!");
		delhome.setItemMeta(delhomemeta);

		ItemStack back = new ItemStack(Material.ARROW, -1);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack close = new ItemStack(Material.BARRIER);
		ItemMeta closemeta = close.getItemMeta();
		closemeta.setDisplayName(ChatColor.DARK_RED + "Close Menu!");
		ArrayList<String> Lore4 = new ArrayList<String>();
		closemeta.setLore(Lore4);
		close.setItemMeta(closemeta);

		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Home " + ChatColor.GREEN + "Menu");
		inv.setItem(0, glassBlue);
		inv.setItem(1, glassBlack);
		inv.setItem(2, glassBlue);
		inv.setItem(3, glassBlack);
		inv.setItem(4, glassBlue);
		inv.setItem(5, glassBlack);
		inv.setItem(6, glassBlue);
		inv.setItem(7, glassBlack);
		inv.setItem(8, glassBlue);
		inv.setItem(9, glassBlack);
		inv.setItem(17, glassBlack);
		inv.setItem(18, glassBlue);
		inv.setItem(20, teleport);
		inv.setItem(22, sethome);
		inv.setItem(24, delhome);
		inv.setItem(26, glassBlue);
		inv.setItem(27, glassBlack);
		inv.setItem(33, back);
		inv.setItem(34, close);
		inv.setItem(35, glassBlack);
		inv.setItem(36, glassBlue);
		inv.setItem(37, glassBlack);
		inv.setItem(38, glassBlue);
		inv.setItem(39, glassBlack);
		inv.setItem(40, glassBlue);
		inv.setItem(41, glassBlack);
		inv.setItem(42, glassBlue);
		inv.setItem(43, glassBlack);
		inv.setItem(44, glassBlue);

		player.getPlayer().openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		MPlayer msender = MPlayer.get(p);
		Faction faction = null;
		faction = msender.getFaction();
		if (e.getInventory().getTitle().equals(ChatColor.GREEN + "Factions GUI")) {
			if (e.getSlot() == 10) {
				if (faction == FactionColl.get().getByName("Wilderness")
						|| faction == FactionColl.get().getByName("None")) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.RED + "You aren't in a faction!");
				} else {
					e.setCancelled(true);
					p.performCommand("f f");
				}
			} else if (e.getSlot() == 11) {
				if (faction == FactionColl.get().getByName("Wilderness")
						|| faction == FactionColl.get().getByName("None")) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.RED + "You aren't in a faction!");
				} else {
					e.setCancelled(true);
					p.performCommand("f status");
				}
			} else if (e.getSlot() == 12) {
				if (e.isLeftClick()) {
					e.setCancelled(true);
					p.performCommand("f list");
				} else if (e.isRightClick()) {
					e.setCancelled(true);
					SpectateGUI listmenu = new SpectateGUI();
					listmenu.openListGUI(p);
				}
			} else if (e.getSlot() == 13) {
				if (e.isLeftClick()) {
					if (faction == FactionColl.get().getByName("Wilderness")
							|| faction == FactionColl.get().getByName("None")) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "You aren't in a faction!");
					} else {
						e.setCancelled(true);
						p.performCommand("f home");
						p.closeInventory();
					}
				} else if (e.isRightClick()) {
					if (faction == FactionColl.get().getByName("Wilderness")
							|| faction == FactionColl.get().getByName("None")) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "You aren't in a faction!");
					} else {
						e.setCancelled(true);
						p.closeInventory();
						openHomeGUI(p);
					}
				} else {
					e.setCancelled(true);
					return;
				}
			} else if (e.getSlot() == 14) {
				if (e.isLeftClick()) {
					e.setCancelled(true);
					p.performCommand("f map");
					p.closeInventory();
				} else if (e.isRightClick()) {
					e.setCancelled(true);
					if (!msender.isMapAutoUpdating()) {
						msender.setMapAutoUpdating(true);
						p.sendMessage(ChatColor.YELLOW + "Map auto update " + ChatColor.GREEN + "ENABLED.");
					} else {
						msender.setMapAutoUpdating(false);
						p.sendMessage(ChatColor.YELLOW + "Map auto update " + ChatColor.RED + "DISABLED.");
					}
				} else {
					e.setCancelled(true);
					return;
				}
			} else if (e.getSlot() == 15) {
				if (e.isLeftClick()) {
					if (faction == FactionColl.get().getByName("Wilderness")
							|| faction == FactionColl.get().getByName("None")) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "You aren't in a faction!");
					} else {
						e.setCancelled(true);
						SpectateGUI spectategui = new SpectateGUI();
						spectategui.openSpectateGUI(p);
					}
				} else if (e.isRightClick()) {
					if (faction == FactionColl.get().getByName("Wilderness")
							|| faction == FactionColl.get().getByName("None")) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "You aren't in a faction!");
					} else {
						e.setCancelled(true);
						SpectateGUI denygui = new SpectateGUI();
						denygui.openDenyGUI(p);
					}
				} else {
					e.setCancelled(true);
					return;
				}
			} else if (e.getSlot() == 16) {
				if (faction == FactionColl.get().getByName("Wilderness")
						|| faction == FactionColl.get().getByName("None")) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.RED + "You aren't in a faction!");
				} else {
					e.setCancelled(true);
					SpectateGUI spectategui = new SpectateGUI();
					spectategui.openKickGUI(p);
				}
			} else if (e.getSlot() == 19) {
				if (e.isLeftClick()) {
					if (faction == FactionColl.get().getByName("Wilderness")
							|| faction == FactionColl.get().getByName("None")) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "You aren't in a faction!");
					} else {
						e.setCancelled(true);
						p.performCommand("f perm show");
						p.closeInventory();
					}
				} else if (e.isRightClick()) {
					if (faction == FactionColl.get().getByName("Wilderness")
							|| faction == FactionColl.get().getByName("None")) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "You aren't in a faction!");
					} else {
						e.setCancelled(true);
						SpectateGUI permgui = new SpectateGUI();
						permgui.openPermGUI(p);
					}
				}
			} else if (e.getSlot() == 20) {
				e.setCancelled(true);
				p.performCommand("f p");
			} else if (e.getSlot() == 21) {
				if (faction == FactionColl.get().getByName("Wilderness")
						|| faction == FactionColl.get().getByName("None")) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.RED + "You aren't in a faction!");
				} else {
					e.setCancelled(true);
					SpectateGUI spectategui = new SpectateGUI();
					spectategui.openProDeGUI(p);
				}
			} else if (e.getSlot() == 22) {
				e.setCancelled(true);
				if (!msender.isSeeingChunk()) {
					msender.setSeeingChunk(true);
					p.sendMessage(ChatColor.YELLOW + "See Chunk is now " + ChatColor.GREEN + "On.");
				} else {
					msender.setSeeingChunk(false);
					p.sendMessage(ChatColor.YELLOW + "See Chunk is now " + ChatColor.RED + "Off.");
				}
			} else if (e.getSlot() == 34) {
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		}

		if (e.getInventory().getTitle().equals(ChatColor.GREEN + "Home " + ChatColor.GREEN + "Menu")) {
			if (e.getSlot() == 20) {
				e.setCancelled(true);
				p.performCommand("f home");
				p.closeInventory();
			} else if (e.getSlot() == 22) {
				e.setCancelled(true);
				p.performCommand("f sethome");
				p.closeInventory();
			} else if (e.getSlot() == 24) {
				e.setCancelled(true);
				p.performCommand("f delhome");
				p.closeInventory();
			} else if (e.getSlot() == 33) {
				e.setCancelled(true);
				openGUI(p);
			} else if (e.getSlot() == 34) {
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		}
	}
}
