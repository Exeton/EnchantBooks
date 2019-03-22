package online.fireflower.enchant_books.enchant_books.book_creation;

import online.fireflower.enchant_books.EnchantApplicationInfo;
import online.fireflower.enchant_books.enchant_item_types.EnchantItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BookCreator {

    private Map<String, String> typesAndDisplayNames;

    public BookCreator(Map<String,String> typesAndDisplayNames){
        this.typesAndDisplayNames = typesAndDisplayNames;
    }

    public ItemStack getBook(String type){
        return createEnchantBook(typesAndDisplayNames.get(type.toLowerCase()));
    }

    public ItemStack makeBook(String displayName){
        return createEnchantBook(displayName);
    }

    private ItemStack createEnchantBook(String name){

        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName(name);
        book.setItemMeta(meta);
        return book;
    }
}
