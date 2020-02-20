package fr.eni.projet.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Categorie;

/**
 * 
 * Classe en charge de
 * 
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 4 déc. 2019 - 10:27:54
 *
 */
public class CategorieDAOJdbcImpl implements CategorieDAO {

	private static final String TOUT_SELECTIONNER = "SELECT * FROM CATEGORIES";
	private static final String SELECTIONNE_AVEC_ID = "SELECT * FROM CATEGORIES WHERE no_categorie=?";
	private static final String SELECTIONNER_AVEC_LIBELLE = "SELECT * FROM CATEGORIES WHERE libelle=?";

	@Override
	public Categorie selectionnerAvecID(int id) throws BusinessException{
		Categorie categorie = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNE_AVEC_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String libelle = rs.getString("libelle");

				categorie = new Categorie(id, libelle);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_CATEGORIE_ID_IMPOSSIBLE);

		}

		return categorie;
	}

	@Override
	public List<Categorie> selectionnerTout() throws BusinessException{
		List<Categorie> listeCategories = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(TOUT_SELECTIONNER);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("no_categorie");
				String libelle = rs.getString("libelle");

				Categorie categorie = new Categorie(id, libelle);

				listeCategories.add(categorie);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_CATEGORIES_IMPOSSIBLE);

		}

		return listeCategories;
	}
	
	@Override
	public int selectionnerAvecLibelle(String libelle) throws BusinessException{
		int id = -1;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNER_AVEC_LIBELLE);
			pstmt.setString(1, libelle);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				id = rs.getInt("no_categorie");
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_CATEGORIES_LIBELLE_IMPOSSIBLE);
		}
		
		return id;
	}

}
