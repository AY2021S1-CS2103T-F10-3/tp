package seedu.fma.model;

import javafx.collections.ObservableList;
import seedu.fma.model.log.Log;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the logs list.
     * This list will not contain any duplicate logs.
     */
    ObservableList<Log> getLogList();

}
