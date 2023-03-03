package org.projet.encheres.forms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.projet.encheres.bll.UtilisateurManager;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

public class ConnexionForm {
	
	private Utilisateur connected;
	private Boolean isConnected = false;
	
	public void authentification( HttpServletRequest request) {
		String login = request.getParameter("identifiant");
		String pass = request.getParameter("motDePasse");
		
		UtilisateurManager manager = new UtilisateurManager();
		try {
			Utilisateur user = manager.chercherUtilisateur(login);
			
			// TODO Erreur si mot de passe is null
			if ((user.getNoUtilisateur() != null) & (user.getMotDePasse() != null) ) {
				if (user.getMotDePasse().equals(pass)) {
					setIsConnected(true);
				}
				
			}
			
			setConnected(user);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
	}

	public Utilisateur getConnected() {
		return connected;
	}

	public void setConnected(Utilisateur connected) {
		this.connected = connected;
	}

	public Boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	

}
