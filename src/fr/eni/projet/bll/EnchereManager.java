package fr.eni.projet.bll;

import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Enchere;
import fr.eni.projet.bo.User;
import fr.eni.projet.dal.DAOFactory;
import fr.eni.projet.dal.EnchereDAO;

public class EnchereManager {
	private EnchereDAO daoEnchere;
	private static EnchereManager instance;

	private EnchereManager() {
		this.daoEnchere = DAOFactory.getEnchereDAO();
	}

	public static EnchereManager getInstance() {
		if (instance == null) {
			instance = new EnchereManager();
		}

		return instance;
	}

	public void ajouterEnchere(Enchere enchere) throws BusinessException{
		daoEnchere.inserer(enchere);
	}

	public void modifierEnchere(Enchere enchere) throws BusinessException{
		daoEnchere.modifier(enchere);
	}

	public void supprimer(int id) throws BusinessException{
		daoEnchere.supprimer(id);
	}

	public List<Enchere> selectionnerToutesLesEncheres() throws BusinessException{
		return daoEnchere.selectionnerTout();
	}

	public Enchere selectionnerAvecID(int id) throws BusinessException{
		return daoEnchere.selectionnerAvecID(id);
	}
	
//	public String selectionnerPseudoEnchere(int id) throws BusinessException{
//		return daoEnchere.selectionnerPseudoEnchere(id);
//	}
	
	public User getSelectPseudo(int montantEnchere, int id) throws BusinessException {

		return daoEnchere.selectPseudo(montantEnchere, id);

	}
}
