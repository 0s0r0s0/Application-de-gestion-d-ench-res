package org.projet.encheres.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.projet.encheres.bll.ArticleManager;
import org.projet.encheres.bll.CategorieManager;
import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Categorie;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

@WebServlet("/")
public class AccueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ArticleManager articleManager = new ArticleManager();
	CategorieManager categorieManager = new CategorieManager();
	
       
   
    public AccueilServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getSession().getAttribute("user") == null ) {
			try {
				List<Article> listeArticles = articleManager.selectAllByDate();
				request.setAttribute("listeArticles", listeArticles);
				
				List<Categorie> listeCategories = categorieManager.lister();
				request.setAttribute("listeCategories", listeCategories);
				
				
			}catch (BusinessException e) {
				e.printStackTrace();
			}
			 
			request.getRequestDispatcher("/WEB-INF/pages/Accueil.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/AccueilConnecte");
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			List<Categorie> listeCategories = categorieManager.lister();
			request.setAttribute("listeCategories", listeCategories);
			
			
		}catch (BusinessException e) {
			e.printStackTrace();
		}
		
		String recherche = request.getParameter("recherche");
		Integer categorie = null;
		
		try {
			categorie = Integer.parseInt(request.getParameter("categorie"));
		} catch (NumberFormatException e) {
			categorie = null;
		}
		
		if (categorie == null & recherche == null) {
			doGet(request, response);
		} else {

			try {
				ArticleManager articleManager = new ArticleManager();
				List<Article> listeArticles = null;
				
			
				if (recherche == null & categorie == 0 ) {
					listeArticles = articleManager.selectAllByDate();
					request.setAttribute("listArticles", listeArticles);
				}
				else if (recherche != null & categorie == 0) {  
					listeArticles = articleManager.selectAllByFirstLetter(recherche);
					request.setAttribute("listeArticles", listeArticles);
				} 
				else if (recherche == null & categorie != 0) { 
					listeArticles = articleManager.selectByCategorie(categorie);
					request.setAttribute("listeArticles", listeArticles);
				} 
				else if (recherche != null & categorie != 0) { 
					listeArticles = articleManager.selectAllByFirstLetterAndCategorie(recherche, categorie);
					request.setAttribute("listeArticles", listeArticles);
				}
			}catch (BusinessException e) {
				e.printStackTrace();
			}
				
			 request.getServletContext().getRequestDispatcher("/WEB-INF/pages/Accueil.jsp").forward(request, response);
		}
		
	}

}
