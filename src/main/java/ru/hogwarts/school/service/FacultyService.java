package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public void remove(long id){
        facultyRepository.deleteById(id);
    }

    public Faculty get(long id){
        return facultyRepository.getById(id);
    }

    public Faculty edit(Faculty faculty){
        return facultyRepository.save(faculty);
    }

}
