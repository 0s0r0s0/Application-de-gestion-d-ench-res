package org.projet.encheres.bll;

import java.util.List;

import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.dal.DAOFactory;
import org.projet.encheres.dal.utilisateur.UtilisateurDAO;
import org.projet.encheres.exception.BusinessException;

public class UtilisateurManager {

	private UtilisateurDAO utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}

	public void ajouter(Utilisateur utilisateur) throws BusinessException {
		
		BusinessException exception = new BusinessException();
		
		this.validerPseudo(utilisateur, exception);
		this.validerNom(utilisateur, exception);
		this.validerPrenom(utilisateur, exception);
		this.validerEmail(utilisateur, exception);
		this.validerTelephone(utilisateur, exception);
		this.validerRue(utilisateur, exception);
		this.validerCodePostal(utilisateur, exception);
		this.validerVille(utilisateur, exception);
		this.validerMotDePasse(utilisateur, exception);
		
		if (exception.hasErrors()) {
			throw exception;
		} else {
			this.utilisateurDAO.insert(utilisateur);
		}
		
	}

	public void modifier(Utilisateur utilisateur) throws BusinessException {
		this.utilisateurDAO.update(utilisateur);
	}

	public void supprimer(Utilisateur utilisateur) throws BusinessException {
		this.utilisateurDAO.delete(utilisateur);
	}

	public List<Utilisateur> lister() throws BusinessException {
		return this.utilisateurDAO.selectAll();
	}

	public Utilisateur chercherUtilisateur(String name) throws BusinessException {
		return this.utilisateurDAO.selectByPseudoOrEmail(name);
	}
	

	public Utilisateur selectById(int id) throws BusinessException {
		return this.utilisateurDAO.selectById(id);
	}


	private void validerPseudo(Utilisateur utilisateur, BusinessException exception) throws BusinessException {

		if (this.chercherUtilisateur(utilisateur.getPseudo()).getNoUtilisateur() != null) {
			exception.addError("- Le pseudo est déjà pris.");
		} else {
			if (utilisateur.getPseudo().length() <= 0 || utilisateur.getPseudo().length() > 30
					|| utilisateur.getPseudo().trim().length() == 0) {
				exception.addError("- Merci de saisir un pseudo non vide et de 30 caractères maximum.");
			}
		}
		

	}
	
	private void validerNom(Utilisateur utilisateur, BusinessException exception) throws BusinessException {

		if (utilisateur.getNom().length() <= 0 || utilisateur.getNom().length() > 30
				|| utilisateur.getNom().trim().length() == 0) {
			exception.addError("- Merci de saisir un nom non vide et de 30 caractères maximum.");
		}

	}
	
	private void validerPrenom(Utilisateur utilisateur, BusinessException exception) throws BusinessException {

		if (utilisateur.getPrenom().length() <= 0 || utilisateur.getPrenom().length() > 30
				|| utilisateur.getPrenom().trim().length() == 0) {
			exception.addError("- Merci de saisir un prénom non vide et de 30 caractères maximum.");
		}

	}
	
	private void validerEmail(Utilisateur utilisateur, BusinessException exception) throws BusinessException {

		if (this.chercherUtilisateur(utilisateur.getEmail()).getNoUtilisateur() != null) {
			exception.addError("- Un compte avec cet email existe déjà.");
		} else {
			if (utilisateur.getEmail().length() <= 0 || utilisateur.getEmail().length() > 20
					|| utilisateur.getEmail().trim().length() == 0 || !utilisateur.getEmail().contains("@")) {
				exception.addError("- Merci de saisir un email valide de 20 caractères maximum.");
			}
		}
		

	}
	
	private void validerTelephone(Utilisateur utilisateur, BusinessException exception) throws BusinessException {

		if (utilisateur.getTelephone().length() <= 0 || utilisateur.getTelephone().length() > 15
				|| utilisateur.getTelephone().trim().length() == 0) {
			exception.addError("- Merci de saisir un téléphone non vide et de 15 caractères maximum.");
		}

	}
	
	private void validerRue(Utilisateur utilisateur, BusinessException exception) throws BusinessException {

		if (utilisateur.getRue().length() <= 0 || utilisateur.getRue().length() > 30
				|| utilisateur.getRue().trim().length() == 0) {
			exception.addError("- Merci de saisir une rue valide de 30 caractères maximum.");
		}

	}
	
	private void validerCodePostal(Utilisateur utilisateur, BusinessException exception) throws BusinessException {

		if (utilisateur.getCodePostal().length() <= 0 || utilisateur.getCodePostal().length() > 10
				|| utilisateur.getCodePostal().trim().length() == 0) {
			exception.addError("- Merci de saisir un code postal valide de 10 caractères maximum.");
		}

	}
	
	private void validerVille(Utilisateur utilisateur, BusinessException exception) throws BusinessException {

		if (utilisateur.getVille().length() <= 0 || utilisateur.getVille().length() > 30
				|| utilisateur.getVille().trim().length() == 0) {
			exception.addError("- Merci de saisir une ville valide de 30 caractères maximum.");
		}

	}
	
	private void validerMotDePasse(Utilisateur utilisateur, BusinessException exception) throws BusinessException {

		if (utilisateur.getMotDePasse().length() <= 0 || utilisateur.getMotDePasse().length() > 30
				|| utilisateur.getMotDePasse().trim().length() == 0) {
			exception.addError("- Merci de saisir un mot de passe valide de 30 caractères maximum.");
		}

	}
	
	
	

}
