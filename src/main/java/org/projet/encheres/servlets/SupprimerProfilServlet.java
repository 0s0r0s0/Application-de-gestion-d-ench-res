package org.projet.encheres.servlets;

import java.io.IOException;
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
 * Servlet implementation class SupprimerProfilServlet
 */
@WebServlet("/profil/supprimer")
public class SupprimerProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerProfilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/pages/EditerProfil.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Récupération de notre utilisateur en session
				HttpSession session = request.getSession();
				String messErreurPass = null;
							
				try {
					Utilisateur utilisateur = new Utilisateur();
					
					utilisateur.setNoUtilisateur(((Utilisateur) session.getAttribute("user")).getNoUtilisateur());
					
					UtilisateurManager manager = new UtilisateurManager();
					
					// Appel de la méthode du Manager pour la suppression de l'utilisateur
					manager.supprimer(utilisateur);
					
				} catch (BusinessException e) {
					e.printStackTrace();
				}
				this.getServletContext().getRequestDispatcher("/deconnexion").forward(request, response);
		
				}   

}
