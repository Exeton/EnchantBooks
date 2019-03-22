package online.fireflower.enchant_books.commands;

import online.fireflower.enchant_books.enchant_books.book_creation.BookGiver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveBookCommand implements CommandExecutor {

    BookGiver bookGiver;

    public GiveBookCommand(BookGiver bookGiver){
        this.bookGiver = bookGiver;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        // /giveCommandBook PlayerName Type
        // /giveCommandBook Type

        if (args.length < 1)
            return false;

        Player target;
        String bookType;

        if (args.length == 1){
            if (!(commandSender instanceof Player))
                return false;

            target = (Player)commandSender;
            bookType = args[0];
        }
        else {
            target = Bukkit.getPlayer(args[0]);
            bookType = args[1];
            if (target == null){
                commandSender.sendMessage(ChatColor.RED + "Could not find player: " + args[0]);
                return true;
            }
        }

        bookGiver.giveBook(target, bookType);
        return true;

    }
}
