package seedu.fma.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.fma.logic.commands.CommandTestUtil.*;

import org.junit.jupiter.api.Test;

import seedu.fma.logic.commands.EditCommand.EditLogDescriptor;
import seedu.fma.testutil.EditLogDescriptorBuilder;

public class EditLogDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditLogDescriptor descriptorWithSameValues = new EditLogDescriptor(EDIT_LOG_DESCRIPTOR_A);
        assertEquals(EDIT_LOG_DESCRIPTOR_A, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(EDIT_LOG_DESCRIPTOR_A, EDIT_LOG_DESCRIPTOR_A);

        // null -> returns false
        assertNotEquals(null, EDIT_LOG_DESCRIPTOR_A);

        // different types -> returns false
        assertNotEquals(5, EDIT_LOG_DESCRIPTOR_A);

        // different values -> returns false
        assertNotEquals(EDIT_LOG_DESCRIPTOR_A, EDIT_LOG_DESCRIPTOR_B);

        // different name -> returns false
        EditLogDescriptor editedAmy = new EditLogDescriptorBuilder(EDIT_LOG_DESCRIPTOR_A)
                .withExercise(VALID_EXERCISE_B).build();
        assertNotEquals(EDIT_LOG_DESCRIPTOR_A, editedAmy);

        // different phone -> returns false
        editedAmy = new EditLogDescriptorBuilder(EDIT_LOG_DESCRIPTOR_A).withExercise(VALID_EXERCISE_B).build();
        assertNotEquals(EDIT_LOG_DESCRIPTOR_A, editedAmy);

        // different email -> returns false
        editedAmy = new EditLogDescriptorBuilder(EDIT_LOG_DESCRIPTOR_A).withReps(VALID_REP_B).build();
        assertNotEquals(EDIT_LOG_DESCRIPTOR_A, editedAmy);

        // different address -> returns false
        editedAmy = new EditLogDescriptorBuilder(EDIT_LOG_DESCRIPTOR_A).withComment(VALID_COMMENT_B).build();
        assertNotEquals(EDIT_LOG_DESCRIPTOR_A, editedAmy);
    }
}
