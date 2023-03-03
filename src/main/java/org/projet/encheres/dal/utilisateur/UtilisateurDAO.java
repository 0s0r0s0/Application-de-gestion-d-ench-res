package org.projet.encheres.dal.utilisateur;

import java.util.List;

import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

public interface UtilisateurDAO {
	
	public void insert(Utilisateur user) throws BusinessException;
	public void update(Utilisateur user) throws BusinessException;
	public void delete(Utilisateur user) throws BusinessException;
	public List<Utilisateur> selectAll() throws BusinessException;
	public Utilisateur selectByPseudoOrEmail(String name) throws BusinessException;
	public Utilisateur selectById(int id) throws BusinessException;
	

}
