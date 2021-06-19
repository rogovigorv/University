INSERT INTO groups (groupname) VALUES ('Dream team');
INSERT INTO groups (groupname) VALUES ('Losers');
INSERT INTO groups (groupname) VALUES ('Geeks');
INSERT INTO groups (groupname) VALUES ('Gorillaz');
INSERT INTO groups (groupname) VALUES ('Rednecks');
INSERT INTO groups (groupname) VALUES ('Suckers');
INSERT INTO groups (groupname) VALUES ('East side guys');
INSERT INTO groups (groupname) VALUES ('Passing by guys');
INSERT INTO groups (groupname) VALUES ('Coolz');
INSERT INTO groups (groupname) VALUES ('Jazz brothers');
INSERT INTO groups (groupname) VALUES ('Virtuosos');
INSERT INTO groups (groupname) VALUES ('Zombies');
INSERT INTO groups (groupname) VALUES ('Virtuosos');
INSERT INTO groups (groupname) VALUES ('Mamas boys');
INSERT INTO groups (groupname) VALUES ('The rolling stones');
INSERT INTO groups (groupname) VALUES ('Capt. Hook group');
INSERT INTO groups (groupname) VALUES ('The kamikaze');
INSERT INTO groups (groupname) VALUES ('Java guys');
INSERT INTO groups (groupname) VALUES ('Testers');
INSERT INTO groups (groupname) VALUES ('Truants');

INSERT INTO student (id, firstname, lastname, group_id) VALUES (1, 'Oleg', 'Silovich', 1);

INSERT INTO teacher (id, firstname, lastname) VALUES (25, 'Bronislav', 'Potemkin');

INSERT INTO lecture (lecturename, description, teacher_id, group_id) VALUES ('Math', 'Simple math', 25, 3);
INSERT INTO lecture (lecturename, description, teacher_id, group_id) VALUES ('Eng', 'Simple eng', 25, 2);
INSERT INTO lecture (lecturename, description, teacher_id, group_id) VALUES ('Art', 'Simple art', 25, 1);
