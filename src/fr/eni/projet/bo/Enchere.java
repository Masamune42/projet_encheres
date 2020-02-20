package fr.eni.projet.bo;

import java.io.Serializable;
/**
 * 
 * Classe en charge de
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 4 déc. 2019 - 15:28:55
 *
 */
import java.time.LocalDateTime;

public class Enchere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3091567179190497947L;

	// ATTRIBUTS
	private Article article;
	private LocalDateTime dateEnchere;
	private int montantEnchere;
	private User utilisateur;

	// CONSTRUCTEUR PAR DEFAUT
	public Enchere() {
	}

	// CONSTRUCTEURS AVEC PARAMETRES
	public Enchere(Article article, LocalDateTime dateEnchere, int montantEnchere, User utilisateur) {
		super();
		this.article = article;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.utilisateur = utilisateur;
	}

	// GETTERS + SETTERS
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public User getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(User utilisateur) {
		this.utilisateur = utilisateur;
	}

	// TOSTRING
	@Override
	public String toString() {
		return "Enchere [article=" + article + ", dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere
				+ ", utilisateur=" + utilisateur + "]";
	}

}
