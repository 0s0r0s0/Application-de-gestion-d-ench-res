package org.projet.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.projet.encheres.bll.UtilisateurManager;
import org.projet.encheres.forms.ConnexionForm;

/**
 * Servlet implementation class ConnexionServlet
 */
@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnexionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/pages/Connexion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		connectUser(request, response);
	}
	
	protected void connectUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Récupération des données du formulaire de connexion via l'utilitaire ConnexionForm
		ConnexionForm form = new ConnexionForm();
		
		form.authentification(request);
		
		// Création d'une session et attribution de mon utilisateur connecté
		HttpSession session = request.getSession();
		
		
		// Renvoie du message d'erreur en cas de login ou mot de passe erroné ou inconnu
		String messErreur = "test";
		
		if (form.getIsConnected() == false) {
			messErreur = " ⛔️ L'identifiant ou le mot de passe est inconnu ";
		}else {
			session.setAttribute("user", form.getConnected());
			messErreur = null;
		}
	
		request.setAttribute("messErreur", messErreur);
		

		//this.getServletContext().getRequestDispatcher("/AccueilConnecte").forward(request, response);
		response.sendRedirect(request.getContextPath() + "/AccueilConnecte");


	
	}


	
}
