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
teacher_id INT,
group_id INT,

FOREIGN KEY (teacher_id) REFERENCES teacher(id),
FOREIGN KEY (group_id) REFERENCES groups(id)
);

INSERT INTO groups (id, groupname) VALUES ('1', 'Dream team');
INSERT INTO groups (id, groupname) VALUES ('2', 'Losers');
INSERT INTO groups (id, groupname) VALUES ('3', 'Geeks');

INSERT INTO student (id, firstname, lastname, group_id) VALUES ('1', 'Oleg', 'Silovich', '1');

INSERT INTO teacher (id, firstname, lastname) VALUES ('25', 'Bronislav', 'Potemkin');

INSERT INTO lecture (id, lecturename, description, teacher_id, group_id) VALUES ('1', 'Math', 'Simple math', '25', 3);
INSERT INTO lecture (id, lecturename, description, teacher_id, group_id) VALUES ('2', 'Eng', 'Simple eng', '25', 1);
INSERT INTO lecture (id, lecturename, description, teacher_id, group_id) VALUES ('3', 'Art', 'Simple art', '25', 1);
