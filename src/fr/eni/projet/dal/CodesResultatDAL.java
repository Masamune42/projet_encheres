package fr.eni.projet.dal;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {

	// ERREURS 10000 ArticlesDAO
	/**
	 * Echec de lecture de l'article lié au pseudo
	 */
	public static final int LECTURE_ARTICLE_PSEUDO_INEXISTANT = 10000;
	/**
	 * Echec de l'ecture de l'article lié à l'ID
	 */
	public static final int LECTURE_ARTICLE_ID_INEXISTANT = 10001;
	/**
	 * Echec de la lecture de tous les articles
	 */
	public static final int LECTURE_ARTICLE_TOUT_INEXISTANT = 10002;
	/**
	 * Modification impossible pour l'article sélectionné
	 */
	public static final int MODIFIER_ARTICLE_IMPOSSIBLE = 10003;
	/**
	 * Suppression impossible de l'article demandé
	 */
	public static final int SUPPRIMER_ARTICLE_IMPOSSIBLE = 10004;
	/**
	 * Insertion de l'article demandé impossible
	 */
	public static final int INSERER_ARTICLE_IMPOSSIBLE = 10005;

	/**
	 * Echec de la lecture des articles par le filtre des achats
	 */
	public static final int LECTURE_ARTICLE_FILTRE_ACHAT_IMPOSSIBLE = 10006;
	
	/**
	 * Echec de la lecture des articles par le filtre des ventes
	 */
	public static final int LECTURE_ARTICLE_FILTRE_VENTE_IMPOSSIBLE = 10007;
	/**
	 * Echec de la lecture des articles sélectionné par le filtrage en mode déconnecté
	 */
	public static final int LECTURE_ARTICLE_FILTRE_RECHERCHE_NON_CONNECT_IMPOSSIBLE = 10008;
	
	// ERREUR 11000 UserDAO
	/**
	 * Echec de l'insertion d'un utilisateur
	 */
	public static final int INSERTION_UTILISATEUR_IMPOSSIBLE = 11000;
	/**
	 * Echec de la suppression d'un utilisateur
	 */
	public static final int SUPPRESSION_UTILISATEUR_IMPOSSIBLE = 11001;
	/**
	 * Echec de la modification d'un utilisateur
	 */
	public static final int MODIFICATION_UTILISATEUR_IMPOSSIBLE = 11002;
	/**
	 * Echec de la sélection des utilisateurs
	 */
	public static final int SELECTION_DES_UTILISATEURS_IMPOSSIBLE = 11003;
	/**
	 * Echec de la vérification du pseudo de l'utilisateur
	 */
	public static final int VERIFICATION_PSEUDO_UTILISATEUR_IMPOSSIBLE = 11004;
	/**
	 * Echec de la sélection de l'utilisateur à partir de l'ID
	 */
	public static final int SELECTION_ID_UTILISATEUR_IMPOSSIBLE = 11005;
	/**
	 * Echec de la vérification de l'utilisateur car pseudo invalide
	 */
	public static final int VERIFICATION_PSEUDO_UTILISATEUR_INEXISTANT = 11006;
	/**
	 * Echec de la vérification de l'utilisateur car email invalide
	 */
	public static final int VERIFICATION_EMAIL_UTILISATEUR_INEXISTANT = 11007;
	/**
	 * Echec de la vérification de l'email de l'utilisateur
	 */
	public static final int VERIFICATION_EMAIL_UTILISATEUR_IMPOSSIBLE = 11008;
	/**
	 * Echec de la sélection de l'utilisateur à partir du pseudo
	 */
	public static final int SELECTION_PSEUDO_UTILISATEUR_IMPOSSIBLE = 11009;

	/**
	 * Modification du crédit de la vente en cours impossible
	 */
	public static final int MODIF_CREDIT_IMPOSSIBLE = 11010;
	
	
	
	
	// ERREURS 12000 CategoriesDAO

	/**
	 * Echec de la lecture de la catégorie à partir de son ID
	 */
	public static final int LECTURE_CATEGORIE_ID_IMPOSSIBLE = 12000;

	/**
	 * Echec de la lecture de toutes les catégories
	 */
	public static final int LECTURE_CATEGORIES_IMPOSSIBLE = 12001;

	/**
	 * Echec de la lecture d'une catégorie à partir de son libelle
	 */
	public static final int LECTURE_CATEGORIES_LIBELLE_IMPOSSIBLE = 12002;

	
	
	//ERREUR 13000 EnchereDAO 
	/**
	 * Echec de l'insertion de l'enchère
	 */
	public static final int INSERTION_ENCHERE_IMPOSSIBLE = 13000;
	/**
	 * Echec de la modification de l'enchère
	 */
	public static final int MODIFICATION_ENCHERE_IMPOSSIBLE = 13001;
	/**
	 * Echec de la suppression de l'enchère
	 */
	public static final int SUPPRESSION_ENCHERE_IMPOSSIBLE = 13002;
	/**
	 * Echec  de la sélection des enchères
	 */
	public static final int SELECTION_DES_ENCHERES_IMPOSSIBLE = 13003;
	/**
	 * Echec de la sélection de l'enchere avec l'ID 
	 */
	public static final int SELECTION_ID_ENCHERE_IMPOSSIBLE = 13004;
	/**
	 * Echec de la sélection du pseudo du vendeur à partir de l'ID de l'article dans une enchère
	 */
	public static final int SELECTION_PSEUDO_UTILISATEUR_ID_ARTICLE_ENCHERE_IMPOSSIBLE = 13005;
	/**
	 * Echec de la lecture du meilleur enchérisseur
	 */
	public static final int LECTURE_MEILLEUR_ENCHERISSEUR_IMPOSSIBLE = 13006;
	
	
	// ERREURS 14000 RetraitDAO

	/**
	 * Echec de la lecture de l'adresse de retrait à partir de son ID
	 */
	public static final int LECTURE_RETRAIT_ID_IMPOSSIBLE = 14000;

	/**
	 * Echec de la lecture d'ID de la table Retrait à partir de l'adresse de Retrait
	 */
	public static final int LECTURE_RETRAIT_ADRESSE_IMPOSSIBLE = 14001;

	/**
	 * Insertion de l'adresse de retrait impossible
	 */
	public static final int INSERTION_RETRAIT_IMPOSSIBLE = 14002;
	

	

	

	

	
}
