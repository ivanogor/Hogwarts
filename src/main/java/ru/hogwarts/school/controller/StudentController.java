package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;



@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student){
        Student addedStudent = studentService.add(student);
        return ResponseEntity.ok(addedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> remove(@PathVariable long id){
        Student removedStudent = studentService.get(id);
        studentService.remove(id);
        if(removedStudent == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(removedStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable long id){
        Student student = studentService.get(id);
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
}
