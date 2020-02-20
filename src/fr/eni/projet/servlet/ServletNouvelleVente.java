package fr.eni.projet.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.ArticleManager;
import fr.eni.projet.bll.CategorieManager;
import fr.eni.projet.bll.RetraitManager;
import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Retrait;
import fr.eni.projet.bo.User;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/ServletNouvelleVente")
@MultipartConfig
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletNouvelleVente() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CategorieManager categorieManager = CategorieManager.getInstance();
		List<Categorie> listeCategoriesCourante = new ArrayList<Categorie>();

		try {
			listeCategoriesCourante = categorieManager.selectionnerToutesLesCategories();
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}
		request.setAttribute("listeCategoriesCourante", listeCategoriesCourante);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
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
		// Récupération de la session
		HttpSession session = request.getSession();

		// Création des managers
		ArticleManager articleManager = ArticleManager.getInstance();
		RetraitManager retraitManager = RetraitManager.getInstance();
		CategorieManager categorieManager = CategorieManager.getInstance();

		// Création des objetss
		Article articleSaisi = new Article();
		Categorie categorieSaisie = new Categorie();
		Retrait retraitSaisi = new Retrait();
		User utilisateurCourant = new User();

		// Récupération de l'utilisateur en contexte de session
		utilisateurCourant = (User) session.getAttribute("utilisateurCourant");

		// Récupération de la catégorie saisie
		categorieSaisie.setLibelle(request.getParameter("categorieArticle"));
		// Récupération de l'ID de la catégorie sélectionnée par l'utilisateur et créé
		// en
		// base de donnée lors de la saisie de l'article
		int idCategorieSaisie = 0;
		try {
			idCategorieSaisie = categorieManager.selectionnerAvecLibelle(categorieSaisie.getLibelle());
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}
		// Implémentation de l'ID de la catégorie
		categorieSaisie.setId(idCategorieSaisie);

		// Récupération de l'adresse de retrait et implémentation de l'objet
		// retraitSaisi
		retraitSaisi.setRue(request.getParameter("retraitRueArticle"));
		retraitSaisi.setCodePostal(request.getParameter("retraitCodePostalArticle"));
		retraitSaisi.setVille(request.getParameter("retraitVilleArticle"));
		// Vérifier la concordance entre l'adresse de retrait saisie par l'utilisateur
		// et celle pouvant
		// exister en base de donnée
		int idRetraitAdresseExistante = 0;
		try {
			idRetraitAdresseExistante = retraitManager.selectionnerAvecAdresseComplete(retraitSaisi.getRue(),
					retraitSaisi.getCodePostal(), retraitSaisi.getVille());
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}
		// Si les champs sont identiques on récupère l'ID de l'adresse de retrait
		if (idRetraitAdresseExistante != -1) {
			retraitSaisi.setId(idRetraitAdresseExistante);
		} else {
			// l'adresse de retrait est différente de celle de base de l'utilisateur
			// Insertion en BDD de la nouvelle adresse de Retrait
			try {
				retraitManager.ajouterRetrait(retraitSaisi);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				e.printStackTrace();
			}
		}

		// Initialisation de l'article saisi
		articleSaisi.setNomArticle(request.getParameter("nomArticle"));
		articleSaisi.setDescription(request.getParameter("descriptionArticle"));
		articleSaisi.setDateDebutEncheres(
				LocalDateTime.parse(request.getParameter("dateDebutEnchere"), DateTimeFormatter.ISO_DATE_TIME));
		articleSaisi.setDateFinEncheres(
				LocalDateTime.parse(request.getParameter("dateFinEnchere"), DateTimeFormatter.ISO_DATE_TIME));
		articleSaisi.setPrixInitial(Integer.parseInt(request.getParameter("prixInitial")));
		articleSaisi.setCategorie(categorieSaisie);
		articleSaisi.setRetrait(retraitSaisi);
		articleSaisi.setUtilisateur(utilisateurCourant);

		// MODIF
		Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		
		InputStream fileContent = filePart.getInputStream();

		articleSaisi.setUrlImage(fileName);

		// Ajout de l'article saisi à la BDD
		try {
			articleManager.ajouterArticle(articleSaisi);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}

		File uploads = new File("C:/uploads/" + articleSaisi.getId());
		File file = new File(uploads, fileName);
		uploads.mkdir();
		
		if (fileName.isEmpty()) {
			
		} else {
			Files.copy(fileContent, file.toPath());
		}
			

		// FIN MODIF

		/*
		 * Récupération de l'image de l'article et stockage sur un dossier sur
		 * l'ordinateur
		 *
		 */
		// TODO prévoir le stockage de l'image sur la BDD plutot qu'en dur sur
		// l'ordinateur
		/*
		 * Part filePart = request.getPart("photoArticle"); // Retrieves <input
		 * type="file" name="file"> String fileName =
		 * Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE
		 * fix. InputStream fileContent = filePart.getInputStream();
		 * 
		 * File uploads = new File(
		 * "C:/Users/spriou2019/Documents/Projet/Test_Projet_1/WebContent/WEB-INF/images"
		 * ); File file = new File(uploads, fileName); Files.copy(fileContent,
		 * file.toPath());
		 * 
		 */

		RequestDispatcher rd = request.getRequestDispatcher("/ServletAccueilConnect");
		rd.forward(request, response);
	}

}
