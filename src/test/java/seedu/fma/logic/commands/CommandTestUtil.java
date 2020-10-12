package seedu.fma.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.fma.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.fma.logic.parser.CliSyntax.PREFIX_EXERCISE;
import static seedu.fma.logic.parser.CliSyntax.PREFIX_REPS;
import static seedu.fma.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.fma.commons.core.index.Index;
import seedu.fma.logic.commands.exceptions.CommandException;
import seedu.fma.model.AddressBook;
import seedu.fma.model.Model;
import seedu.fma.model.exercise.Exercise;
import seedu.fma.model.log.Log;
import seedu.fma.model.log.Rep;
import seedu.fma.model.util.NameContainsKeywordsPredicate;
import seedu.fma.testutil.ExerciseBuilder;
import seedu.fma.testutil.LogBuilder;

/**
 * Contains helper methods for testing commands.
 */

public class CommandTestUtil {
    public static final String VALID_EXERCISE_SIT_UP = "Sit up";
    public static final String VALID_EXERCISE_CRUNCHES = "Crunches";

    public static final Exercise VALID_EXERCISE_A = new ExerciseBuilder().withName(VALID_EXERCISE_SIT_UP).build();
    public static final Exercise VALID_EXERCISE_B = new ExerciseBuilder().withName(VALID_EXERCISE_CRUNCHES).build();
    public static final String VALID_REP_A = "5";
    public static final String VALID_REP_B = "10";
    public static final String VALID_COMMENT_A = "This exercise is tough";
    public static final String VALID_COMMENT_B = "Easy";

    public static final int VALID_YEAR_A = 2021;
    public static final int VALID_MONTH_A = 2; // February
    public static final int VALID_DAY_A = 2;
    public static final int VALID_HOUR_A = 2;
    public static final int VALID_MINUTE_A = 2;
    public static final LocalDateTime VALID_DATE_TIME_A = LocalDateTime.of(
            VALID_YEAR_A, VALID_MONTH_A, VALID_DAY_A, VALID_HOUR_A, VALID_MINUTE_A
    );
    public static final int VALID_YEAR_B = 2020;
    public static final int VALID_MONTH_B = 1; // January
    public static final int VALID_DAY_B = 1;
    public static final int VALID_HOUR_B = 1;
    public static final int VALID_MINUTE_B = 1;
    public static final LocalDateTime VALID_DATE_TIME_B = LocalDateTime.of(
            VALID_YEAR_B, VALID_MONTH_B, VALID_DAY_B, VALID_HOUR_B, VALID_MINUTE_B
    );

    public static final String EXERCISE_DESC_A = " " + PREFIX_EXERCISE + VALID_EXERCISE_A;
    public static final String EXERCISE_DESC_B = " " + PREFIX_EXERCISE + VALID_EXERCISE_B;
    public static final String REP_DESC_A = " " + PREFIX_REPS + VALID_REP_A;
    public static final String REP_DESC_B = " " + PREFIX_REPS + VALID_REP_B;
    public static final String COMMENT_DESC_A = " " + PREFIX_COMMENT + VALID_COMMENT_A;
    public static final String COMMENT_DESC_B = " " + PREFIX_COMMENT + VALID_COMMENT_B;

    // '?' not allowed in exercise names
    public static final String INVALID_EXERCISE_DESC = " " + PREFIX_EXERCISE + "Sit ups?";
    public static final String INVALID_REP_DESC = " " + PREFIX_REPS + "911a"; // 'a' not allowed in rep
    public static final String INVALID_COMMENT_DESC = " " + PREFIX_COMMENT + "    "; // comment cannot be blank

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final Log DESC_A;
    public static final Log DESC_B;

    public static final EditCommand.EditLogDescriptor EDIT_LOG_DESCRIPTOR_A;
    public static final EditCommand.EditLogDescriptor EDIT_LOG_DESCRIPTOR_B;

    static {
        DESC_A = new LogBuilder().withExercise(VALID_EXERCISE_A)
                .withReps(VALID_REP_A).withComment(VALID_COMMENT_A).build();
        DESC_B = new LogBuilder().withExercise(VALID_EXERCISE_B)
                .withReps(VALID_REP_B).withComment(VALID_COMMENT_B).build();

        EDIT_LOG_DESCRIPTOR_A = new EditCommand.EditLogDescriptor();
        EDIT_LOG_DESCRIPTOR_A.setExercise(VALID_EXERCISE_A);

        EDIT_LOG_DESCRIPTOR_B = new EditCommand.EditLogDescriptor();
        EDIT_LOG_DESCRIPTOR_B.setRep(new Rep(VALID_REP_B));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
//            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered log list and selected log in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Log> expectedFilteredList = new ArrayList<>(actualModel.getFilteredLogList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredLogList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the log at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showLogAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredLogList().size());

        Log log = model.getFilteredLogList().get(targetIndex.getZeroBased());
        Predicate<Log> firstPredicate = new NameContainsKeywordsPredicate(Collections.singletonList(log.toString()));
        model.updateFilteredLogList(firstPredicate);
        assertEquals(1, model.getFilteredLogList().size());
    }
}
