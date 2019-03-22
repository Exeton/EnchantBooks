package online.fireflower.enchant_books.enchant_item_types;

import org.bukkit.inventory.ItemStack;

public class TypeApplicationChecker implements IApplicationChecker {


    private String typeContains;

    public TypeApplicationChecker(String containsString){
        typeContains = containsString;
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return itemStack.getType().toString().toLowerCase().contains(typeContains.toLowerCase());
    }
}
