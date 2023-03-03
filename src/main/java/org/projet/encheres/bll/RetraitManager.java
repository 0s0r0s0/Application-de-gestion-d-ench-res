package org.projet.encheres.bll;

import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Retrait;
import org.projet.encheres.dal.DAOFactory;
import org.projet.encheres.dal.retrait.RetraitDAO;
import org.projet.encheres.exception.BusinessException;

public class RetraitManager {
	

	private RetraitDAO retraitDAO = DAOFactory.getRetraitDAO();

	public void insert(Retrait retrait) throws BusinessException {
				
			retraitDAO.insert(retrait);
		
	}
	
	public void update(Retrait retrait) throws BusinessException {
				
			retraitDAO.update(retrait);
		
	}
	
	public void delete(Retrait retrait) throws BusinessException {
					
			retraitDAO.delete(retrait);
		
	}

	public Retrait selectByArticle(Article article) throws BusinessException {
	
			return retraitDAO.selectByArticle(article);
		
	}

}
