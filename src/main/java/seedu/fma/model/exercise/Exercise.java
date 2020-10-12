package seedu.fma.model.exercise;

import static seedu.fma.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.fma.commons.util.IntegerUtil.requirePositiveInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import seedu.fma.model.exercise.exceptions.ExerciseNotFoundException;
import seedu.fma.model.util.Name;
import seedu.fma.model.util.SampleDataUtil;

/**
 * Represents a Exercise in the log book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Exercise {
    public static final String MESSAGE_CONSTRAINTS =
            "Exercise does not exist";

    private static List<Exercise> exerciseList = new ArrayList<>();

    // Identity fields
    private final Name name;
    private final int caloriesPerRep;

    // TODO: Exercises currently hardcoded
    static {
        exerciseList.addAll(Arrays.asList(SampleDataUtil.getSampleExercises()));
    }

    /**
     * Every field must be present and not null.
     */
    public Exercise(Name name, int caloriesPerRep) {
        requireAllNonNull(name, caloriesPerRep);
        requirePositiveInteger(caloriesPerRep);
        this.name = name;
        this.caloriesPerRep = caloriesPerRep;
    }

    /**
     * Returns an existing exercise with the same Name, or null if none are found.
     */
    public static Exercise find(Name name) throws ExerciseNotFoundException {
        for (Exercise e : exerciseList) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        throw new ExerciseNotFoundException();
    }

    public Name getName() {
        return name;
    }

    public int getCaloriesPerRep() {
        return caloriesPerRep;
    }

    /**
     * Returns true if both exercises have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (!(other instanceof Exercise)) {
            return false;
        }

        Exercise otherExercise = (Exercise) other;
        return otherExercise.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, caloriesPerRep);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" CaloriesPerRep: ")
                .append(getCaloriesPerRep());
        return builder.toString();
    }

}
