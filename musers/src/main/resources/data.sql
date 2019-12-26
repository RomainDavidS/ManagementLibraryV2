insert into role (id,name,wording)
VALUES
(-1,'ROLE_ADMIN','Administrateur'),
(-2,'ROLE_ACTUATOR','Membre'),
(-3,'ROLE_USER','Utilisateur');

INSERT INTO city(
	insee, name, postal_code)
	VALUES
	('69381', 'LYON 1ER ARRONDISSEMENT', '69001');
INSERT INTO address(
	id, complement_street, complement_street_number, lattitude, longitude, street, street_number, street_type)
	VALUES
	(1, '', '', '45.773788358','4.826584669', 'DE LA CROIX ROUSSE', 100, 'BD'),
	(2, '', '', '45.773788358','4.826584669', 'DE LA CROIX ROUSSE', 102, 'BD'),
	(3, '', '', '45.773788358','4.826584669', 'DE LA CROIX ROUSSE', 104, 'BD'),
	(4, '', '', '45.773788358','4.826584669', 'DE LA CROIX ROUSSE', 106, 'BD');



INSERT INTO address_city(
	id_address, insee_city)
	VALUES (1, '69381'),
	(2, '69381'),
	(3, '69381'),
	(4, '69381');

/*  Insert USERS */
INSERT INTO users
(id,email,active,last_name,password,first_name,phone)
VALUES
(-1,'romaindavid.sergeant@gmail.com',
true,'USER','$2a$10$mVLweGs6HLxItvuYt5W21e9sr7sgkqeuk6q.3pke4HEHJZry4fWSO',
'Romain-David' ,'0768000001'),
(-2,'romaind.sergeant@gmail.com',
true,'ACTUATOR','$2a$10$mVLweGs6HLxItvuYt5W21e9sr7sgkqeuk6q.3pke4HEHJZry4fWSO',
'RomainD' ,'0768000002'),
(-3,'romaind.ocrlibrary@gmail.com',
true,'ADMIN','$2a$10$mVLweGs6HLxItvuYt5W21e9sr7sgkqeuk6q.3pke4HEHJZry4fWSO',
'Library Admin' ,'0768000003');

/*  Insert role_user */
insert into users_role (id_user,id_role)
VALUES
(-1,-1),
(-2,-2),
(-3,-1);


