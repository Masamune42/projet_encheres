package fr.eni.projet.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Local;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.ArticleManager;
import fr.eni.projet.bll.EnchereManager;
import fr.eni.projet.bll.UserManager;
import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Enchere;
import fr.eni.projet.bo.User;

/**
 * Servlet implementation class ServletDetailsVente
 */
@WebServlet("/ServletDetailsVente")
public class ServletDetailsVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletDetailsVente() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Affichage de l'article

		// Association nom article et id article

		int articleEnVente = Integer.parseInt(request.getParameter("idArticle"));
		// System.out.println(articleEnVente);
		ArticleManager articleManager = ArticleManager.getInstance();
		Article articleEnchere = new Article();
		try {
			articleEnchere = articleManager.selectionnerArticleAvecID(articleEnVente);
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		request.setAttribute("articleEnchere", articleEnchere);

		// Récupération du nom du dernier enchérisseur
		if (articleEnchere.getPrixVente() != 0) {

			int meilleureEnchere = articleEnchere.getPrixVente();

			// Instanciation d'un utilisateur pour récupérer le meilleur enchérisseur
			User encherisseur = new User();
			EnchereManager em = EnchereManager.getInstance();
			try {
				encherisseur = em.getSelectPseudo(meilleureEnchere, articleEnVente); // User id et non article id

			} catch (BusinessException e) {
				e.printStackTrace();
			}
			request.setAttribute("encherisseur", encherisseur);

		}
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/detailsVente.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserManager userManager = UserManager.getInstance();
		EnchereManager enchManager = EnchereManager.getInstance();
		ArticleManager artManager = ArticleManager.getInstance();

		/**
		 * Redirection vers la page de la fin de l'enchère
		 */
		// Vérification de la date de fin d'enchère avec celle d'aujourd'hui
		// Récupération de la date de fin d'enchère
		LocalDateTime dateFinEnchere = (LocalDateTime) request.getAttribute("finEnchere");
		// Récupération de la date d'aujourd'hui
		// LocalDateTime dateToday = LocalDateTime now();

		// Récupération de la meilleure offre

		String meillOffre = request.getParameter("meilleureOffre");
		String prixIn = request.getParameter("prixInitial");

		int meilleureOffre = 0;
		int prixInitial = 0;

		// System.out.println("meilleure offre : " + meillOffre);
		if (meillOffre != null) {
			meilleureOffre = Integer.parseInt(meillOffre);
		}

		// String encherisseur = request.getParameter("encherisseur");
		String numEncherisseurPrecedent = request.getParameter("encherisseurId");
		int encherisseurPrecedent = -1;

		if (numEncherisseurPrecedent != null) {
			encherisseurPrecedent = Integer.parseInt(numEncherisseurPrecedent);

		}

		// TODO fixer prix intial = prix de vente (?)

		// Récupération de la date de fin de l'enchère
		String dateFin = request.getParameter("finEnchere");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(dateFin, formatter);

		// Mise en place de la date de l'enchère
		LocalDateTime today = LocalDateTime.now();

		// Récupération de la proposition d'enchère
		String proposition = request.getParameter("prop");
		int prop = Integer.parseInt(proposition);

		// Récupération de l'id article
		int noArticle = Integer.parseInt(request.getParameter("idArticle"));

		// Recupération de l'utilisateur + récupération de son id
		HttpSession session = request.getSession();
		User utilisateur = (User) request.getSession().getAttribute("utilisateurCourant");
		int idUtilisateurCourant = Integer.parseInt(request.getParameter("idUser"));

		// Récupération du vendeur
		String vendeur = request.getParameter("pseudoVendeur");

		Article article = null;
		try {
			article = artManager.selectionnerArticleAvecID(noArticle);
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Instanciation de la nouvelle enchère
		Enchere enchere = new Enchere();
		enchere.setDateEnchere(today);
		enchere.setMontantEnchere(prop);
		enchere.setArticle(article);

		try {
			enchere.setUtilisateur(userManager.selectionnerAvecID(idUtilisateurCourant));
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		// Deduction de l'enchere des crédits de l'utilisateur + maj credit
		int creditActuel = utilisateur.getCredit();

		if (prop <= creditActuel && prop > meilleureOffre) {
			// S'il n'y a pas d'enchérisseur, on ajoute une enchère
			// Sinon on la modifie.
			if (numEncherisseurPrecedent == null) {
				try {
					// System.out.println("ajout enchere");
					enchManager.ajouterEnchere(enchere);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					// System.out.println("modifier enchere");
					enchManager.modifierEnchere(enchere);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// On récupère les infos de l'article vendu
			Article articleVendu = new Article();
			int idArticle = Integer.parseInt(request.getParameter("idArticle"));
			try {
				articleVendu = artManager.selectionnerArticleAvecID(idArticle);
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// On change le prix de l'article
			articleVendu.setPrixVente(prop);
			try {
				artManager.modifierArticle(articleVendu);
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			utilisateur.setCredit(creditActuel - prop);
			if (numEncherisseurPrecedent == null) {
				// S'il n'y a pas d'enchérisseur, on ne recrédite personne
			} else {
				try {
					// S'il y en a un, on récupère ses infos et on le crédite
					User ancienUtilisateur = userManager.selectionnerAvecID(encherisseurPrecedent);
					int creditAncienUtilisateur = ancienUtilisateur.getCredit();
					int nouveauCredit = creditAncienUtilisateur + meilleureOffre;
					ancienUtilisateur.setCredit(nouveauCredit);
					userManager.modifierUtilisateur(ancienUtilisateur);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			try {
				userManager.modifierUtilisateur(utilisateur);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// TODO : Suppression de l'article une fois vendu

		RequestDispatcher rd = request.getRequestDispatcher("/ServletAccueilConnect");
		rd.forward(request, response);
		// doGet(request, response);
	}

}
