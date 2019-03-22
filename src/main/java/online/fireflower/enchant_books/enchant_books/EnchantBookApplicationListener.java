package online.fireflower.enchant_books.enchant_books;

import me.Test.hammy2899.glow.Glow;
import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantInfoParser;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantReadWriter;
import online.fireflower.enchant_books.EnchantApplicationInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Random;

public class EnchantBookApplicationListener implements Listener {

    IEnchantBookInfoReadWriter enchantBookInfoReadWriter;
    IEnchantReadWriter enchantReadWriter;
    HashMap<String, EnchantApplicationInfo> enchantNamesAndApplicationInfo;
    Random random;

    public EnchantBookApplicationListener(IEnchantBookInfoReadWriter enchantBookInfoReadWriter, IEnchantReadWriter enchantReadWriter, HashMap<String, EnchantApplicationInfo> enchantNamesAndApplicationInfo, Random random){
        this.enchantBookInfoReadWriter = enchantBookInfoReadWriter;
        this.enchantReadWriter = enchantReadWriter;
        this.enchantNamesAndApplicationInfo = enchantNamesAndApplicationInfo;
        this.random = random;
    }

    @EventHandler
    public void onEnchantApplication(InventoryClickEvent event){

        ItemStack enchantBook = event.getCursor();

        if (enchantBook == null || enchantBook.getType() == Material.AIR || enchantBook.getType() != Material.BOOK)
            return;

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
            return;

        EnchantBookInfo enchantBookInfo = enchantBookInfoReadWriter.getEnchantBookInfo(enchantBook);
        if (enchantBookInfo == null)
            return;

        ItemStack currentItem = event.getCurrentItem();
        if (!shouldApply(enchantBookInfo.enchantInfo, currentItem))
            return;

        apply(currentItem, enchantBookInfo);
        event.setCancelled(true);

        event.setCurrentItem(currentItem);
        event.setCursor(new ItemStack(Material.AIR , 1));
    }

    private boolean shouldApply(EnchantInfo enchantInfo, ItemStack itemStack){
        String key = enchantInfo.name;
        if (!enchantNamesAndApplicationInfo.containsKey(key)){
            Bukkit.getLogger().info("Invalid key: " + key);

            Bukkit.getLogger().info("Valid keys");
            for (String keyVal : enchantNamesAndApplicationInfo.keySet()){
                Bukkit.getLogger().info(keyVal);
            }
        }

        return enchantNamesAndApplicationInfo.get(key).canApply(itemStack);
    }

    private void apply(ItemStack itemStack, EnchantBookInfo enchantBookInfo){

        if (random.nextInt(100) <= enchantBookInfo.success){
            Glow glow = new Glow(70);
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.getEnchantLevel(glow) != 2){
                meta.addEnchant(glow, 2, true);
                itemStack.setItemMeta(meta);
            }

            enchantReadWriter.setEnchant(itemStack, enchantBookInfo.enchantInfo);
        }

        else if (random.nextInt(100) < enchantBookInfo.failure)
            itemStack.setType(Material.AIR);

    }
}
