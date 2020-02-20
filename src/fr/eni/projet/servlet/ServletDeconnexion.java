package fr.eni.projet.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.UserManager;
import fr.eni.projet.bo.User;

/**
 * Servlet implementation class ServletDeconnexion
 */
@WebServlet("/ServletDeconnexion")
public class ServletDeconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDeconnexion() {
		super();
	}

	/**
	 * Cette méthode va rediriger l'utilisateur vers la page d'accueil en mode
	 * déconnecté
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//TODO gérer la déconnexion de la session de l'utilisateur
		HttpSession session = request.getSession();
		//Fermeture de la session.
		session.invalidate();
		
		response.sendRedirect("ServletAccueil");

	}

	/**
	 * Cette méthode va rediriger l'utilisateur vers la page d'accueil en mode
	 * déconnecté en supprimant le compte de l'utilisateur
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		UserManager usermanager = UserManager.getInstance();
		User utilisateurCourant = new User();
		
		
		// TODO Vérifier quel parametre on récupère depuis la page Profil pour savoir si
		// c'est l'ID ou le nom de l'utilisateur

		// Récupération de l'id de l'utilisateur depuis la page du profil.
		HttpSession session = request.getSession();
		utilisateurCourant = (User)session.getAttribute("utilisateurCourant");
		System.out.println(utilisateurCourant);
		// suppression de l'utilisateur dans la BDD à partir de son ID
		try {
			usermanager.supprimerUtilisateur(utilisateurCourant.getId());
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}

		doGet(request, response);
	}

}
