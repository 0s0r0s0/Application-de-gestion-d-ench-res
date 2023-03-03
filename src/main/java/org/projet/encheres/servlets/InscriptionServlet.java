package org.projet.encheres.servlets;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.projet.encheres.bll.UtilisateurManager;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/Inscription.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// On récupère l'utilisateur
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPseudo(request.getParameter("pseudo"));
		utilisateur.setNom(request.getParameter("nom"));
		utilisateur.setPrenom(request.getParameter("prenom"));
		utilisateur.setEmail(request.getParameter("email"));
		utilisateur.setTelephone(request.getParameter("telephone"));
		utilisateur.setRue(request.getParameter("rue"));
		utilisateur.setCodePostal(request.getParameter("code_postal"));
		utilisateur.setVille(request.getParameter("ville"));
		utilisateur.setMotDePasse(request.getParameter("password"));
		
		if (request.getParameter("verification_password").trim().equals(request.getParameter("password").trim())) {
				
			UtilisateurManager manager = new UtilisateurManager();
			
			try {
				
				manager.ajouter(utilisateur);
				
				// Création d'une session et attribution de mon utilisateur connecté
				HttpSession session = request.getSession();
				session.setAttribute("user", utilisateur);
				
				request.setAttribute("connected", true);
				request.getRequestDispatcher("/connexion").forward(request, response);

			} catch (BusinessException e) {
				
				e.printStackTrace();
				
				request.setAttribute("utilisateur", utilisateur);
				request.setAttribute("erreurs", e.getErrors());
				request.getRequestDispatcher("/WEB-INF/pages/Inscription.jsp").forward(request, response);
				
			}
			
		} else {
			
			BusinessException exception = new BusinessException();
			exception.addError("Les deux mots de passe ne sont pas identiques");
			request.setAttribute("utilisateur", utilisateur);
			request.setAttribute("erreurs", exception.getErrors());
			request.getRequestDispatcher("/WEB-INF/pages/Inscription.jsp").forward(request, response);
			
			
		}
		
		
		
		
		
	}

}
