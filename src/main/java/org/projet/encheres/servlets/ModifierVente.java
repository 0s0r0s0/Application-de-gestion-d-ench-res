package org.projet.encheres.servlets;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
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
import org.projet.encheres.bll.RetraitManager;
import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Categorie;
import org.projet.encheres.bo.Retrait;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

/**
 * Servlet implementation class ModifierVente
 */
@WebServlet("/modifier")
public class ModifierVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Article article = new Article();
		BusinessException exception = new BusinessException();
		
		try {
			Integer idArticle = Integer.parseInt(request.getParameter("id"));
			ArticleManager manager = new ArticleManager();
			
			article = manager.selectById(idArticle);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath());
		} catch (BusinessException e) {
			e.printStackTrace();
			exception.addError("Problème inconnu, merci de reessayer");
			request.setAttribute("erreurs", exception.getErrors());
			request.getRequestDispatcher("/detail?id="+request.getParameter("id")).forward(request, response);
		}
		
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		
		if (user != null) {
			if (user.getNoUtilisateur() == article.getUtilisateur().getNoUtilisateur()) {
				
				if (article.getDateFinEncheres().isBefore(LocalDate.now())) {
					
					exception.addError("Vous ne pouvez plus modifier cet article");
					request.setAttribute("erreurs", exception.getErrors());
					request.getRequestDispatcher("/detail?id="+request.getParameter("id")).forward(request, response);
					
				} else {
					List<Categorie> categories = getCategories();
					request.setAttribute("categories", categories);
					
					request.setAttribute("utilisateur", user);
					request.setAttribute("article", article);
					request.getRequestDispatcher("/WEB-INF/pages/ModifierVente.jsp").forward(request, response);
				}
				
			} else {
				exception.addError("Vous ne pouvez pas modifier cet article");
				request.setAttribute("erreurs", exception.getErrors());
				request.getRequestDispatcher("/detail?id="+request.getParameter("id")).forward(request, response);
			}
		} else {
			response.sendRedirect(request.getContextPath());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		BusinessException exception = new BusinessException();
		
		if (request.getSession().getAttribute("user") != null ) {
			
			// On récupère l'article
			Article article = new Article();
			
			try {
				article.setNoArticle(Integer.parseInt(request.getParameter("id_article")));
			} catch (NumberFormatException e) {
				exception.addError("Erreur inconnue, merci de recommencer");
				request.setAttribute("erreurs", exception.getErrors());
				request.getRequestDispatcher("/detail?id="+request.getParameter("id")).forward(request, response);
			}
			
			article.setNomArticle(request.getParameter("nom_article"));
			article.setDescription(request.getParameter("description"));
			
			Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
			
			Categorie categorie = new Categorie();
			categorie.setNoCategorie(Integer.parseInt(request.getParameter("categorie")));
			
			article.setUtilisateur(user);
			article.setCategorie(categorie);
			
			try {
				article.setDateDebutEncheres(LocalDate.parse(request.getParameter("date_debut")));
				article.setDateFinEncheres(LocalDate.parse(request.getParameter("date_fin")));
			} catch (DateTimeException e) {
				exception.addError("- Format de date non conforme");
			}
			try {
				article.setPrixInitial(Integer.parseInt(request.getParameter("mise_prix")));
			} catch (NumberFormatException e) {
				exception.addError("- Format du prix non conforme");
			}
			
			List<Categorie> categories = getCategories();
			request.setAttribute("categories", categories);
			
			ArticleManager manager = new ArticleManager();
			
			if (!exception.hasErrors()) {
				try {
					manager.update(article);
					
					RetraitManager retraitManager = new RetraitManager();
					
					Retrait retrait = new Retrait();
					retrait.setArticle(article);
					retrait.setRue(request.getParameter("rue"));
					retrait.setCodePostal(request.getParameter("code_postal"));
					retrait.setVille(request.getParameter("ville"));
					
					retraitManager.update(retrait);
					
					request.setAttribute("modified", "true");
					request.getRequestDispatcher("/detail?id="+article.getNoArticle()).forward(request, response);
					
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("article", article);
					request.setAttribute("erreurs", e.getErrors());
					request.getRequestDispatcher("/WEB-INF/pages/ModifierVente.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("article", article);
				request.setAttribute("erreurs", exception.getErrors());
				request.getRequestDispatcher("/WEB-INF/pages/ModifierVente.jsp").forward(request, response);
			}
			
			
			
		} else {
			request.getRequestDispatcher("/connexion").forward(request, response);
		}
	}
	
	private List<Categorie> getCategories() {
		
		List<Categorie> categories = new ArrayList<Categorie>();
		CategorieManager manager = new CategorieManager();
		
		try {
			categories = manager.lister();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return categories;
		
	}

}
