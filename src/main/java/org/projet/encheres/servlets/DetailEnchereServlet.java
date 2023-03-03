package org.projet.encheres.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.projet.encheres.bll.ArticleManager;
import org.projet.encheres.bll.EnchereManager;
import org.projet.encheres.bll.UtilisateurManager;
import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Enchere;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.exception.BusinessException;

/**
 * Servlet implementation class DetailEnchereServlet
 */
@WebServlet("/detail")
public class DetailEnchereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailEnchereServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Article article = new Article();
		
		// Utilisation de ArticleManager pour retrouver un article avec son id
		ArticleManager manager = new ArticleManager();
		EnchereManager enchereManager = new EnchereManager();
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		
		Integer idArticle = Integer.parseInt(request.getParameter("id"));
		
		
		try {
			//Si date fin enchere dépassée : 		
			if (manager.selectArticleByDate(idArticle).getNoArticle() != null) {
				//On récupère l'article par son id
				article = manager.selectById(idArticle);
				//On récup enchère max de l'article
				Enchere enchere = enchereManager.selectMaxByArticle(article);
				//On récup l'utilisateur qui a remporté l'enchère
				Utilisateur utilisateur = utilisateurManager.selectById(enchere.getUtilisateur().getNoUtilisateur());
				
				request.setAttribute("enchere", enchere);
				request.setAttribute("article", article);
				request.setAttribute("utilisateur", utilisateur);
			
				request.getRequestDispatcher("/WEB-INF/pages/VenteRemportee.jsp").forward(request, response);
			} else {
				
				try {
					article = manager.selectById(idArticle);
			
					int miseMini; 
					
					Enchere enchere = enchereManager.selectMaxByArticle(article);
					
					if (enchere.getMontantEnchere() != null) {
						miseMini = enchere.getMontantEnchere() + 1;
					} else {
						miseMini = article.getPrixInitial() + 1;
					}
					request.setAttribute("enchere", enchere);
					request.setAttribute("article", article);
					request.setAttribute("miseMini", miseMini);
					
					request.getRequestDispatcher("/WEB-INF/pages/DetailEnchere.jsp").forward(request, response);
				} catch (NumberFormatException | BusinessException e) {
					e.printStackTrace();
				}
				
			}
		} catch (BusinessException e1) {
			e1.printStackTrace();
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
