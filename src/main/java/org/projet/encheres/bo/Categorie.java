package org.projet.encheres.bo;

import java.util.List;

public class Categorie {
	
	private Integer noCategorie;
	private String libelle;
	
	private List<Article> articles;

	public Categorie() {
		super();
	}

	public Categorie(Integer noCategorie, String libelle, List<Article> articles) {
		super();
		this.noCategorie = noCategorie;
		this.libelle = libelle;
		this.articles = articles;
	}

	public Integer getNoCategorie() {
		return noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setNoCategorie(Integer noCategorie) {
		this.noCategorie = noCategorie;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "Categorie [noCategorie=" + noCategorie + ", libelle=" + libelle + ", articles=" + articles
				+ ", toString()=" + super.toString() + "]";
	}
	
	

}
