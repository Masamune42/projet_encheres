package fr.eni.projet.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.UserManager;
import fr.eni.projet.bo.User;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet(urlPatterns = { "/Creer", "/ServletInscription" })
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletInscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inscription.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// encodage
		request.setCharacterEncoding("UTF-8");
		UserManager userManager = UserManager.getInstance();
		User utilisateurCourant = null;

		// Vérifier que le pseudo et l'adresse mail n'existent pas déjà => Le pseudo doit être unique sur toute la plateforme, ainsi que l’email.
		boolean VerifPseudo = false;
		boolean VerifEmail = false;
		try {
			VerifPseudo = userManager.verifierPseudo(request.getParameter("pseudo"));
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}
		try {
			VerifEmail = userManager.verifierEmail(request.getParameter("email"));
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}

		if (VerifPseudo == false || VerifEmail == false ) {
			request.setAttribute("erreur pseudo", "Ce pseudo est déjà utilisé; merci d'en choisir un autre");
			getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);

			return;
		} 

		// Vérifier que les champs "required" sont bien remplis (tous sauf tel)
		else if (!request.getParameter("pseudo").isEmpty() && 
				!request.getParameter("nom").isEmpty() && 
				!request.getParameter("prenom").isEmpty()&& 
				!request.getParameter("email").isEmpty() && 
				!request.getParameter("rue").isEmpty() && 
				!request.getParameter("cp").isEmpty()&& 
				!request.getParameter("ville").isEmpty()&& 
				!request.getParameter("mdp").isEmpty()
				) {
			
		// Instanciation du nouvel utilisateur
			utilisateurCourant = new User();
			utilisateurCourant.setPseudo(request.getParameter("pseudo"));
			utilisateurCourant.setNom(request.getParameter("nom"));
			utilisateurCourant.setPrenom(request.getParameter("prenom"));
			utilisateurCourant.setEmail(request.getParameter("email"));
			utilisateurCourant.setTelephone(request.getParameter("tel"));
			utilisateurCourant.setRue(request.getParameter("rue"));
			utilisateurCourant.setCodePostal(request.getParameter("cp"));
			utilisateurCourant.setVille(request.getParameter("ville"));
			
			// Récupération MDP de l'utilisateur
			String motDePasse = request.getParameter("mdp");
			// Hashage du MDP
			String motDePasseHash = hashageMotDePasse(motDePasse);
			
			utilisateurCourant.setMotDePasse(motDePasseHash);
			utilisateurCourant.setCredit(100);
			utilisateurCourant.setAdministrateur(false);
			
		} else {
			request.setAttribute("erreur inscription", "L'inscription n'a pas pu aboutir, veuillez recommencer");
			getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
			
			
		}


		//  Insertion dans la BDD une fois les conditions remplies

		if (request.getParameter("mdpconf").equals(request.getParameter("mdp"))) {
			try {
				userManager.ajouterUtilisateur(utilisateurCourant);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				e.printStackTrace();
			}

		} else {
			request.setAttribute("erreur mdp", "Les mots de passe ne sont pas identiques; nous vous invitons à vérifier l'orthographe");
			getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
			return;

		}

		// Création d'une session pour l'utilisateur
		HttpSession session = request.getSession();
		session.setAttribute("connecte", true);
		session.setAttribute("utilisateurCourant", utilisateurCourant);

		// Redirection
		// Si le bouton Créer est cliqué, on renvoit l'utilisateur vers la page
		// d'accueil en mode connecté
		if (request.getServletPath().equals("/Creer")) {
			session.setAttribute("connecte", true);
			RequestDispatcher rd = request.getRequestDispatcher("/ServletAccueilConnect");
			rd.forward(request, response);

		}
	}
	/**
	 * Méthode en charge de
	 * @param motDePasse
	 */
	private String hashageMotDePasse(String motDePasse) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(motDePasse.getBytes());
		byte[] digest = md.digest();
		String motDePasseHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return motDePasseHash;
	}

}
