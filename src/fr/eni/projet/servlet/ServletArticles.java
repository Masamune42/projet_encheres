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
import javax.servlet.http.HttpSession;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.ArticleManager;
import fr.eni.projet.bll.CategorieManager;
import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.User;

/**
 * Servlet implementation class ServletArticles
 */
@WebServlet("/ServletArticles")
public class ServletArticles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletArticles() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//Récupérer de la session
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		
		
		CategorieManager categorieManager = CategorieManager.getInstance();
		//Création d'un manager
		ArticleManager articleManager= ArticleManager.getInstance();
		//Création d'une liste d'articles
		List<Article> listeArticlesFiltrage = new ArrayList<Article>();
		
		String checkEnchereOuverte = request.getParameter("enchereOuverte");
		String checkEnchereEnCours = request.getParameter("enchereEnCours");
		String checkEnchereRemportee = request.getParameter("enchereRemportee");
		
		//Conversion en boolean
		Boolean checkEnchereOuverteBool = Boolean.parseBoolean(checkEnchereOuverte);
		Boolean checkEnchereEnCoursBool = Boolean.parseBoolean(checkEnchereEnCours);
		Boolean checkEnchereRemporteeBool = Boolean.parseBoolean(checkEnchereRemportee);
		
		

		String checkVenteEnCours= request.getParameter("venteEnCours");
		String checkVenteNonDebutee = request.getParameter("venteNonDebutees");
		String checkVenteTerminee = request.getParameter("venteTerminees");

		
		//Conversion en boolean
		Boolean checkVenteEnCoursBool = conversionStringtoBoolean(checkVenteEnCours);
		Boolean checkVenteNonDebuteeBool = conversionStringtoBoolean(checkVenteNonDebutee);
		Boolean checkVenteTermineeBool = conversionStringtoBoolean(checkVenteTerminee);
		
		
		//Récupération de la catégorie saisie
		String categorieChoisie = request.getParameter("cat");
		
		//Récupération de la zone texte rechercher
		String articleRecherche =  request.getParameter("search");
		
		int idCategorie = 0;
		try {
			idCategorie = categorieManager.selectionnerAvecLibelle(categorieChoisie);
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Création d'un utilisateur pour récupérer son id
		User utilisateurCourant = new User();
		
		//Récupération de l'id de l'utilisateur
		utilisateurCourant = (User) session.getAttribute("utilisateurCourant");
		
		
		/**
		 * Si la session est nulle, il n'y a pas d'utilisateur		
		 */
		if(utilisateurCourant == null) {
			try {
				listeArticlesFiltrage = articleManager.selectionnerFiltrageRechercheNonConnecte(articleRecherche, idCategorie);
				
				rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
			
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			
			
			int idUtilisateur = utilisateurCourant.getId();
			/**
			 * Si la session n'est pas nulle, l'utilsateur est connecté
			 */
					
			//Création d'une liste d'article suivant le filtrage des achats choisies par l'utilisateur
			try {
				listeArticlesFiltrage = articleManager.selectionnerFiltrageAchats(articleRecherche, idCategorie, idUtilisateur, checkEnchereEnCoursBool, checkEnchereOuverteBool, checkEnchereRemporteeBool);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Création d'une liste d'article suivant le filtrage des ventes choisies par l'utilisateur
			try {
				listeArticlesFiltrage = articleManager.selectionnerFiltrageVentes(articleRecherche, idCategorie, idUtilisateur, checkVenteEnCoursBool, checkVenteNonDebuteeBool, checkVenteTermineeBool);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			rd = request.getRequestDispatcher("/WEB-INF/accueilConnect.jsp");
		}
		
		//Récupération de toutes les catégories
		List<Categorie> listeCategoriesCourante = new ArrayList<Categorie>();
		try {
			listeCategoriesCourante = categorieManager.selectionnerToutesLesCategories();
		} catch (BusinessException e1) {
			request.setAttribute("listeCodesErreur", e1.getListeCodesErreur());
			e1.printStackTrace();
		}

		
		request.setAttribute("listeCategoriesCourante", listeCategoriesCourante);
		request.setAttribute("listeEncheresCourante", listeArticlesFiltrage);
		
		rd.forward(request, response);
	}

	/**
	 * Méthode en charge de
	 * @param checkVenteEnCours
	 * @return
	 */
	private Boolean conversionStringtoBoolean(String checkBoxEnCours) {
		boolean checkOk = false;
		if("null".equals(checkBoxEnCours)) {
			checkOk = false;
		}else if("on".equals(checkBoxEnCours)) {
			checkOk = true;
		}
		return checkOk;
	}

}
