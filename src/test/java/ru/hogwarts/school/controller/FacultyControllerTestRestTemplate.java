package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    private Faculty faculty;
    private Long id;

    @BeforeEach
    void setUp() {
        Student student = new Student();
        student.setFaculty(null);
        student.setId(1);
        student.setName("name");
        student.setAge(18);

        id = 1L;
        faculty = new Faculty();
        faculty.setName("name");
        faculty.setColor("color");
        Collection<Student> list = new ArrayList<>(List.of(student));
        faculty.setStudents(list);
    }

    @Test
    void contextLoads() {
        assertThat(facultyController)
                .isNotNull();
    }

    @Test
    void addFacultyTest(){
        ResponseEntity<Faculty> response = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().toString()).contains("name").contains("color");

        id = response.getBody().getId();
        facultyRepository.deleteById(id);
    }

    @Test
    void removeFacultyTest(){
        facultyRepository.save(faculty);
        id = faculty.getId();
        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/faculty/" + id,
                HttpMethod.DELETE,
                null,
                String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getFacultyTest(){
        facultyRepository.save(faculty);
        id = faculty.getId();

        ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + id,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().contains("name").contains("color");
        facultyRepository.deleteById(id);
    }

    @Test
    void editFacultyTest(){
        facultyRepository.save(faculty);
        id = faculty.getId();
        faculty.setName("newName");

        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("newName");
        facultyRepository.deleteById(id);
    }

    @Test
    void getAllFacultiesTest(){
        facultyRepository.save(faculty);
        id = faculty.getId();

        ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.GET,
                null,
                String.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("name").contains("color").contains(id.toString());
        facultyRepository.deleteById(id);
    }

    @Test
    void getStudentsTest(){
        facultyRepository.save(faculty);
        id = faculty.getId();

        ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/get-students?facultyId=" + id,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        facultyRepository.deleteById(id);
    }
}
