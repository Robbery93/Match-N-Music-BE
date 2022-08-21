package nl.robbertij.matchnmusic.service;

import nl.robbertij.matchnmusic.exception.BadRequestException;
import nl.robbertij.matchnmusic.exception.RecordNotFoundException;
import nl.robbertij.matchnmusic.model.Lesson;
import nl.robbertij.matchnmusic.model.Student;
import nl.robbertij.matchnmusic.model.StudentTeacherKey;
import nl.robbertij.matchnmusic.model.Teacher;
import nl.robbertij.matchnmusic.repository.LessonRepository;
import nl.robbertij.matchnmusic.repository.StudentRepository;
import nl.robbertij.matchnmusic.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository,
                         StudentRepository studentRepository,
                         TeacherRepository teacherRepository) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public Lesson getLessonById(long teacherId, long studentId) {
        StudentTeacherKey id = new StudentTeacherKey(studentId, teacherId);
        if (lessonRepository.existsById(id)) {
            return lessonRepository.findById(id).orElse(null);
        }
        else {
            throw new RecordNotFoundException("ID does not exist");
        }
    }

    public void deleteLesson(long studentId, long teacherId) {
        StudentTeacherKey id = new StudentTeacherKey(studentId, teacherId);
        if (lessonRepository.existsById(id)) {
            lessonRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("ID does not exist");
        }
    }


    public StudentTeacherKey applyForLesson(long studentId, long teacherId){
        StudentTeacherKey id = new StudentTeacherKey(studentId, teacherId);

        if (lessonRepository.existsById(id)) {
            throw new BadRequestException("Combination of Student and Teacher already exists");
        }

        else {
            if (!studentRepository.existsById(studentId)) {
                throw new RecordNotFoundException("ID does not exist");
            }
            Student student = studentRepository.findById(studentId).orElse(null);

            if (!teacherRepository.existsById(teacherId)) {
                throw new RecordNotFoundException("ID does not exist");
            }
            Teacher teacher = teacherRepository.findById(teacherId).orElse(null);

            Lesson newLesson = new Lesson();
            newLesson.setStudent(student);
            newLesson.setTeacher(teacher);

            newLesson.setId(id);
            lessonRepository.save(newLesson);
            return id;
        }
    }


    public void updateHomework(long studentId, long teacherId, Lesson lesson) {
        StudentTeacherKey id = new StudentTeacherKey(studentId, teacherId);
        if(lessonRepository.findById(id).isEmpty()) throw new RecordNotFoundException("ID does not exist");

        Lesson existingLesson = lessonRepository.findById(id).orElse(null);
        if(lesson.getHomework() != null) {
            if (!existingLesson.isActive()) {
                existingLesson.setActive(true);
            }
            existingLesson.setHomework(lesson.getHomework());
        } else {
            if (!existingLesson.isActive()) {
                existingLesson.setActive(true);
            }
            existingLesson.setHomework("Er is nog geen huiswerk opgegeven.");
        }

        List<Lesson> applicationsOfStudent = lessonRepository.findAllByStudentId(studentId);
        for (Lesson application : applicationsOfStudent) {
            if (!application.isActive()) {
                lessonRepository.delete(application);
            }
        }

        lessonRepository.save(existingLesson);
    }
}
