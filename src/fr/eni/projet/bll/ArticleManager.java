package fr.eni.projet.bll;

import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Article;
import fr.eni.projet.dal.ArticleDAO;
import fr.eni.projet.dal.DAOFactory;

public class ArticleManager {
	private ArticleDAO daoArticle;
	private static ArticleManager instance;

	private ArticleManager() {
		this.daoArticle = DAOFactory.getArticleDAO();
	}

	public static ArticleManager getInstance() {
		if (instance == null) {
			instance = new ArticleManager();
		}

		return instance;
	}

	public void ajouterArticle(Article article) throws BusinessException{
		daoArticle.inserer(article);
	}

	public void supprimerArticle(int id) throws BusinessException{
		daoArticle.supprimer(id);
	}

	public void modifierArticle(Article article) throws BusinessException{
		daoArticle.modifier(article);
	}

	public List<Article> selectionnerTousLesArticles() throws BusinessException{
		return daoArticle.selectionnerTout();
	}

	public List<Article> getSelectionnerAvecPseudo() throws BusinessException{
		List<Article> listeEncheres = daoArticle.selectionnerAvecPseudo();
		return listeEncheres;
	}

	public Article selectionnerArticleAvecID(int id) throws BusinessException{
		return daoArticle.selectionnerAvecID(id);
	}
	
	public List<Article> selectionnerFiltrageAchats(String recherche, int categorieSelec, int idUser, boolean option1,
			boolean option2, boolean option3) throws BusinessException{
		return daoArticle.selectionnerFiltrageAchats(recherche, categorieSelec, idUser, option1, option2, option3);
	}
	
	public List<Article> selectionnerFiltrageVentes(String recherche, int categorieSelec, int idUser, boolean option1,
			boolean option2, boolean option3) throws BusinessException{
		return daoArticle.selectionnerFiltrageVentes(recherche, categorieSelec, idUser, option1, option2, option3);
	}

	public List<Article> selectionnerFiltrageRechercheNonConnecte(String recherche, int categorieSelec) throws BusinessException{
		return daoArticle.selectionnerFiltrageRechercheNonConnecte(recherche, categorieSelec);
	}
}
