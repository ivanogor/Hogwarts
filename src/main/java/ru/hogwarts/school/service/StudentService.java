package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long idCounter = 1L;

    public Student add(Student student){
        student.setId(idCounter);
        students.put(idCounter++, student);
        return student;
    }

    public Student remove(long id){
        return students.remove(id);
    }

    public Student get(long id){
        return students.get(id);
    }

    public Student edit(Student student){
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }
}
