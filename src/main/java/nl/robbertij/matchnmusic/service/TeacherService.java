package nl.robbertij.matchnmusic.service;

import nl.robbertij.matchnmusic.dto.request.TeacherRequestDto;
import nl.robbertij.matchnmusic.exception.BadRequestException;
import nl.robbertij.matchnmusic.exception.RecordNotFoundException;
import nl.robbertij.matchnmusic.model.Lesson;
import nl.robbertij.matchnmusic.model.Teacher;
import nl.robbertij.matchnmusic.model.User;
import nl.robbertij.matchnmusic.repository.LessonRepository;
import nl.robbertij.matchnmusic.repository.TeacherRepository;
import nl.robbertij.matchnmusic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository,
                          LessonRepository lessonRepository,
                          UserRepository userRepository,
                          UserService userService,
                          FileService fileService) {
        this.teacherRepository = teacherRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.fileService = fileService;
    }

    public List<Teacher> getTeachers(){
        return teacherRepository.findAll();
    }

    public List<Teacher> getTeachersByInstrumentAndPreference(String instrument, String preference){
        if(Objects.equals(preference, "Geen voorkeur")) {
            List<Teacher> availableTeachers = new ArrayList<>();
            List<Teacher> liveTeachers = teacherRepository.findAllByInstrumentAndPreferenceForLessonType(instrument, "Live lessen");
            List<Teacher> onlineTeachers = teacherRepository.findAllByInstrumentAndPreferenceForLessonType(instrument,"Online lessen");

            availableTeachers.addAll(liveTeachers);
            availableTeachers.addAll(onlineTeachers);


            Collections.shuffle(availableTeachers);

            return availableTeachers;
        }
        else {
            return teacherRepository.findAllByInstrumentAndPreferenceForLessonType(instrument, preference);
        }
    }

    public Teacher getTeacherById(Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);

        if (optionalTeacher.isPresent()){
            return optionalTeacher.get();
        }
        else {
            throw new RecordNotFoundException("ID does not exist");
        }
    }

    public Teacher getTeacherByEmail(String email) {
        Optional<Teacher> optionalTeacher = teacherRepository.findByEmail(email);

        if (optionalTeacher.isPresent()) {
            return optionalTeacher.get();
        }
         else throw new RecordNotFoundException("Email does not exist");
    }

    public void deleteTeacher(Long id) {
        if(teacherRepository.existsById(id)) {

            List<Lesson> allLessonsOfTeacher = lessonRepository.findAllByTeacherId(id);
            lessonRepository.deleteAll(allLessonsOfTeacher);
            teacherRepository.deleteById(id);
        }
        else {
            throw new RecordNotFoundException("ID does not exist");
        }
    }

    public Long addTeacher(TeacherRequestDto teacherRequestDto) {
        String email = teacherRequestDto.getEmail();
        List<Teacher> teachers = teacherRepository.findAllByEmail(email);
        if(teachers.size() > 0) {
            throw new BadRequestException("Email is already taken");
        }

        Teacher teacher = new Teacher();
        teacher.setName(teacherRequestDto.getName());
        teacher.setEmail(teacherRequestDto.getEmail());
        teacher.setAge(teacherRequestDto.getAge());
        teacher.setPhoneNumber(teacherRequestDto.getPhoneNumber());
        teacher.setResidence(teacherRequestDto.getResidence());
        teacher.setPhoto(teacherRequestDto.getPhoto());
        teacher.setInstrument(teacherRequestDto.getInstrument());
        teacher.setDescription(teacherRequestDto.getDescription());
        teacher.setExperience(teacherRequestDto.getExperience());
        teacher.setPreferenceForLessonType(teacherRequestDto.getPreferenceForLessonType());

        Teacher newTeacher = teacherRepository.save(teacher);
        return newTeacher.getId();
    }

    public void updateProfileImage(Long id, MultipartFile file) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);

        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            fileService.storeFile(file);

            teacher.setPhoto(file.getOriginalFilename());
            teacherRepository.save(teacher);
        }
        else throw new RecordNotFoundException("ID does not exist");
    }

    public void updateTeacher(Long id, Teacher teacher){
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);

        if(optionalTeacher.isPresent()){
            Teacher storedTeacher = optionalTeacher.get();

            teacher.setId(storedTeacher.getId());
            teacherRepository.save(teacher);
        }
        else {
            throw new RecordNotFoundException("ID does not exist");
        }
    }

    public void partialUpdateTeacher(Long id, Teacher teacher){
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);

        if(optionalTeacher.isPresent()){
            Teacher storedTeacher = teacherRepository.findById(id).orElse(null);

            if (teacher.getName() != null && !teacher.getName().isEmpty()) {
                assert storedTeacher != null;
                storedTeacher.setName(teacher.getName());
            }
            if (teacher.getEmail() != null && !teacher.getEmail().isEmpty()) {
                assert storedTeacher != null;
                storedTeacher.setEmail(teacher.getEmail());
            }
            if (teacher.getAge() != null && !teacher.getAge().isEmpty()) {
                assert storedTeacher != null;
                storedTeacher.setAge(teacher.getAge());
            }
            if (teacher.getPhoneNumber() != null && !teacher.getPhoneNumber().isEmpty()) {
                assert storedTeacher != null;
                storedTeacher.setPhoneNumber(teacher.getPhoneNumber());
            }
            if (teacher.getResidence() != null && !teacher.getResidence().isEmpty()) {
                assert storedTeacher != null;
                storedTeacher.setResidence(teacher.getResidence());
            }
            if (teacher.getPhoto() != null && !teacher.getPhoto().isEmpty()){
                assert storedTeacher != null;
                storedTeacher.setPhoto(teacher.getPhoto());
            }
            if (teacher.getInstrument() != null && !teacher.getInstrument().isEmpty()) {
                assert storedTeacher != null;
                storedTeacher.setInstrument(teacher.getInstrument());
            }
            if (teacher.getDescription() != null && !teacher.getDescription().isEmpty()) {
                assert storedTeacher != null;
                storedTeacher.setDescription(teacher.getDescription());
            }
            if (teacher.getExperience() != null && !teacher.getExperience().isEmpty()) {
                assert storedTeacher != null;
                storedTeacher.setExperience(teacher.getExperience());
            }
            if (teacher.getPreferenceForLessonType() != null && !teacher.getPreferenceForLessonType().isEmpty()) {
                assert storedTeacher != null;
                storedTeacher.setPreferenceForLessonType(teacher.getPreferenceForLessonType());
            }
            assert storedTeacher != null;
            teacherRepository.save(storedTeacher);
        }
        else {
            throw new BadRequestException("ID does not exist");
        }
    }

    public List<Lesson> getLessons(Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);

        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            return teacher.getLessons();
        }
        else {
            throw new RecordNotFoundException("ID does not exist!");
        }
    }

    public List<Lesson> getApplications(Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);

        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            return teacher.getApplications();
        }
        else {
            throw new RecordNotFoundException("ID does not exist");
        }
    }

    public void linkToCurrentUser(String username, String email) {
        User currentUser = userService.getUser(username);

        Optional<Teacher> optionalTeacher = teacherRepository.findByEmail(email);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            currentUser.setTeacher(teacher);
            userRepository.save(currentUser);
        } else {
            throw new RecordNotFoundException("Student niet gevonden");
        }
    }
}
