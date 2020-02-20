package fr.eni.projet.bo;

import java.io.Serializable;

/**
 * 
 * Classe en charge de créer l'objet Categorie
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 3 déc. 2019 - 16:40:38
 *
 */
public class Categorie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8377456604348105734L;

	// JAVABEAN CATEGORIE

	// ATTRIBUTS
	private int id;
	private String libelle;

	// CONSTRUCTEUR PAR DEFAUT
	public Categorie() {
	}

	// CONSTRUCTEURS AVEC PARAMETRES
	public Categorie(int id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}

	public Categorie(String libelle) {
		this.libelle = libelle;
	}

	// GETTERS + SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	// TOSTRING
	@Override
	public String toString() {
		return "Categorie [id=" + id + ", libelle=" + libelle + "]";
	}

}
