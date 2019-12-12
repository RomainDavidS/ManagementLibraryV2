INSERT INTO "language" (id, value)
VALUES
(1, 'Français'),
(2,'Anglais');

INSERT INTO "author" (id,last_name,first_name)
VALUES
(1,'Banon','Martiel'),
(2,'Van der Heyde','Fien'),
(3,'Debrauwer','Laurent'),
(4,'MATHIVET','Virginie'),
(5,'LE MORVAN','Hervé'),
(6,'GREAU','Maxime'),
(7,'Sobréro','Aurélie');


INSERT INTO "edition" (id,name)
VALUES
(1,'ENI'),
(2,'Hachette'),
(3,'Les auteurs réunis');

INSERT INTO "theme" (id, value)
VALUES
(1,'Policier'),
(2,'Cuisine'),
(3,'Informatique');


INSERT INTO "books"(
	id, availability, id_cover, isbn, review, summary, title, id_author,id_coauthor, id_edition, id_language, id_theme)
	VALUES
	(1, 20, 'a0841cf1-1fe9-4bc0-8c9c-377b70bcc848', '9782409001000', 30, 'Initiation, exemples et exercices corrigés', 'UML 2.5', 3,2, 1, 1, 3),
	(2, 2, '4a66b149-2b77-49e2-a49c-2250c4275890', '9782409005824', 30, 'Apprenez le mapping objet-relationnel (ORM) avec Java', 'JPA et Java Hibernate', 1,null, 1, 1, 3),
	(3, 20, '1cb6e756-f073-4cb8-a37e-aa462845b670', '9782409013843', 30, 'Le socle technique des applications Java EE', 'Java Spring', 5,null, 1, 1, 3),
	(4, 20, 'be863f03-d00e-443b-aab2-b625dc2ef5d0', '9782409014499', 30, 'Construisez vos applications réactives avec une architecture micro-services en environnement Java EE', 'Java Spring', 5,null, 1, 1, 3),
	(5, 2, 'b1d08817-1122-483c-a8fc-8a524a665f36', '9782746065093', 30, 'Maîtrisez l infrastructure d un projet Java EE  ', 'Apache Maven', 6,null, 1, 1, 3),
	(6, 20, '6e0a0725-736a-4a54-9520-5b250af89321', '9782746090170', 30, 'Echanger des données au format JSON', 'Développer des services REST en Java', 7,null, 1, 1, 3),
	(7, 10, '796b17f6-699f-43e4-837e-bfa51c161cc5', '9782746092150', 30, 'Concepts et implémentation en C#', 'L intelligence Artificielle pour les développeurs', 4,null, 1, 1, 3);


INSERT INTO "lending"(
	id, end_date, id_user, renewal, return_date, start_date, id_books)
	VALUES
	(1, '2019-10-26 14:00:00', 1, 0, null , '2019-09-28 14:00:00', 1),
	(2, '2019-10-26 14:00:00', 1, 0, null , '2019-09-28 14:00:00', 2),
	(3, '2019-11-21 14:00:00', 1, 0, null , '2019-10-26 14:00:00', 3),
	(4, '2019-12-29 14:00:00', 1, 1, null , '2019-12-01 14:00:00', 5),
	(5, '2019-12-29 14:00:00', 1, 0, null , '2019-12-01 14:00:00', 7);


INSERT INTO "email" (id, name,subject,content)
VALUES
(1,'relance','Relance pour livre non rendu',
'	Bonjour,

	Vous devriez rendre le livre [BOOK_TITLE] à la blibliothèque au plus tard pour [END_DATE].
Hors nous n avons toujours pas enregistré ce retour.
Nous vous demandons de régulariser la situation dès à présent.
Bien cordialement.');