package org.projet.encheres.bll;

import java.time.LocalDateTime;
import java.util.List;

import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Enchere;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.dal.DAOFactory;
import org.projet.encheres.dal.enchere.EnchereDAO;
import org.projet.encheres.exception.BusinessException;

public class EnchereManager {

	
	private EnchereDAO enchereDAO = DAOFactory.getEnchereDAO();

	public void insert(Enchere enchere, Integer nouveauPrix) throws BusinessException {
		
		BusinessException exception = new BusinessException();
		
		Utilisateur user = enchere.getUtilisateur();
		Article article = enchere.getArticle();
		
		enchere = this.selectMaxByArticle(article);
		if (enchere.getDateEnchere() == null) {
			enchere.setDateEnchere(LocalDateTime.now());
		}
		
		enchere.setArticle(article);
		this.validerNouvelleEnchere(enchere, nouveauPrix, user, exception);
		if (!exception.hasErrors()) {
			this.validerCreditDisponible(nouveauPrix, user, exception);
			enchere.setMontantEnchere(nouveauPrix);
		}
		
		enchere.setUtilisateur(user);
		
		if (exception.hasErrors()) {
			throw exception;
		} else {
			Enchere ancienneEnchere = this.selectMaxByArticle(enchere.getArticle());
			if (this.selectByArticleAndUtilisateur(article, user).getDateEnchere() != null) {
				this.enchereDAO.update(enchere);
			} else {
				this.enchereDAO.insert(enchere);
			}
			if (ancienneEnchere.getMontantEnchere() != null) {
				this.redonnerCredit(ancienneEnchere, exception);
			}
			
		}
			
	}
	
	private void redonnerCredit(Enchere enchere, BusinessException exception) {
			
			UtilisateurManager manager = new UtilisateurManager();
			
			try {
				Utilisateur user = manager.selectById(enchere.getUtilisateur().getNoUtilisateur());
				user.setCredit(user.getCredit() + enchere.getMontantEnchere());
				manager.modifier(user);
			} catch (BusinessException e) {
				e.printStackTrace();
				exception.addError("Erreur inconnue, merci de reessayer");
			}
	}
	
	private void validerCreditDisponible(Integer nouveauPrix, Utilisateur user,
			BusinessException exception) {
		if (user.getCredit() < nouveauPrix) {
			exception.addError("Votre crédit est insuffisant");
		} else {
			user.setCredit(user.getCredit() - nouveauPrix);
			
			UtilisateurManager manager = new UtilisateurManager();
			try {
				manager.modifier(user);
			} catch (BusinessException e) {
				e.printStackTrace();
				exception.addError("Erreur inconnue, merci de reessayer");
			}
		}
		
	}

	private void validerNouvelleEnchere(Enchere enchere, Integer nouveauPrix, Utilisateur user, BusinessException exception) {
		
		if (enchere.getMontantEnchere() != null) {
			if (nouveauPrix <= enchere.getMontantEnchere()) {
				exception.addError("Enchère inférieure au prix actuel"); 
			} else {
				if (enchere.getUtilisateur().getNoUtilisateur() == user.getNoUtilisateur()) {
					exception.addError("Vous avez déjà l'enchère la plus élevée");
				}
			}
		} else {
			if (nouveauPrix <= enchere.getArticle().getPrixInitial()) {
				exception.addError("Enchère inférieure au prix de départ");
			}
		}
		
	}

	public void update(Enchere enchere) throws BusinessException {
			enchereDAO.update(enchere);
	}
	
	public List<Enchere> selectByUtilisateur(Utilisateur utilisateur) throws BusinessException {
			return enchereDAO.selectByUtilisateur(utilisateur);
	}
	
	public List<Enchere> selectByArticle(Article article) throws BusinessException {
			return enchereDAO.selectByArticle(article);
	}
	

	public List<Enchere> selectEnchereGagne(Utilisateur utilisateur) throws BusinessException {
		return enchereDAO.selectEnchereGagne(utilisateur);
	}	
	public List<Enchere> selectByUtilisateurVenteEnCours(Utilisateur utilisateur) throws BusinessException {
		return enchereDAO.selectByUtilisateurVenteEnCours(utilisateur);
	}

	public Enchere selectByArticleAndUtilisateur(Article article, Utilisateur utilisateur) throws BusinessException {
		return this.enchereDAO.selectByArticleAndUtilisateur(article, utilisateur);
	}
	
	public Enchere selectMaxByArticle(Article article) throws BusinessException {
			return this.enchereDAO.selectMaxByArticle(article);
	}

}
