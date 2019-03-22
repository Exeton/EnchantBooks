package online.fireflower.enchant_books.enchant_item_types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum EnchantItemType {

    SWORD("Swords", new TypeApplicationChecker("sword")),
    AXE("Axes", new MaterialApplicationChecker(Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOOD_AXE)),
    BOW("Bows", new MaterialApplicationChecker(Material.BOW)),
    HELMET("Helmets", (new TypeApplicationChecker("helmet"))),
    CHEST_PLATE("Chestplates", (new TypeApplicationChecker("chestplate"))),
    LEGS("Legs", (new TypeApplicationChecker("leggings"))),
    BOOTS("Boots", new TypeApplicationChecker("boots")),
    EVERY_OTHER_ITEM("All other items", (new TypeApplicationChecker("")));

    IApplicationChecker applicationChecker;
    public String ItemTypePlural;

    EnchantItemType(String ItemTypePlural, IApplicationChecker applicationChecker){
        this.applicationChecker = applicationChecker;
        this.ItemTypePlural = ItemTypePlural;
    }

    public boolean canApply(ItemStack itemStack){
        return applicationChecker.canApply(itemStack);
    }

}
