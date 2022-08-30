package nl.robbertij.matchnmusic.service;

import nl.robbertij.matchnmusic.MatchNMusicApplication;
import nl.robbertij.matchnmusic.model.Lesson;
import nl.robbertij.matchnmusic.model.Student;
import nl.robbertij.matchnmusic.model.StudentTeacherKey;
import nl.robbertij.matchnmusic.model.Teacher;
import nl.robbertij.matchnmusic.repository.LessonRepository;
import nl.robbertij.matchnmusic.repository.StudentRepository;
import nl.robbertij.matchnmusic.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {MatchNMusicApplication.class})
@EnableConfigurationProperties
class LessonServiceTest {

    @Autowired
    private LessonService lessonService;

    @MockBean
    private LessonRepository lessonRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private TeacherRepository teacherRepository;

    @Mock
    private Student student;
    @Mock
    private Student student2;

    @Mock
    private Teacher teacher;

    @Mock
    private StudentTeacherKey key;
    @Mock
    private StudentTeacherKey key2;

    @Mock
    private Lesson lesson;
    @Mock
    private Lesson lesson2;

    @BeforeEach
    void setup() {
        student = new Student();
        student.setId(1L);
        student2 = new Student();
        student2.setId(2L);

        teacher = new Teacher();
        teacher.setId(1L);

        key = new StudentTeacherKey();
        key.setStudentId(student.getId());
        key.setTeacherId(teacher.getId());

        key2 = new StudentTeacherKey();
        key2.setStudentId(student2.getId());
        key2.setTeacherId(teacher.getId());

        // active Lesson
        lesson = new Lesson();
        lesson.setId(key);
        lesson.setStudent(student);
        lesson.setTeacher(teacher);
        lesson.setHomework("Er is nog veel werk aan de winkel.");

//        // application
        lesson2 = new Lesson();
        lesson2.setId(key2);
        lesson2.setStudent(student2);
        lesson2.setTeacher(teacher);
    }

    @DisplayName("Get Lesson by Id")
    @Test
    void getLessonById() {
        StudentTeacherKey id = lesson.getId();
        long teacherId = teacher.getId();
        long studentId = student.getId();

        when(lessonRepository.existsById(id)).thenReturn(true);
        when(lessonRepository.findById(id)).thenReturn(Optional.ofNullable(lesson));

        Lesson found = lessonService.getLessonById(teacherId, studentId);

        Student expected = student;
        Student actual = found.getStudent();

        assertEquals(expected, actual);
        verify(lessonRepository, times(1)).existsById(id);
        verify(lessonRepository, times(1)).findById(id);
    }

    @DisplayName("Delete a Lesson by Id")
    @Test
    void deleteLesson() {
        StudentTeacherKey id = lesson.getId();
        long teacherId = teacher.getId();
        long studentId = student.getId();

        when(lessonRepository.existsById(id)).thenReturn(true);

        lessonService.deleteLesson(studentId, teacherId);

        verify(lessonRepository, times(1)).existsById(id);
        verify(lessonRepository, times(1)).deleteById(id);
    }

    @DisplayName("Create a new Lesson (as an application)")
    @Test
    void applyForLesson() {
        long teacherId = teacher.getId();
        long studentId = student2.getId();

        when(studentRepository.existsById(studentId)).thenReturn(true);
        when(studentRepository.findById(studentId)).thenReturn(Optional.ofNullable(student2));
        when(teacherRepository.existsById(teacherId)).thenReturn(true);
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.ofNullable(teacher));

        StudentTeacherKey newKey = lessonService.applyForLesson(studentId, teacherId);

        // Should make this new Lesson
        Lesson newLesson = new Lesson(newKey, teacher, student2);

        when(lessonRepository.existsById(newKey)).thenReturn(true);
        when(lessonRepository.findById(newKey)).thenReturn(Optional.of(newLesson));

        Lesson found = lessonService.getLessonById(teacherId, studentId);

        assertEquals(student2, found.getStudent());
        assertEquals(teacher, found.getTeacher());
    }

    @DisplayName("Update homework of existing Lesson, or activate Lesson by adding homework")
    @Test
    void updateHomework() {
        Long studentId = student.getId();
        Long teacherId = teacher.getId();
        StudentTeacherKey lessonId = lesson.getId();

        List<Lesson> allLessons = new ArrayList<>();
        allLessons.add(lesson);
        allLessons.add(lesson2);

        when(lessonRepository.existsById(lessonId)).thenReturn(true);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.ofNullable(lesson));
        when(lessonRepository.findAllByStudentId(studentId)).thenReturn(allLessons);

        assertEquals("Er is nog veel werk aan de winkel.", lesson.getHomework());

        lesson.setHomework("Dit is weer een nieuwe les");
        lessonService.updateHomework(studentId, teacherId, lesson);

        verify(lessonRepository, times(1)).delete(lesson2);
        verify(lessonRepository, times(1)).save(lesson);

        assertEquals("Dit is weer een nieuwe les", lesson.getHomework());
    }
}