package org.projet.encheres.bo;

import java.time.LocalDateTime;

public class Enchere {
	
	private Utilisateur utilisateur;
	private Article article;
	private LocalDateTime dateEnchere;
	private Integer montantEnchere;
	
	public Enchere() {
		super();
	}

	public Enchere(Utilisateur utilisateur, Article article, LocalDateTime dateEnchere, Integer montantEnchere) {
		super();
		this.utilisateur = utilisateur;
		this.article = article;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public Article getArticle() {
		return article;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public Integer getMontantEnchere() {
		return montantEnchere;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public void setMontantEnchere(Integer montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	@Override
	public String toString() {
		return "Enchere [utilisateur=" + utilisateur + ", article=" + article + ", dateEnchere=" + dateEnchere
				+ ", montantEnchere=" + montantEnchere + ", toString()=" + super.toString() + "]";
	}
	
	

}
