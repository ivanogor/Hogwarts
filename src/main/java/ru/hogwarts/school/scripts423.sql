SELECT student.name, student.age, faculty.name FROM student
INNER JOIN faculty ON faculty.id = student.faculty_id;

SELECT student.name, student.age FROM student
INNER JOIN avatar ON student.id = avatar.student_id;