package nl.robbertij.matchnmusic.controller;

import nl.robbertij.matchnmusic.dto.request.StudentRequestDto;
import nl.robbertij.matchnmusic.model.Student;
import nl.robbertij.matchnmusic.model.StudentTeacherKey;
import nl.robbertij.matchnmusic.service.LessonService;
import nl.robbertij.matchnmusic.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@CrossOrigin
@RequestMapping(path = "/students")
public class StudentController {
    private final StudentService studentService;
    private final LessonService lessonService;

    @Autowired
    public StudentController(StudentService studentService,
                             LessonService lessonService) {
        this.studentService = studentService;
        this.lessonService = lessonService;
    }

    // Endpoints for students

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping(path = "/")
    public ResponseEntity<Object> getStudentsByInstrumentAndPreference(@RequestParam(name = "instrument") String instrument,
                                                                       @RequestParam(name = "preference") String preference){
        return ResponseEntity.ok(studentService.getStudentsByInstrumentAndPreference(instrument, preference));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping(path = "/email={email}")
    public ResponseEntity<Object> getStudentByEmail(@PathVariable String email) {
        return ResponseEntity.ok(studentService.getStudentByEmail(email));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> addStudent(@Valid @RequestBody StudentRequestDto studentRequestDto) {
        Long newId = studentService.addStudent(studentRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path="/{id}/upload")
    public ResponseEntity<Object> updateProfileImage(@PathVariable Long id, @RequestParam MultipartFile file) {
        studentService.updateProfileImage(id, file);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        studentService.updateStudent(id, student);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Object> partialUpdateStudent(@PathVariable Long id, @RequestBody Student student) {
        studentService.partialUpdateStudent(id, student);

        return ResponseEntity.noContent().build();
    }

    // Endpoints for lesson

    @GetMapping(path = "/{id}/lesson")
    public ResponseEntity<Object> getLesson(@PathVariable("id") Long id) {
        return ResponseEntity.ok(studentService.getLesson(id));
    }

    @GetMapping(path = "{id}/applications")
    public ResponseEntity<Object> getApplications(@PathVariable("id") Long id) {
        return ResponseEntity.ok(studentService.getApplications(id));
    }

    @DeleteMapping(path = "/{id}/unsubscribe")
    public ResponseEntity<Object> unsubscribeLesson(@PathVariable("id") Long studentId,
                                              @RequestParam("teacher_id") Long teacherId) {
        lessonService.deleteLesson(studentId, teacherId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(path = "/{id}/apply")
    public ResponseEntity<Object> applyForLesson(@PathVariable(name = "id") Long studentId,
                                                 @RequestParam(name = "teacher_id") Long teacherId) {
        StudentTeacherKey newId = lessonService.applyForLesson(studentId, teacherId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // Link to User

    @PatchMapping(path="/linkuser/{username}")
    public ResponseEntity<Object> addToUser(@PathVariable(name ="username") String username,
                                            @RequestParam(name = "email") String email) {
       studentService.linkToCurrentUser(username, email);
       return ResponseEntity.noContent().build();
    }

}
