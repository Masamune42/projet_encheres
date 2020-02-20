package fr.eni.projet.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.User;

/**
 * 
 * Classe en charge d'utiliser les requêtes SQL définies
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 3 déc. 2019 - 09:59:54
 *
 */
public class UserDAOJdbcImpl implements UserDAO {
	// Requêtes SQL utilisées dans les méthodes.
	private static final String INSERER = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
	private static final String SUPPRIMER = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?";
	private static final String MODIFIER = "UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=?, administrateur=? WHERE no_utilisateur=?;";
	private static final String TOUT_SELECTIONNER = "SELECT * FROM UTILISATEURS";
	private static final String SELECTIONNE_AVEC_PSEUDO_MDP = "SELECT * FROM UTILISATEURS WHERE pseudo=? AND mot_de_passe=?";
	private static final String SELECTIONNE_AVEC_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur=?";
	private static final String SELECTIONNE_AVEC_PSEUDO="SELECT * FROM UTILISATEURS WHERE pseudo=?;";
	private static final String MODIFIER_CREDIT = "UPDATE UTILISATEURS SET credit=? WHERE no_utilisateur=?;";


	@Override
	public void inserer(User user) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERER, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getPseudo());
			pstmt.setString(2, user.getNom());
			pstmt.setString(3, user.getPrenom());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getTelephone());
			pstmt.setString(6, user.getRue());
			pstmt.setString(7, user.getCodePostal());
			pstmt.setString(8, user.getVille());
			pstmt.setString(9, user.getMotDePasse());
			pstmt.setInt(10, user.getCredit());
			pstmt.setBoolean(11, user.isAdministrateur());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				user.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERTION_UTILISATEUR_IMPOSSIBLE);
		}

	}

	@Override
	public void supprimer(int id) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SUPPRIMER);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_UTILISATEUR_IMPOSSIBLE);
		}

	}

	@Override
	public void modifier(User user) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(MODIFIER);

			pstmt.setString(1, user.getPseudo());
			pstmt.setString(2, user.getNom());
			pstmt.setString(3, user.getPrenom());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getTelephone());
			pstmt.setString(6, user.getRue());
			pstmt.setString(7, user.getCodePostal());
			pstmt.setString(8, user.getVille());
			pstmt.setString(9, user.getMotDePasse());
			pstmt.setInt(10, user.getCredit());
			pstmt.setBoolean(11, user.isAdministrateur());
			pstmt.setInt(12, user.getId());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.MODIFICATION_UTILISATEUR_IMPOSSIBLE);
		}

	}

	@Override
	public List<User> selectionnerTout() {
		List<User> listeUsers = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(TOUT_SELECTIONNER);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("no_utilisateur");
				String pseudo = rs.getString("pseudo");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				String rue = rs.getString("rue");
				String codePostal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				String motDePasse = rs.getString("mot_de_passe");
				int credit = rs.getInt("credit");
				boolean administrateur = rs.getBoolean("administrateur");

				User user = new User(id, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse,
						credit, administrateur);

				listeUsers.add(user);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECTION_DES_UTILISATEURS_IMPOSSIBLE);
		}

		return listeUsers;
	}

	@Override
	public int verifierUtilisateurPseudo(String pseudo, String motDePasse) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNE_AVEC_PSEUDO_MDP);

			pstmt.setString(1, pseudo);
			pstmt.setString(2, motDePasse);

			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				return -1;
			} else {
				return rs.getInt("no_utilisateur");
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.VERIFICATION_PSEUDO_UTILISATEUR_IMPOSSIBLE);
		}
		return -1;
	}

	@Override
	public User selectionnerAvecID(int id) {
		User user = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNE_AVEC_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String pseudo = rs.getString("pseudo");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				String rue = rs.getString("rue");
				String codePostal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				String motDePasse = rs.getString("mot_de_passe");
				int credit = rs.getInt("credit");
				boolean administrateur = rs.getBoolean("administrateur");

				user = new User(id, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit,
						administrateur);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECTION_ID_UTILISATEUR_IMPOSSIBLE);
		}

		return user;
	}

	@Override
	public boolean verifierPseudo(String pseudo) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement("SELECT * FROM UTILISATEURS WHERE pseudo=?");

			pstmt.setString(1, pseudo);

			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.VERIFICATION_EMAIL_UTILISATEUR_INEXISTANT);
		}
		return false;
	}

	@Override
	public boolean verifierEmail(String email) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement("SELECT * FROM UTILISATEURS WHERE email=?");

			pstmt.setString(1, email);

			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.VERIFICATION_EMAIL_UTILISATEUR_INEXISTANT);
		}
		return false;
	}

	@Override
	public int verifierUtilisateurEmail(String email, String motDePasse) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement("SELECT * FROM UTILISATEURS WHERE email=? AND mot_de_passe=?");

			pstmt.setString(1, email);
			pstmt.setString(2, motDePasse);

			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				return -1;
			} else {
				return rs.getInt("no_utilisateur");
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.VERIFICATION_EMAIL_UTILISATEUR_IMPOSSIBLE);
		}
		return -1;
		
		
	}
	
	@Override
	public User selectionnerAvecPseudo(String pseudo) {
		User user = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNE_AVEC_PSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("no_utilisateur");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				String rue = rs.getString("rue");
				String codePostal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				String motDePasse = rs.getString("mot_de_passe");
				int credit = rs.getInt("credit");
				boolean administrateur = rs.getBoolean("administrateur");

				user = new User(id, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit,
						administrateur);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECTION_PSEUDO_UTILISATEUR_IMPOSSIBLE);
		}

		return user;
	}

	// Permet de recréditer l'enchérisseur précédent
	@Override
	public void modifierCredit(int id, int credit) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(MODIFIER_CREDIT);
			pstmt.setInt(1, credit);
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.MODIF_CREDIT_IMPOSSIBLE);
		}
		
	}
}
