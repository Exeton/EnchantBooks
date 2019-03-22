package online.fireflower.enchant_books.test_enchant;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;
import online.fireflower.easy_enchants.enchant_types.EnchantType;
import online.fireflower.easy_enchants.enchant_types.RandomEnchant;
import online.fireflower.easy_enchants.events.player_damage_player.PlayerDamageEntityEvent;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

import java.util.Random;

public class OneShotEnchant extends RandomEnchant {

    public OneShotEnchant(String displayName, Random random) {
        super(displayName, random);
    }


    @EventHandler
    public void onAttack(PlayerDamageEntityEvent event, EnchantInfo enchantInfo){
        event.event.setDamage(15);
        event.getPlayer().getWorld().strikeLightning(event.event.getEntity().getLocation());
    }

    @Override
    public int calculatePercentActivation(Event event, EnchantInfo enchantInfo) {
        return 100;
    }

    @Override
    public EnchantType getType() {
        return EnchantType.ITEM_ENCHANT;
    }
}
