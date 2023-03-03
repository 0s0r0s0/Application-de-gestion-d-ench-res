package org.projet.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.projet.encheres.bll.ArticleManager;
import org.projet.encheres.bll.CategorieManager;
import org.projet.encheres.bll.EnchereManager;
import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Categorie;
import org.projet.encheres.bo.Enchere;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

@WebServlet("/AccueilConnecte")
public class AccueilConnecteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ArticleManager articleManager = new ArticleManager();
	CategorieManager categorieManager = new CategorieManager();
	EnchereManager enchereManager = new EnchereManager();
	
       
   
    public AccueilConnecteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Article> listeArticles = articleManager.selectAllByDate();
			request.setAttribute("listeArticles", listeArticles);
			
			List<Categorie> listeCategories = categorieManager.lister();
			request.setAttribute("listeCategories", listeCategories);
			
			
		}catch (BusinessException e) {
			e.printStackTrace();
		}
		
		 
		request.getRequestDispatcher("/WEB-INF/pages/AccueilConnecte.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<Categorie> listeCategories = categorieManager.lister();
			request.setAttribute("listeCategories", listeCategories);		
		}catch (BusinessException e) {
			e.printStackTrace();
		}
					
		HttpSession session = request.getSession();
		Utilisateur utilisateurConnected = (Utilisateur) session.getAttribute("user");
				
	
		List<Article> listeArticles = new ArrayList<Article>();
		List<Enchere> listeEncheres = new ArrayList<Enchere>();
		String recherche = request.getParameter("recherche");
		Integer categorie = null; 
		try {
			categorie = Integer.parseInt(request.getParameter("categorie"));
		}catch (NumberFormatException e) {
			categorie = null;
		}
		if (categorie == null & recherche == null) {
			doGet(request, response);
		} else {
			
		
		
				if (request.getParameter("encheresOuvertes") != null) {
					try {
						
						if (recherche == null & categorie == 0 ) {
							listeArticles = articleManager.selectAllByDate();
							
						}
						else if (recherche != null & categorie == 0) {  
							listeArticles = articleManager.selectAllByFirstLetter(recherche);
						} 
						else if (recherche == null & categorie != 0) { 
							listeArticles = articleManager.selectByCategorie(categorie);
		
						} 
						else if (recherche != null & categorie != 0) { 
							listeArticles = articleManager.selectAllByFirstLetterAndCategorie(recherche, categorie);
						} 
						
					}catch (BusinessException e) {
						e.printStackTrace();
					} 
					request.setAttribute("listeArticles", listeArticles);
					
				}
				if (request.getParameter("mesEncheresEnCours") != null) {
					
					try {
						listeEncheres.addAll(enchereManager.selectByUtilisateurVenteEnCours(utilisateurConnected));
					} catch (BusinessException e) {
						e.printStackTrace();
					}
					request.setAttribute("listeEncheres", listeEncheres);	
					
				}
				if (request.getParameter("mesEncheresRemportees") != null) {
					
					try {
						listeEncheres.addAll(enchereManager.selectEnchereGagne(utilisateurConnected));
					
					} catch (BusinessException e1) {
						e1.printStackTrace();
					}
					request.setAttribute("listeEncheres", listeEncheres);	
							
				}
				if (request.getParameter("mesVentes") != null) {
					
					try {
						listeArticles.addAll(articleManager.selectByUtilisateur(utilisateurConnected));
						
					} catch (BusinessException e) {
						e.printStackTrace();
					}
					request.setAttribute("listeArticles", listeArticles);
					
				}
				if (request.getParameter("ventesNonDebutees") != null) {
					
					try {
						
						listeArticles.addAll(articleManager.selectVenteNonDebuteeByUtilisateur(utilisateurConnected));
						
					} catch (BusinessException e) {
						e.printStackTrace();
					}
					request.setAttribute("listeArticles", listeArticles);
					
				}
				if (request.getParameter("ventesTermine") != null) {
					
					try {
						listeArticles.addAll(articleManager.selectVenteTermineeByUtilisateur(utilisateurConnected));
						
					} catch (BusinessException e) {
						e.printStackTrace();
					}
					request.setAttribute("listeArticles", listeArticles);
					
				}		
			
		 request.getServletContext().getRequestDispatcher("/WEB-INF/pages/AccueilConnecte.jsp").forward(request, response);
		}
	}
	
	

}
