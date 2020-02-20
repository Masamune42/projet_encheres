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
 * Servlet implementation class ServletProfil
 */
@WebServlet("/ServletProfil")
public class ServletProfil extends HttpServlet {
	private static final long serialVersionUID = -8163161871674122849L;
	private String enre = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletProfil() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifierProfil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		// Récupération de la saison
		HttpSession session = request.getSession();
		UserManager usermanager = UserManager.getInstance();
		User utilisateurCourant = new User();
		utilisateurCourant = (User) session.getAttribute("utilisateurCourant");
		enre = request.getParameter("enregistrer");
		int Mot = 0;

		// Récupération MDP de l'utilisateur
		String mdpActu = request.getParameter("mdp");

		// Hashage du MDP Actuel
		String MdpHash = hashageMotDePasse(mdpActu);

		// Vérifier que le pseudo et l'adresse mail n'existent pas déjà => Le pseudo
		// doit être unique sur toute la plateforme, ainsi que l’email.
		boolean VerifPseudo = false;
		boolean VerifEmail = false;
		try {
			VerifPseudo = usermanager.verifierPseudo(request.getParameter("pseudo"));
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}
		try {
			VerifEmail = usermanager.verifierEmail(request.getParameter("email"));
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}

		if (VerifPseudo == false && utilisateurCourant.getPseudo().equals(request.getParameter("pseudo"))
				|| VerifEmail == false && utilisateurCourant.getEmail().equals(request.getParameter("email"))) {
			// Instanciation des paramètres
			utilisateurCourant.setPseudo(request.getParameter("pseudo"));
			utilisateurCourant.setNom(request.getParameter("nom"));
			utilisateurCourant.setPrenom(request.getParameter("prenom"));
			utilisateurCourant.setEmail(request.getParameter("email"));
			utilisateurCourant.setTelephone(request.getParameter("tel"));
			utilisateurCourant.setRue(request.getParameter("rue"));
			utilisateurCourant.setCodePostal(request.getParameter("cp"));
			utilisateurCourant.setVille(request.getParameter("ville"));

			// Récupération nouveau MDP
			String nvMdpModif = request.getParameter("nvMdp");

			// Hashage du nouveau MDP
			String nvMotDePasseHash = hashageMotDePasse(nvMdpModif);

			utilisateurCourant.setMotDePasse(nvMotDePasseHash);

		}

		else if (VerifPseudo == false || VerifEmail == false) {
			System.out.println("erreur pseudo/mail" + "Ce pseudo/mail est déjà utilisé; merci d'en choisir un autre");
			getServletContext().getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request, response);

			return;
		} else if (!request.getParameter("pseudo").isEmpty() && !request.getParameter("nom").isEmpty()
				&& !request.getParameter("prenom").isEmpty() && !request.getParameter("email").isEmpty()
				&& !request.getParameter("ville").isEmpty() && !request.getParameter("rue").isEmpty()
				&& !request.getParameter("cp").isEmpty() && !request.getParameter("mdp").isEmpty()
				&& !request.getParameter("nvMdp").isEmpty() && !request.getParameter("confMdp").isEmpty()) {

			getServletContext().getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request, response);
			return;
		}

		if (!request.getParameter("nvMdp").equals(request.getParameter("confMdp"))
				&& !utilisateurCourant.getMotDePasse().equals(MdpHash)) {
			Mot=1;
			request.setAttribute("Mot", Mot);
			getServletContext().getRequestDispatcher("/WEB-INF/modifierProfil.jsp").forward(request, response);
			
			return;
		} else {
			try {
				usermanager.modifierUtilisateur(utilisateurCourant);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				e.printStackTrace();
			}
		}

		// Quand on clique sur le bouton Enregistrer, on enclenche la modification de
		// l'utilisateur
		// Redirection vers l'accueil de l'utilisateur
		if (enre != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/ServletAccueilConnect");
			rd.forward(request, response);
		}
	}

	/**
	 * Méthode en charge de hasher le mot de passe
	 * 
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
