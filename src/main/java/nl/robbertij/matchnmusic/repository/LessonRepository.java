package nl.robbertij.matchnmusic.repository;


import nl.robbertij.matchnmusic.model.Lesson;
import nl.robbertij.matchnmusic.model.StudentTeacherKey;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, StudentTeacherKey> {

    List<Lesson> findAllByTeacherId(long teacherId);
    List<Lesson> findAllByStudentId(long studentId);
}
