package org.projet.encheres.dal.retrait;


import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Retrait;
import org.projet.encheres.exception.BusinessException;

public interface RetraitDAO {

	public void insert(Retrait retrait) throws BusinessException;

	public void update(Retrait retrait) throws BusinessException;

	public void delete(Retrait retrait) throws BusinessException;
	
	public Retrait selectByArticle(Article article) throws BusinessException;

}
