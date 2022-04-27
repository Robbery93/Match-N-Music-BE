package nl.robbertij.matchnmusic.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String age;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String residence;
    private String instruments;

    // to store a large String
    @Column(length = 3000)
    private String description;

    // to store a large String
    @Column(length = 3000)
    private String experience;

    @Column(name = "preference_for_lesson_type")
    private String preferenceForLessonType;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Lesson> applications;

    public Teacher() {};

    public Teacher(Long id,
                   String name,
                   String email,
                   String age,
                   String phoneNumber,
                   String residence,
                   String instruments,
                   String description,
                   String experience,
                   String preferenceForLessonType,
                   List<Lesson> lessons,
                   List<Lesson> applications) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.residence = residence;
        this.instruments = instruments;
        this.description = description;
        this.experience = experience;
        this.preferenceForLessonType = preferenceForLessonType;
        this.lessons = lessons;
        this.applications = applications;
    }

    public Teacher(String name,
                   String email,
                   String age,
                   String phoneNumber,
                   String residence,
                   String instruments,
                   String description,
                   String experience,
                   String preferenceForLessonType,
                   List<Lesson> lessons,
                   List<Lesson> applications) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.residence = residence;
        this.instruments = instruments;
        this.description = description;
        this.experience = experience;
        this.preferenceForLessonType = preferenceForLessonType;
        this.lessons = lessons;
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

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getInstruments() {
        return instruments;
    }

    public void setInstruments(String instruments) {
        this.instruments = instruments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPreferenceForLessonType() {
        return preferenceForLessonType;
    }

    public void setPreferenceForLessonType(String preferenceForLessonType) {
        this.preferenceForLessonType = preferenceForLessonType;
    }

    public List<Lesson> getLessons() {
        List<Lesson> activeLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.isActive()) {
                activeLessons.add(lesson);
            }
        }
        return activeLessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getApplications() {
        List<Lesson> allApplications = new ArrayList<>();
        for (Lesson lesson : applications) {
            if (!lesson.isActive()) {
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
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", residence='" + residence + '\'' +
                ", age='" + age + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", instruments='" + instruments + '\'' +
                ", description='" + description + '\'' +
                ", experience='" + experience + '\'' +
                ", preferenceForLessonType='" + preferenceForLessonType + '\'' +
                ", lessons=" + lessons +
                '}';
    }
}
