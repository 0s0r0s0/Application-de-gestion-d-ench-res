package org.projet.encheres.bll;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.List;

import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Categorie;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.dal.DAOFactory;
import org.projet.encheres.dal.article.ArticleDAO;
import org.projet.encheres.exception.BusinessException;

public class ArticleManager {
	
	private ArticleDAO articleDAO;
	
	public ArticleManager () {
		this.articleDAO = DAOFactory.getArticleDAO();
	}
	
	public void insert(Article article) throws BusinessException {
		
		BusinessException exception = new BusinessException();
		
			this.validerNomArticle(article, exception);
			this.validerDescription(article, exception);
			this.validerDateDebutEncheres(article, exception);
			this.validerDateFinEncheres(article, exception);
			this.validerPrix(article, exception);
			this.validerCategorie(article, exception);
			
			if (exception.hasErrors()) {
				throw exception;
			} else {
				this.articleDAO.insert(article);
			}
		}
	
	private void validerCategorie(Article article, BusinessException exception) {

		if (article.getCategorie().getNoCategorie() == 0) {
			exception.addError("- Merci de sélectionner une catégorie");
		}
	}

	public void update(Article article) throws BusinessException {
		
		BusinessException exception = new BusinessException();
		
		this.validerNomArticle(article, exception);
		this.validerDescription(article, exception);
		this.validerDateFinEncheres(article, exception);
		this.validerPrix(article, exception);
		this.validerCategorie(article, exception);
		
		if (exception.hasErrors()) {
			throw exception;
		} else {
			this.articleDAO.update(article);
		}
	}
		
	public void delete(Article article) throws BusinessException {
		articleDAO.delete(article);
		
	}

	public List<Article> selectAll() throws BusinessException {
		return articleDAO.selectAll();
		
	}

	
	private void validerNomArticle(Article article, BusinessException exception) throws BusinessException {

		if (article.getNomArticle().length() <= 0 || article.getNomArticle().length() > 30
				|| article.getNomArticle().trim().length() == 0) {
			exception.addError("- Merci de saisir un nom d'article non vide et de 30 caractères maximum.");
		}

	}
	
	private void validerDescription(Article article, BusinessException exception) throws BusinessException {

		if (article.getDescription().length() <= 0 || article.getDescription().length() > 300
				|| article.getDescription().trim().length() == 0) {
			exception.addError("- Merci de saisir une description non vide et de 300 caractères maximum.");
		}

	}
	
	private void validerDateDebutEncheres(Article article, BusinessException exception) throws BusinessException {

		if (article.getDateDebutEncheres().isBefore(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth())) ||
				article.getDateDebutEncheres() == null) {
			exception.addError("- Merci de saisir une date de début valide.");
		}

	}
	
	private void validerDateFinEncheres(Article article, BusinessException exception) throws BusinessException {

		if (article.getDateFinEncheres().isBefore(article.getDateDebutEncheres()) ||
				article.getDateFinEncheres() == null) {
			exception.addError("- Merci de saisir une date de fin valide.");
		}

	}
	
	private void validerPrix(Article article, BusinessException exception) throws BusinessException {

		if (article.getPrixInitial() <= 0) {
			exception.addError("- Merci de saisir un prix de vente supérieur à 0.");
		}

	}

	public Article selectById(int id) throws BusinessException {
		return articleDAO.selectById(id);
		
	}

	public List<Article> selectByCategorie(int id) throws BusinessException {
		return articleDAO.selectByCategorie(id);
		
	}

	public List<Article> selectByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		return articleDAO.selectByUtilisateur(utilisateur);
		
	}
	
	public List<Article> selectAllByDate() throws BusinessException {
		return articleDAO.selectAllByDate();
		
	}
	
	public List<Article> selectAllByFirstLetter(String recherche) throws BusinessException {
		return articleDAO.selectAllByFirstLetter(recherche);
		
	}
	
	public List<Article> selectAllByFirstLetterAndCategorie(String recherche, int id) throws BusinessException {
		return articleDAO.selectAllByFirstLetterAndCategorie(recherche, id);
		
	}
	
	public List<Article> selectVenteNonDebuteeByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		return articleDAO.selectVenteNonDebuteeByUtilisateur(utilisateur);
		
	}
	
	public List<Article> selectVenteTermineeByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		return articleDAO.selectVenteTermineeByUtilisateur(utilisateur);
		
	}
	
	public Article selectArticleByDate(int id) throws BusinessException {
		return articleDAO.selectArticleByDate(id);
		
	}
	
	
	

}
