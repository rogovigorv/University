INSERT INTO groups (id, groupname) VALUES (1, 'Dream team');
INSERT INTO groups (id, groupname) VALUES (2, 'Losers');
INSERT INTO groups (id, groupname) VALUES (3, 'Geeks');
INSERT INTO groups (id, groupname) VALUES (4, 'Gorillaz');
INSERT INTO groups (id, groupname) VALUES (5, 'Rednecks');
INSERT INTO groups (id, groupname) VALUES (6, 'Suckers');
INSERT INTO groups (id, groupname) VALUES (7, 'East side guys');
INSERT INTO groups (id, groupname) VALUES (8, 'Passing by guys');
INSERT INTO groups (id, groupname) VALUES (9, 'Coolz');
INSERT INTO groups (id, groupname) VALUES (10, 'Jazz brothers');
INSERT INTO groups (id, groupname) VALUES (11, 'Virtuosos');
INSERT INTO groups (id, groupname) VALUES (12, 'Zombies');
INSERT INTO groups (id, groupname) VALUES (13, 'Virtuosos');
INSERT INTO groups (id, groupname) VALUES (14, 'Mamas boys');
INSERT INTO groups (id, groupname) VALUES (15, 'The rolling stones');
INSERT INTO groups (id, groupname) VALUES (16, 'captain Lebyadkin group');
INSERT INTO groups (id, groupname) VALUES (17, 'The kamikaze');
INSERT INTO groups (id, groupname) VALUES (18, 'Java guys');
INSERT INTO groups (id, groupname) VALUES (19, 'Testers');
INSERT INTO groups (id, groupname) VALUES (20, 'Truants');

INSERT INTO student (id, firstname, lastname, group_id) VALUES (1, 'Oleg', 'Silovich', 1);

INSERT INTO teacher (id, firstname, lastname) VALUES (25, 'Bronislav', 'Potemkin');

INSERT INTO lecture (id, lecturename, description, teacher_id, group_id) VALUES (1, 'Math', 'Simple math', 25, 3);
INSERT INTO lecture (id, lecturename, description, teacher_id, group_id) VALUES (2, 'Eng', 'Simple eng', 25, 2);
INSERT INTO lecture (id, lecturename, description, teacher_id, group_id) VALUES (3, 'Art', 'Simple art', 25, 1);
