package online.fireflower.enchant_books.enchant_books;

import java.util.List;

public interface ISuccessRateParser {

    boolean hasSuccessAndFailure(List<String> lore);
    int getSuccess(List<String> lore);
    int getFailure(List<String> lore);

    String createSuccessString(int success);
    String createFailureString(int failure);
}
