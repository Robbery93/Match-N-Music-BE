package nl.robbertij.matchnmusic.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String age;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String residence;
    private String photo;
    private String instrument;
    @Column(length = 4000)
    private String request;
    @Column(name = "preference_for_lesson_type")
    private String preferenceForLessonType;
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Lesson> lesson;
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Lesson> applications;



    public Student() {};

    public Student(Long id,
                   String name,
                   String email,
                   String age,
                   String phoneNumber,
                   String photo,
                   String residence,
                   String instrument,
                   String request,
                   String preferenceForLessonType,
                   List<Lesson> lesson,
                   List<Lesson> applications
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
        this.residence = residence;
        this.instrument = instrument;
        this.request = request;
        this.preferenceForLessonType = preferenceForLessonType;
        this.lesson = lesson;
        this.applications = applications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResidence() { return residence; }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getPreferenceForLessonType() {
        return preferenceForLessonType;
    }

    public void setPreferenceForLessonType(String preferenceForLessonType) {
        this.preferenceForLessonType = preferenceForLessonType;
    }

    public List<Lesson> getLesson() {
        List<Lesson> activeLesson = new ArrayList<>();
        for (Lesson lesson : lesson) {
            if(lesson.isActive()) {
                activeLesson.add(lesson);
            }
        }
        return activeLesson;
    }

    public void setLesson(List<Lesson> lessons) {
        this.lesson = lessons;
    }

    public List<Lesson> getApplications() {
        List<Lesson> allApplications = new ArrayList<>();
        for (Lesson lesson : applications) {
            if(!lesson.isActive()) {
                allApplications.add(lesson);
            }
        }
        return allApplications;
    }

    public void setApplications(List<Lesson> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", residence='" + residence + '\'' +
                ", photo='" + photo + '\'' +
                ", instrument='" + instrument + '\'' +
                ", request='" + request + '\'' +
                ", preferenceForLessonType='" + preferenceForLessonType + '\'' +
                ", lessons='" + lesson + '\'' +
                ", applications='" + applications + '\'' +
                '}';
    }
}
