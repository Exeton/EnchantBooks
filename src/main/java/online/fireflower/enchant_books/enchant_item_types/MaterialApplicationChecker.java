package online.fireflower.enchant_books.enchant_item_types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class MaterialApplicationChecker implements IApplicationChecker {

    private List<Material> materials = new LinkedList<>();

    public MaterialApplicationChecker(Material... materials){

        for (Material material : materials)
            this.materials.add(material);

    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return materials.contains(itemStack.getType());
    }
}
