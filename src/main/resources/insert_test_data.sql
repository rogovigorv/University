INSERT INTO groups (id, groupname) VALUES (1, 'Dream team');
INSERT INTO groups (id, groupname) VALUES (2, 'Losers');
INSERT INTO groups (id, groupname) VALUES (3, 'Geeks');

INSERT INTO student (id, firstname, lastname, group_id) VALUES (1, 'Oleg', 'Silovich', 1);

INSERT INTO teacher (id, firstname, lastname) VALUES (25, 'Bronislav', 'Potemkin');

INSERT INTO lecture (id, lecturename, description, teacher_id, group_id) VALUES (1, 'Math', 'Simple math', 25, 3);
INSERT INTO lecture (id, lecturename, description, teacher_id, group_id) VALUES (2, 'Eng', 'Simple eng', 25, 2);
INSERT INTO lecture (id, lecturename, description, teacher_id, group_id) VALUES (3, 'Art', 'Simple art', 25, 1);
