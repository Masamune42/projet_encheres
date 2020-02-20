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
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.bo.Retrait;
import fr.eni.projet.bo.User;

/**
 * 
 * Classe en charge de définir toutes les méthodes d'un article
 * 
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 3 déc. 2019 - 16:55:25
 *
 */
public class ArticleDAOJdbcImpl implements ArticleDAO {
	// Requêtes SQL utilisées dans les méthodes.
	private static final String INSERER = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_retrait, url_image) VALUES(?,?,?,?,?,?,?,?,?,?);";
	private static final String SUPPRIMER = "DELETE FROM ARTICLES_VENDUS WHERE no_article=?";
	private static final String MODIFIER = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=?, no_retrait=? WHERE no_article=?;";
	private static final String TOUT_SELECTIONNER = "SELECT * FROM ARTICLES_VENDUS a INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie INNER JOIN RETRAITS r ON a.no_retrait = r.no_retrait;";
	private static final String SELECTIONNE_AVEC_ID = "SELECT * FROM ARTICLES_VENDUS a INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie INNER JOIN RETRAITS r ON a.no_retrait = r.no_retrait WHERE no_article=?";
	private static final String SELECTIONNE_AVEC_PSEUDO = "SELECT a.no_article, a.nom_article, a.prix_initial, a.prix_vente, a.date_debut_encheres, a.date_fin_encheres, a.url_image, u.pseudo, c.no_categorie FROM ARTICLES_VENDUS a INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie";

	@Override
	public void inserer(Article article) throws BusinessException{
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERER, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, article.getNomArticle());
			pstmt.setString(2, article.getDescription());
			pstmt.setTimestamp(3, Timestamp.valueOf(article.getDateDebutEncheres()));
			pstmt.setTimestamp(4, Timestamp.valueOf(article.getDateFinEncheres()));
			pstmt.setInt(5, article.getPrixInitial());
			pstmt.setInt(6, article.getPrixVente());
			pstmt.setInt(7, article.getUtilisateur().getId());
			pstmt.setInt(8, article.getCategorie().getId());
			pstmt.setInt(9, article.getRetrait().getId());
			pstmt.setString(10, article.getUrlImage());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				article.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERER_ARTICLE_IMPOSSIBLE);

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
			businessException.ajouterErreur(CodesResultatDAL.SUPPRIMER_ARTICLE_IMPOSSIBLE);

		}

	}

	@Override
	public void modifier(Article article) throws BusinessException{
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(MODIFIER);

			pstmt.setString(1, article.getNomArticle());
			pstmt.setString(2, article.getDescription());
			pstmt.setTimestamp(3, Timestamp.valueOf(article.getDateDebutEncheres()));
			pstmt.setTimestamp(4, Timestamp.valueOf(article.getDateFinEncheres()));
			pstmt.setInt(5, article.getPrixInitial());
			pstmt.setInt(6, article.getPrixVente());
			pstmt.setInt(7, article.getUtilisateur().getId());
			pstmt.setInt(8, article.getCategorie().getId());
			pstmt.setInt(9, article.getRetrait().getId());
			pstmt.setInt(10, article.getId());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.MODIFIER_ARTICLE_IMPOSSIBLE);

		}

	}

	@Override
	public List<Article> selectionnerTout() throws BusinessException{
		List<Article> listeArticles = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(TOUT_SELECTIONNER);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("no_article");
				String nomArticle = rs.getString("nom_article");
				String description = rs.getString("description");
				LocalDateTime dateDebutEncheres = rs.getTimestamp("date_debut_encheres").toLocalDateTime();
				LocalDateTime dateFinEncheres = rs.getTimestamp("date_fin_encheres").toLocalDateTime();
				int prixInitial = rs.getInt("prix_initial");
				int prixVente = rs.getInt("prix_vente");
				
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
				
				
				Categorie categorie = new Categorie();
				categorie.setId(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				
				Retrait retrait = new Retrait();
				retrait.setId(rs.getInt("no_retrait"));
				retrait.setRue(rs.getString("rue"));
				retrait.setCodePostal(rs.getString("code_postal"));
				retrait.setVille(rs.getString("ville"));

				Article article = new Article(id, nomArticle, description, dateDebutEncheres, dateFinEncheres,
						prixInitial, prixVente, utilisateur, categorie, retrait);

				listeArticles.add(article);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLE_TOUT_INEXISTANT);

		}

		return listeArticles;
	}

	@Override
	public Article selectionnerAvecID(int id) throws BusinessException{
		Article article = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNE_AVEC_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String nomArticle = rs.getString("nom_article");
				String description = rs.getString("description");
				LocalDateTime dateDebutEncheres = rs.getTimestamp("date_debut_encheres").toLocalDateTime();
				LocalDateTime dateFinEncheres = rs.getTimestamp("date_fin_encheres").toLocalDateTime();
				int prixInitial = rs.getInt("prix_initial");
				int prixVente = rs.getInt("prix_vente");
				String urlImage = rs.getString("url_image");
				
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
				
				
				Categorie categorie = new Categorie();
				categorie.setId(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				
				Retrait retrait = new Retrait();
				retrait.setId(rs.getInt("no_retrait"));
				retrait.setRue(rs.getString("rue"));
				retrait.setCodePostal(rs.getString("code_postal"));
				retrait.setVille(rs.getString("ville"));

				article = new Article(id, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial,
						prixVente, utilisateur, categorie, retrait, urlImage);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLE_ID_INEXISTANT);

		}

		return article;
	}

	/* 
	 * {@inheritDoc}
	 * @see fr.eni.projet.dal.ArticleDAO#selectionnerAvecPseudo()
	 */
	@Override
	public List<Article> selectionnerAvecPseudo() throws BusinessException{
		List<Article> listeEncheres = new ArrayList<>();
		Article article = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECTIONNE_AVEC_PSEUDO);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				article = new Article();
				article.setId(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setPrixInitial(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
				article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
				article.setUrlImage(rs.getString("url_image"));
				
				User utilisateur = new User();
				utilisateur.setPseudo(rs.getString("pseudo"));
				article.setUtilisateur(utilisateur);
				
				Categorie categorie = new Categorie();
				categorie.setId(rs.getInt("no_categorie"));
				article.setCategorie(categorie);
				
				listeEncheres.add(article);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLE_PSEUDO_INEXISTANT);

		}
		return listeEncheres;
	}

	
	@Override
	public List<Article> selectionnerFiltrageAchats(String recherche, int categorieSelec, int idUser, boolean option1,
			boolean option2, boolean option3) throws BusinessException{
		List<Article> listeArticles = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			String filtrage = "SELECT * FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON a.no_article = e.no_article "
					+ "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie WHERE a.nom_article LIKE ? AND (c.no_categorie = ? OR ? = -1)"
					+ " AND (GETDATE() < a.date_fin_encheres AND GETDATE() > a.date_debut_encheres AND ? = 1"
					+ " OR e.no_utilisateur = ? AND GETDATE() < a.date_fin_encheres AND ? = 1"
					+ " OR e.no_utilisateur = ? AND GETDATE() > a.date_fin_encheres AND ? = 1)";

			PreparedStatement pstmt = cnx.prepareStatement(filtrage);
			pstmt.setString(1, "%" + recherche + "%");

			pstmt.setInt(2, categorieSelec);
			pstmt.setInt(3, categorieSelec);
			
			pstmt.setBoolean(4, option1);
			
			pstmt.setInt(5, idUser);
			pstmt.setBoolean(6, option2);
			
			pstmt.setInt(7, idUser);
			pstmt.setBoolean(8, option3);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("no_article");
				String nomArticle = rs.getString("nom_article");
				String description = rs.getString("description");
				LocalDateTime dateDebutEncheres = rs.getTimestamp("date_debut_encheres").toLocalDateTime();
				LocalDateTime dateFinEncheres = rs.getTimestamp("date_fin_encheres").toLocalDateTime();
				int prixInitial = rs.getInt("prix_initial");
				int prixVente = rs.getInt("prix_vente");

				User utilisateur = new User();
				utilisateur.setId(rs.getInt("no_utilisateur"));

				Categorie categorie = new Categorie();
				categorie.setId(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));

				Retrait retrait = new Retrait();
				retrait.setId(rs.getInt("no_retrait"));

				Article article = new Article(id, nomArticle, description, dateDebutEncheres, dateFinEncheres,
						prixInitial, prixVente, utilisateur, categorie, retrait);

				listeArticles.add(article);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLE_FILTRE_ACHAT_IMPOSSIBLE);
		}

		return listeArticles;
	}

	@Override
	public List<Article> selectionnerFiltrageVentes(String recherche, int categorieSelec, int idUser, boolean option1,
			boolean option2, boolean option3) throws BusinessException{
		List<Article> listeArticles = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			String filtrage = "SELECT * FROM ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie WHERE a.nom_article LIKE ? AND (c.no_categorie = ? OR ? = -1)"
					+ " AND (a.no_utilisateur = ? AND GETDATE() < a.date_fin_encheres AND GETDATE() > a.date_debut_encheres AND ? = 1"
					+ " OR a.no_utilisateur = ? AND GETDATE() < a.date_debut_encheres AND ? = 1"
					+ " OR a.no_utilisateur = ? AND GETDATE() > a.date_debut_encheres AND ? = 1)";

			PreparedStatement pstmt = cnx.prepareStatement(filtrage);
			pstmt.setString(1, "%" + recherche + "%");

			pstmt.setInt(2, categorieSelec);
			pstmt.setInt(3, categorieSelec);
			
			pstmt.setInt(4, idUser);
			pstmt.setBoolean(5, option1);
			
			pstmt.setInt(6, idUser);
			pstmt.setBoolean(7, option2);
			
			pstmt.setInt(8, idUser);
			pstmt.setBoolean(9, option3);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("no_article");
				String nomArticle = rs.getString("nom_article");
				String description = rs.getString("description");
				LocalDateTime dateDebutEncheres = rs.getTimestamp("date_debut_encheres").toLocalDateTime();
				LocalDateTime dateFinEncheres = rs.getTimestamp("date_fin_encheres").toLocalDateTime();
				int prixInitial = rs.getInt("prix_initial");
				int prixVente = rs.getInt("prix_vente");

				User utilisateur = new User();
				utilisateur.setId(rs.getInt("no_utilisateur"));

				Categorie categorie = new Categorie();
				categorie.setId(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));

				Retrait retrait = new Retrait();
				retrait.setId(rs.getInt("no_retrait"));

				Article article = new Article(id, nomArticle, description, dateDebutEncheres, dateFinEncheres,
						prixInitial, prixVente, utilisateur, categorie, retrait);

				listeArticles.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLE_FILTRE_VENTE_IMPOSSIBLE);
		}

		return listeArticles;
	}

	
	
	@Override
	public List<Article> selectionnerFiltrageRechercheNonConnecte(String recherche, int categorieSelec) throws BusinessException{
		List<Article> listeArticles = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			String filtrage = "SELECT * FROM ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie WHERE a.nom_article LIKE ? AND (c.no_categorie = ? OR ? = -1)";

			PreparedStatement pstmt = cnx.prepareStatement(filtrage);
			pstmt.setString(1, "%" + recherche + "%");

			pstmt.setInt(2, categorieSelec);
			pstmt.setInt(3, categorieSelec);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("no_article");
				String nomArticle = rs.getString("nom_article");
				String description = rs.getString("description");
				LocalDateTime dateDebutEncheres = rs.getTimestamp("date_debut_encheres").toLocalDateTime();
				LocalDateTime dateFinEncheres = rs.getTimestamp("date_fin_encheres").toLocalDateTime();
				int prixInitial = rs.getInt("prix_initial");
				int prixVente = rs.getInt("prix_vente");

				User utilisateur = new User();
				utilisateur.setId(rs.getInt("no_utilisateur"));

				Categorie categorie = new Categorie();
				categorie.setId(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));

				Retrait retrait = new Retrait();
				retrait.setId(rs.getInt("no_retrait"));

				Article article = new Article(id, nomArticle, description, dateDebutEncheres, dateFinEncheres,
						prixInitial, prixVente, utilisateur, categorie, retrait);

				listeArticles.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLE_FILTRE_RECHERCHE_NON_CONNECT_IMPOSSIBLE);
		}

		return listeArticles;
	}
}
