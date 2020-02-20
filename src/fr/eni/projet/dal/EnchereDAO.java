package fr.eni.projet.dal;

import java.util.List;

import fr.eni.projet.BusinessException;
import fr.eni.projet.bo.Enchere;
import fr.eni.projet.bo.User;

/**
 * 
 * Classe en charge de
 * 
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 4 déc. 2019 - 16:01:34
 *
 */
public interface EnchereDAO {
	public void inserer(Enchere enchere)throws BusinessException;

	public void modifier(Enchere enchere)throws BusinessException;

	public void supprimer(int id)throws BusinessException;

	public List<Enchere> selectionnerTout()throws BusinessException;

	public Enchere selectionnerAvecID(int id)throws BusinessException;

//	public String selectionnerPseudoEnchere(int id)throws BusinessException;
	
	public User selectPseudo(int montantEnchere, int id)throws BusinessException;

}
