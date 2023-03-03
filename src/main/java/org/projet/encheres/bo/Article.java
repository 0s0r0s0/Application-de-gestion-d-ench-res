package org.projet.encheres.bo;

import java.time.LocalDate;

public class Article {

	private Integer noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private Integer prixInitial;
	private Integer prixVente;
	
	private Utilisateur utilisateur;
	
	private Categorie categorie;
	private EtatVente etatVente;
	
	
	public Article() {
		super();
	}

	public Article(Integer noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, Integer prixInitial, Integer prixVente, Utilisateur utilisateur,
			Categorie categorie, EtatVente etatVente) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.etatVente = etatVente;
	}
	
	public Article(String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, Integer prixInitial, Integer prixVente, Utilisateur utilisateur,
			Categorie categorie, EtatVente etatVente) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.etatVente = etatVente;
	}

	public Integer getNoArticle() {
		return noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public Integer getPrixInitial() {
		return prixInitial;
	}

	public Integer getPrixVente() {
		return prixVente;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public EtatVente getEtatVente() {
		return etatVente;
	}

	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public void setPrixInitial(Integer prixInitial) {
		this.prixInitial = prixInitial;
	}

	public void setPrixVente(Integer prixVente) {
		this.prixVente = prixVente;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public void setEtatVente(EtatVente etatVente) {
		this.etatVente = etatVente;
	}

	@Override
	public String toString() {
		return "Article [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", prixInitial="
				+ prixInitial + ", prixVente=" + prixVente + ", utilisateur=" + utilisateur + ", categorie=" + categorie
				+ ", etatVente=" + etatVente + ", toString()=" + super.toString() + "]";
	}

	
	
	

}
