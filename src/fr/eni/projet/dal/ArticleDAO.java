package fr.eni.projet.dal;

import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Article;


/**
 * 
 * Classe en charge de définir toutes les méthodes d'un article
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 3 déc. 2019 - 16:53:16
 *
 */
public interface ArticleDAO {
	/**
	 * Insère un artcle
	 * @param article Article
	 */
	public void inserer(Article article)throws BusinessException;

	/**
	 * Supprime un article avec son ID
	 * @param id ID article
	 */
	public void supprimer(int id)throws BusinessException;

	/**
	 * Modifie un article
	 * @param article Article
	 */
	public void modifier(Article article)throws BusinessException;

	/**
	 * Récupère la liste des articles
	 * @return Liste des articles
	 */
	public List<Article> selectionnerTout()throws BusinessException;

	/**
	 * Sélectionne un article suivant son id
	 * @param id ID de l'article
	 * @return L'article
	 */
	public Article selectionnerAvecID(int id)throws BusinessException;
	
	/**
	 * Sélectionne la liste des articles mis en vente
	 * @return Liste des Encheres
	 */
	public List<Article> selectionnerAvecPseudo() throws BusinessException;

	/**
	 * 
	 * @param recherche
	 * @param categorie
	 * @param idUser
	 * @param option1
	 * @param option2
	 * @param option3
	 * @return
	 */
	public List<Article> selectionnerFiltrageAchats(String recherche, int categorie, int idUser, boolean option1, boolean option2, boolean option3)throws BusinessException;

	/**
	 * 
	 * @param recherche
	 * @param categorie
	 * @param idUser
	 * @param option1
	 * @param option2
	 * @param option3
	 * @return
	 */
	public List<Article> selectionnerFiltrageVentes(String recherche, int categorie, int idUser, boolean option1, boolean option2, boolean option3)throws BusinessException;


	public List<Article> selectionnerFiltrageRechercheNonConnecte(String recherche, int categorie) throws BusinessException;


}


