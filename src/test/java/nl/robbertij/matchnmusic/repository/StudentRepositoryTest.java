package nl.robbertij.matchnmusic.repository;

import nl.robbertij.matchnmusic.MatchNMusicApplication;
import nl.robbertij.matchnmusic.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {MatchNMusicApplication.class})
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    // Testing with existing data in data.sql file

    @DisplayName("Find all by name, ignoring case")
    @Test
    void whenFindAllByNameIgnoreCase_thenReturnStudents() {
        List<Student> found = studentRepository.findAllByNameIgnoreCase("robbErt");

        String expected = "Robbert";
        String actual = found.get(0).getName();

        assertEquals(expected, actual);
    }

    @DisplayName("Find by email address")
    @Test
    void whenFindAllByEmail_thenReturnStudent() {
        List<Student> found = studentRepository.findAllByEmail("bas@gmail.com");

        String expected = "bas@gmail.com";
        String actual = found.get(0).getEmail();

        assertEquals(expected, actual);
        assertEquals(1, found.size());
    }

    @DisplayName("Find all by instrument")
    @Test
    void whenFindAllByInstrument_thenReturnStudentsMatchingInstrument() {
        List<Student> found = studentRepository.findAllByInstrument("Zang");

        int expectedSize = 1;
        int actualSize = found.size();

        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("Find all by preference")
    @Test
    void whenFindAllByPreferenceForLessonType_theReturnStudentMatchingPreference() {
        List<Student> found = studentRepository.findAllByInstrumentAndPreferenceForLessonType("Gitaar","Live lessen");

        int expectedSize = 2;
        int actualSize = found.size();

        assertEquals(expectedSize, actualSize);
    }
}