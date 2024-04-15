package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student add(Student student){
        return studentRepository.save(student);
    }

    public void remove(long id){
        studentRepository.deleteById(id);
    }

    public Student get(long id){
        return studentRepository.getReferenceById(id);
    }

    public Student edit(Student student){
        return studentRepository.save(student);
    }
}
