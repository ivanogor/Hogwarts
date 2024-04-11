package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();

    private long idCounter = 1L;

    public Faculty add(Faculty faculty){
        faculty.setId(idCounter);
        faculties.put(idCounter++, faculty);
        return faculty;
    }

    public Faculty remove(long id){
        return faculties.remove(id);
    }

    public Faculty get(long id){
        return faculties.get(id);
    }

    public Faculty edit(Faculty faculty){
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

}
