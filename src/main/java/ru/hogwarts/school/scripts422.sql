CREATE TABLE cars (
    cars_id int8 PRIMARY KEY,
    model Text,
    price INTEGER
);
CREATE TABLE humans (
    cars_id int8 PRIMARY KEY,
    name Text,
    age INTEGER,
    licence BOOLEAN,
    car_id int8 REFERENCES cars (cars_id)
);

