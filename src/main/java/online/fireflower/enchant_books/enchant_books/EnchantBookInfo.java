package online.fireflower.enchant_books.enchant_books;

import online.fireflower.easy_enchants.enchant_parsing.EnchantInfo;

public class EnchantBookInfo {

    public EnchantInfo enchantInfo;
    public int success;
    public int failure;

    public EnchantBookInfo(EnchantInfo enchantInfo, int success, int failure){
        this.enchantInfo = enchantInfo;
        this.success = success;
        this.failure = failure;
    }

}
