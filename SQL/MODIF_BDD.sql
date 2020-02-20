-- Modifie la taille du champs mot de passe pour accueillir un hashcode
ALTER TABLE UTILISATEURS 
ALTER COLUMN mot_de_passe VARCHAR(40) NOT NULL

-- Modifie la taille du champs des adresse mails
ALTER TABLE UTILISATEURS 
ALTER COLUMN email VARCHAR(100) NOT NULL

-- Modifie le type de données dans les dates des articles pour avoir la date ET l'heure
ALTER TABLE ARTICLES_VENDUS 
ALTER COLUMN date_debut_encheres DATETIME NOT NULL

ALTER TABLE ARTICLES_VENDUS 
ALTER COLUMN date_fin_encheres DATETIME NOT NULL

-- Suppression enchère si utilisateur supprimé
ALTER TABLE ENCHERES
DROP CONSTRAINT encheres_utilisateur_fk;

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE CASCADE 
    ON UPDATE no action

-- Suppression article si utilisateur supprimé
ALTER TABLE ARTICLES_VENDUS
DROP CONSTRAINT ventes_utilisateur_fk;

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE CASCADE 
    ON UPDATE no action 

-- Ajout colonne url image articles
ALTER TABLE ARTICLES_VENDUS
ADD url_image VARCHAR(50);