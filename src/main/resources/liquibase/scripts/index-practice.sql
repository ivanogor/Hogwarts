--liquibase formatted sql

--changeset ivanogor:1
CREATE INDEX student_name_index ON student(name);

--changeset ivanogor:2
CREATE INDEX faculty_name_and_color_index ON faculty(color, name);