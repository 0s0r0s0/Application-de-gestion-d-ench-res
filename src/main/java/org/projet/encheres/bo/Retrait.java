package org.projet.encheres.bo;

public class Retrait {
	
	private Article article;
	private String rue;
	private String codePostal;
	private String ville;
	
	
	public Retrait() {
		super();
	}
	public Retrait(Article article, String rue, String codePostal, String ville) {
		super();
		this.article = article;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	public Article getArticle() {
		return article;
	}
	public String getRue() {
		return rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	@Override
	public String toString() {
		return "Retrait [article=" + article + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	

}
