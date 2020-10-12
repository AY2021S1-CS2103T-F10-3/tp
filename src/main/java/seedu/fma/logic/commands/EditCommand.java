package seedu.fma.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.fma.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.fma.logic.parser.CliSyntax.PREFIX_EXERCISE;
import static seedu.fma.logic.parser.CliSyntax.PREFIX_REPS;
import static seedu.fma.model.Model.PREDICATE_SHOW_ALL_LOGS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.fma.commons.core.Messages;
import seedu.fma.commons.core.index.Index;
import seedu.fma.commons.util.CollectionUtil;
import seedu.fma.logic.commands.exceptions.CommandException;
import seedu.fma.model.Model;
import seedu.fma.model.exercise.Exercise;
import seedu.fma.model.log.Comment;
import seedu.fma.model.log.Log;
import seedu.fma.model.log.Rep;

/**
 * Edits the details of an existing log in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the log identified "
            + "by the index number used in the displayed log list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EXERCISE + "EXERCISE] "
            + "[" + PREFIX_REPS + "REPS] "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REPS + "3000 "
            + PREFIX_COMMENT + "I love my abs 3000";

    public static final String MESSAGE_EDIT_LOG_SUCCESS = "Edited Log: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LOG = "This log already exists in the address book.";

    private final Index index;
    private final EditLogDescriptor editLogDescriptor;

    /**
     * @param index of the log in the filtered log list to edit
     * @param editLogDescriptor details to edit the log with
     */
    public EditCommand(Index index, EditLogDescriptor editLogDescriptor) {
        requireNonNull(index);
        requireNonNull(editLogDescriptor);

        this.index = index;
        this.editLogDescriptor = new EditLogDescriptor(editLogDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Log> lastShownList = model.getFilteredLogList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOG_DISPLAYED_INDEX);
        }

        Log logToEdit = lastShownList.get(index.getZeroBased());
        Log editedLog = createEditedLog(logToEdit, editLogDescriptor);

        if (!logToEdit.isSameLog(editedLog) && model.hasLog(editedLog)) {
            throw new CommandException(MESSAGE_DUPLICATE_LOG);
        }

        model.setLog(logToEdit, editedLog);
        model.updateFilteredLogList(PREDICATE_SHOW_ALL_LOGS);
        return new CommandResult(String.format(MESSAGE_EDIT_LOG_SUCCESS, editedLog));
    }

    /**
     * Creates and returns a {@code Log} with the details of {@code logToEdit}
     * edited with {@code editLogDescriptor}.
     */
    private static Log createEditedLog(Log logToEdit, EditLogDescriptor editLogDescriptor) {
        assert logToEdit != null;

        Exercise updatedExercise = editLogDescriptor.getExercise().orElse(logToEdit.getExercise());
        Rep updatedRep = editLogDescriptor.getRep().orElse(logToEdit.getReps());
        Comment updatedComment = editLogDescriptor.getComment().orElse(logToEdit.getComment());


        return new Log(updatedExercise, updatedRep, updatedComment);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editLogDescriptor.equals(e.editLogDescriptor);
    }

    /**
     * Stores the details to edit the log with. Each non-empty field value will replace the
     * corresponding field value of the log.
     */
    public static class EditLogDescriptor {
        private Exercise exercise;
        private LocalDateTime dateTime;
        private Rep rep;
        private Comment comment;

        public EditLogDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditLogDescriptor(EditLogDescriptor toCopy) {
            setExercise(toCopy.exercise);
            setDatetime(toCopy.dateTime);
            setComment(toCopy.comment);
            setRep(toCopy.rep);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(exercise, dateTime, comment, rep);
        }

        public void setExercise(Exercise exercise) {
            this.exercise = exercise;
        }

        public Optional<Exercise> getExercise() {
            return Optional.ofNullable(exercise);
        }

        public void setDatetime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<LocalDateTime> getDateTime() {
            return Optional.ofNullable(this.dateTime);
        }

        public void setRep(Rep rep) {
            this.rep = rep;
        }

        public Optional<Rep> getRep() {
            return Optional.ofNullable(rep);
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public Optional<Comment> getComment() {
            return Optional.ofNullable(comment);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLogDescriptor)) {
                return false;
            }

            // state check
            EditLogDescriptor e = (EditLogDescriptor) other;

            return getExercise().equals(e.getExercise())
                    && getDateTime().equals(e.getDateTime())
                    && getComment().equals(e.getComment())
                    && getRep().equals(e.getRep());
        }
    }
}
