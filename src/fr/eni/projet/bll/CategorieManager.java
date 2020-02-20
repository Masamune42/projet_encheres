package fr.eni.projet.bll;

import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Categorie;
import fr.eni.projet.dal.CategorieDAO;
import fr.eni.projet.dal.DAOFactory;

/**
 * 
 * Classe en charge de
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 4 déc. 2019 - 12:10:31
 *
 */
public class CategorieManager {
	private CategorieDAO daoCategorie;
	private static CategorieManager instance;

	private CategorieManager() {
		this.daoCategorie = DAOFactory.getCategorieDAO();
	}
	
	public static CategorieManager getInstance() {
		if (instance == null) {
			instance = new CategorieManager();
		}
		
		return instance;
	}
	
	public Categorie selectionnerCategorieAvecID(int id) throws BusinessException{
		return daoCategorie.selectionnerAvecID(id);
	}
	
	public List<Categorie> selectionnerToutesLesCategories()throws BusinessException{
		return daoCategorie.selectionnerTout();
	}
	
	public int selectionnerAvecLibelle(String libelle) throws BusinessException{
		return daoCategorie.selectionnerAvecLibelle(libelle);
	}
}
