DELETE FROM reservation;
DELETE FROM lending ;
DELETE FROM  books;
DELETE FROM  books_reservation;

INSERT INTO books_reservation(
	id, next_return_date, number, possible)
	VALUES
	(-1, '2020-01-15 14:00:00' ,0, 60),
	(-2,'2020-01-15 14:00:00' , 2, 60),
	(-3, '2020-01-15 14:00:00', 2, 60),
	(-4, '2020-01-15 14:00:00', 2, 60),
	(-5, '2020-01-15 14:00:00', 60, 60),
	(-6, '2020-01-15 14:00:00' , 3, 60),
	(-7, '2020-01-15 14:00:00' , 0, 60);

INSERT INTO books(
	id_book_reservation,id, availability, id_cover, isbn, review, summary, title, id_author,id_coauthor, id_edition, id_language, id_theme)
	VALUES
	(-1,-1, 0, 'a0841cf1-1fe9-4bc0-8c9c-377b70bcc848', '9782409001000', 30, 'Initiation, exemples et exercices corrigés', 'UML 2.5', 3,2, 1, 1, 3),
	(-2,-2, 3, '4a66b149-2b77-49e2-a49c-2250c4275890', '9782409005824', 30, 'Apprenez le mapping objet-relationnel (ORM) avec Java', 'JPA et Java Hibernate', 1,null, 1, 1, 3),
	(-3,-3, 0, '1cb6e756-f073-4cb8-a37e-aa462845b670', '9782409013843', 30, 'Le socle technique des applications Java EE', 'Java Spring', 5,null, 1, 1, 3),
	(-4,-4, 0, 'be863f03-d00e-443b-aab2-b625dc2ef5d0', '9782409014499', 30, 'Construisez vos applications réactives avec une architecture micro-services en environnement Java EE', 'Java Spring', 5,null, 1, 1, 3),
	(-5,-5, 0, 'b1d08817-1122-483c-a8fc-8a524a665f36', '9782746065093', 30, 'Maîtrisez l infrastructure d un projet Java EE  ', 'Apache Maven', 6,null, 1, 1, 3),
	(-6,-6, 0, '6e0a0725-736a-4a54-9520-5b250af89321', '9782746090170', 30, 'Echanger des données au format JSON', 'Développer des services REST en Java', 7,null, 1, 1, 3),
	(-7,-7, 10, '796b17f6-699f-43e4-837e-bfa51c161cc5', '9782746092150', 30, 'Concepts et implémentation en C#', 'L intelligence Artificielle pour les développeurs', 4,null, 1, 1, 3);



INSERT INTO lending(
	id, end_date, id_user, renewal, return_date, start_date, id_books)
	VALUES
	(-1, '2019-12-29 14:00:00', -1, 0, null , '2019-12-01 14:00:00', -1),
	(-2, '2020-01-30 14:00:00', -1, 0, null , '2020-01-02 14:00:00', -2);

INSERT INTO reservation(
	id, id_user_reservation, notification_date, reservation_date, state, id_books)
	VALUES
	(-1, -2,  null, '2019-12-16 09:00:00', 'En cours', -1),
	(-2, -3,  null, '2019-12-16 10:00:00', 'En cours', -1),
	(-3, -3,  null, '2019-12-16 11:00:00', 'En cours', -2),
	(-4, -2,  null, '2019-12-16 12:00:00', 'En cours', -2),
	(-5, -1,  null, '2019-12-15 12:00:00', 'En cours', -3),
	(-6, -2,  null, '2019-12-16 12:00:00', 'En cours', -3),
	(-7, -2,  null, '2019-12-15 12:00:00', 'En cours', -4),
	(-9, -1, '2019-12-05 12:00:00', '2019-12-09 12:00:00', 'En cours', -6),
	(-10, -2, null, '2019-12-10 12:00:00', 'En cours', -6),
	(-11, -3, null, '2019-12-11 12:00:00', 'En cours', -6);
