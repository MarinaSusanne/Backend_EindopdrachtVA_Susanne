INSERT INTO groups (id, group_name, start_date, end_date, group_info)
VALUES (110, 'Groep X', '2023-08-14', '2023-10-20', 'De eerste groep en ze zijn tof!'),
       (111, 'Groep voor admin', '2023-10-01', '2025-12-31', 'Groep voor de admin ');

INSERT INTO users (id, username, password, enabled, email, first_name, last_name, street_name, house_number, zipcode, city, date_of_birth, group_id, photo)
VALUES (110, 'user', '$2a$12$sAfLA/rINPK3r3qaTSKNKu0b0wagrp42dNXKJaJlN5G.Qq8DD6vCq', true, 'marina@marina.nl', 'Marina', 'Jansen', 'Novistraat', '12-d', '1234KK', 'Delft', '1992/12/03', 110, null),
       (111, 'admin', '$2a$12$sAfLA/rINPK3r3qaTSKNKu0b0wagrp42dNXKJaJlN5G.Qq8DD6vCq', true, 'gijsje@gijsje.nl', 'Seksuoloog', 'Susanne', 'kerkstraat', '3', '2345CC', 'Leiden', '1998/03/06', null, null);

INSERT INTO messages (id, content, user_id)
VALUES (110,'Hallo Hallo', 110),
       (111,'Joe joe', 110);



-- password = "password" (dit comment is een security lek, zet dit nooit in je code.
-- Als je hier je plaintext password niet meer weet, moet je een nieuw password encrypted)


INSERT INTO authorities (user_id, authority)
VALUES (110, 'ROLE_USER');

INSERT INTO authorities (user_id, authority)
VALUES (111, 'ROLE_USER');

INSERT INTO authorities (user_id, authority)
VALUES (111, 'ROLE_ADMIN');

-- INSERT INTO roles (id, role_name)
-- VALUES (101, 'ROLE_USER');
--
-- INSERT INTO roles (id, role_name)
-- VALUES (102, 'ROLE_ADMIN');

