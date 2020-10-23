delete from usr_role;
delete from role;
delete from usr;

insert into role(id, name) values
(1, 'admin'),
(2, 'user'),
(3, 'analyst'),
(4, 'operator');

insert into usr(login, name, password) values
('login1', 'name1', 'pass1'),
('login2', 'name2', 'pass2'),
('login3', 'name3', 'pass3');

insert into usr_role(usr_id, role_id) values
('login1', 1),
('login1', 2),
('login1', 3),
('login1', 4),
('login2', 2),
('login3', 3),
('login3', 4);
