package org.projet.encheres.dal.enchere;

import java.util.List;

import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Enchere;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;


public interface EnchereDAO {

	public void insert(Enchere enchere) throws BusinessException;
	
	public void update(Enchere enchere) throws BusinessException;

	public List<Enchere> selectByUtilisateur(Utilisateur utilisateur) throws BusinessException;

	// A trier par montant décroissant
	public List<Enchere> selectByArticle(Article article) throws BusinessException;
	

	public List<Enchere> selectEnchereGagne(Utilisateur utilisateur) throws BusinessException;
	
	public List<Enchere> selectByUtilisateurVenteEnCours(Utilisateur utilisateur) throws BusinessException;

	public Enchere selectByArticleAndUtilisateur(Article article, Utilisateur utilisateur) throws BusinessException;
	
	public Enchere selectMaxByArticle(Article article) throws BusinessException;

}
