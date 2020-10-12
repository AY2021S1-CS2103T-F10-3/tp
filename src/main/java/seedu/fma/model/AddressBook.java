package seedu.fma.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.fma.model.log.Log;
import seedu.fma.model.log.UniqueLogList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameLog comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueLogList logs;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        logs = new UniqueLogList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the log list with {@code logs}.
     * {@code logs} must not contain duplicate logs.
     */
    public void setLogs(List<Log> logs) {
        this.logs.setPersons(logs);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setLogs(newData.getLogList());
    }

    //// log-level operations

    /**
     * Returns true if a log with the same identity as {@code log} exists in the address book.
     */
    public boolean hasLog(Log log) {
        requireNonNull(log);
        return logs.contains(log);
    }

    /**
     * Adds a log to the address book.
     * The log must not already exist in the address book.
     */
    public void addLog(Log p) {
        logs.add(p);
    }

    /**
     * Replaces the given log {@code target} in the list with {@code editedLog}.
     * {@code target} must exist in the address book.
     * The log identity of {@code editedLog} must not be the same as another existing log in the address book.
     */
    public void setLog(Log target, Log editedLog) {
        requireNonNull(editedLog);

        logs.setLog(target, editedLog);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeLog(Log key) {
        logs.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return logs.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Log> getLogList() {
        return logs.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && logs.equals(((AddressBook) other).logs));
    }

    @Override
    public int hashCode() {
        return logs.hashCode();
    }
}
