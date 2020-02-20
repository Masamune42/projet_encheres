package fr.eni.projet.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.UserManager;
import fr.eni.projet.bo.User;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/ServletConnexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletConnexion() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// MAJ de la page de connexion
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		int idUtilisateurEMail = 0;
		int idUtilisateurPseudo = 0;
		int idUtilisateurPrincipal = 0;
		RequestDispatcher rd = null;

		UserManager usermanager = UserManager.getInstance();
		User utilisateurCourant = new User();

		// récupération du paramètre identifiant entrer par l'utilisateur (pseudo ou
		// email)
		String pseudoEMail = request.getParameter("pseudo");

		// Récupération du mot de passe de l'utilisateur
		String motDePasse = request.getParameter("mdp");

		// Récuparation de la case cocher "Se souvenir de moi"
		String souvenir = request.getParameter("souvenir");

		/**
		 * Hashage du mot de passe de l'utilisateur
		 */
		String motDePasseHash = hashageMotDePasse(motDePasse);

		/*
		 * Vérification de l'existance de l'identifiant par rapport à la base de donnée
		 */

		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pseudoEMail);

		// Vérification si l'identifiant saisie est un pseudo ou une adresse mail
		if (matcher.matches()) {
			// l'identifiant saisi est une adresse mail
			try {
				idUtilisateurEMail = usermanager.verifierUtilisateurEmail(pseudoEMail, motDePasseHash);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				e.printStackTrace();
			}
			idUtilisateurPrincipal = idUtilisateurEMail;
		} else {
			// l'identifiant est un pseudo
			try {
				idUtilisateurPseudo = usermanager.verifierUtilisateurPseudo(pseudoEMail, motDePasseHash);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				e.printStackTrace();
			}
			idUtilisateurPrincipal = idUtilisateurPseudo;
		}

		// Si l'utilisateur n'existe pas on reste sur la même page en indiquant que
		// l'utilisateur n'existe pas
		if (idUtilisateurPrincipal == -1) {
			// On renvoie sur la page de connexion.jsp un entier de valeur -1 pour indiquer
			// que l'utilisateur n'existe pas
			request.setAttribute("utilisateurInexistant", idUtilisateurPrincipal);
		//	request.setAttribute("utilisateurInexistant", idUtilisateurPseudo);
		//	request.setAttribute("utilisateurInexistant", idUtilisateurEMail);
			rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
			// rd.forward(request, response);
		}


		/*
		 * Vérification de la case "Se souvenir de moi"
		 */

		// Si la case "se souvenir de moi" est cochée, alors garder le paramètre
		// "identifiant" en contexte de cookie
		if (souvenir != null) {
			// On passe les paramètres en contexte de cookie avec une valeur aléatoire
			Random random = new Random();
			Cookie cookieUtilisateur = new Cookie("cookieUtilisateur", "valeurCookieUtilisateur_" + random.nextInt());
			response.addCookie(cookieUtilisateur);

			// Stockage des données de l'utilisateur courant par son id
			try {
				utilisateurCourant = usermanager.selectionnerAvecID(idUtilisateurPrincipal);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				e.printStackTrace();
			}
			request.setAttribute("utilisateurCourant", utilisateurCourant);

		} else {
			// La case se souvenir n'est pas cochée, donc nous gardons le pseudo et le mot
			// de
			// passe le temps de la navigation de l'utilisateur

			// Récupération de la session de l'utilisateur
			HttpSession session = request.getSession();

			// L'utilisateur est déconnecté après 5 minutes d'inactivité
	//		session.setMaxInactiveInterval(5 * 60);
	//		response.sendRedirect(request.getContextPath() + "/accueil");

			// Stockage des données de l'utilisateur courant par son id
			try {
				utilisateurCourant = usermanager.selectionnerAvecID(idUtilisateurPrincipal);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				e.printStackTrace();
			}
			session.setAttribute("utilisateurCourant", utilisateurCourant);

		}
		if (idUtilisateurPrincipal != -1) {
			rd = request.getRequestDispatcher("/ServletAccueilConnect");
			
		}
		rd.forward(request, response);
	}

	/**
	 * Méthode en charge de crypter le mot de passe de l'utilisateur
	 * 
	 * @param motDePasse
	 * @return
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
