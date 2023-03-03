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
 * Servlet implementation class EditerProfilServlet
 */
@WebServlet("/profil/editer")
public class EditerProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditerProfilServlet() {
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
		
		request.setCharacterEncoding("UTF-8");
		
		String messErreurPass = null;
		String messErreurNewPass = null;
		
		// Récupération de notre utilisateur en session
		HttpSession session = request.getSession();
		Utilisateur utilisateurConnected = (Utilisateur) session.getAttribute("user");
		
		// Création de l'utilisateur avec données mises à jour
		Utilisateur utilisateur = new Utilisateur();
		if (request.getParameter("password").equals(utilisateurConnected.getMotDePasse())) {
			
			if (request.getParameter("passwordNew") != null & request.getParameter("passwordNew").length() >= 0 & request.getParameter("passwordConfirm") != null) {
				if (request.getParameter("passwordNew").equals(request.getParameter("passwordConfirm"))) {
					try {
						
						utilisateur.setNoUtilisateur(utilisateurConnected.getNoUtilisateur());
						utilisateur.setPseudo(request.getParameter("pseudo"));
						utilisateur.setNom(request.getParameter("nom"));
						utilisateur.setPrenom(request.getParameter("prenom"));
						utilisateur.setEmail(request.getParameter("email"));
						utilisateur.setTelephone(request.getParameter("telephone"));
						utilisateur.setRue(request.getParameter("rue"));
						utilisateur.setCodePostal(request.getParameter("code_postal"));
						utilisateur.setVille(request.getParameter("ville"));
						utilisateur.setMotDePasse(request.getParameter("passwordNew"));
						
						UtilisateurManager manager = new UtilisateurManager();
						
						// Appel de la méthode du Manager pour l'update de l'utilisateur
						manager.modifier(utilisateur);
						response.sendRedirect(request.getContextPath());
						
					} catch (BusinessException e) {
						e.printStackTrace();
					}
				}else {
					messErreurNewPass = " ⛔️ Le nouveau mot de passe et/ou la confirmation ne sont pas identiques !";
					request.setAttribute("messErreurNewPass", messErreurNewPass);
					this.getServletContext().getRequestDispatcher("/WEB-INF/pages/EditerProfil.jsp").forward(request, response);
				}
			}else {
				messErreurNewPass = " ⛔️ Merci de taper un mot de passe et de le confirmer !";
				request.setAttribute("messErreurNewPass", messErreurNewPass);
				this.getServletContext().getRequestDispatcher("/WEB-INF/pages/EditerProfil.jsp").forward(request, response);
			}
			
			
			
		}else {
			messErreurPass = " ⛔️ Le mot de passe saisi n'est pas valide !";
			request.setAttribute("messErreurPass", messErreurPass);
			this.getServletContext().getRequestDispatcher("/WEB-INF/pages/EditerProfil.jsp").forward(request, response);
		}
		
		
			
	}

}
