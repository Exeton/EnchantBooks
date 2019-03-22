package online.fireflower.enchant_books;

import online.fireflower.enchant_books.enchant_books.EnchantBookInfo;
import online.fireflower.enchant_books.enchant_books.IEnchantBookInfoReadWriter;
import online.fireflower.enchant_books.enchant_books.book_creation.IEnchanter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnchantBookOpenListener implements Listener {

    IEnchantBookInfoReadWriter enchantBookInfoReadWriter;
    IEnchanter basicEnchanter;
    Map<String, String> tierNamesAndRefs;

    public EnchantBookOpenListener(IEnchantBookInfoReadWriter enchantBookInfoReadWriter, IEnchanter basicEnchanter,  Map<String, String> tierNamesAndRefs){
        this.enchantBookInfoReadWriter = enchantBookInfoReadWriter;
        this.basicEnchanter = basicEnchanter;
        this.tierNamesAndRefs = tierNamesAndRefs;
    }

    @EventHandler
    public void onOpenEnchantBook(PlayerInteractEvent event){

        ItemStack item = event.getItem();
        if (item == null)
            return;

        if (item.getType() != Material.BOOK || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null)
            return;

        if (!tierNamesAndRefs.containsKey(item.getItemMeta().getDisplayName()))
            return;

        EnchantBookInfo enchantBookInfo = basicEnchanter.enchant(tierNamesAndRefs.get(item.getItemMeta().getDisplayName()));

        if (item.getAmount() == 1)
            enchantBookInfoReadWriter.createEnchantBook(item, enchantBookInfo);
        else{
            item.setAmount(item.getAmount() - 1);
            ItemStack book = new ItemStack(Material.BOOK);
            enchantBookInfoReadWriter.createEnchantBook(book, enchantBookInfo);
            EnchantBooks.giveOrDrop(event.getPlayer(),book);
        }
    }
}
