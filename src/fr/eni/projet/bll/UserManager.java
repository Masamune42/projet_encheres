package fr.eni.projet.bll;

import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.User;
import fr.eni.projet.dal.DAOFactory;
import fr.eni.projet.dal.UserDAO;

/**
 * 
 * Classe en charge d'appeler les méthodes de la DAL
 * 
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 3 déc. 2019 - 10:01:20
 *
 */
public class UserManager {
	private UserDAO daoUser;
	private static UserManager instance;

	private UserManager() {
		this.daoUser = DAOFactory.getUserDAO();
	}

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}

		return instance;
	}

	/**
	 * Ajoute un utilisateur
	 * 
	 * @param user
	 *            Utilisateur
	 */
	public void ajouterUtilisateur(User user) throws BusinessException {
		daoUser.inserer(user);
	}

	/**
	 * Supprime un utilisateur
	 * 
	 * @param id
	 *            ID Utilisateur
	 */
	public void supprimerUtilisateur(int id) throws BusinessException {
		daoUser.supprimer(id);
	}
	
	public void getRecredit(int id, int credit) throws BusinessException {
		try {
			daoUser.modifierCredit(id, credit);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Modifie un utilisateur
	 * 
	 * @param user
	 *            Utilisateur
	 */
	public void modifierUtilisateur(User user) throws BusinessException {
		daoUser.modifier(user);
	}

	/**
	 * Sélectionne tous les utilisateurs
	 * 
	 * @return Une liste d'utilisateurs
	 */
	public List<User> selectionnerTousLesUtilisateurs() throws BusinessException {
		return daoUser.selectionnerTout();
	}

	/**
	 * Vérifie si l'utilisateur existe avec son pseudo
	 * 
	 * @param pseudo
	 *            Pseudo
	 * @param motDePasse
	 *            mot de passe
	 * @return l'ID sinon -1.
	 */
	public int verifierUtilisateurPseudo(String pseudo, String motDePasse) throws BusinessException {
		return daoUser.verifierUtilisateurPseudo(pseudo, motDePasse);
	}

	/**
	 * Sélectionne un utilisateur avec l'ID
	 * 
	 * @param id
	 *            ID utilisateur
	 * @return un Utilisateur
	 */
	public User selectionnerAvecID(int id) throws BusinessException {
		return daoUser.selectionnerAvecID(id);
	}

	/**
	 * Vérifie si le pseudo existe déjà
	 * 
	 * @param pseudo
	 *            Pseudo utilisateur
	 * @return true si pseudo dispo, sinon false
	 */
	public boolean verifierPseudo(String pseudo) throws BusinessException{
		return daoUser.verifierPseudo(pseudo);
	}

	/**
	 * Vérifie si l'email existe déjà
	 * 
	 * @param email
	 *            Email utilisateur
	 * @return true si email dispo, sinon false
	 */
	public boolean verifierEmail(String email)  throws BusinessException{
		return daoUser.verifierEmail(email);
	}

	/**
	 * Vérifie si l'utilisateur existe avec son email
	 * 
	 * @param email
	 *            Email utilisateur
	 * @param motDePasse
	 *            Mot de passe utilisateur
	 * @return l'ID sinon -1.
	 */
	public int verifierUtilisateurEmail(String email, String motDePasse)throws BusinessException{
		return daoUser.verifierUtilisateurEmail(email, motDePasse);
	}
	
	/**
	 * Sélectionne un utilisateur avec le pseudo
	 * 
	 * @param pseudo
	 *            pseudo utilisateur
	 * @return un Utilisateur
	 */
	public User selectionnerAvecPseudo(String pseudo) throws BusinessException{
		return daoUser.selectionnerAvecPseudo(pseudo);
	}
	


}
