package toxxicjtag.com.github;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.massivecraft.factions.FactionListComparator;
import com.massivecraft.factions.PlayerRoleComparator;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.mixin.Mixin;

import net.md_5.bungee.api.ChatColor;

public class SpectateGUI implements Listener {

	int rel = 0;
	private Inventory spectate;
	private Inventory kick;
	Inventory inventory;

	public void openSpectateGUI(Player player) {
		int rows;
		if (Bukkit.getOnlinePlayers().size() % 9 == 0) {
			rows = (int) (Bukkit.getOnlinePlayers().size() / 9);
		} else {
			rows = (int) (Bukkit.getOnlinePlayers().size() / 9) + 1;
		}
		spectate = Bukkit.createInventory(player, rows * 9, ChatColor.GREEN + "Click Head to Offer Membership");
		int i = 0;
		for (Player p : Bukkit.getOnlinePlayers()) {
			ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta im = (SkullMeta) is.getItemMeta();
			im.setOwner(p.getName());
			im.setDisplayName(p.getName());
			is.setItemMeta(im);
			this.spectate.setItem(i, is);
			if (i < this.spectate.getSize()) {
				i++;
			}
		}
		player.openInventory(this.spectate);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) throws MassiveException {
		String perm = null;
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Click Head to Offer Membership")) {
			if ((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != null)) {
				e.setCancelled(true);
				if ((e.getCurrentItem().hasItemMeta()) && (e.getCurrentItem().getItemMeta().hasDisplayName())) {
					Player c = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().trim());
					if (c != null) {
						p.performCommand("f invite a " + c.getName());
					}
				} else {
					p.closeInventory();
					p.performCommand("fgui");
				}
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Click to Revoke Membership")) {
			if ((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != null)) {
				e.setCancelled(true);
				if ((e.getCurrentItem().hasItemMeta()) && (e.getCurrentItem().getItemMeta().hasDisplayName())) {
					Player c = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().trim());
					if (c != null) {
						p.performCommand("f invite r " + c.getName());
					}
				} else {
					p.closeInventory();
					p.performCommand("fgui");
				}
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Click Head to Kick A Member")) {
			if ((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != null)) {
				e.setCancelled(true);
				if ((e.getCurrentItem().hasItemMeta()) && (e.getCurrentItem().getItemMeta().hasDisplayName())) {
					Player c = Bukkit.getServer().getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().trim());

					@SuppressWarnings("deprecation")
					OfflinePlayer offline = Bukkit.getServer()
							.getOfflinePlayer(e.getCurrentItem().getItemMeta().getDisplayName().trim());
					if (c != null) {
						p.performCommand("f kick " + c.getName());
					} else {
						p.performCommand("f kick " + offline.getName());
					}
				} else {
					p.closeInventory();
					p.performCommand("fgui");
				}
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "List Factions")) {
			if (e.getCurrentItem() != null && e.getCurrentItem().getType() != null) {
				e.setCancelled(true);
				if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
					String f = e.getCurrentItem().getItemMeta().getDisplayName().trim();
					if (e.isLeftClick()) {
						if (f != null) {
							p.performCommand("f f " + f);
						} else {
							p.performCommand("f f " + f);
						}
					} else if (e.isRightClick()) {
						String c =  FactionColl.get().getByName(f).getName();
						int z = 0;
						while (z < 1) {
						if (rel == 0) {
							p.performCommand("f enemy " + c);
							rel++;
							z = 1;
							continue;
						}
						if (rel == 1) {
							p.performCommand("f truce " + c);
							rel++;
							z = 1;
							continue;
						}
						if (rel == 2) {
							p.performCommand("f ally " + c);
							rel++;
							z = 1;
							continue;
						}
						if (rel == 3) {
							p.performCommand("f neutral " + c);
							rel = 0;
							z = 1;
							continue;
						}
					}
					}
				} else {
					p.closeInventory();
					p.performCommand("fgui");
				}
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Promote/Demote Menu")) {
			if (e.getCurrentItem() != null && e.getCurrentItem().getType() != null) {
				e.setCancelled(true);
				if (e.isLeftClick()) {
					if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
						Player c = Bukkit.getServer()
								.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().trim());
						@SuppressWarnings("deprecation")
						OfflinePlayer offline = Bukkit.getServer()
								.getOfflinePlayer(e.getCurrentItem().getItemMeta().getDisplayName().trim());
						if (c != null) {
							p.performCommand("f promote " + c.getName());
						} else {
							p.performCommand("f promote " + offline.getName());
						}
					} else {
						p.closeInventory();
						p.performCommand("fgui");
					}
				} else if (e.isRightClick()) {
					if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
						Player c = Bukkit.getServer()
								.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().trim());
						@SuppressWarnings("deprecation")
						OfflinePlayer offline = Bukkit.getServer()
								.getOfflinePlayer(e.getCurrentItem().getItemMeta().getDisplayName().trim());
						if (c != null) {
							p.performCommand("f demote " + c.getName());
						} else {
							p.performCommand("f demote " + offline.getName());
						}
					}
				} else {
					p.closeInventory();
					p.performCommand("fgui");
				}
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "1/21 Permission: Build")) {
			perm = "build";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				p.closeInventory();
				p.performCommand("fgui");
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				openPermPainGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "2/21 Permission: Painbuild")) {
			perm = "painbuild";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				openPermGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				openDoorGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "3/21 Permission: Use Doors")) {
			perm = "door";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				openPermPainGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				openButtonGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "4/21 Permission: Buttons")) {
			perm = "button";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				openDoorGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				openLeverGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "5/21 Permission: Use Levers")) {
			perm = "lever";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				openButtonGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				openChestGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "6/21 Permission: Use Chests")) {
			perm = "container";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				openLeverGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fNameGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "7/21 Permission: Naming")) {
			perm = "name";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				openChestGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fDescGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "8/21 Permission: Description")) {
			perm = "desc";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fNameGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fMotdGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "9/21 Permission: Edit MOTD")) {
			perm = "motd";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fDescGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fInviteGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "10/21 Permission: Inviting")) {
			perm = "invite";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fMotdGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fkickGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "11/21 Permission: Kicking")) {
			perm = "kick";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fInviteGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fTitleGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "12/21 Permission: Edit Title")) {
			perm = "title";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fkickGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fHomeTPGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "13/21 Permission: /Home")) {
			perm = "home";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fTitleGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fHomeSetGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "14/21 Permission: Set Home")) {
			perm = "sethome";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fHomeTPGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fClaimGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "15/21 Permission: Claiming")) {
			perm = "territory";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fHomeSetGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fAccessGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "16/21 Permission: Access")) {
			perm = "access";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fClaimGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fRelationsGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "17/21 Permission: Relations")) {
			perm = "rel";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fAccessGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fDisbandGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "18/21 Permission: Disbanding")) {
			perm = "disband";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fRelationsGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fFlagsGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "19/21 Permission: Flags")) {
			perm = "flags";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fDisbandGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fPermsGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "20/21 Permission: Perms")) {
			perm = "perms";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fFlagsGUI(p);
			} else if (e.getSlot() == 43) { // next
				e.setCancelled(true);
				fStatusGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		} else if (e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "21/21 Permission: Status")) {
			perm = "status";
			runPermCMDs(p, e, perm);
			if (e.getSlot() == 42) { // back
				e.setCancelled(true);
				fPermsGUI(p);
			} else if (e.getSlot() == 44) { // close
				e.setCancelled(true);
				p.closeInventory();
			} else {
				e.setCancelled(true);
				return;
			}
		}
	}

	public void openPermGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, -1);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 2);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "1/21 Permission: Build");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void openListGUI(Player player) {
		MPlayer msender = MPlayer.get(player);
		List<Faction> factions = null;

		factions = FactionColl.get().getAll(FactionListComparator.get());

		Inventory list = Bukkit.createInventory(null, 54, ChatColor.AQUA + "List Factions");

		int z = 0;
		for (Faction faction : factions) {
			ItemStack is = new ItemStack(Material.PAPER);
			ItemMeta ismeta = is.getItemMeta();
			ismeta.setDisplayName(faction.getName(msender));
			ArrayList<String> Lore11 = new ArrayList<String>();
			Lore11.add(ChatColor.YELLOW + "" + faction.getMPlayersWhereOnline(true).size() + "/"
					+ faction.getMPlayers().size() + " Online");
			Lore11.add(ChatColor.YELLOW + "Chunks: " + faction.getLandCount());
			Lore11.add(ChatColor.YELLOW + "Current Power: " + faction.getLandCount());
			Lore11.add(ChatColor.YELLOW + "Max Power: " + faction.getPowerMaxRounded());
			Lore11.add(ChatColor.DARK_RED + "Right Click To Change Relations.");
			ismeta.setLore(Lore11);
			is.setItemMeta(ismeta);
			list.setItem(z, is);
			if (z < list.getSize()) {
				z++;
			}
		}

		player.getPlayer().openInventory(list);
	}

	public void openPermPainGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 1);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 3);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "2/21 Permission: Painbuild");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void openDoorGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 2);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 4);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "3/21 Permission: Use Doors");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void openButtonGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 3);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 5);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "4/21 Permission: Buttons");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void openLeverGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 4);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 6);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "5/21 Permission: Use Levers");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void openChestGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 5);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 7);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "6/21 Permission: Use Chests");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fNameGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 6);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 8);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "7/21 Permission: Naming");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fDescGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 7);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 9);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "8/21 Permission: Description");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fMotdGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 8);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 10);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "9/21 Permission: Edit MOTD");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fInviteGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 9);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 11);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "10/21 Permission: Inviting");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fkickGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 10);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 12);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "11/21 Permission: Kicking");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fTitleGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 11);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 13);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "12/21 Permission: Edit Title");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fHomeTPGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 12);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 14);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "13/21 Permission: /Home");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fHomeSetGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 13);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 15);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "14/21 Permission: Set Home");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fClaimGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 14);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 16);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "15/21 Permission: Claiming");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fAccessGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 15);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 17);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "16/21 Permission: Access");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fRelationsGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 16);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 18);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "17/21 Permission: Relations");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fDisbandGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 17);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 19);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "18/21 Permission: Disbanding");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fFlagsGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 18);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 20);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "19/21 Permission: Flags");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fPermsGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 19);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		ItemStack forward = new ItemStack(Material.ARROW, 21);
		ItemMeta forwardmeta = forward.getItemMeta();
		forwardmeta.setDisplayName(ChatColor.DARK_RED + "Forward!");
		ArrayList<String> Lore13 = new ArrayList<String>();
		forwardmeta.setLore(Lore13);
		forward.setItemMeta(forwardmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "20/21 Permission: Perms");
		defaultLayout(perms);
		perms.setItem(42, back);
		perms.setItem(43, forward);

		player.getPlayer().openInventory(perms);
	}

	public void fStatusGUI(Player player) {

		ItemStack back = new ItemStack(Material.ARROW, 20);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.DARK_RED + "Back!");
		ArrayList<String> Lore5 = new ArrayList<String>();
		backmeta.setLore(Lore5);
		back.setItemMeta(backmeta);

		Inventory perms = Bukkit.createInventory(null, 54, ChatColor.AQUA + "21/21 Permission: Status");
		defaultLayout(perms);
		perms.setItem(42, back);

		player.getPlayer().openInventory(perms);
	}

	public void openDenyGUI(Player player) {
		int rows;
		if (Bukkit.getOnlinePlayers().size() % 9 == 0) {
			rows = (int) (Bukkit.getOnlinePlayers().size() / 9);
		} else {
			rows = (int) (Bukkit.getOnlinePlayers().size() / 9) + 1;
		}
		spectate = Bukkit.createInventory(player, rows * 9, ChatColor.GREEN + "Click to Revoke Membership");
		int i = 0;
		for (Player p : Bukkit.getOnlinePlayers()) {
			ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta im = (SkullMeta) is.getItemMeta();
			im.setOwner(p.getName());
			im.setDisplayName(p.getName());
			is.setItemMeta(im);
			spectate.setItem(i, is);
			if (i < spectate.getSize()) {
				i++;
			}
		}
		player.openInventory(spectate);
	}

	public void openKickGUI(Player player) {
		MPlayer msender = MPlayer.get(player);
		Faction faction = msender.getFaction();

		List<String> followerNamesOnline = new ArrayList();

		List<MPlayer> followers = faction.getMPlayers();
		Collections.sort(followers, PlayerRoleComparator.get());
		for (MPlayer follower : followers) {
			if ((follower.isOnline()) && (Mixin.canSee(player, follower.getId()))) {
				followerNamesOnline.add(follower.getNameAndTitle(msender));
			}
		}

		int rows;
		if (followers.size() % 9 == 0) {
			rows = followers.size() / 9;
		} else {
			rows = followers.size() / 9 + 1;
		}
		this.kick = Bukkit.createInventory(player, rows * 9, ChatColor.GREEN + "Click Head to Kick A Member");
		int i = 0;
		for (MPlayer p : followers) {
			ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta im = (SkullMeta) is.getItemMeta();
			im.setOwner(p.getName());
			im.setDisplayName(p.getName());
			is.setItemMeta(im);
			this.kick.setItem(i, is);
			if (i < this.kick.getSize()) {
				i++;
			}
		}
		player.openInventory(this.kick);
	}

	public void openProDeGUI(Player player) {
		MPlayer msender = MPlayer.get(player);
		Faction faction = msender.getFaction();

		List<String> followerNamesOnline = new ArrayList<String>();

		List<MPlayer> followers = faction.getMPlayers();
		Collections.sort(followers, PlayerRoleComparator.get());
		for (MPlayer follower : followers) {
			if (follower.isOnline() && Mixin.canSee(player, follower.getId())) {
				followerNamesOnline.add(follower.getNameAndTitle(msender));
			}
		}
		int rows;
		if (followers.size() % 9 == 0) {
			rows = (int) (followers.size() / 9);
		} else {
			rows = (int) (followers.size() / 9) + 1;
		}
		spectate = Bukkit.createInventory(player, rows * 9, ChatColor.GREEN + "Promote/Demote Menu");
		int i = 0;
		for (MPlayer p : followers) {
			ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta im = (SkullMeta) is.getItemMeta();
			im.setOwner(p.getName());
			im.setDisplayName(p.getName());
			ArrayList<String> Lore11 = new ArrayList<String>();
			Lore11.add(ChatColor.GREEN + "Left Click Promote.");
			Lore11.add(ChatColor.DARK_RED + "Right Click To Demote.");
			im.setLore(Lore11);
			is.setItemMeta(im);
			this.spectate.setItem(i, is);
			if (i < spectate.getSize()) {
				i++;
			}
		}
		player.openInventory(this.spectate);
	}

	public ItemStack glassGreen() {
		ItemStack glassGreen = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemMeta gGMeta = glassGreen.getItemMeta();
		gGMeta.setDisplayName(" ");
		glassGreen.setItemMeta(gGMeta);
		return glassGreen;
	}

	public ItemStack glassBlack() {
		ItemStack glassBlack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta gBlMeta = glassBlack.getItemMeta();
		gBlMeta.setDisplayName(" ");
		glassBlack.setItemMeta(gBlMeta);
		return glassBlack;
	}

	public ItemStack Leader() {
		ItemStack Leader = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta Leadermeta = Leader.getItemMeta();
		Leadermeta.setDisplayName(ChatColor.GOLD + "Leader");
		ArrayList<String> Lore1 = new ArrayList<String>();
		Leadermeta.setLore(Lore1);
		Leader.setItemMeta(Leadermeta);
		return Leader;
	}

	public ItemStack Officer() {
		ItemStack Officer = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta Officermeta = Officer.getItemMeta();
		Officermeta.setDisplayName(ChatColor.GOLD + "Officer");
		ArrayList<String> Lore2 = new ArrayList<String>();
		Officermeta.setLore(Lore2);
		Officer.setItemMeta(Officermeta);
		return Officer;
	}

	public ItemStack Member() {
		ItemStack Member = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta Membermeta = Member.getItemMeta();
		Membermeta.setDisplayName(ChatColor.GOLD + "Member");
		ArrayList<String> Lore3 = new ArrayList<String>();
		Membermeta.setLore(Lore3);
		Member.setItemMeta(Membermeta);
		return Member;
	}

	public ItemStack Recruit() {
		ItemStack Recruit = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta Recruitmeta = Recruit.getItemMeta();
		Recruitmeta.setDisplayName(ChatColor.GOLD + "Recruit");
		ArrayList<String> Lore7 = new ArrayList<String>();
		Recruitmeta.setLore(Lore7);
		Recruit.setItemMeta(Recruitmeta);
		return Recruit;
	}

	public ItemStack Allies() {
		ItemStack Allies = new ItemStack(Material.DIAMOND_HOE);
		ItemMeta Alliesmeta = Allies.getItemMeta();
		Alliesmeta.setDisplayName(ChatColor.GOLD + "Allies");
		ArrayList<String> Lore6 = new ArrayList<String>();
		Alliesmeta.setLore(Lore6);
		Allies.setItemMeta(Alliesmeta);
		return Allies;
	}

	public ItemStack Truce() {
		ItemStack Truce = new ItemStack(Material.DIAMOND_SPADE);
		ItemMeta Trucemeta = Truce.getItemMeta();
		Trucemeta.setDisplayName(ChatColor.GOLD + "Truce");
		ArrayList<String> Lore8 = new ArrayList<String>();
		Trucemeta.setLore(Lore8);
		Truce.setItemMeta(Trucemeta);
		return Truce;
	}

	public ItemStack Neutral() {
		ItemStack Neutral = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta Neutralmeta = Neutral.getItemMeta();
		Neutralmeta.setDisplayName(ChatColor.GOLD + "Neutral");
		ArrayList<String> Lore9 = new ArrayList<String>();
		Neutralmeta.setLore(Lore9);
		Neutral.setItemMeta(Neutralmeta);
		return Neutral;
	}

	public ItemStack Enemy() {
		ItemStack Enemy = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta Enemymeta = Enemy.getItemMeta();
		Enemymeta.setDisplayName(ChatColor.GOLD + "Enemy");
		ArrayList<String> Lore10 = new ArrayList<String>();
		Enemymeta.setLore(Lore10);
		Enemy.setItemMeta(Enemymeta);
		return Enemy;
	}

	public ItemStack Yes() {
		ItemStack Yes = new ItemStack(Material.WOOL, 1, (short) 5);
		ItemMeta Yesmeta = Yes.getItemMeta();
		Yesmeta.setDisplayName(ChatColor.GREEN + "Yes");
		ArrayList<String> Lore11 = new ArrayList<String>();
		Yesmeta.setLore(Lore11);
		Yes.setItemMeta(Yesmeta);
		return Yes;
	}

	public ItemStack No() {
		ItemStack No = new ItemStack(Material.WOOL, 1, (short) 14);
		ItemMeta Nometa = No.getItemMeta();
		Nometa.setDisplayName(ChatColor.RED + "No");
		ArrayList<String> Lore12 = new ArrayList<String>();
		Nometa.setLore(Lore12);
		No.setItemMeta(Nometa);
		return No;
	}

	public ItemStack Close() {
		ItemStack close = new ItemStack(Material.BARRIER);
		ItemMeta closemeta = close.getItemMeta();
		closemeta.setDisplayName(ChatColor.DARK_RED + "Close Menu!");
		ArrayList<String> Lore4 = new ArrayList<String>();
		closemeta.setLore(Lore4);
		close.setItemMeta(closemeta);
		return close;
	}

	public void runPermCMDs(Player p, InventoryClickEvent e, String perm) {
		if (e.getSlot() == 19) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Leader yes");
		} else if (e.getSlot() == 20) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + "  Officer yes");
		} else if (e.getSlot() == 21) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Member yes");
		} else if (e.getSlot() == 22) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Recruit yes");
		} else if (e.getSlot() == 23) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Ally yes");
		} else if (e.getSlot() == 24) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Truce yes");
		} else if (e.getSlot() == 25) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Neutral yes");
		} else if (e.getSlot() == 26) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Enemy yes");
		} else if (e.getSlot() == 28) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Leader no");
		} else if (e.getSlot() == 29) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Officer no");
		} else if (e.getSlot() == 30) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Member no");
		} else if (e.getSlot() == 31) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Recruit no");
		} else if (e.getSlot() == 32) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Ally no");
		} else if (e.getSlot() == 33) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Truce no");
		} else if (e.getSlot() == 34) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Neutral no");
		} else if (e.getSlot() == 35) {
			e.setCancelled(true);
			p.performCommand("f perm set " + perm + " Enemy no");
		}
	}

	public void defaultLayout(Inventory perms) {
		ItemStack glassGreen = glassGreen();
		ItemStack glassBlack = glassBlack();
		ItemStack Leader = Leader();
		ItemStack Officer = Officer();
		ItemStack Member = Member();
		ItemStack Recruit = Recruit();
		ItemStack Allies = Allies();
		ItemStack Truce = Truce();
		ItemStack Neutral = Neutral();
		ItemStack Enemy = Enemy();
		ItemStack Yes = Yes();
		ItemStack No = No();
		ItemStack close = Close();
		perms.setItem(0, glassGreen);
		perms.setItem(1, glassBlack);
		perms.setItem(2, glassGreen);
		perms.setItem(3, glassBlack);
		perms.setItem(4, glassGreen);
		perms.setItem(5, glassBlack);
		perms.setItem(6, glassGreen);
		perms.setItem(7, glassBlack);
		perms.setItem(8, glassGreen);
		perms.setItem(9, glassBlack);
		perms.setItem(10, Leader);
		perms.setItem(11, Officer);
		perms.setItem(12, Member);
		perms.setItem(13, Recruit);
		perms.setItem(14, Allies);
		perms.setItem(15, Truce);
		perms.setItem(16, Neutral);
		perms.setItem(17, Enemy);
		perms.setItem(18, glassGreen);
		perms.setItem(19, Yes);
		perms.setItem(20, Yes);
		perms.setItem(21, Yes);
		perms.setItem(22, Yes);
		perms.setItem(23, Yes);
		perms.setItem(24, Yes);
		perms.setItem(25, Yes);
		perms.setItem(26, Yes);
		perms.setItem(27, glassBlack);
		perms.setItem(28, No);
		perms.setItem(29, No);
		perms.setItem(30, No);
		perms.setItem(31, No);
		perms.setItem(32, No);
		perms.setItem(33, No);
		perms.setItem(34, No);
		perms.setItem(35, No);
		perms.setItem(36, glassGreen);
		perms.setItem(44, close);
		perms.setItem(45, glassBlack);
		perms.setItem(46, glassGreen);
		perms.setItem(47, glassBlack);
		perms.setItem(48, glassGreen);
		perms.setItem(49, glassBlack);
		perms.setItem(50, glassGreen);
		perms.setItem(51, glassBlack);
		perms.setItem(52, glassGreen);
		perms.setItem(53, glassBlack);
	}
}