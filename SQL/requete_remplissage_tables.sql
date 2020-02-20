-- Requêtes table CATEGORIES
INSERT INTO CATEGORIES VALUES ('Informatique');
INSERT INTO CATEGORIES VALUES ('Ameublement');
INSERT INTO CATEGORIES VALUES ('Vêtement');
INSERT INTO CATEGORIES VALUES ('Sport&Loisirs');

-- Ajout USER (prise en compte du hashage)
INSERT INTO UTILISATEURS VALUES(
	'bob29',
	'Bob',
	'Jobs',
	'bob@ht.fr',
	'0666666666',
	'42 avenue Jean Jaurès',
	'29000',
	'Quimper',
	'5A7E42BC6E6F87048CA5D5FE55B0D1EB', -- mdp123
	100,
	0
);

-- Ajout Retraits
INSERT INTO RETRAITS VALUES(
	'42 avenue Jean Jaurès',
	'29000',
	'Quimper'
);

-- Ajout Articles
INSERT INTO ARTICLES_VENDUS VALUES(
	'PC gamer',
	'Un beau PC de gamer pour geeker tout le week-end',
	'20191210 15:42:00',
	'20191219 15:42:00',
	50,
	50,
	3,
	1,
	1
);

-- Ajout Enchères
INSERT INTO ENCHERES VALUES(
	4,
	'20191210 19:00:00',
	60,
	4
);