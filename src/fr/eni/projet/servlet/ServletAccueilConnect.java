package fr.eni.projet.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.ArticleManager;
import fr.eni.projet.bll.CategorieManager;
import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;


/**
 * Servlet implementation class ServletAccueilConnect
 */
@WebServlet("/ServletAccueilConnect")
public class ServletAccueilConnect extends HttpServlet {


	private static final long serialVersionUID = -9130600481083441822L;


	public ServletAccueilConnect() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Instanciation pour récupération de la liste des catégories
		CategorieManager catManager = CategorieManager.getInstance();
		List<Categorie> listeCategoriesCourante = new ArrayList<Categorie>();
		
		try {
			listeCategoriesCourante = catManager.selectionnerToutesLesCategories();
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}
		request.setAttribute("listeCategoriesCourante", listeCategoriesCourante);

		// Section Liste des enchères => Instanciation pour récupération de la liste des articles mis en vente
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
		HttpSession session = request.getSession();
		session.getAttribute("utilisateurCourant");
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/accueilConnect.jsp");
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
