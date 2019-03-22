package online.fireflower.enchant_books.commands;

import online.fireflower.enchant_books.EnchantBooks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

public class EnchanterCommand implements CommandExecutor {

    List<String> enchantBookTypes;
    List<Byte> enchantColors;
    List<Integer> bookCosts;

    public EnchanterCommand(List<String> enchantBookTypes, List<Byte> enchantColors, List<Integer> bookCosts){
        this.enchantBookTypes = enchantBookTypes;
        this.enchantColors = enchantColors;
        this.bookCosts = bookCosts;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player)commandSender;
        player.openInventory(getInventory());

        return true;
    }

    private Inventory getInventory(){

        Inventory inventory = Bukkit.createInventory(null, 9, EnchantBooks.enchantGuiName);
        ItemStack fillerPane = getEmptyPane(15);

        for (int i = 0; i < 9; i++)
            inventory.setItem(i, fillerPane);

        int startingIndex = (9 - enchantBookTypes.size()) / 2;
        for (int i = 0; i < enchantBookTypes.size(); i++)
            inventory.setItem(startingIndex + i, getColoredPane(enchantBookTypes.get(i), enchantColors.get(i), bookCosts.get(i)));

        return inventory;
    }

    private ItemStack getColoredPane(String name, byte color, int cost){

        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, color);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(name);

        List<String> lore = new LinkedList<>();
        lore.add(EnchantBooks.xpCostString + cost);
        meta.setLore(lore);

        pane.setItemMeta(meta);
        return pane;
    }

    private ItemStack getEmptyPane(int color){

        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)color);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(" ");
        pane.setItemMeta(meta);

        return pane;
    }

}
