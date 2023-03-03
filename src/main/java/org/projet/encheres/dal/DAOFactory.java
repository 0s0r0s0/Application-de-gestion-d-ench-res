package org.projet.encheres.dal;

import org.projet.encheres.dal.article.ArticleDAO;
import org.projet.encheres.dal.article.ArticleDAOJdbcImpl;
import org.projet.encheres.dal.categorie.CategorieDAO;
import org.projet.encheres.dal.categorie.CategorieDAOJbdcImpl;
import org.projet.encheres.dal.enchere.EnchereDAO;
import org.projet.encheres.dal.enchere.EnchereDAOJdbcImpl;
import org.projet.encheres.dal.retrait.RetraitDAO;
import org.projet.encheres.dal.retrait.RetraitDAOJdbcImpl;
import org.projet.encheres.dal.utilisateur.UtilisateurDAO;
import org.projet.encheres.dal.utilisateur.UtilisateurDAOJdbcImpl;

public abstract class DAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDAOJdbcImpl();
	}
	
	public static CategorieDAO getCategorieDAO() {
		return new CategorieDAOJbdcImpl();
	}
	
	public static RetraitDAO getRetraitDAO() {
		return new RetraitDAOJdbcImpl();
	}
	
	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOJdbcImpl();
	}
	
	public static ArticleDAO getArticleDAO() {
		return new ArticleDAOJdbcImpl();
	}

}
