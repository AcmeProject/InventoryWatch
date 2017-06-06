package net.poweredbyhate.inventorywatch;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Created by Lax on 6/5/2017.
 */
public class IHolder implements InventoryHolder {

    private InventoryWatch plugin;

    public IHolder(InventoryWatch plugin) {
        this.plugin = plugin;
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(null, 54);
    }
}
