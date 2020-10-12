package seedu.fma.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.fma.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.fma.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.fma.logic.commands.CommandTestUtil.showLogAtIndex;
import static seedu.fma.testutil.TypicalIndexes.*;
import static seedu.fma.testutil.TypicalLogs.getTypicalLogBook;

import org.junit.jupiter.api.Test;

import seedu.fma.commons.core.Messages;
import seedu.fma.commons.core.index.Index;
import seedu.fma.model.Model;
import seedu.fma.model.ModelManager;
import seedu.fma.model.UserPrefs;
import seedu.fma.model.log.Log;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalLogBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Log logToDelete = model.getFilteredLogList().get(INDEX_FIRST_LOG.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOG);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LOG_SUCCESS, logToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLog(logToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLogList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_LOG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showLogAtIndex(model, INDEX_FIRST_LOG);

        Log logToDelete = model.getFilteredLogList().get(INDEX_FIRST_LOG.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOG);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LOG_SUCCESS, logToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLog(logToDelete);
        showNoLog(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showLogAtIndex(model, INDEX_FIRST_LOG);

        Index outOfBoundIndex = INDEX_SECOND_LOG;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLogList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_LOG_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_LOG);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_LOG);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_LOG);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different log -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoLog(Model model) {
        model.updateFilteredLogList(p -> false);

        assertTrue(model.getFilteredLogList().isEmpty());
    }
}
