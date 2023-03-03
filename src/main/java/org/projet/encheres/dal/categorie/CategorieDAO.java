package org.projet.encheres.dal.categorie;

import java.util.List;

import org.projet.encheres.bo.Categorie;
import org.projet.encheres.exception.BusinessException;

public interface CategorieDAO {

	public void insert(Categorie categorie) throws BusinessException;

	public void update(Categorie categorie) throws BusinessException;

	public void delete(Categorie categorie) throws BusinessException;

	public List<Categorie> selectAll() throws BusinessException;

	public Categorie selectById(int id) throws BusinessException;

}
