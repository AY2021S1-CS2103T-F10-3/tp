package seedu.fma.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.fma.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.fma.commons.core.GuiSettings;
import seedu.fma.logic.commands.exceptions.CommandException;
import seedu.fma.model.AddressBook;
import seedu.fma.model.Model;
import seedu.fma.model.ReadOnlyAddressBook;
import seedu.fma.model.ReadOnlyUserPrefs;
import seedu.fma.model.exercise.Exercise;
import seedu.fma.model.log.Log;
import seedu.fma.model.util.Name;
import seedu.fma.testutil.LogBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullLog_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_logAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLogAdded modelStub = new ModelStubAcceptingLogAdded();
        Log validLog = new LogBuilder().build();

        CommandResult commandResult = new AddCommand(validLog).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validLog), commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validLog), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicateLog_throwsCommandException() {
        Log validLog = new LogBuilder().build();
        AddCommand addCommand = new AddCommand(validLog);
        ModelStub modelStub = new ModelStubWithPerson(validLog);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_LOG, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Exercise sitsUp = Exercise.find(new Name("Sit ups"));
        Exercise flyingKicks = Exercise.find(new Name("Flying kicks"));


        Log logSitsUp = new LogBuilder()
                .withExercise(sitsUp)
                .withComment("This is okay")
                .withReps("13")
                .build();
        Log logFlyingKicks = new LogBuilder()
                .withExercise(flyingKicks)
                .withComment("This is not okay!")
                .withReps("13")
                .build();
        AddCommand addSitUpCommand = new AddCommand(logSitsUp);
        AddCommand addFlyingKicksCommand = new AddCommand(logFlyingKicks);

        // same object -> returns true
        assertEquals( addSitUpCommand,  addSitUpCommand);

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(logSitsUp);
        assertEquals( addSitUpCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1,  addSitUpCommand);

        // null -> returns false
        assertNotEquals(null,  addSitUpCommand);

        // different log -> returns false
        assertNotEquals( addSitUpCommand, addFlyingKicksCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLog(Log log) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLog(Log log) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLog(Log target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLog(Log target, Log editedLog) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Log> getFilteredLogList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLogList(Predicate<Log> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single log.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Log log;

        ModelStubWithPerson(Log log) {
            requireNonNull(log);
            this.log = log;
        }

        @Override
        public boolean hasLog(Log log) {
            requireNonNull(log);
            return this.log.isSameLog(log);
        }
    }

    /**
     * A Model stub that always accept the log being added.
     */
    private class ModelStubAcceptingLogAdded extends ModelStub {
        final ArrayList<Log> personsAdded = new ArrayList<>();

        @Override
        public boolean hasLog(Log log) {
            requireNonNull(log);
            return personsAdded.stream().anyMatch(log::isSameLog);
        }

        @Override
        public void addLog(Log log) {
            requireNonNull(log);
            personsAdded.add(log);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
