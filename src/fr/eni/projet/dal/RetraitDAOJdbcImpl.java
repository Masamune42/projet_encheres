package fr.eni.projet.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Retrait;

/**
 * 
 * Classe en charge de
 * 
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 4 déc. 2019 - 10:29:16
 *
 */
public class RetraitDAOJdbcImpl implements RetraitDAO {

	private static final String SELECTIONNE_AVEC_ID = "SELECT * FROM RETRAITS WHERE no_retrait=?";
	private static final String SELECTIONNE_AVEC_ADRESSE = "SELECT * FROM RETRAITS WHERE rue=? AND code_postal=? AND ville=?";
	private static final String INSERER = "INSERT INTO RETRAITS(rue, code_postal, ville) VALUES(?,?,?)";

	@Override
	public Retrait selectionnerAvecID(int id) throws BusinessException{
		Retrait retrait = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNE_AVEC_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String rue = rs.getString("rue");
				String codePostal = rs.getString("code_postal");
				String ville = rs.getString("ville");

				retrait = new Retrait(id, rue, codePostal, ville);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_RETRAIT_ID_IMPOSSIBLE);

		}

		return retrait;
	}

	@Override
	public int selectionnerAvecAdresse(String rue, String codePostal, String ville) throws BusinessException{
		int id = -1;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNE_AVEC_ADRESSE);
			pstmt.setString(1, rue);
			pstmt.setString(2, codePostal);
			pstmt.setString(3, ville);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				id = rs.getInt("no_retrait");

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_RETRAIT_ADRESSE_IMPOSSIBLE);

		}

		return id;
	}

	@Override
	public void inserer(Retrait retrait) throws BusinessException{
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(INSERER, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, retrait.getRue());
			pstmt.setString(2, retrait.getCodePostal());
			pstmt.setString(3, retrait.getVille());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				retrait.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERTION_RETRAIT_IMPOSSIBLE);
		}

	}

}
