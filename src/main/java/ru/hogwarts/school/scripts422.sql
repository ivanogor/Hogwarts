CREATE TABLE cars (
    model Text PRIMARY KEY,
    price INTEGER
);
CREATE TABLE humans (
    name Text PRIMARY KEY,
    age INTEGER,
    licence BOOLEAN,
    car_model Text REFERENCES cars (model)
);

