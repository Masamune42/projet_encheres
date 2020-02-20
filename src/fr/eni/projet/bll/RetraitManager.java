package fr.eni.projet.bll;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Retrait;
import fr.eni.projet.dal.DAOFactory;
import fr.eni.projet.dal.RetraitDAO;

/**
 * 
 * Classe en charge de
 * 
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 4 déc. 2019 - 12:10:35
 *
 */
public class RetraitManager {
	private RetraitDAO daoRetrait;
	private static RetraitManager instance;

	private RetraitManager() {
		this.daoRetrait = DAOFactory.getRetraitDAO();
	}

	public static RetraitManager getInstance() {
		if (instance == null) {
			instance = new RetraitManager();
		}

		return instance;
	}

	public Retrait selectionnerRetraitAvecID(int id) throws BusinessException{
		return daoRetrait.selectionnerAvecID(id);
	}

	public int selectionnerAvecAdresseComplete(String rue, String codePostal, String ville) throws BusinessException{
		return daoRetrait.selectionnerAvecAdresse(rue, codePostal, ville);
	}

	public void ajouterRetrait(Retrait retrait) throws BusinessException{
		daoRetrait.inserer(retrait);
	}
}
