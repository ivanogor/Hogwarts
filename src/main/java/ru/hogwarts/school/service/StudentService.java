package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student save(Student student){
        return studentRepository.save(student);
    }

    public void deleteById(long id){
        studentRepository.deleteById(id);
    }

    public Student findById(long id){
        return studentRepository.findById(id).get();
    }

    public Student edit(Student student){
        return studentRepository.save(student);
    }

    public Collection<Student> findAllStudents(){
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int lowLimit, int highLimit) {
        return studentRepository.findByAgeBetween(lowLimit, highLimit);
    }
    public long findFacultyId(long studentId){
        return studentRepository.findById(studentId)
                .map(student -> student.getFaculty().getId()
                ).orElseThrow();
    }
}
