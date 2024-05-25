package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;


@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty save(Faculty faculty){
        logger.info("Faculty created.");
        return facultyRepository.save(faculty);
    }

    public void deleteById(long id){
        if(facultyRepository.findById(id).isPresent()){
            logger.info("Faculty deleted.");
        }
        else {
            logger.error("Faculty not found.");
            throw new FacultyNotFoundException();
        }
        facultyRepository.deleteById(id);
    }

    public Faculty findById(long id){
        Optional<Faculty> foundFaculty = facultyRepository.findById(id);
        if (foundFaculty.isPresent()){
            logger.info("Faculty found.");
            return foundFaculty.get();
        }
        else {
            logger.error("Faculty not found.");
            throw new FacultyNotFoundException();
        }
    }

    public Faculty edit(Faculty faculty){
        if (facultyRepository.findById(faculty.getId()).isPresent()){
            logger.info("Faculty edited.");
            return facultyRepository.save(faculty);
        }
        else{
            logger.error("Faculty not found");
            throw new FacultyNotFoundException();
        }
    }

    public Collection<Faculty> findAllFaculties(){
        logger.info("Faculties found.");
        return facultyRepository.findAll();
    }
    public Collection<Faculty> findFacultiesByColorIgnoreCase(String color){
        logger.info("Faculty found.");
        return facultyRepository.findFacultiesByColorIgnoreCase(color);
    }
    public Collection<Faculty> findFacultiesByNameIgnoreCase(String name){
        logger.info("Faculties found.");
        return facultyRepository.findFacultiesByNameIgnoreCase(name);
    }

    public Collection<Student> findStudents(long facultyId){
        logger.info("Students by faculty found.");
        return facultyRepository.findById(facultyId).get().getStudents();
    }
}
