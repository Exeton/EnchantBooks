package online.fireflower.enchant_books;

import online.fireflower.enchant_books.enchant_item_types.EnchantItemType;
import online.fireflower.enchant_books.enchant_item_types.EnchantTypeGroup;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class EnchantApplicationInfo {

    public List<EnchantItemType> enchantItemTypes;
    public int maxLevel;

    public EnchantApplicationInfo(int maxLevel, EnchantItemType... itemTypes){

        this.maxLevel = maxLevel;

        enchantItemTypes = new LinkedList<>();
        for (EnchantItemType enchantItemType : itemTypes)
            enchantItemTypes.add(enchantItemType);
    }

    public EnchantApplicationInfo(int maxLevel, EnchantTypeGroup type){
        this.maxLevel = maxLevel;
        enchantItemTypes = type.itemTypes;
    }

    public boolean canApply(ItemStack item){

        for (EnchantItemType enchantItemType : enchantItemTypes)
            if (enchantItemType.canApply(item))
                return true;

        return false;
    }
}
