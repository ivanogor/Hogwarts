package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student save(Student student){
        logger.info("Student created.");
        return studentRepository.save(student);
    }

    public void deleteById(long id){
        if(studentRepository.findById(id).isPresent()){
            logger.info("Student deleted.");
        }
        else {
            logger.error("Student not found.");
            throw new StudentNotFoundException();
        }
        studentRepository.deleteById(id);
    }

    public Student findById(long id){
        Optional<Student> foundStudent = studentRepository.findById(id);

        if (foundStudent.isPresent()){
            logger.info("Student found.");
            return foundStudent.get();
        }
        else {
            logger.error("Student not found.");
            throw new StudentNotFoundException();
        }
    }

    public Student edit(Student student){
        if (studentRepository.findById(student.getId()).isPresent()){
            logger.info("Student edited.");
            return studentRepository.save(student);
        }
        else{
            logger.error("Student not found");
            throw new StudentNotFoundException();
        }
    }

    public Collection<Student> findAllStudents(){
        logger.info("Students founded.");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int lowLimit, int highLimit) {
        if (lowLimit > highLimit){
            logger.error("lowLimit greater than highLimit.");
            throw new RuntimeException();
        }
        else{
            logger.info("Students found.");
            return studentRepository.findByAgeBetween(lowLimit, highLimit);
        }

    }
    public Faculty findFacultyId(long id){
        Optional<Student> foundStudent = studentRepository.findById(id);

        if (foundStudent.isPresent()){
            logger.info("Student found.");
            Faculty foundFaculty = foundStudent.get().getFaculty();
            if(foundFaculty != null){
                logger.info("Faculty is not null.");
                return foundFaculty;
            }
            else {
                logger.error("Faculty is null.");
                throw new RuntimeException();
            }
        }
        else {
            logger.error("Student not found.");
            throw new StudentNotFoundException();
        }
    }

    public int getCountOfStudent(){
        logger.info("Count of students returned.");
        return studentRepository.getCountOfStudent();
    }

    public double getAverageStudentsAge(){
        logger.info("Average students age returned.");
        return studentRepository.getAverageStudentsAge();
    }

    public List<Student> getFiveLastStudents(){
        logger.info("Last five students returned.");
        return studentRepository.getFiveLastStudents();
    }

    public List<Student> getStudentWhichNameStartsWithLetterA(){
        List<Student> allStudents = studentRepository.findAll();

        return allStudents.parallelStream()
                .filter(student -> student.getName().startsWith("A"))
                .sorted()
                .toList();
    }

    public double getAverageStudentAge(){
        List<Student> allStudents = studentRepository.findAll();

        return allStudents.parallelStream().mapToInt(Student::getAge).average().orElse(0.0);
    }
}
