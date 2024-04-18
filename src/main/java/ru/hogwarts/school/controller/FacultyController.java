package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;


@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> add(@RequestBody Faculty faculty){
        Faculty addedFaculty = facultyService.save(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable long id){
        facultyService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> get(@PathVariable long id){
        Faculty faculty = facultyService.findById(id);
        if(faculty == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> edit(@RequestBody Faculty faculty){
        Faculty editedFaculty = facultyService.edit(faculty);

        if(editedFaculty == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(editedFaculty);
    }

    @GetMapping
    public ResponseEntity getAllFaculties(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String color){
        if(checkParam(name)){
            return ResponseEntity.ok(facultyService.findFacultiesByNameIgnoreCase(name));
        }
        if(checkParam(color)){
            return ResponseEntity.ok(facultyService.findFacultiesByColorIgnoreCase(color));
        }

        return ResponseEntity.ok(facultyService.findAllFaculties());
    }

    @GetMapping("get-students")
    public ResponseEntity<Collection<Student>> getStudents(@RequestParam long facultyId){
        return ResponseEntity.ok(facultyService.findStudents(facultyId));
    }

    private boolean checkParam(String param){
        return param != null && param.isBlank();
    }

}
