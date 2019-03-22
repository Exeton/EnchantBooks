package online.fireflower.enchant_books;

import com.google.common.collect.HashBiMap;
import me.Test.hammy2899.glow.Glow;
import online.fireflower.easy_enchants.EasyEnchants;
import online.fireflower.easy_enchants.enchant_types.Enchant;
import online.fireflower.enchant_books.commands.EnchanterCommand;
import online.fireflower.enchant_books.commands.GiveBookCommand;
import online.fireflower.enchant_books.enchant_books.BasicEnchantBookInfoReadWriter;
import online.fireflower.enchant_books.enchant_books.BasicSuccessRateParser;
import online.fireflower.enchant_books.enchant_books.EnchantBookApplicationListener;
import online.fireflower.enchant_books.enchant_books.IEnchantBookInfoReadWriter;
import online.fireflower.enchant_books.enchant_books.book_creation.BasicEnchanter;
import online.fireflower.enchant_books.enchant_books.book_creation.BookCreator;
import online.fireflower.enchant_books.enchant_books.book_creation.BookGiver;
import online.fireflower.enchant_books.enchant_books.book_creation.IEnchanter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;

public class EnchantBooks extends JavaPlugin {

    public static String enchantGuiName = "Enchanter";
    public static String xpCostString = ChatColor.YELLOW + "XP Cost: ";
    private Random random;

    IEnchantBookInfoReadWriter enchantBookInfoReadWriter;
    IEnchanter enchanter;

    HashBiMap<String, String> typesAndBookNames = HashBiMap.create(6);
    public static HashMap<String, List<Enchant>> typesAndEnchants = new HashMap<>();
    static HashMap<String, EnchantApplicationInfo> enchantNamesAndApplicationInfo = new HashMap<>();
    List<Byte> enchantColors = new LinkedList<>();
    List<String> enchantNames = new LinkedList<>();

    @Override
    public void onEnable() {

        random = new Random();
        registerGlow();

        EasyEnchants.dependencyBuilder.addTask(() -> runDI(), 10);
        registerBookTypes();

        BookCreator bookCreator = new BookCreator(typesAndBookNames);
        BookGiver bookGiver = new BookGiver(bookCreator);
        this.getCommand("giveBook").setExecutor(new GiveBookCommand(bookGiver));

        List<Integer> bookCosts = new LinkedList<>();
        bookCosts.add(400);
        bookCosts.add(800);
        bookCosts.add(2500);
        bookCosts.add(10000);

        this.getCommand("enchanter").setExecutor(new EnchanterCommand(enchantNames, enchantColors, bookCosts));

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

    private void registerBookTypes(){

        registerBookType("common", "&8&lCommon", (byte)0);
        registerBookType("uncommon", "&2&aUncommon", (byte)5);
        registerBookType("rare", "&b&lRare", (byte)3);
        registerBookType("legendary", "&6&lLegendary", (byte)1);
    }

    private void registerBookType(String ref, String type, Byte enchantColor){

        String enchantName = ChatColor.translateAlternateColorCodes('&', type + " Enchant Book");

        typesAndBookNames.put(ref.toLowerCase(), enchantName);
        enchantColors.add(enchantColor);
        enchantNames.add(enchantName);
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

    public static void registerEnchant(String tier, String refName, Enchant enchant, EnchantApplicationInfo enchantApplicationInfo){
        registerEnchant(enchant, tier, enchantApplicationInfo);
        EasyEnchants.registerEnchant(refName, enchant);
        enchantNamesAndApplicationInfo.put(enchant.displayName, enchantApplicationInfo);
    }



    public static void registerEnchant(Enchant enchant, String tier, EnchantApplicationInfo enchantApplicationInfo){

        if (!typesAndEnchants.containsKey(tier)){
            typesAndEnchants.put(tier, new LinkedList<>());
        }

        List<Enchant> enchants = typesAndEnchants.get(tier);
        enchants.add(enchant);
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
