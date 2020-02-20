package fr.eni.projet.servlet;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bll.CategorieManager;
import fr.eni.projet.bo.Categorie;

/**
 * Servlet implementation class ServletAffichageImage
 */
@WebServlet("/ServletAffichageImage")
public class ServletAffichageImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAffichageImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			int idArticle = Integer.parseInt(request.getParameter("idArticle"));
			int idCategorie = Integer.parseInt(request.getParameter("categorie"));

			CategorieManager catManager = CategorieManager.getInstance();
			List<Categorie> listeCategories = catManager.selectionnerToutesLesCategories();

			DataOutput output = new DataOutputStream(response.getOutputStream());
			response.setContentType("image/jpeg");

			String nomImage = request.getParameter("nomImage");

			File file = null;

			FileInputStream in = null;

			String filePath = "";
			if (nomImage.isEmpty()) {
				// System.out.println(idArticle + " n'a pas d'image");
				if (idCategorie == listeCategories.get(0).getId()) {
					filePath = "C:/uploads/hp-omen-15-dh0059nf.jpg";
				} else if (idCategorie == listeCategories.get(1).getId()) {
					filePath = "C:/uploads/odilon-meuble-d-entree-1-porte-4-tiroirs.jpg";
				} else if (idCategorie == listeCategories.get(2).getId()) {
					filePath = "C:/uploads/istock-174077874-recadre.jpg";
				} else if (idCategorie == listeCategories.get(3).getId()) {
					filePath = "C:/uploads/sportloisir.jpg";
				}
				// System.out.println(filePath);
			} else {

				filePath = "C:/uploads/" + idArticle + "/" + nomImage;
			}

			// System.out.println(filePath);

			file = new File(filePath);

			/*
			 * Dans le cas ou l'image n'est pas présente dans le répertoire On affiche une
			 * image par defaut 'Image Introuuvable'
			 */
			// if(!file.exists())
			// {
			// String path =
			// session.getServletContext().getRealPath("")+"/img/imageIntrouvable.jpg"+
			// file = new File(path);
			// }

			in = new FileInputStream(file);

			response.setContentLength((int) file.length());

			byte buffer[] = new byte[4096];
			int nbLecture;

			while ((nbLecture = in.read(buffer)) != -1) {
				output.write(buffer, 0, nbLecture);
			}

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
