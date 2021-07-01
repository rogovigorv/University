DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups (
id SERIAL PRIMARY KEY,
groupName VARCHAR NOT NULL
);

DROP TABLE IF EXISTS teacher CASCADE;
CREATE TABLE teacher (
id SERIAL PRIMARY KEY,
firstName VARCHAR NOT NULL,
lastName VARCHAR NOT NULL
);

DROP TABLE IF EXISTS student CASCADE;
CREATE TABLE student (
id SERIAL PRIMARY KEY,
firstName VARCHAR NOT NULL,
lastName VARCHAR NOT NULL,
group_id INT,

FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS lecture CASCADE;
CREATE TABLE lecture (
id SERIAL PRIMARY KEY,
lectureName VARCHAR NOT NULL,
description VARCHAR NOT NULL,
teacher_id INT,
group_id INT,

FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE ON UPDATE CASCADE
);