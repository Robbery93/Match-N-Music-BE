package nl.robbertij.matchnmusic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String residence;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String instrument;

    // to store a large String
    @Column(length = 2000)
    private String request;

    @Column(name = "preference_for_lesson_type")
    private String preferenceForLessonType;

    @JsonIgnoreProperties("students")
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    public Student() {};

    public Student(Long id,
                   String name,
                   String email,
                   String residence,
                   String phoneNumber,
                   String instrument,
                   String request,
                   String preferenceForLessonType,
                   Teacher teacher) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.residence = residence;
        this.phoneNumber = phoneNumber;
        this.instrument = instrument;
        this.request = request;
        this.preferenceForLessonType = preferenceForLessonType;
        this.teacher = teacher;
    }

    public Student(String name,
                   String email,
                   String residence,
                   String phoneNumber,
                   String instrument,
                   String request,
                   String preferenceForLessonType,
                   Teacher teacher) {
        this.name = name;
        this.email = email;
        this.residence = residence;
        this.phoneNumber = phoneNumber;
        this.instrument = instrument;
        this.request = request;
        this.preferenceForLessonType = preferenceForLessonType;
        this.teacher = teacher;
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

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
