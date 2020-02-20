package fr.eni.projet.bo;

import java.io.Serializable;

/**
 * 
 * Classe en charge de créer l'objet Retrait
 * @version Test_Projet_1 - V1.0
 * @author acaignar2019
 * @date 3 déc. 2019 - 16:40:38
 *
 */
public class Retrait implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1957253908799625007L;

	// ATTRIBUTS
	private int id;
	private String rue;
	private String codePostal;
	private String ville;
	
	// CONSTRUCTEUR PAR DEFAUT
	public Retrait() {
	}

	// CONSTRUCTEURS AVEC PARAMETRES
	public Retrait(int id, String rue, String codePostal, String ville) {
		this.id = id;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public Retrait(String rue, String codePostal, String ville) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	// GETTERS + SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	// TOSTRING
	@Override
	public String toString() {
		return "Retrait [id=" + id + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + "]";
	}
	
	
	
	
}
