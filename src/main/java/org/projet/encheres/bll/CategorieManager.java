package org.projet.encheres.bll;


import java.util.List;

import org.projet.encheres.bo.Categorie;
import org.projet.encheres.dal.DAOFactory;
import org.projet.encheres.dal.categorie.CategorieDAO;
import org.projet.encheres.exception.BusinessException;

public class CategorieManager {
	
	private CategorieDAO categorieDAO;
	
	public CategorieManager() {
		this.categorieDAO = DAOFactory.getCategorieDAO();
	}
	
	public void ajouter(Categorie categorie) throws BusinessException {
			categorieDAO.insert(categorie);
	}
	
	public void modifier(Categorie categorie) throws BusinessException {
			categorieDAO.update(categorie);
	}
	
	public void supprimer(Categorie categorie) throws BusinessException {
			categorieDAO.delete(categorie);
	}
	
	public List<Categorie> lister() throws BusinessException {
		return categorieDAO.selectAll();
	}
	
	public Categorie chercher(int id) throws BusinessException {
		return categorieDAO.selectById(id);
	}

}
