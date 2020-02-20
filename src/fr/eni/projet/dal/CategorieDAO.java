package fr.eni.projet.dal;

import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Categorie;

/**
 * 
 * Classe en charge de
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 4 déc. 2019 - 10:28:00
 *
 */
public interface CategorieDAO {
	/**
	 * Sélectionne un retrait avec son ID
	 * @param id ID du retrait
	 * @return Utilisateur
	 */
	public Categorie selectionnerAvecID(int id)throws BusinessException;
	
	public List<Categorie> selectionnerTout()throws BusinessException;
	
	public int selectionnerAvecLibelle(String libelle)throws BusinessException;
}
