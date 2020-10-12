package seedu.fma.testutil;

import java.time.LocalDateTime;

import seedu.fma.model.exercise.Exercise;
import seedu.fma.model.log.Comment;
import seedu.fma.model.log.Log;
import seedu.fma.model.log.Rep;
import seedu.fma.model.util.Name;

/**
 * A utility class to help with building Log objects.
 */
public class LogBuilder {

    public static final String DEFAULT_REPS = "50";
    public static final String DEFAULT_COMMENT = "This exercise was really tiring";
    public static final int DEFAULT_YEAR = 2020;
    public static final int DEFAULT_MONTH = 1; // January
    public static final int DEFAULT_DAY = 1;
    public static final int DEFAULT_HOUR = 1;
    public static final int DEFAULT_MINUTE = 1;

    private Exercise exercise;
    private LocalDateTime dateTime;
    private Rep reps;
    private Comment comment;

    /**
     * Creates a {@code LogBuilder} with the default details.
     */
    public LogBuilder() {
        exercise = new ExerciseBuilder().build();
        dateTime =
                LocalDateTime.of(DEFAULT_YEAR, DEFAULT_MONTH, DEFAULT_DAY, DEFAULT_HOUR, DEFAULT_MINUTE);
        reps = new Rep(DEFAULT_REPS);
        comment = new Comment(DEFAULT_COMMENT);
    }

    /**
     * Initializes the LogBuilder with the data of {@code logToCopy}.
     */
    public LogBuilder(Log logToCopy) {
        exercise = logToCopy.getExercise();
        dateTime = logToCopy.getDateTime();
        reps = logToCopy.getReps();
        comment = logToCopy.getComment();
    }

    /**
     * Sets the {@code Exercise} of the {@code Log} that we are building.
     */
    public LogBuilder withExercise(Exercise exercise) {
        this.exercise = exercise;
        return this;
    }

    /**
     * Sets the {@code Rep} of the {@code Log} that we are building.
     */
    public LogBuilder withReps(String reps) {
        this.reps = new Rep(reps);
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code Log} that we are building.
     */
    public LogBuilder withComment(String comment) {
        this.comment = new Comment(comment);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Log} that we are building.
     */
    public LogBuilder withDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Log build() {
        return new Log(exercise, reps, comment, dateTime);
    }
}
