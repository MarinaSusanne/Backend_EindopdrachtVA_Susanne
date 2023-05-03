INSERT INTO messages ( id, content)
VALUES (110,'Hallo Hallo'),
       (111,'Joe joe');


INSERT INTO users (id, username, password, email, first_name, last_name, street_name, house_number, zipcode, city, date_of_birth, photo)
VALUES (110, 'Marina123', 'MarinaTesttest1!', 'marina@marina.nl', 'Marina', 'Jansen', 'Novistraat', '12-d', '1234KK', 'Delft', '1992/12/03', null),
       (111, 'Gijsje123', 'GijsjeTesttest1!', 'gijsje@gijsje.nl', 'Gijsje', 'Gijsen', 'kerkstraat', '3', '2345CC', 'Leiden', '1998/03/06', null );

-- -- password = "password" (dit comment is een security lek, zet dit nooit in je code.
-- -- Als je hier je plaintext password niet meer weet, moet je een nieuw password encrypted)
-- INSERT INTO users (username, password, email, enabled) VALUES ('user', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','user@test.nl', TRUE);
-- INSERT INTO users (username, password, email, enabled) VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl', TRUE);
--
-- INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
-- INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
-- INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
-- Gouden kraan: sequence generator

