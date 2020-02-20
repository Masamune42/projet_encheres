package fr.eni.projet.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Article;
import fr.eni.projet.bo.Enchere;
import fr.eni.projet.bo.User;

/**
 * 
 * Classe en charge de
 * 
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 4 déc. 2019 - 16:01:39
 *
 */
public class EnchereDAOJdbcImpl implements EnchereDAO {

	private static final String SELECTIONNER_PSEUDO_AVEC_ID_ARTICLE_ENCHERE = "SELECT e.no_utilisateur, e.date_enchere, e.montant_enchere, u.pseudo FROM ENCHERES e INNER JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur WHERE e.no_article=?;";
	private static final String INSERER = "INSERT INTO ENCHERES(no_article, date_enchere, montant_enchere, no_utilisateur) VALUES(?,?,?,?);";
	private static final String SUPPRIMER = "DELETE FROM ENCHERES WHERE no_article=?";
	private static final String MODIFIER = "UPDATE ENCHERES SET date_enchere=?, montant_enchere=?, no_utilisateur=? WHERE no_article=?;";
	private static final String TOUT_SELECTIONNER = "SELECT * FROM ENCHERES e INNER JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur;";
	private static final String SELECTIONNE_AVEC_ID = "SELECT * FROM ENCHERES e LEFT JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur WHERE no_article=?;";

	@Override
	public void inserer(Enchere enchere) throws BusinessException{
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERER);
			pstmt.setInt(1, enchere.getArticle().getId());
			pstmt.setTimestamp(2, Timestamp.valueOf(enchere.getDateEnchere()));
			pstmt.setInt(3, enchere.getMontantEnchere());
			pstmt.setInt(4, enchere.getUtilisateur().getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERTION_ENCHERE_IMPOSSIBLE);
		}

	}

	@Override
	public void modifier(Enchere enchere) throws BusinessException{
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(MODIFIER);

			pstmt.setTimestamp(1, Timestamp.valueOf(enchere.getDateEnchere()));
			pstmt.setInt(2, enchere.getMontantEnchere());
			pstmt.setInt(3, enchere.getUtilisateur().getId());
			pstmt.setInt(4, enchere.getArticle().getId());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.MODIFICATION_ENCHERE_IMPOSSIBLE);
		}

	}

	@Override
	public void supprimer(int id) throws BusinessException{
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SUPPRIMER);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_ENCHERE_IMPOSSIBLE);
		}

	}

	@Override
	public List<Enchere> selectionnerTout() throws BusinessException{
		List<Enchere> listeEncheres = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(TOUT_SELECTIONNER);
			ResultSet rs = pstmt.executeQuery();
			ArticleDAO articleDAO = new ArticleDAOJdbcImpl();

			while (rs.next()) {
				Article article = articleDAO.selectionnerAvecID(rs.getInt("no_article"));
				LocalDateTime dateEnchere = rs.getTimestamp("date_enchere").toLocalDateTime();
				int montantEnchere = rs.getInt("montant_enchere");
				
				User utilisateur = new User();
				utilisateur.setId(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
				
				Enchere enchere = new Enchere(article, dateEnchere, montantEnchere, utilisateur);

				listeEncheres.add(enchere);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECTION_DES_ENCHERES_IMPOSSIBLE);
		}

		return listeEncheres;
	}

	@Override
	public Enchere selectionnerAvecID(int id) throws BusinessException{
		Enchere enchere = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNE_AVEC_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			ArticleDAO articleDAO = new ArticleDAOJdbcImpl();

			while (rs.next()) {
				Article article = articleDAO.selectionnerAvecID(rs.getInt(id));
				LocalDateTime dateEnchere = rs.getTimestamp("date_enchere").toLocalDateTime();
				int montantEnchere = rs.getInt("montant_enchere");
				
				User utilisateur = new User();
				utilisateur.setId(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));

				enchere = new Enchere(article, dateEnchere, montantEnchere, utilisateur);
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECTION_ID_ENCHERE_IMPOSSIBLE);
		}

		return enchere;
	}

//	@Override
//	public String selectionnerPseudoEnchere(int id) throws BusinessException{
//		String pseudo = null;
//		try (Connection cnx = ConnectionProvider.getConnection()) {
//			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNER_PSEUDO_AVEC_ID_ARTICLE_ENCHERE);
//			pstmt.setInt(1, id);
//			ResultSet rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				pseudo = rs.getString("pseudo");
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			BusinessException businessException = new BusinessException();
//			businessException.ajouterErreur(CodesResultatDAL.SELECTION_PSEUDO_UTILISATEUR_ID_ARTICLE_ENCHERE_IMPOSSIBLE);
//		}
//
//		return pseudo;
//	}
	
//	private static final String SELECTIONNER_PSEUDO_AVEC_ID_ARTICLE_ENCHERE = "SELECT e.no_article, e.date_enchere, e.montant_enchere, u.pseudo FROM ENCHERES e INNER JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur WHERE u.no_utilisateur=?;";

	// méthode permttant de retrouver le meilleur enchérisseur
	@Override
	public User selectPseudo(int montantEnchere, int id) throws BusinessException{

		User utilisateur = null; 

		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = null; 
			pstmt=cnx.prepareStatement(SELECTIONNER_PSEUDO_AVEC_ID_ARTICLE_ENCHERE); 
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			//instanciation d'un object grâce au constructeur de Utilisateur user avec donnée récupérer 
			while(rs.next()) {
				utilisateur = new User();
				utilisateur.setId(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));	
				
				
				Enchere enchere = new Enchere();
				enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime());
				enchere.setMontantEnchere(rs.getInt("montant_enchere"));
				enchere.setUtilisateur(utilisateur);
				


				
			}
		}catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_MEILLEUR_ENCHERISSEUR_IMPOSSIBLE);
		}		 
		
		return utilisateur;
	}




}
