//package nl.robbertij.matchnmusic.controller;
//
//import nl.robbertij.matchnmusic.MatchNMusicApplication;
//import nl.robbertij.matchnmusic.dto.request.StudentRequestDto;
//import nl.robbertij.matchnmusic.model.Student;
//import nl.robbertij.matchnmusic.service.CustomUserDetailsService;
//import nl.robbertij.matchnmusic.service.StudentService;
//import nl.robbertij.matchnmusic.utils.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.hasValue;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(MatchNMusicApplication.class)
//@AutoConfigureMockMvc(addFilters = false)
//class StudentControllerTest {
//
//    @Autowired
//    public MockMvc mockMvc;
//
//    @MockBean
//    public StudentService mockService;
//
//    @MockBean
//    public JwtUtil jwtUtil;
//    @MockBean
//    public CustomUserDetailsService customUserDetailsService;
//    @MockBean
//    public StudentController studentController;
//
////    @Mock
////    public Student student;
////    @Mock
////    public Student student2;
////    @Mock
////    public Student student3;
////    @Mock
////    public List<Student> students;
//
////    @BeforeEach
////    void setup() {
////        student = new Student();
////        student.setId(1L);
////        student.setName("Robbert");
////        student2 = new Student();
////        student2.setId(2L);
////        student2.setName("Anna");
////        student3 = new Student();
////        student3.setId(3L);
////        student3.setName("Brian");
////
////        students = new ArrayList<>();
////
////        students.add(student);
////        students.add(student2);
////        students.add(student2);
////    }
//
////    @DisplayName("Should return all Students")
////    @Test
////    void getAllStudentsTest() throws Exception {
////        when(mockService.getStudents(null, null, null)).thenReturn(students);
////
////        mockMvc.perform(get("/students")
////                .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$", hasSize(3)));
////
////    }
//
//    @DisplayName("Should return Students with specified instrument")
//    @Test
//    void getAllStudents() {
//    }
//
//    @Test
//    void getStudentById() {
//    }
//
//    @Test
//    void deleteStudent() {
//    }
//
//    @Test
//    void addStudent() {
//    }
//
//    @Test
//    void updateStudent() {
//    }
//
//    @Test
//    void partialUpdateStudent() {
//    }
//
//    @Test
//    void getLesson() {
//    }
//
//    @Test
//    void unsubscribe() {
//    }
//
//    @Test
//    void applyForLesson() {
//    }
//}