package online.fireflower.enchant_books.enchant_books;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_parsing.IEnchantInfoParser;
import online.fireflower.enchant_books.EnchantApplicationInfo;
import online.fireflower.enchant_books.enchant_item_types.EnchantItemType;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BasicEnchantBookInfoReadWriter implements IEnchantBookInfoReadWriter {

    IEnchantInfoParser enchantInfoParser;
    ISuccessRateParser successRateParser;
    private Map<String, EnchantApplicationInfo> namesAndEnchantApplicationInfo;

    public BasicEnchantBookInfoReadWriter(IEnchantInfoParser enchantInfoParser, ISuccessRateParser successRateParser, Map<String, EnchantApplicationInfo> namesAndEnchantApplicationInfo){
        this.enchantInfoParser = enchantInfoParser;
        this.successRateParser = successRateParser;
        this.namesAndEnchantApplicationInfo = namesAndEnchantApplicationInfo;
    }

    @Override
    public EnchantBookInfo getEnchantBookInfo(ItemStack itemStack) {

        if (itemStack.getItemMeta() == null)
            return null;

        List<String> lore = getLore(itemStack);
        if (!successRateParser.hasSuccessAndFailure(lore))
            return null;

        EnchantInfo enchantInfo = enchantInfoParser.getEnchantInfo(itemStack.getItemMeta().getDisplayName());
        return new EnchantBookInfo(enchantInfo, successRateParser.getSuccess(lore), successRateParser.getFailure(lore));
    }

    @Override
    public void createEnchantBook(ItemStack itemStack, EnchantBookInfo enchantBookInfo) {

        try{
            if (itemStack.getItemMeta() == null)
                throw new Exception("This item doesn't have meta");
        }catch (Exception e){
            e.printStackTrace();
        }

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(enchantInfoParser.createEnchantString(enchantBookInfo.enchantInfo));


        EnchantApplicationInfo enchantApplicationInfo = namesAndEnchantApplicationInfo.get(enchantBookInfo.enchantInfo.name);
        List<String> applayableTo = new LinkedList<>();
        for (EnchantItemType enchantItemType : enchantApplicationInfo.enchantItemTypes)
            applayableTo.add(enchantItemType.ItemTypePlural);

        String applyableToStr = ChatColor.GRAY + "Applyable to: " + String.join(", ", applayableTo);

        //Using getLore() prevents null ref exception
        List<String> lore = getLore(itemStack);
        lore.add(applyableToStr);
        lore.add(successRateParser.createSuccessString(enchantBookInfo.success));
        lore.add(successRateParser.createFailureString(enchantBookInfo.failure));
        meta.setLore(lore);

        itemStack.setItemMeta(meta);
    }

    private static List<String> getLore(ItemStack item){

        if (item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null)
            return new LinkedList<>();

        return item.getItemMeta().getLore();
    }
}
