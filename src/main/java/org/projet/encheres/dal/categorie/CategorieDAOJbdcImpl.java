package org.projet.encheres.dal.categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Categorie;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.dal.ConnectionProvider;
import org.projet.encheres.exception.BusinessException;

public class CategorieDAOJbdcImpl implements CategorieDAO {

	private static final String SQL_INSERT = "INSERT INTO Categories (libelle) VALUES (?);";
	private static final String SQL_UPDATE = "UPDATE Categories SET libelle=? WHERE no_categorie = ?;";
	private static final String SQL_DELETE_CATEGORIE = "DELETE FROM Categories WHERE no_categorie = ?;";
	private static final String SQL_DELETE_ARTICLES = "DELETE FROM Articles_vendu WHERE no_categorie = ?;";
	private static final String SQL_SELECT_ARTICLES = "SELECT * FROM Articles_vendus WHERE no_catgorie = ? ;";
	private static final String SQL_DELETE_RETRAIT = "DELETE FROM Retraits WHERE no_article = ?;";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Categories;";
	private static final String SQL_SELECT_ARTICLES_BY_CATEGORIE = "SELECT * FROM Articles_vendus WHERE no_categorie = ?;";
	private static final String SQL_SELECT_UTILISATEUR_BY_ID = "SELECT * FROM Utilisateurs WHERE no_utilisateur = ? ;";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM Categories WHERE no_categorie = ?;";

	@Override
	public void insert(Categorie categorie) throws BusinessException {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
						PreparedStatement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, categorie.getLibelle());
			statement.executeUpdate();

			try (ResultSet result = statement.getGeneratedKeys()) {
				if (result.next()) {
					categorie.setNoCategorie(result.getInt(1));
				}
			}

		} catch (SQLException e) {
			throw new BusinessException("Erreur sur insert Categorie", e);
		}

	}

	@Override
	public void update(Categorie categorie) throws BusinessException {

		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

			statement.setString(1, categorie.getLibelle());
			statement.setInt(2, categorie.getNoCategorie());

			statement.executeUpdate();

		} catch (SQLException e) {
			throw new BusinessException("Erreur sur update Categorie", e);
		}

	}

	@Override
	public void delete(Categorie categorie) throws BusinessException {

		try (Connection connection = ConnectionProvider.getConnection()) {
			connection.setAutoCommit(false);

			try {
				// Pour supprimer une CATEGORIE, il faut supprimer les ARTICLES et les RETRAITS
				try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CATEGORIE)) {
					statement.setInt(1, categorie.getNoCategorie());

					// Recherche de tous les ARTICLES à supprimer pour trouver tous les RETRAITS par
					// no_article
					try (PreparedStatement statementArticles = connection.prepareStatement(SQL_SELECT_ARTICLES)) {
						statementArticles.setInt(1, categorie.getNoCategorie());

						try (ResultSet result = statementArticles.executeQuery()) {

							// Itération sur chaque ARTICLE et suppression de chaque RETRAIT
							while (result.next()) {
								try (PreparedStatement statementDeleteRetrait = connection
										.prepareStatement(SQL_DELETE_RETRAIT)) {
									statementDeleteRetrait.setInt(1, result.getInt("no_article"));
									statementDeleteRetrait.executeUpdate();
								}
							}

						}

					}
					// Suppression des ARTICLES
					try (PreparedStatement statementArticles = connection.prepareStatement(SQL_DELETE_ARTICLES)) {
						statementArticles.setInt(1, categorie.getNoCategorie());
						statementArticles.executeUpdate();
					}

					// Execute la suppression de la CATEGORIE
					statement.executeUpdate();

				}

				// Si tout ok, commit
				connection.commit();

			} catch (SQLException e) {
				// Si erreur dans les suppressions successives, rollback
				connection.rollback();
				throw new BusinessException("Erreur sur delete Categorie", e);

			} finally {
				connection.setAutoCommit(true);
			}

		} catch (SQLException e) {
			throw new BusinessException("Erreur sur delete Categorie", e);
		}

	}

	@Override
	public List<Categorie> selectAll() throws BusinessException {

		List<Categorie> listeCategories = new ArrayList<Categorie>();

		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
				ResultSet result = statement.executeQuery()) {
			
			while (result.next()) {
				Categorie categorie = new Categorie();
				
				categorie.setNoCategorie(result.getInt("no_categorie"));
				categorie.setLibelle(result.getString("libelle"));
				
				List<Article> articles = new ArrayList<Article>();
				
				// Récupère tous les ARTICLES liés à la CATEGORIE
				try (PreparedStatement statementArticles = connection.prepareStatement(SQL_SELECT_ARTICLES_BY_CATEGORIE)) {
					statementArticles.setInt(1, categorie.getNoCategorie());
					
					try (ResultSet resultArticles = statementArticles.executeQuery()) {
			
						while (resultArticles.next()) {
							Article article = new Article();
							
							Utilisateur user = new Utilisateur();
							try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
								stmtUser.setInt(1, resultArticles.getInt("no_utilisateur"));
								
								try (ResultSet resUser =stmtUser.executeQuery()){
									if (resUser.next()) {
										user.setNoUtilisateur(resUser.getInt("no_utilisateur"));
										user.setPseudo(resUser.getString("pseudo"));
										user.setNom(resUser.getString("nom"));
										user.setPrenom(resUser.getString("prenom"));
										user.setEmail(resUser.getString("email"));
										user.setTelephone(resUser.getString("telephone"));
										user.setRue(resUser.getString("rue"));
										user.setCodePostal(resUser.getString("code_postal"));
										user.setVille(resUser.getString("ville"));
										user.setMotDePasse(resUser.getString("mot_de_passe"));
										user.setCredit(resUser.getInt("credit"));
										user.setAdministrateur(resUser.getBoolean("administrateur"));
										
																			
									}
									
								}
								
							}
							
							
							Categorie cat = new Categorie();
							cat.setNoCategorie(resultArticles.getInt("no_categorie"));
							
							article.setNoArticle(resultArticles.getInt("no_article"));
							article.setNomArticle(resultArticles.getString("nom_article"));
							article.setDescription(resultArticles.getString("description"));
							article.setDateDebutEncheres(resultArticles.getDate("date_debut_encheres").toLocalDate());
							article.setDateFinEncheres(resultArticles.getDate("date_fin_encheres").toLocalDate());
							article.setPrixInitial(resultArticles.getInt("prix_initial"));
							article.setPrixVente(resultArticles.getInt("prix_vente"));
							article.setUtilisateur(user);
							article.setCategorie(cat);
							
							articles.add(article);
						}
						
					}
				}
				
				categorie.setArticles(articles);
				listeCategories.add(categorie);
			}

		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectAll Categorie", e);
		}

		return listeCategories;
	}

	@Override
	public Categorie selectById(int id) throws BusinessException {
		
		Categorie categorie = new Categorie();

		try (Connection connection = ConnectionProvider.getConnection();PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
			statement.setInt(1, id);
			
			try (ResultSet result = statement.executeQuery()) {
				if(result.next()) {
					categorie.setNoCategorie(result.getInt("no_categorie"));
					categorie.setLibelle(result.getString("libelle"));
					
					List<Article> articles = new ArrayList<Article>();
					
					// Récupère tous les ARTICLES liés à la CATEGORIE
					try (PreparedStatement statementArticles = connection.prepareStatement(SQL_SELECT_ARTICLES_BY_CATEGORIE)) {
						statementArticles.setInt(1, categorie.getNoCategorie());
						
						try (ResultSet resultArticles = statementArticles.executeQuery()) {
							
							while (resultArticles.next()) {
								Article article = new Article();
								
								article.setNoArticle(resultArticles.getInt("no_article"));
								article.setNomArticle(resultArticles.getString("nom_article"));
								article.setDescription(resultArticles.getString("description"));
								article.setDateDebutEncheres(resultArticles.getDate("date_debut_encheres").toLocalDate());
								article.setDateFinEncheres(resultArticles.getDate("date_fin_encheres").toLocalDate());
								article.setPrixInitial(resultArticles.getInt("prix_initial"));
								article.setPrixVente(resultArticles.getInt("prix_vente"));
								article.getUtilisateur().setNoUtilisateur(resultArticles.getInt("no_utilisateur"));
								article.getCategorie().setNoCategorie(resultArticles.getInt("no_categorie"));
								
								articles.add(article);
							}
							
						}
					}
					
					categorie.setArticles(articles);
					
				}
			}
			
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectById Categorie", e);
		}
		
		return categorie;
	}

}
