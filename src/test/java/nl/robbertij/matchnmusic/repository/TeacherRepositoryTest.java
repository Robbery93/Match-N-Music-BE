package nl.robbertij.matchnmusic.repository;

import nl.robbertij.matchnmusic.MatchNMusicApplication;
import nl.robbertij.matchnmusic.model.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {MatchNMusicApplication.class})
@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    // Testing with existing data drom data.sql file

    @DisplayName("Find all by email")
    @Test
    void findAllByEmail() {
        List<Teacher> found = teacherRepository.findAllByEmail("anke@gmail.com");

        assertEquals("Anke", found.get(0).getName());
        assertEquals(1, found.size());
    }

    @DisplayName("Find all by instrument")
    @Test
    void findAllByInstrumentsContainingIgnoreCase() {
        List<Teacher> found = teacherRepository.findAllByInstrumentEqualsIgnoreCase("Gitaar");

        assertEquals(3, found.size());
    }

    @DisplayName("Find all by preference")
    @Test
    void findAllByPreferenceForLessonType() {
        List<Teacher> found = teacherRepository.findAllByPreferenceForLessonType("Online lessen");

        assertEquals(6, found.size());
    }
}