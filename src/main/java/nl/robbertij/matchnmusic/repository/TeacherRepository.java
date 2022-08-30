package nl.robbertij.matchnmusic.repository;

import nl.robbertij.matchnmusic.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findAllByEmail(String email);
    Optional<Teacher> findByEmail(String email);
    List<Teacher> findAllByInstrumentAndPreferenceForLessonType(String instrument, String preference);
    List<Teacher> findAllByInstrumentEqualsIgnoreCase(String instrument);
    List<Teacher> findAllByPreferenceForLessonType(String preference);
}
