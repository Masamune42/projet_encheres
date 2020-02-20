package fr.eni.projet.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.UserManager;
import fr.eni.projet.bo.User;

/**
 * Servlet implementation class ServletUtilisateur
 */

@WebServlet("/ServletUtilisateur")
public class ServletUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }
    
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User UtilisateurVendeur = new User();
		UserManager usermanager = UserManager.getInstance();
		String pseudo = request.getParameter("pseudo");
		try {
			UtilisateurVendeur = usermanager.selectionnerAvecPseudo(pseudo);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}
		
		request.setAttribute("utilisateurVendeur", UtilisateurVendeur);
		
	
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/afficherUtilisateur.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
