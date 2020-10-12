package seedu.fma.model.log;

import static java.util.Objects.requireNonNull;
import static seedu.fma.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.fma.model.log.exceptions.DuplicateLogException;
import seedu.fma.model.log.exceptions.LogNotFoundException;

/**
 * A list of logs that enforces uniqueness between its elements and does not allow nulls.
 * A log is considered unique by comparing using {@code Log#isSameLog(Log)}. As such, adding and updating of
 * logs uses Log#isSameLog(Log) for equality so as to ensure that the log being added or updated is
 * unique in terms of identity in the UniqueLogList. However, the removal of a log uses Log#equals(Object) so
 * as to ensure that the log with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Log#isSameLog(Log)
 */
public class UniqueLogList implements Iterable<Log> {

    private final ObservableList<Log> internalList = FXCollections.observableArrayList();
    private final ObservableList<Log> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent log as the given argument.
     */
    public boolean contains(Log toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameLog);
    }

    /**
     * Adds a log to the list.
     * The log must not already exist in the list.
     */
    public void add(Log toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLogException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the log {@code target} in the list with {@code editedLog}.
     * {@code target} must exist in the list.
     * The log identity of {@code editedLog} must not be the same as another existing log in the list.
     */
    public void setLog(Log target, Log editedLog) {
        requireAllNonNull(target, editedLog);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LogNotFoundException();
        }

        if (!target.isSameLog(editedLog) && contains(editedLog)) {
            throw new DuplicateLogException();
        }

        internalList.set(index, editedLog);
    }

    /**
     * Removes the equivalent log from the list.
     * The log must exist in the list.
     */
    public void remove(Log toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LogNotFoundException();
        }
    }

    public void setPersons(UniqueLogList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code logs}.
     * {@code logs} must not contain duplicate logs.
     */
    public void setPersons(List<Log> logs) {
        requireAllNonNull(logs);
        if (!logsAreUnique(logs)) {
            throw new DuplicateLogException();
        }

        internalList.setAll(logs);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Log> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Log> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLogList // instanceof handles nulls
                        && internalList.equals(((UniqueLogList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code logs} contains only unique logs.
     */
    private boolean logsAreUnique(List<Log> logs) {
        for (int i = 0; i < logs.size() - 1; i++) {
            for (int j = i + 1; j < logs.size(); j++) {
                if (logs.get(i).isSameLog(logs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
