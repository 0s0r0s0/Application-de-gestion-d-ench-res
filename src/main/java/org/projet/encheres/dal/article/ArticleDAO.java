package org.projet.encheres.dal.article;

import java.util.List;

import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Categorie;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

public interface ArticleDAO {

	public void insert(Article article) throws BusinessException;

	public void update(Article article) throws BusinessException;

	public void delete(Article article) throws BusinessException;

	public List<Article> selectAll() throws BusinessException;

	public Article selectById(int id) throws BusinessException;

	public List<Article> selectByCategorie(int id) throws BusinessException;

	public List<Article> selectByUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public List<Article> selectAllByDate() throws BusinessException;
	
	public List<Article> selectAllByFirstLetter(String recherche) throws BusinessException;
	
	public List<Article> selectAllByFirstLetterAndCategorie(String recherche, int id) throws BusinessException;
	
	public List<Article> selectVenteNonDebuteeByUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public List<Article> selectVenteTermineeByUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public Article selectArticleByDate(int id) throws BusinessException;
	
	

}
