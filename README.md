# EnchantBooks
## Intro
This plugin adds the /Enchanter command which can be used to purchase enchant books with xp. This plugin uses [EasyEnchants](https://github.com/Exeton/EasyEnchants) which you can use to create enchants for this plugin.

## Pictures

![alt text](https://github.com/Exeton/EnchantBooks/blob/master/Pictures/Enchanter.PNG)
![alt text](https://github.com/Exeton/EnchantBooks/blob/master/Pictures/Dank%20Enchant%20Book.PNG)
![alt text](https://github.com/Exeton/EnchantBooks/blob/master/Pictures/Enchant%20Book%20With%20Odds.PNG)

## Developer Information
The API provides 3 main methods for adding enchants. These methods are static voids inside the EnchantBooks class.  **Be sure to use the tiers ref name when registering enchant books of a given tier. **

```java
//Registers an Enchant for  this plugin (EnchantBooks) and EasyEnchants
registerBookAndEasyEnchant(String tier, String refName, Enchant enchant, EnchantApplicationInfo enchantApplicationInfo)

//Registers a book just for EnchantBooks
registerBook(Enchant enchant, String tier, EnchantApplicationInfo enchantApplicationInfo)

//Registers a book tier
public static void registerBookType(String ref, String tierName, Byte enchantPaneColor)
```
