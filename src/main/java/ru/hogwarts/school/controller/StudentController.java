package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student){
        Student addedStudent = studentService.save(student);
        return ResponseEntity.ok(addedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable long id){
        studentService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable long id){
        Student student = studentService.findById(id);

        if(student == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> edit(@RequestBody Student student){
        Student editedStudent = studentService.edit(student);

        if(editedStudent == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(editedStudent);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findAllStudents(@RequestParam(required = false) Integer lowLimit,
                                                               @RequestParam(required = false) Integer highLimit){
        if(lowLimit != null && highLimit != null) {
            return ResponseEntity.ok(studentService.findByAgeBetween(lowLimit, highLimit));
        }
        return ResponseEntity.ok(studentService.findAllStudents());
    }

    @GetMapping("find-faculty")
    public ResponseEntity<Long> findFacultyOfStudent(@RequestParam long studentId){
        return ResponseEntity.ok(studentService.findFacultyId(studentId));
    }
}
