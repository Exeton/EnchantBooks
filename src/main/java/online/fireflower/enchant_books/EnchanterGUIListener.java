package online.fireflower.enchant_books;

import com.github.jikoo.experience.Experience;
import online.fireflower.easy_enchants.EasyEnchants;
import online.fireflower.enchant_books.enchant_books.EnchantBookInfo;
import online.fireflower.enchant_books.enchant_books.book_creation.BookCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class EnchanterGUIListener implements Listener {

    BookCreator bookCreator;

    public EnchanterGUIListener(BookCreator bookCreator){
        this.bookCreator = bookCreator;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

        if (!event.getInventory().getName().equalsIgnoreCase(EnchantBooks.enchantGuiName))
            return;

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR)
            return;

        event.setCancelled(true);

        if (!event.getClickedInventory().getName().equalsIgnoreCase(EnchantBooks.enchantGuiName))
            return;

        String displayName = item.getItemMeta().getDisplayName();
        if (displayName == null || !displayName.contains("Enchant"))
            return;

        String costStr = item.getItemMeta().getLore().get(0).replace(EnchantBooks.xpCostString, "");
        int cost = Integer.parseInt(costStr);


        Player player = (Player)event.getWhoClicked();

        int xp = Experience.getExp(player);
        if (xp < cost){
            player.sendMessage(ChatColor.RED + "You cannot afford this book");
            return;
        }

        EnchantBooks.giveOrDrop(player, bookCreator.makeBook(displayName));
        Experience.changeExp(player, -cost);

    }


}
