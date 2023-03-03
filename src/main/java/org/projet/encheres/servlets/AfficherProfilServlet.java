package org.projet.encheres.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.projet.encheres.bll.UtilisateurManager;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

/**
 * Servlet implementation class AfficherProfilServlet
 */
@WebServlet("/profil/*")
public class AfficherProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String profil = request.getPathInfo().replaceFirst("/", "");
		
		UtilisateurManager manager = new UtilisateurManager();
		Utilisateur user = new Utilisateur();
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			user = manager.chercherUtilisateur(profil);
			
			request.setAttribute("profil", user);
			
			if (user.getPseudo() != null) {
				request.getRequestDispatcher("/WEB-INF/pages/AfficherProfil.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/").forward(request, response);
			}
			
			
		} catch (BusinessException e) {
			e.printStackTrace();
			
		}
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
