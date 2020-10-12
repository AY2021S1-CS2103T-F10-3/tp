package seedu.fma.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.fma.model.AddressBook;
import seedu.fma.model.log.Log;


/**
 * A utility class containing a list of {@code Log} objects to be used in tests.
 */
public class TypicalLogs {

    public static final Log LOG_A = new LogBuilder()
            .withExercise(new ExerciseBuilder().withName("Jumping Jacks").build())
            .withDateTime(LocalDateTime.of(2020, 1, 1, 1, 1))
            .withReps("5")
            .withComment("I love jumping jacks")
            .build();
    public static final Log LOG_B = new LogBuilder()
            .withExercise(new ExerciseBuilder().withName("Lunges").build())
            .withDateTime(LocalDateTime.of(2020, 2, 2, 2, 2))
            .withReps("51")
            .withComment("I love Lunges")
            .build();
    public static final Log LOG_C = new LogBuilder()
            .withExercise(new ExerciseBuilder().withName("Body ups").build())
            .withDateTime(LocalDateTime.of(2020, 3, 3, 3, 3))
            .withReps("15")
            .withComment("I love Body ups")
            .build();
    public static final Log LOG_D = new LogBuilder()
            .withExercise(new ExerciseBuilder().withName("Head shakes").build())
            .withDateTime(LocalDateTime.of(2020, 4, 4, 4, 4))
            .withReps("52")
            .withComment("I love Head shakes")
            .build();
    public static final Log LOG_E = new LogBuilder()
            .withExercise(new ExerciseBuilder().withName("Buddha claps").build())
            .withDateTime(LocalDateTime.of(2020, 5, 5, 5, 5))
            .withReps("53")
            .withComment("I love Buddha claps")
            .build();
    public static final Log LOG_F = new LogBuilder()
            .withExercise(new ExerciseBuilder().withName("Shaking hands").build())
            .withDateTime(LocalDateTime.of(2020, 6, 6, 6, 6))
            .withReps("54")
            .withComment("I love Shaking hands")
            .build();
    public static final Log LOG_G = new LogBuilder()
            .withExercise(new ExerciseBuilder().withName("Punching").build())
            .withDateTime(LocalDateTime.of(2020, 7, 7, 7, 7))
            .withReps("55")
            .withComment("I love Punching")
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalLogs() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    // TODO change Addressbook variables
    public static AddressBook getTypicalLogBook() {
        AddressBook ab = new AddressBook();
        for (Log log : getTypicalLogs()) {
            ab.addLog(log);
        }
        return ab;
    }

    public static List<Log> getTypicalLogs() {
        return new ArrayList<>(Arrays.asList(LOG_A, LOG_B, LOG_C, LOG_D, LOG_E, LOG_F, LOG_G));
    }
}
