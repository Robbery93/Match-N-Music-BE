package nl.robbertij.matchnmusic.repository;

import nl.robbertij.matchnmusic.MatchNMusicApplication;
import nl.robbertij.matchnmusic.model.Lesson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {MatchNMusicApplication.class})
@DataJpaTest
class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;

    // Using data from data.sql file

    @Test
    void findAllByTeacherId() {
        List<Lesson> found = lessonRepository.findAllByTeacherId(1);

        assertEquals(4, found.size());
    }

    @Test
    void findAllByStudentId() {
        List<Lesson> found = lessonRepository.findAllByStudentId(1);

        assertEquals(4, found.size());
    }
}