package fr.eni.projet.dal;

import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.User;
/**
 * 
 * Classe en charge de définir toutes les méthodes d'un utilisateur
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 3 déc. 2019 - 09:59:02
 *
 */
public interface UserDAO {
	/**
	 * Insère un utilisateur
	 * @param user Utilisateur
	 */
	public void inserer(User user)throws BusinessException;

	/**
	 * Supprime un utilisateur avec son ID
	 * @param id ID de l'utilisateur à supprimer
	 */
	public void supprimer(int id)throws BusinessException;

	/**
	 * Modifie les infos d'un utilisateur
	 * @param user
	 */
	public void modifier(User user)throws BusinessException;
	
	/**
	 * Recrédit enchérisseur précédent
	 * @param id id Article
	 * @param montantEnchere 
	 * @throws DALException 
	 */
	
	public void modifierCredit(int id, int credit) throws BusinessException;

	/**
	 * Récupère tous les utilisateurs de la BDD
	 * @return Une liste d'utilisateurs
	 */
	public List<User> selectionnerTout()throws BusinessException;
	
	/**
	 * Sélectionne un utilisateur avec son ID
	 * @param id ID de l'utilisateur
	 * @return Utilisateur
	 */
	public User selectionnerAvecID(int id)throws BusinessException;
	
	/**
	 * Vérifie que l'utilisateur existe dans la BDD avec le pseudo
	 * @param pseudo Pseudo de l'utilisateur
	 * @param motDePasse Mot de passe
	 * @return l'ID de l'utilisateur s'il existe, sinon -1.
	 */
	public int verifierUtilisateurPseudo(String pseudo, String motDePasse)throws BusinessException;
	
	/**
	 * Vérifie que l'utilisateur existe dans la BDD avec l'email
	 * @param email Email utilisateur
	 * @param motDePasse Mot de passe
	 * @return l'ID de l'utilisateur s'il existe, sinon -1.
	 */
	public int verifierUtilisateurEmail(String email, String motDePasse)throws BusinessException;
	
	/**
	 * Vérifie si le pseudo existe dans la BDD
	 * @param pseudo Pseudo utilisateur
	 * @return true si pseudo dispo, sinon false
	 */
	public boolean verifierPseudo(String pseudo)throws BusinessException;
	
	/**
	 * Vérifie si l'email existe dans la BDD
	 * @param email Email utilisateur
	 * @return true si email dispo, sinon false
	 */
	public boolean verifierEmail(String email)throws BusinessException;
	
	/**
	 * Sélectionner un utilisateur avec son pseudo
	 * @param pseudo Pseudo e l'utilisateur
	 * @return Utilisateur
	 */
	
	public User selectionnerAvecPseudo(String pseudo)throws BusinessException;
	

	

}
