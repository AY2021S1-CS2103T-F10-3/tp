package seedu.fma.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.fma.logic.commands.CommandTestUtil.*;
import static seedu.fma.testutil.TypicalIndexes.INDEX_FIRST_LOG;
import static seedu.fma.testutil.TypicalIndexes.INDEX_SECOND_LOG;
import static seedu.fma.testutil.TypicalLogs.getTypicalLogBook;

import org.junit.jupiter.api.Test;

import seedu.fma.commons.core.Messages;
import seedu.fma.commons.core.index.Index;
import seedu.fma.logic.commands.EditCommand.EditLogDescriptor;
import seedu.fma.model.AddressBook;
import seedu.fma.model.Model;
import seedu.fma.model.ModelManager;
import seedu.fma.model.UserPrefs;
import seedu.fma.model.log.Log;
import seedu.fma.testutil.EditLogDescriptorBuilder;
import seedu.fma.testutil.LogBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */

public class EditCommandTest {

    private Model model = new ModelManager(getTypicalLogBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Log editedLog = new LogBuilder().withExercise(VALID_EXERCISE_B).withComment("This is boring").build();
        EditLogDescriptor descriptor = new EditLogDescriptorBuilder(editedLog).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOG, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOG_SUCCESS, editedLog);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLog(model.getFilteredLogList().get(0), editedLog);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastLog = Index.fromOneBased(model.getFilteredLogList().size());
        Log lastLog = model.getFilteredLogList().get(indexLastLog.getZeroBased());

        LogBuilder logInList = new LogBuilder(lastLog);
        Log editedLog = logInList.withExercise(VALID_EXERCISE_A).withComment(VALID_COMMENT_A)
                .withReps(VALID_REP_A).build();

        EditLogDescriptor descriptor = new EditLogDescriptorBuilder().withExercise(VALID_EXERCISE_A)
                .withComment(VALID_COMMENT_A).withReps(VALID_REP_A).build();
        EditCommand editCommand = new EditCommand(indexLastLog, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOG_SUCCESS, editedLog);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLog(lastLog, editedLog);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOG, new EditLogDescriptor());
        Log editedLog = model.getFilteredLogList().get(INDEX_FIRST_LOG.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOG_SUCCESS, editedLog);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showLogAtIndex(model, INDEX_FIRST_LOG);

        Log logInFilteredList = model.getFilteredLogList().get(INDEX_FIRST_LOG.getZeroBased());
        Log editedLog = new LogBuilder(logInFilteredList).withExercise(VALID_EXERCISE_B).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOG,
                new EditLogDescriptorBuilder().withExercise(VALID_EXERCISE_B).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOG_SUCCESS, editedLog);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLog(model.getFilteredLogList().get(0), editedLog);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidLogIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLogList().size() + 1);
        EditLogDescriptor descriptor = new EditLogDescriptorBuilder().withExercise(VALID_EXERCISE_A).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_LOG_DISPLAYED_INDEX);
    }


     /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidLogIndexFilteredList_failure() {
        showLogAtIndex(model, INDEX_FIRST_LOG);
        Index outOfBoundIndex = INDEX_SECOND_LOG;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLogList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditLogDescriptorBuilder().withExercise(VALID_EXERCISE_A).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_LOG_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_LOG, EDIT_LOG_DESCRIPTOR_A);

        // same values -> returns true
        EditCommand.EditLogDescriptor copyDescriptor = EDIT_LOG_DESCRIPTOR_A;
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_LOG, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_LOG, EDIT_LOG_DESCRIPTOR_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LOG, EDIT_LOG_DESCRIPTOR_B)));
    }

}

