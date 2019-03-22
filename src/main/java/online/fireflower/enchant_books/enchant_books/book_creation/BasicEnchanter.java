package online.fireflower.enchant_books.enchant_books.book_creation;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.enchant_books.EnchantApplicationInfo;
import online.fireflower.enchant_books.enchant_books.EnchantBookInfo;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BasicEnchanter implements IEnchanter {

    Map<String, List<Enchant>> tiersAndEnchants;
    HashMap<String, EnchantApplicationInfo> enchantNamesAndApplicationInfo;
    Random random;

    public BasicEnchanter(Map<String, List<Enchant>> tiersAndEnchants, HashMap<String, EnchantApplicationInfo> enchantNamesAndApplicationInfo, Random random){
        this.tiersAndEnchants = tiersAndEnchants;
        this.enchantNamesAndApplicationInfo = enchantNamesAndApplicationInfo;
        this.random = random;
    }

    @Override
    public EnchantBookInfo enchant(String tier) {

        List<Enchant> enchants = tiersAndEnchants.get(tier);
        if (enchants == null){
            Bukkit.getLogger().info("Null enchants for tier: " + tier);

            Bukkit.getLogger().info("Available:" );
            for (String key : tiersAndEnchants.keySet())
                Bukkit.getLogger().info(key);

        }


        Enchant enchant = enchants.get(random.nextInt(enchants.size()));

        int maxLevel = enchantNamesAndApplicationInfo.get(enchant.displayName).maxLevel;
        int level = random.nextInt(maxLevel) + 1;

        return new EnchantBookInfo(new EnchantInfo(enchant.displayName, level), random.nextInt(101), random.nextInt(101));
    }
}
