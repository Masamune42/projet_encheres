-- JOINTURE TABLES ENCHERES ET UTILISATEURS, no_utilisateur
SELECT e.no_article, e.date_enchere, e.montant_enchere, u.pseudo
FROM ENCHERES e INNER JOIN UTILISATEURS u
ON e.no_utilisateur = u.no_utilisateur
WHERE u.no_utilisateur=11;

-- JOINTURE TABLES ARTICLES_VENDUS, UTILISATEURS, CATEGORIES et RETRAITS
SELECT * FROM ARTICLES_VENDUS a
INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
INNER JOIN RETRAITS r ON a.no_retrait = r.no_retrait;

-- JOINTURE TABLES ARTICLES_VENDUS, UTILISATEURS, CATEGORIES et RETRAITS, no_article
SELECT * FROM ARTICLES_VENDUS a
INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
INNER JOIN RETRAITS r ON a.no_retrait = r.no_retrait
WHERE no_article=1;

-- JOINTURE TABLES ENCHERES ET UTILISATEURS
SELECT * FROM ENCHERES e
INNER JOIN UTILISATEURS u
ON e.no_utilisateur = u.no_utilisateur;

-- JOINTURE TABLES ENCHERES ET UTILISATEURS, ID
SELECT * FROM ENCHERES e
LEFT JOIN UTILISATEURS u
ON e.no_utilisateur = u.no_utilisateur
WHERE no_article=1;

-- REQUÊTES POUR FILTRAGE
-- PAR CATEGORIE
SELECT * FROM ARTICLES_VENDUS a
INNER JOIN CATEGORIES c
ON a.no_categorie = c.no_categorie
WHERE a.no_categorie = 1;


-- FILTRAGE SELON CRITERES
SELECT * FROM ARTICLES_VENDUS a
-- ON AFFICHE LES ARTICLES MÊME SI IL N'Y A AUCUNE ENCHERE DESSUS, PAS POSSIBLE AVEC INNER JOIN
LEFT JOIN ENCHERES e ON a.no_article = e.no_article
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
-- FILTRAGE SUIVANT LE NOM CHERCHE
WHERE a.nom_article LIKE '%%'
-- FILTRAGE SUIVANT LA CATEGORIE
AND (c.no_categorie = 2 or -1=?)
-- FILTRAGE SUIVANT LE TYPE D'ARTICLE : AJOUTER AND PUIS CUSTOMISER DANS LES PARANTHESES AVEC DES OR
-- ACHAT :
-- ENCHERES OUVERTES
AND (GETDATE() < a.date_fin_encheres AND GETDATE() > a.date_debut_encheres
-- MES ENCHERES EN COURS
OR e.no_utilisateur = 15 AND GETDATE() < a.date_fin_encheres
-- MES ENCHERES REMPORTEES
OR e.no_utilisateur = 15 AND GETDATE() > a.date_fin_encheres)
-- VENTE :
-- MES VENTES EN COURS
AND (a.no_utilisateur = 15 AND GETDATE() < a.date_fin_encheres AND GETDATE() > a.date_debut_encheres
-- MES VENTES NON DEBUTEES
OR a.no_utilisateur = 15 AND GETDATE() < a.date_debut_encheres
-- MES VENTES TERMINEES
OR a.no_utilisateur = 15 AND GETDATE() > a.date_debut_encheres
);

SELECT * FROM ARTICLES_VENDUS a
-- ON AFFICHE LES ARTICLES MÊME SI IL N'Y A AUCUNE ENCHERE DESSUS, PAS POSSIBLE AVEC INNER JOIN
LEFT JOIN ENCHERES e ON a.no_article = e.no_article
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
-- FILTRAGE SUIVANT LE NOM CHERCHE
WHERE a.nom_article LIKE '%%'
-- FILTRAGE SUIVANT LA CATEGORIE
AND c.no_categorie = 2
AND (a.no_utilisateur = 15 AND GETDATE() < a.date_fin_encheres AND GETDATE() > a.date_debut_encheres);

SELECT * FROM UTILISATEURS WHERE administrateur = 1;

-- FILTRAGE SELON CRITERES
SELECT * FROM ARTICLES_VENDUS a
-- ON AFFICHE LES ARTICLES MÊME SI IL N'Y A AUCUNE ENCHERE DESSUS, PAS POSSIBLE AVEC INNER JOIN
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
-- FILTRAGE SUIVANT LE NOM CHERCHE
WHERE a.nom_article LIKE '%%'
-- FILTRAGE SUIVANT LA CATEGORIE
AND (c.no_categorie = 2 or -1=?)
-- VENTE :
-- MES VENTES EN COURS
AND (a.no_utilisateur = 15 AND GETDATE() < a.date_fin_encheres AND GETDATE() > a.date_debut_encheres
-- MES VENTES NON DEBUTEES
OR a.no_utilisateur = 15 AND GETDATE() < a.date_debut_encheres
-- MES VENTES TERMINEES
OR a.no_utilisateur = 15 AND GETDATE() > a.date_debut_encheres
);

SELECT * FROM ARTICLES_VENDUS a
-- ON AFFICHE LES ARTICLES MÊME SI IL N'Y A AUCUNE ENCHERE DESSUS, PAS POSSIBLE AVEC INNER JOIN
INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie
-- FILTRAGE SUIVANT LE NOM CHERCHE
WHERE a.nom_article LIKE '%%'
-- FILTRAGE SUIVANT LA CATEGORIE
AND a.no_utilisateur = 15 AND GETDATE() < a.date_debut_encheres;