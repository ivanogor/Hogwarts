package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int lowLimit, int highLimit);
    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    int getCountOfStudent();

    @Query(value = "SELECT avg(age) FROM student", nativeQuery = true)
    double getAverageStudentsAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getFiveLastStudents();
}
