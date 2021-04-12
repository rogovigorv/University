DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups (
id INT PRIMARY KEY,
groupName VARCHAR NOT NULL
);

DROP TABLE IF EXISTS teacher CASCADE;
CREATE TABLE teacher (
id INT PRIMARY KEY,
firstName VARCHAR NOT NULL,
lastName VARCHAR NOT NULL
);

DROP TABLE IF EXISTS time_table CASCADE;
CREATE TABLE time_table (
id INT PRIMARY KEY,
start TIMESTAMP NOT NULL,
duration TIME NOT NULL
);

DROP TABLE IF EXISTS student CASCADE;
CREATE TABLE student (
id INT PRIMARY KEY,
firstName VARCHAR NOT NULL,
lastName VARCHAR NOT NULL,
group_id INT,

FOREIGN KEY (group_id) REFERENCES groups(id)
);

DROP TABLE IF EXISTS lecture CASCADE;
CREATE TABLE lecture (
id INT PRIMARY KEY,
lectureName VARCHAR NOT NULL,
description VARCHAR NOT NULL,
time_table_id INT UNIQUE,
teacher_id INT,
student_id INT,

FOREIGN KEY (time_table_id) REFERENCES time_table(id),
FOREIGN KEY (teacher_id) REFERENCES teacher(id),
FOREIGN KEY (student_id) REFERENCES student(id)
);

