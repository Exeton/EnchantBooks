package online.fireflower.enchant_books.enchant_books;

import org.bukkit.ChatColor;
import java.util.List;

public class BasicSuccessRateParser implements ISuccessRateParser {


    String successString = ChatColor.YELLOW + "Success: ";
    String failureString = ChatColor.YELLOW + "Failure: ";

    @Override
    public boolean hasSuccessAndFailure(List<String> lore) {
        return hasOdds(lore, successString) && hasOdds(lore, failureString);
    }

    public int getSuccess(List<String> lore) {
        return parseOdds(lore, successString);
    }

    public int getFailure(List<String> lore) {
        return parseOdds(lore, failureString);
    }

    @Override
    public String createSuccessString(int success) {
        return successString + success;
    }

    @Override
    public String createFailureString(int failure) {
        return failureString + failure;
    }

    private static int parseOdds(List<String> lore, String oddsPrefix){

        for (String str : lore){
            if (str.startsWith(oddsPrefix)){
                String oddsStr = str.substring(oddsPrefix.length());
                int odds = Integer.parseInt(oddsStr);
                return odds;
            }
        }

        return 0;
    }

    private static boolean hasOdds(List<String> lore, String oddsPrefix){
        for (String str : lore){
            if (str.startsWith(oddsPrefix))
                return true;
        }
        return false;
    }

}
