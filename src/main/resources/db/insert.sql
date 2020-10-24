DELETE FROM usr_role;
DELETE FROM role;
DELETE FROM usr;

INSERT INTO role(id, name) VALUES
(1, 'admin'),
(2, 'user'),
(3, 'analyst'),
(4, 'operator');

INSERT INTO usr(login, name, password) VALUES
('login1', 'name1', 'pass1'),
('login2', 'name2', 'pass2'),
('login3', 'name3', 'pass3');

INSERT INTO usr_role(usr_id, role_id) VALUES
('login1', 1),
('login1', 2),
('login1', 3),
('login1', 4),
('login2', 2),
('login3', 3),
('login3', 4);