package online.fireflower.enchant_books.enchant_item_types;


import java.util.LinkedList;
import java.util.List;

public enum EnchantTypeGroup {

    ALL(EnchantItemType.AXE, EnchantItemType.BOOTS, EnchantItemType.BOW, EnchantItemType.CHEST_PLATE, EnchantItemType.EVERY_OTHER_ITEM, EnchantItemType.HELMET, EnchantItemType.LEGS, EnchantItemType.SWORD),
    ARMOR(EnchantItemType.CHEST_PLATE, EnchantItemType.HELMET, EnchantItemType.LEGS, EnchantItemType.BOOTS),
    WEAPONS(EnchantItemType.SWORD, EnchantItemType.AXE, EnchantItemType.BOW),
    MELEE(EnchantItemType.SWORD, EnchantItemType.AXE);

    public List<EnchantItemType> itemTypes;

    EnchantTypeGroup(EnchantItemType... enchantTypes){
        this.itemTypes = new LinkedList<>();
        for (EnchantItemType enchantItemType : enchantTypes)
            itemTypes.add(enchantItemType);
    }
}
