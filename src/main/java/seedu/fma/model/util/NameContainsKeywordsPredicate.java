package seedu.fma.model.util;

import java.util.List;
import java.util.function.Predicate;

import seedu.fma.commons.util.StringUtil;
import seedu.fma.model.log.Log;

/**
 * Tests that a {@code Log}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Log> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Log log) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordsIgnoreCases(log.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
