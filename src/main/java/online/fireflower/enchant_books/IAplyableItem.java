package online.fireflower.enchant_books;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IAplyableItem {

    String getName();
    boolean verifyItem(List<String> lore);
    boolean tryApply(ItemStack applyTo, ItemStack applyable);

}
