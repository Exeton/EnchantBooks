package online.fireflower.enchant_books;

import com.google.common.collect.HashBiMap;
import me.Test.hammy2899.glow.Glow;
import online.fireflower.easy_enchants.EasyEnchants;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.enchant_books.commands.EnchanterCommand;
import online.fireflower.enchant_books.commands.GiveBookCommand;
import online.fireflower.enchant_books.enchant_books.BasicEnchantBookInfoReadWriter;
import online.fireflower.enchant_books.enchant_books.BasicSuccessRateParser;
import online.fireflower.enchant_books.enchant_books.IEnchantBookInfoReadWriter;
import online.fireflower.enchant_books.enchant_books.book_creation.BasicEnchanter;
import online.fireflower.enchant_books.enchant_books.book_creation.BookCreator;
import online.fireflower.enchant_books.enchant_books.book_creation.BookGiver;
import online.fireflower.enchant_books.enchant_books.book_creation.IEnchanter;
import online.fireflower.enchant_books.enchant_item_types.EnchantTypeGroup;
import online.fireflower.enchant_books.test_enchant.OneShotEnchant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;

public class EnchantBooks extends JavaPlugin {

    public static EnchantBooks instance;

    public static String enchantGuiName = "Enchanter";
    public static String xpCostString = ChatColor.YELLOW + "XP Cost: ";
    private Random random = new Random();

    IEnchantBookInfoReadWriter enchantBookInfoReadWriter;
    IEnchanter enchanter;

    HashBiMap<String, String> typesAndBookNames = HashBiMap.create(6);
    public static HashMap<String, List<Enchant>> typesAndEnchants = new HashMap<>();
    static HashMap<String, EnchantApplicationInfo> enchantNamesAndApplicationInfo = new HashMap<>();
    List<Byte> enchantColors = new LinkedList<>();
    List<String> enchantNames = new LinkedList<>();

    List<String> tierDisplayNames = new LinkedList<>();
    List<Integer> tierCosts = new LinkedList<>();

    @Override
    public void onEnable() {

        instance = this;
        registerGlow();

        saveDefaultConfig();
        registerTiers(getConfig().getConfigurationSection("bookTiers"));

        EasyEnchants.dependencyBuilder.addTask(() -> runDI(), 10);

        BookCreator bookCreator = new BookCreator(typesAndBookNames);
        BookGiver bookGiver = new BookGiver(bookCreator);
        this.getCommand("giveBook").setExecutor(new GiveBookCommand(bookGiver));
        this.getCommand("enchanter").setExecutor(new EnchanterCommand(enchantNames, enchantColors, tierCosts));

        registerTestEnchants();
    }


    private void registerTiers(ConfigurationSection config){

        for (String key : config.getKeys(false)){
            ConfigurationSection subSection = config.getConfigurationSection(key);
            String name = subSection.getString("displayName");
            int cost = subSection.getInt("xpCost");
            int paneColor = subSection.getInt("paneColor");

            tierDisplayNames.add(name);
            tierCosts.add(cost);
            registerBookType(key, name, (byte)paneColor);
        }
    }


    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(70);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void registerBookType(String ref, String tierName, Byte enchantPaneColor){

        String enchantName = ChatColor.translateAlternateColorCodes('&', tierName + " Enchant Book");

        instance.typesAndBookNames.put(ref.toLowerCase(), enchantName);
        instance.enchantColors.add(enchantPaneColor);
        instance.enchantNames.add(enchantName);
    }

    private void runDI(){

        EasyEnchants instance = EasyEnchants.getInstance();

        enchantBookInfoReadWriter = new BasicEnchantBookInfoReadWriter(instance.dependencyRetriever.getEnchantInfoParser(), new BasicSuccessRateParser(), enchantNamesAndApplicationInfo);
        enchanter = new BasicEnchanter(typesAndEnchants, enchantNamesAndApplicationInfo, random);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EnchantBookOpenListener(enchantBookInfoReadWriter, enchanter, typesAndBookNames.inverse()), this);

        pluginManager.registerEvents(new EnchantBookApplicationListener(enchantBookInfoReadWriter, instance.dependencyRetriever.getEnchantReadWriter(), enchantNamesAndApplicationInfo, random), this);
        pluginManager.registerEvents(new EnchanterGUIListener(new BookCreator(typesAndBookNames)), this);
    }

    private void registerTestEnchants(){

        OneShotEnchant oneShotEnchant = new OneShotEnchant(ChatColor.translateAlternateColorCodes('&', "&4O&3n&6e &cS&eh&4o&3t"));
        registerBookAndEasyEnchant("dank", "OneShot",  oneShotEnchant, new EnchantApplicationInfo(5, EnchantTypeGroup.WEAPONS));
    }

    public static void registerBookAndEasyEnchant(String tier, String refName, Enchant enchant, EnchantApplicationInfo enchantApplicationInfo){
        registerBook(enchant, tier, enchantApplicationInfo);
        EasyEnchants.registerEnchant(refName, enchant);
    }

    public static void registerBook(Enchant enchant, String tier, EnchantApplicationInfo enchantApplicationInfo){

        if (!typesAndEnchants.containsKey(tier)){
            typesAndEnchants.put(tier, new LinkedList<>());
        }

        List<Enchant> enchants = typesAndEnchants.get(tier);
        enchants.add(enchant);
        enchantNamesAndApplicationInfo.put(enchant.displayName, enchantApplicationInfo);
    }

    public static void giveOrDrop(Player player, ItemStack item){

        if (firstSlot(player.getInventory()) != -1)
            player.getInventory().addItem(item);
        else{
            player.getWorld().dropItem(player.getLocation(), item);
            player.sendMessage(ChatColor.RED + "You did not have inventory space for this item so it was dropped on the ground");
        }

    }

    private static int firstSlot(Inventory inventory){

        for (int i = 0; i < inventory.getSize(); i++){
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR)
                return i;
        }
        return -1;
    }

}
