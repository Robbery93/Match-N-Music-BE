package nl.robbertij.matchnmusic.repository;

import nl.robbertij.matchnmusic.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByNameIgnoreCase(String name);
    List<Student> findAllByEmail(String email);
    Optional<Student> findByEmail(String email);
    List<Student> findAllByInstrument(String instrument);
    List<Student> findAllByInstrumentAndPreferenceForLessonType(String instrument, String preference);
}
