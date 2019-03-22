package online.fireflower.enchant_books.enchant_books;

import org.bukkit.inventory.ItemStack;

public interface IEnchantBookInfoReadWriter {

    EnchantBookInfo getEnchantBookInfo(ItemStack itemStack);
    void createEnchantBook(ItemStack itemStack, EnchantBookInfo enchantBookInfo);
}
