package fr.eni.projet.dal;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Retrait;

/**
 * 
 * Classe en charge de
 * 
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 4 déc. 2019 - 10:29:27
 *
 */
public interface RetraitDAO {
	/**
	 * Sélectionne un retrait avec son ID
	 * 
	 * @param id
	 *            ID du retrait
	 * @return Utilisateur
	 */
	public Retrait selectionnerAvecID(int id)throws BusinessException;
	
	public int selectionnerAvecAdresse(String rue, String codePostal, String ville)throws BusinessException;
	
	public void inserer(Retrait retrait)throws BusinessException;
}
