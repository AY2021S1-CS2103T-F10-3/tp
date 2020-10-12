//package seedu.address.model.log;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalLogs.ALICE;
//import static seedu.address.testutil.TypicalLogs.BOB;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.log.exceptions.DuplicateLogException;
//import seedu.address.model.log.exceptions.LogNotFoundException;
//import seedu.address.testutil.LogBuilder;
//
//public class UniqueLogListTest {
//
////    private final UniqueLogList uniquePersonList = new UniqueLogList();
////
////    @Test
////    public void contains_nullPerson_throwsNullPointerException() {
////        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
////    }
////
////    @Test
////    public void contains_personNotInList_returnsFalse() {
////        assertFalse(uniquePersonList.contains(ALICE));
////    }
////
////    @Test
////    public void contains_personInList_returnsTrue() {
////        uniquePersonList.add(ALICE);
////        assertTrue(uniquePersonList.contains(ALICE));
////    }
////
////    @Test
////    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
////        uniquePersonList.add(ALICE);
////        Log editedAlice = new LogBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
////                .build();
////        assertTrue(uniquePersonList.contains(editedAlice));
////    }
////
////    @Test
////    public void add_nullPerson_throwsNullPointerException() {
////        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
////    }
////
////    @Test
////    public void add_duplicatePerson_throwsDuplicatePersonException() {
////        uniquePersonList.add(ALICE);
////        assertThrows(DuplicateLogException.class, () -> uniquePersonList.add(ALICE));
////    }
////
////    @Test
////    public void setPerson_nullTargetPerson_throwsNullPointerException() {
////        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
////    }
////
////    @Test
////    public void setPerson_nullEditedPerson_throwsNullPointerException() {
////        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
////    }
////
////    @Test
////    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
////        assertThrows(LogNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
////    }
////
////    @Test
////    public void setPerson_editedPersonIsSamePerson_success() {
////        uniquePersonList.add(ALICE);
////        uniquePersonList.setPerson(ALICE, ALICE);
////        UniqueLogList expectedUniquePersonList = new UniqueLogList();
////        expectedUniquePersonList.add(ALICE);
////        assertEquals(expectedUniquePersonList, uniquePersonList);
////    }
////
////    @Test
////    public void setPerson_editedPersonHasSameIdentity_success() {
////        uniquePersonList.add(ALICE);
////        Log editedAlice = new LogBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
////                .build();
////        uniquePersonList.setPerson(ALICE, editedAlice);
////        UniqueLogList expectedUniquePersonList = new UniqueLogList();
////        expectedUniquePersonList.add(editedAlice);
////        assertEquals(expectedUniquePersonList, uniquePersonList);
////    }
////
////    @Test
////    public void setPerson_editedPersonHasDifferentIdentity_success() {
////        uniquePersonList.add(ALICE);
////        uniquePersonList.setPerson(ALICE, BOB);
////        UniqueLogList expectedUniquePersonList = new UniqueLogList();
////        expectedUniquePersonList.add(BOB);
////        assertEquals(expectedUniquePersonList, uniquePersonList);
////    }
////
////    @Test
////    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
////        uniquePersonList.add(ALICE);
////        uniquePersonList.add(BOB);
////        assertThrows(DuplicateLogException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
////    }
////
////    @Test
////    public void remove_nullPerson_throwsNullPointerException() {
////        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
////    }
////
////    @Test
////    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
////        assertThrows(LogNotFoundException.class, () -> uniquePersonList.remove(ALICE));
////    }
////
////    @Test
////    public void remove_existingPerson_removesPerson() {
////        uniquePersonList.add(ALICE);
////        uniquePersonList.remove(ALICE);
////        UniqueLogList expectedUniquePersonList = new UniqueLogList();
////        assertEquals(expectedUniquePersonList, uniquePersonList);
////    }
////
////    @Test
////    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
////        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniqueLogList) null));
////    }
////
////    @Test
////    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
////        uniquePersonList.add(ALICE);
////        UniqueLogList expectedUniquePersonList = new UniqueLogList();
////        expectedUniquePersonList.add(BOB);
////        uniquePersonList.setPersons(expectedUniquePersonList);
////        assertEquals(expectedUniquePersonList, uniquePersonList);
////    }
////
////    @Test
////    public void setPersons_nullList_throwsNullPointerException() {
////        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Log>) null));
////    }
////
////    @Test
////    public void setPersons_list_replacesOwnListWithProvidedList() {
////        uniquePersonList.add(ALICE);
////        List<Log> logList = Collections.singletonList(BOB);
////        uniquePersonList.setPersons(logList);
////        UniqueLogList expectedUniquePersonList = new UniqueLogList();
////        expectedUniquePersonList.add(BOB);
////        assertEquals(expectedUniquePersonList, uniquePersonList);
////    }
////
////    @Test
////    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
////        List<Log> listWithDuplicateLogs = Arrays.asList(ALICE, ALICE);
////        assertThrows(DuplicateLogException.class, () -> uniquePersonList.setPersons(listWithDuplicateLogs));
////    }
////
////    @Test
////    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
////        assertThrows(UnsupportedOperationException.class, ()
////            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
////    }
//}
