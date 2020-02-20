package fr.eni.projet.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.ArticleManager;
import fr.eni.projet.bll.CategorieManager;
import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet("/ServletAccueil")
public class ServletAccueil extends HttpServlet {

	private static final long serialVersionUID = 697054620879487552L;

	public ServletAccueil() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Section Filtre => Instanciation pour récupération de la liste des catégories
		CategorieManager catManager = CategorieManager.getInstance();
		List<Categorie> listeCategoriesCourante = new ArrayList<Categorie>();
		try {
			listeCategoriesCourante = catManager.selectionnerToutesLesCategories();
		} catch (BusinessException e1) {
			request.setAttribute("listeCodesErreur", e1.getListeCodesErreur());
			e1.printStackTrace();
		}
		request.setAttribute("listeCategoriesCourante", listeCategoriesCourante);

		// Section Liste des enchères => Instanciation pour récupération de la liste des
		// articles mis en vente
		ArticleManager artManager = ArticleManager.getInstance();
		List<Article> listeEncheresCourante = new ArrayList<>();
		try {
			listeEncheresCourante = artManager.getSelectionnerAvecPseudo();
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}
		request.setAttribute("listeEncheresCourante", listeEncheresCourante);

		// Point d'entrée utilisateur qui renvoit vers la JSP Accueil
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// encodage
		request.setCharacterEncoding("UTF-8");

		// Rechercher un article mis en vente
		ArticleManager artManager = ArticleManager.getInstance();
		List<Article> listeEncheresCourante = new ArrayList<>();

		// Récupération des paramètres de accueil.jsp
		String rechercher = request.getParameter("search");
		String cat = request.getParameter("cat");

		// filtre par nom
		try {
			listeEncheresCourante = artManager.getSelectionnerAvecPseudo(/* rechercher */);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		} // TO DO = créer des méthodes dans articleManager pour filtres
		request.setAttribute("listeEncheresCourante", listeEncheresCourante);

		// filtre par catégorie
		// On récupère le libellé de la catégorie (String) et on le rattache à
		// articlesVendus via no_categorie (int)
		int categorie = Integer.parseInt(cat);
		try {
			listeEncheresCourante = artManager.getSelectionnerAvecPseudo(/* categorie */);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		} // TO DO = créer des méthodes dans articleManager pour filtres
		request.setAttribute("listeEncheresCourante", listeEncheresCourante);

	}

}
