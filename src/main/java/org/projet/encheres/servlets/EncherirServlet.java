package org.projet.encheres.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.projet.encheres.bll.ArticleManager;
import org.projet.encheres.bll.EnchereManager;
import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Enchere;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

/**
 * Servlet implementation class EncherirServlet
 */
@WebServlet("/encherir")
public class EncherirServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BusinessException exception = new BusinessException();
		ArticleManager articleManager = new ArticleManager();
		EnchereManager enchereManager = new EnchereManager();
		
		Enchere enchere = new Enchere(); 
		
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		
		enchere.setUtilisateur(user);
		
		Article article = null;
		
		// Récupération de l'id de l'article concerné par l'enchère
		try {
			Integer idArticle = Integer.parseInt(request.getParameter("id_article"));
			
			// Récupération de l'article
			try {
				article = articleManager.selectById(idArticle);
			} catch (BusinessException e1) {
				e1.printStackTrace();
				exception.addError("Problème inconnu, merci de reessayer");
				request.setAttribute("erreurs", exception.getErrors());
				request.getRequestDispatcher("/detail?id="+request.getParameter("id_article")).forward(request, response);
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath());
		}
		
		enchere.setArticle(article);
		
		Integer nouveauPrix = null;
		
		// Récupération du montant de l'enchère
		try {
			nouveauPrix = Integer.parseInt(request.getParameter("nouveau_prix"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			exception.addError("Enchère non valide");
			request.setAttribute("erreurs", exception.getErrors());
			request.getRequestDispatcher("/detail?id="+request.getParameter("id_article")).forward(request, response);
		}
		
		// Insert de l'enchère
		try {
			
				enchereManager.insert(enchere, nouveauPrix);
				request.setAttribute("enchere_reussie", "true");
				request.getRequestDispatcher("/detail?id="+request.getParameter("id_article")).forward(request, response);

		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("erreurs", e.getErrors());
			request.getRequestDispatcher("/detail?id="+request.getParameter("id_article")).forward(request, response);
		}
		
		
	}
	
}
