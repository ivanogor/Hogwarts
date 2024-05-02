package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;
    private Long id;
    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    void setUp() {
        id = 1L;

        Faculty faculty = new Faculty();
        faculty.setColor("color");
        faculty.setName("name");

        student = new Student();
        student.setFaculty(null);
        student.setAge(18);
        student.setName("name");
        student.setFaculty(faculty);

        facultyRepository.save(faculty);
    }

    @Test
    void contextLoads() {
        assertThat(studentController)
                .isNotNull();
    }

    @Test
    void addStudentTest(){
        ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + port + "/student", student, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().contains("name").contains("18");

        studentRepository.deleteById(student.getId());
    }

    @Test
    void deleteStudentTest(){
        studentRepository.save(student);
        id = student.getId();
        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/student/" + id,
                HttpMethod.DELETE,
                null,
                String.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getStudentTest(){
        studentRepository.save(student);
        id = student.getId();
        ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + id, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        studentRepository.deleteById(id);
    }

    @Test
    void editStudentTest(){
        studentRepository.save(student);
        id = student.getId();
        student.setName("newName");
        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(student),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Optional<Student> updatedStudent = studentRepository.findById(student.getId());
        assertThat(updatedStudent).isPresent();
        assertThat(updatedStudent.get().getName()).isEqualTo("newName");
        studentRepository.deleteById(id);
    }

    @Test
    public void findAllStudentsTest(){
        studentRepository.save(student);
        id = student.getId();

        ResponseEntity<Collection<Student>> response = testRestTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        Collection<Student> students = response.getBody();
        assertThat(students).isNotNull();
        assertThat(students).isNotEmpty();

        Student resultStudent = students.stream()
                .filter(student1 -> student1.getId() == id)
                .findFirst()
                .orElse(null);

        assertThat(resultStudent).isNotNull();
        assertThat(resultStudent.getName()).isEqualTo(student.getName());
        studentRepository.deleteById(id);
    }

    @Test
    public void findFacultyOfStudentTest(){
        studentRepository.save(student);
        id = student.getId();

        ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port + "/student/find-faculty?studentId=" + id,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("color").contains("name");
        studentRepository.deleteById(id);
    }
}
