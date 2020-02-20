package fr.eni.projet.dal;

/**
 * 
 * Classe en charge de
 * 
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 2 déc. 2019 - 15:34:08
 *
 */
public abstract class DAOFactory {
	public static UserDAO getUserDAO() {
		return new UserDAOJdbcImpl();
	}

	public static ArticleDAO getArticleDAO() {
		return new ArticleDAOJdbcImpl();
	}

	public static CategorieDAO getCategorieDAO() {
		return new CategorieDAOJdbcImpl();
	}

	public static RetraitDAO getRetraitDAO() {
		return new RetraitDAOJdbcImpl();
	}
	
	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOJdbcImpl();
	}
}
