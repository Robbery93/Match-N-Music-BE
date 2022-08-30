package nl.robbertij.matchnmusic.service;

import nl.robbertij.matchnmusic.dto.request.StudentRequestDto;
import nl.robbertij.matchnmusic.exception.BadRequestException;
import nl.robbertij.matchnmusic.exception.RecordNotFoundException;
import nl.robbertij.matchnmusic.model.Lesson;
import nl.robbertij.matchnmusic.model.Student;
import nl.robbertij.matchnmusic.model.User;
import nl.robbertij.matchnmusic.repository.LessonRepository;
import nl.robbertij.matchnmusic.repository.StudentRepository;
import nl.robbertij.matchnmusic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          LessonRepository lessonRepository,
                          UserRepository userRepository,
                          UserService userService,
                          FileService fileService) {
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.fileService = fileService;
    }

    public List<Student> getStudents(){
            return studentRepository.findAll();
    }

    public List<Student> getStudentsByInstrumentAndPreference(String instrument, String preference) {
        if(Objects.equals(preference, "Geen voorkeur")) {
            List<Student> allStudents = new ArrayList<>();
            List<Student> liveStudents = studentRepository.findAllByInstrumentAndPreferenceForLessonType(instrument, "Live lessen");
            List<Student> onlineStudents = studentRepository.findAllByInstrumentAndPreferenceForLessonType(instrument, "Online lessen");

            allStudents.addAll(liveStudents);
            allStudents.addAll(onlineStudents);

            return allStudents;
        }
        else {
            return studentRepository.findAllByInstrumentAndPreferenceForLessonType(instrument, preference);
        }
    }

    public Student getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()){
            return optionalStudent.get();
        }
        else throw new RecordNotFoundException("ID does not exist");
    }

    public Student getStudentByEmail(String email) {
        Optional<Student> optionalStudent = studentRepository.findByEmail(email);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        }
        else throw new RecordNotFoundException("Email does not exist");
    }

    public void deleteStudentById(Long id){
        if (studentRepository.existsById(id)) {

            List<Lesson> allLessonsOfStudent = lessonRepository.findAllByStudentId(id);
                lessonRepository.deleteAll(allLessonsOfStudent);

            studentRepository.deleteById(id);
        }
        else throw new RecordNotFoundException("ID does not exist");
    }

    public Long addStudent(StudentRequestDto studentRequestDto){
        String email = studentRequestDto.getEmail();
        List<Student> students = studentRepository.findAllByEmail(email);
        if (students.size() > 0) {
            throw new BadRequestException("Email is already taken");
        }

        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setEmail(studentRequestDto.getEmail());
        student.setAge(studentRequestDto.getAge());
        student.setPhoneNumber(studentRequestDto.getPhoneNumber());
        student.setResidence(studentRequestDto.getResidence());
        student.setPhoto(studentRequestDto.getPhoto());
        student.setInstrument(studentRequestDto.getInstrument());
        student.setRequest(studentRequestDto.getRequest());
        student.setPreferenceForLessonType(studentRequestDto.getPreferenceForLessonType());

        Student newStudent = studentRepository.save(student);
        return newStudent.getId();
    }

    public void updateProfileImage(Long id, MultipartFile file) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            fileService.storeFile(file);

            student.setPhoto(file.getOriginalFilename());
            studentRepository.save(student);
        }
        else throw new RecordNotFoundException("ID does not exist");
    }

    public void updateStudent(Long id, Student student){
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if(optionalStudent.isPresent()) {
            Student storedStudent = optionalStudent.get();

            student.setId(storedStudent.getId());
            studentRepository.save(student);
        }
        else throw new RecordNotFoundException("ID does not exist");
    }

    public void partialUpdateStudent(Long id, Student student) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if(optionalStudent.isPresent()) {
            Student storedStudent = studentRepository.findById(id).orElse(null);

            if (student.getName() != null && !student.getName().isEmpty()){
                assert storedStudent != null;
                storedStudent.setName(student.getName());
            }
            if (student.getEmail() != null && !student.getEmail().isEmpty()){
                assert storedStudent != null;
                storedStudent.setEmail(student.getEmail());
            }
            if (student.getAge() != null && !student.getAge().isEmpty()){
                assert storedStudent != null;
                storedStudent.setAge(student.getAge());
            }
            if (student.getPhoneNumber() != null && !student.getPhoneNumber().isEmpty()) {
                assert storedStudent != null;
                storedStudent.setPhoneNumber(student.getPhoneNumber());
            }
            if (student.getResidence() != null && !student.getResidence().isEmpty()){
                assert storedStudent != null;
                storedStudent.setResidence(student.getResidence());
            }
            if (student.getPhoto() != null && !student.getPhoto().isEmpty()){
                assert storedStudent != null;
                storedStudent.setPhoto(student.getPhoto());
            }
            if (student.getInstrument() != null && !student.getInstrument().isEmpty()){
                assert storedStudent != null;
                storedStudent.setInstrument(student.getInstrument());
            }
            if (student.getRequest() != null && !student.getRequest().isEmpty()){
                assert storedStudent != null;
                storedStudent.setRequest(student.getRequest());
            }
            if (student.getPreferenceForLessonType() != null && !student.getPreferenceForLessonType().isEmpty()){
                assert storedStudent != null;
                storedStudent.setPreferenceForLessonType(student.getPreferenceForLessonType());
            }
            assert storedStudent != null;
            studentRepository.save(storedStudent);
        }
        else throw new RecordNotFoundException("ID does not exist");
    }

    public List<Lesson> getLesson(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            return student.getLesson();
        }
        else throw new RecordNotFoundException("ID does not exist");
    }

    public List<Lesson> getApplications(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            return student.getApplications();
        }
        else throw new RecordNotFoundException("ID does not exist");

    }

    public void linkToCurrentUser(String username, String email) {
        User currentUser = userService.getUser(username);

        Optional<Student> optionalStudent = studentRepository.findByEmail(email);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            currentUser.setStudent(student);
            userRepository.save(currentUser);
        } else throw new RecordNotFoundException("Student niet gevonden");
    }

}
