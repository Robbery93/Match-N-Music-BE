package nl.robbertij.matchnmusic.service;

import nl.robbertij.matchnmusic.MatchNMusicApplication;
import nl.robbertij.matchnmusic.dto.request.TeacherRequestDto;
import nl.robbertij.matchnmusic.model.Lesson;
import nl.robbertij.matchnmusic.model.Student;
import nl.robbertij.matchnmusic.model.StudentTeacherKey;
import nl.robbertij.matchnmusic.model.Teacher;
import nl.robbertij.matchnmusic.repository.LessonRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {MatchNMusicApplication.class})
@EnableConfigurationProperties
class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @MockBean
    private TeacherRepository teacherRepository;

    @MockBean
    private LessonRepository lessonRepository;

    @Mock
    Teacher teacher;
    @Mock
    Teacher teacher2;
    @Mock
    Teacher teacher3;
    @Mock
    Teacher teacher4;

    @Mock
    Student student1;
    @Mock
    Student student2;
    @Mock
    Student student3;
    @Mock
    Student student4;
    @Mock
    Student student5;

    @Mock
    StudentTeacherKey key1;
    @Mock
    StudentTeacherKey key2;
    @Mock
    StudentTeacherKey key3;
    @Mock
    StudentTeacherKey key4;
    @Mock
    StudentTeacherKey key5;

    @Mock
    Lesson lesson1;
    @Mock
    Lesson lesson2;
    @Mock
    Lesson lesson3;
    @Mock
    Lesson lesson4;
    @Mock
    Lesson lesson5;

    private List<Lesson> lessons = new ArrayList<>();

    @Mock
    TeacherRequestDto teacherRequestDto;

    @BeforeEach
    void setup() {
        teacher = new Teacher (
                1L,
                "Dirk",
                "dirk@gmail.com",
                "35",
                "0698765432",
                "Den Haag",
                null,
                "Gitaar",
                "Halloooo",
                "Super veel",
                "Live lessen",
                null,
                null
        );

        teacher2 = new Teacher();
        teacher2.setInstrument("piano");
        teacher2.setPreferenceForLessonType("Online lessen");

        teacher3 = new Teacher();
        teacher3.setInstrument("guitar");
        teacher3.setPreferenceForLessonType("Online lessen");

        teacher4 = new Teacher();
        teacher4.setInstrument("keyboard");
        teacher4.setPreferenceForLessonType("Live lessen");

        student1.setId(1L);
        student2.setId(2L);
        student3.setId(3L);
        student4.setId(4L);
        student5.setId(5L);

        key1 = new StudentTeacherKey(1L, 1L);
        key2 = new StudentTeacherKey(2L, 1L);
        key3 = new StudentTeacherKey(3L, 1L);
        key4 = new StudentTeacherKey(4L, 1L);
        key5 = new StudentTeacherKey(5L, 1L);

        lesson1 = new Lesson(key1, teacher, student1);
        lesson1.setActive(true);

        lesson2 = new Lesson(key2, teacher, student2);
        lesson2.setActive(true);

        lesson3 = new Lesson(key3, teacher, student3);
        lesson4 = new Lesson(key4, teacher, student4);
        lesson5 = new Lesson(key5, teacher, student5);

        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        lessons.add(lesson4);
        lessons.add(lesson5);

        teacherRequestDto.setName("Rosa");
        teacherRequestDto.setEmail("rosa@gmail.com");
        teacherRequestDto.setAge("31");
        teacherRequestDto.setPhoneNumber("0645678913");
        teacherRequestDto.setResidence("Den Haag");
        teacherRequestDto.setInstrument("piano");
        teacherRequestDto.setDescription("Hallo, ik ben Rosa");
        teacherRequestDto.setExperience("Ik kan heel goed piano spelen");
        teacherRequestDto.setPreferenceForLessonType("Live lessen");
    }

    @DisplayName("Get all Teachers given no parameters")
    @Test
    void getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        teachers.add(teacher2);
        teachers.add(teacher3);
        teachers.add(teacher4);

        when(teacherRepository.findAll()).thenReturn(teachers);

        List<Teacher> found = teacherService.getTeachers();

        assertEquals(4, found.size());
    }

    @DisplayName("Get Teacher by id")
    @Test
    void getTeacher() {
        long id = teacher.getId();

        when(teacherRepository.findById(id))
                .thenReturn(Optional.of(teacher));

        Teacher found = teacherService.getTeacherById(id);

        String expected = "Dirk";
        String actual = found.getName();

        assertEquals(expected, actual);
    }

    @DisplayName("Delete Teacher by id")
    @Test
    void deleteTeacher() {
        long id = teacher.getId();

        when(teacherRepository.existsById(id)).thenReturn(true);

        teacherService.deleteTeacher(id);

        verify(teacherRepository, times(1)).existsById(id);
        verify(teacherRepository, times(1)).deleteById(id);
    }

    @DisplayName("Add a Teacher")
    @Test
    void addTeacher() {
        // same as teacherRequestDto
        Teacher testTeacher = new Teacher(
                2L,
                "Rosa",
                "rosa@gmail.com",
                "31",
                "0645678913",
                "Den Haag",
                null,
                "piano",
                "Hallo, ik ben Rosa",
                "Ik kan heel goed piano spelen",
                "Live lessen",
                null,
                null
        );

        when(teacherRepository.save(any(Teacher.class))).thenReturn(testTeacher);

        Long newId = teacherService.addTeacher(teacherRequestDto);

        when(teacherRepository.findById(newId)).thenReturn(Optional.ofNullable(testTeacher));

        Teacher found = teacherService.getTeacherById(newId);

        String expected = "rosa@gmail.com";
        String actual = found.getEmail();

        assertEquals(expected, actual);
    }

    @DisplayName("Update a Teacher")
    @Test
    void updateTeacher() {
        teacher.setDescription("Heeeeeeeey, dit gaat echt suuuuper goed!");

        when(teacherRepository.findById(1L)).thenReturn(Optional.ofNullable(teacher));
        assertThat(teacher.getDescription()).isEqualTo("Heeeeeeeey, dit gaat echt suuuuper goed!");
        assertThat(teacher.getName()).isEqualTo("Dirk");

        teacherService.updateTeacher(1L, teacher);

        verify(teacherRepository, times(1)).save(teacher);
    }

    @DisplayName("Partially update Teacher")
    @Test
    void partialUpdateTeacher() {
        teacher.setName("Beau");
        teacher.setEmail("beau@gmail.com");
        teacher.setResidence("Rotterdam");

        when(teacherRepository.findById(1L)).thenReturn(Optional.ofNullable(teacher));

        teacherService.partialUpdateTeacher(1L, teacher);

        verify(teacherRepository, times(1)).save(teacher);

        when(teacherRepository.findById(1L)).thenReturn(Optional.ofNullable(teacher));

        Teacher found = teacherService.getTeacherById(1L);

        assertEquals("Beau", found.getName());
        assertEquals("beau@gmail.com", found.getEmail());
        assertEquals("Rotterdam", found.getResidence());
    }

    @DisplayName("Should only return active lessons of Teacher")
    @Test
    void getLessons() {
        long id = teacher.getId();

        when(teacherRepository.findById(id)).thenReturn(Optional.ofNullable(teacher));
        when(lessonRepository.findAllByTeacherId(id)).thenReturn(lessons);

        teacher.setLessons(lessons);

        List<Lesson> foundLessons = teacherService.getLessons(id);

        int expectedNumberOfLessons = 2;
        int actualNumberOfLessons = foundLessons.size();

        assertEquals(expectedNumberOfLessons, actualNumberOfLessons);
        assertTrue(foundLessons.contains(lesson1));
        assertFalse(foundLessons.contains(lesson3));
    }

    @DisplayName("Should only return applications of Teacher")
    @Test
    void getApplications() {
        long id = teacher.getId();

        when(teacherRepository.findById(id)).thenReturn(Optional.ofNullable(teacher));
        when(lessonRepository.findAllByTeacherId(id)).thenReturn(lessons);

        teacher.setApplications(lessons);

        List<Lesson> foundLessons = teacherService.getApplications(id);

        int expectedNumberOfApplications = 3;
        int actualNumberOfApplications = foundLessons.size();

        assertEquals(expectedNumberOfApplications, actualNumberOfApplications);
        assertFalse(foundLessons.contains(lesson2));
        assertTrue(foundLessons.contains(lesson4));
    }
}