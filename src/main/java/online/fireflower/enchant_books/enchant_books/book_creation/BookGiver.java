package online.fireflower.enchant_books.enchant_books.book_creation;

import online.fireflower.enchant_books.EnchantBooks;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BookGiver {

    BookCreator bookCreator;

    public BookGiver(BookCreator bookCreator){
        this.bookCreator = bookCreator;
    }

    public void giveBook(Player target, String bookType){

        ItemStack item = bookCreator.getBook(bookType);
        EnchantBooks.giveOrDrop(target, item);

    }
}
