package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;


@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty save(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public void deleteById(long id){
        facultyRepository.deleteById(id);
    }

    public Faculty findById(long id){
        return facultyRepository.findById(id).get();
    }

    public Faculty edit(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> findAllFaculties(){
        return facultyRepository.findAll();
    }
    public Collection<Faculty> findFacultiesByColorIgnoreCase(String color){
        return facultyRepository.findFacultiesByColorIgnoreCase(color);
    }
    public Collection<Faculty> findFacultiesByNameIgnoreCase(String name){
        return facultyRepository.findFacultiesByNameIgnoreCase(name);
    }

    public Collection<Student> findStudents(long facultyId){
        return facultyRepository.findById(facultyId).get().getStudents();
    }
}
