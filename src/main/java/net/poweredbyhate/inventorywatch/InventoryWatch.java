package net.poweredbyhate.inventorywatch;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public final class InventoryWatch extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadItemSchematics();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void openInventory(Player target, Player p) {
        Inventory playerInv = Bukkit.createInventory(new IHolder(this), 54, ChatColor.AQUA+target.getDisplayName());
        playerInv.setContents(target.getInventory().getArmorContents());
        playerInv.setContents(target.getInventory().getContents());
        p.openInventory(playerInv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent ev) {
        if (ev.getInventory().getHolder() instanceof IHolder) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent ev) {
        if (ev.getRightClicked() instanceof Player) {
            if (ev.getPlayer().getInventory().getItemInMainHand() != null) {
                if (ev.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                    if (ev.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Scanner")) {
                        if (ev.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK) {
                            if (((Player) ev.getRightClicked()).getInventory().getItemInMainHand() != null) {
                                if (((Player) ev.getRightClicked()).getInventory().getItemInMainHand().hasItemMeta()) {
                                    if (((Player) ev.getRightClicked()).getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Blocker")) {
                                        if (((Player) ev.getRightClicked()).getInventory().getItemInMainHand().getType() == Material.REDSTONE_BLOCK) {
                                            return; //idk how to make more if statements
                                        }
                                    }
                                }
                            }
                            openInventory((Player) ev.getRightClicked(), ev.getPlayer());
                        }
                    }
                }
            }
        }
    }

    public void loadItemSchematics() {
        ShapedRecipe scanner = new ShapedRecipe(createItem(Material.STICK, 1, 0, "&aScanner", ""));
        ShapedRecipe blocker = new ShapedRecipe(createItem(Material.REDSTONE_BLOCK, 1, 0, "&cBlocker", ""));

        scanner.shape(getConfig().getString("scanner_recipe.line1"),getConfig().getString("scanner_recipe.line2"),getConfig().getString("scanner_recipe.line3"));
        blocker.shape(getConfig().getString("blocker_recipe.line1"),getConfig().getString("blocker_recipe.line2"),getConfig().getString("blocker_recipe.line3"));

        for (String s : getConfig().getStringList("scanner_recipe.ingredients")) {
            String[] itmz = s.split(":");
            scanner.setIngredient(itmz[0].charAt(0), Material.valueOf(itmz[1]));
        }

        for (String s : getConfig().getStringList("blocker_recipe.ingredients")) {
            String[] itmz = s.split(":");
            blocker.setIngredient(itmz[0].charAt(0), Material.valueOf(itmz[1]));
        }

        getServer().addRecipe(scanner);
        getServer().addRecipe(blocker);
    }

    public ItemStack createItem(Material material, int amount, int shrt, String displayname, String lore) {
        ItemStack i = new ItemStack(material, amount, (short) shrt);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));

        ArrayList<String> loreList = new ArrayList<String>();
        String[] lores = lore.split("/");
        loreList.addAll(Arrays.asList(lores));

        im.setLore(loreList);
        i.setItemMeta(im);
        return i;
    }

}
