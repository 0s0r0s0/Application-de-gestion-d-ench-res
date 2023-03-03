package org.projet.encheres.dal.article;

import java.sql.Connection;
import java.sql.Date;
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

public class ArticleDAOJdbcImpl implements ArticleDAO{
	
	private static final String SQL_INSERT = "INSERT INTO Articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?);";
	private static final String SQL_UPDATE = "UPDATE Articles_vendus SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, no_utilisateur=?, no_categorie=? WHERE no_article = ?;";
	private static final String SQL_DELETE_ARTICLE = "DELETE FROM Articles_vendus WHERE no_article =?;";
	private static final String SQL_DELETE_ENCHERE = "DELETE FROM ENCHERES WHERE no_article = ? ;";
	private static final String SQL_DELETE_RETRAIT = "DELETE FROM RETRAITS WHERE no_article = ? ;";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Articles_vendus;";
	private static final String SQL_SELECT_UTILISATEUR_BY_ID = "SELECT * FROM Utilisateurs WHERE no_utilisateur = ? ;";
	private static final String SQL_SELECT_CATEGORIE_BY_ID ="SELECT * FROM Categories WHERE no_categorie = ? ;";
	private static final String SQL_SELECT_BY_ID ="SELECT * FROM Articles_vendus WHERE no_article = ?;";
	private static final String SQL_SELECT_ARTICLE_BY_DATE ="SELECT * FROM Articles_vendus WHERE no_article = ? and date_fin_encheres < CURRENT_TIMESTAMP;";
	private static final String SQL_SELECT_BY_CATEGORIE = "SELECT * FROM Articles_vendus WHERE no_categorie = ? and date_debut_encheres <= CURRENT_TIMESTAMP AND date_fin_encheres >= CURRENT_TIMESTAMP;";
	private static final String SQL_SELECT_BY_UTILISATEUR = "SELECT * FROM Articles_vendus WHERE no_utilisateur = ? and date_debut_encheres <= CURRENT_TIMESTAMP AND date_fin_encheres >= CURRENT_TIMESTAMP;";
	private static final String SQL_SELECT_EN_VENTE = "SELECT * FROM Articles_vendus WHERE  date_debut_encheres <= CURRENT_TIMESTAMP AND date_fin_encheres >= CURRENT_TIMESTAMP;";
	private static final String SQL_SELECT_BY_FIRST_LETTER = "select * from ARTICLES_VENDUS where nom_article like ? + '%' and date_debut_encheres <= CURRENT_TIMESTAMP AND date_fin_encheres >= CURRENT_TIMESTAMP;";
	private static final String SQL_SELECT_BY_FIRST_LETTER_AND_CATEGORIE = "select * from ARTICLES_VENDUS where nom_article like  ? + '%' and no_categorie=? and date_debut_encheres <= CURRENT_TIMESTAMP AND date_fin_encheres >= CURRENT_TIMESTAMP;";
	private static final String SQL_SELECT_VENTE_NON_DEBUTE_BY_UTILISATEUR = "SELECT * FROM Articles_vendus WHERE no_utilisateur = ? AND date_debut_encheres > CURRENT_TIMESTAMP;";
	private static final String SQL_SELECT_VENTE_TERMINEE_BY_UTILISATEUR = "SELECT * FROM Articles_vendus WHERE no_utilisateur = ? AND date_fin_encheres < CURRENT_TIMESTAMP;";
	
	
	

	@Override
	public void insert(Article article) throws BusinessException {
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)){
			int i = 1;
			statement.setString(i++, article.getNomArticle());
			statement.setString(i++, article.getDescription());
			statement.setDate(i++, Date.valueOf(article.getDateDebutEncheres()));
			statement.setDate(i++, Date.valueOf(article.getDateFinEncheres()));
			statement.setInt(i++, article.getPrixInitial());
			statement.setInt(i++, article.getUtilisateur().getNoUtilisateur());
			statement.setInt(i++, article.getCategorie().getNoCategorie());
			
			statement.executeUpdate();
			
			try (ResultSet result = statement.getGeneratedKeys()){
				if (result.next()) {
					article.setNoArticle(result.getInt(1));
				}
				
			}
			
		}catch (SQLException e) {
			throw new BusinessException("Erreur sur insert Article", e);
		}
		
	}
	

	@Override
	public void update(Article article) throws BusinessException {
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)){
			
			int i = 1;
			statement.setString(i++, article.getNomArticle());
			statement.setString(i++, article.getDescription());
			statement.setDate(i++, Date.valueOf(article.getDateDebutEncheres()));
			statement.setDate(i++, Date.valueOf(article.getDateFinEncheres()));
			statement.setInt(i++, article.getPrixInitial());
			statement.setInt(i++, article.getUtilisateur().getNoUtilisateur());
			statement.setInt(i++, article.getCategorie().getNoCategorie());
			
			statement.setInt(i++, article.getNoArticle());
			
			statement.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur update Article", e);
		}

		
	}

	@Override
	public void delete(Article article) throws BusinessException {
		
		try (Connection connection = ConnectionProvider.getConnection()){
			
			connection.setAutoCommit(false);
			
			try (PreparedStatement statementRetrait = connection.prepareStatement(SQL_DELETE_RETRAIT);
					PreparedStatement statementEnchere = connection.prepareStatement(SQL_DELETE_ENCHERE);
					PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ARTICLE)){
				
				statementRetrait.setInt(1, article.getNoArticle());
				statementRetrait.executeUpdate();
				
				statementEnchere.setInt(1, article.getNoArticle());
				statementEnchere.executeUpdate();
				
			
				statement.setInt(1, article.getNoArticle());
				statement.executeUpdate();
					
				
				connection.commit();	
				
				
			} catch (SQLException e) {
				connection.rollback();
				throw new BusinessException("Erreur sur delete Article", e);
			}finally {
				connection.setAutoCommit(true);
			}
			
		} catch (SQLException ex) {
			throw new BusinessException("Erreur sur delete Article", ex);
		}
	}

	@Override
	public List<Article> selectAll() throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
				ResultSet resultat = statement.executeQuery()){
			
			while(resultat.next()) {
				Article article = new Article();
				Utilisateur user = new Utilisateur();
				try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
					stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
					
					try (ResultSet resUser =stmtUser.executeQuery()){
						while (resUser.next()) {
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
				try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
					stmtCat.setInt(1, resultat.getInt("no_categorie"));
					
					try (ResultSet resCat =stmtCat.executeQuery()){
						while (resCat.next()) {
							cat.setNoCategorie(resCat.getInt("no_categorie"));
							cat.setLibelle(resCat.getString("libelle"));			
						}
					}
				}
				
				article.setNoArticle(resultat.getInt("no_article"));
				article.setNomArticle(resultat.getString("nom_article"));
				article.setDescription(resultat.getString("description"));
				article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
				article.setPrixInitial(resultat.getInt("prix_initial"));
				article.setPrixVente(resultat.getInt("prix_vente"));
				article.setUtilisateur(user);
				article.setCategorie(cat);
				
				listeArticles.add(article);
				
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectAll Article", e);
		}
		
		return listeArticles;
	}

	@Override
	public Article selectById(int id) throws BusinessException {

		Article article = new Article();
		Utilisateur user = new Utilisateur();
		Categorie cat = new Categorie();
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)){
			statement.setInt(1, id);
			
			try (ResultSet resultat = statement.executeQuery()){
				if(resultat.next()) {
					
					try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
						stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
						
						try (ResultSet resUser =stmtUser.executeQuery()){
							while (resUser.next()) {
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
						
						try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
							stmtCat.setInt(1, resultat.getInt("no_categorie"));
							
							try (ResultSet resCat =stmtCat.executeQuery()){
								while (resCat.next()) {
									cat.setNoCategorie(resCat.getInt("no_categorie"));
									cat.setLibelle(resCat.getString("libelle"));
											
								}
								
							}
					
					article.setNoArticle(resultat.getInt("no_article"));
					article.setNomArticle(resultat.getString("nom_article"));
					article.setDescription(resultat.getString("description"));
					article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
					article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
					article.setPrixInitial(resultat.getInt("prix_initial"));
					article.setPrixVente(resultat.getInt("prix_vente"));
					article.setUtilisateur(user);
					article.setCategorie(cat);
				}
			}
			
		}
			}catch (SQLException e) {
			throw new BusinessException("Erreur sur selectById Article", e);
		}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return article;
	}

	@Override
	public List<Article> selectByCategorie(int id) throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_CATEGORIE)){
			statement.setInt(1, id);
			
			try (ResultSet resultat = statement.executeQuery()){
				while(resultat.next()) {
					Article article = new Article();
					Utilisateur user = new Utilisateur();
					try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
						stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
						
						try (ResultSet resUser =stmtUser.executeQuery()){
							while (resUser.next()) {
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
					try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
						stmtCat.setInt(1, resultat.getInt("no_categorie"));
						
						try (ResultSet resCat =stmtCat.executeQuery()){
							while (resCat.next()) {
								cat.setNoCategorie(resCat.getInt("no_categorie"));
								cat.setLibelle(resCat.getString("libelle"));
										
							}
							
						}
					}	
					
					article.setNoArticle(resultat.getInt("no_article"));
					article.setNomArticle(resultat.getString("nom_article"));
					article.setDescription(resultat.getString("description"));
					article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
					article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
					article.setPrixInitial(resultat.getInt("prix_initial"));
					article.setPrixVente(resultat.getInt("prix_vente"));
					article.setUtilisateur(user);
					article.setCategorie(cat);
					
					listeArticles.add(article);
				}
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByCategorie Article", e);
		}
		return listeArticles;
	}

	@Override
	public List<Article> selectByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_UTILISATEUR)){
			statement.setInt(1, utilisateur.getNoUtilisateur());
			
			try (ResultSet resultat = statement.executeQuery()){
				if(resultat.next()) {
					Article article = new Article();
					Utilisateur user = new Utilisateur();
					try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
						stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
						
						try (ResultSet resUser =stmtUser.executeQuery()){
							while (resUser.next()) {
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
					try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
						stmtCat.setInt(1, resultat.getInt("no_categorie"));
						
						try (ResultSet resCat =stmtCat.executeQuery()){
							while (resCat.next()) {
								cat.setNoCategorie(resCat.getInt("no_categorie"));
								cat.setLibelle(resCat.getString("libelle"));			
							}
						}
					}
					
					article.setNoArticle(resultat.getInt("no_article"));
					article.setNomArticle(resultat.getString("nom_article"));
					article.setDescription(resultat.getString("description"));
					article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
					article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
					article.setPrixInitial(resultat.getInt("prix_initial"));
					article.setPrixVente(resultat.getInt("prix_vente"));
					article.setUtilisateur(user);
					article.setCategorie(cat);
					
					listeArticles.add(article);
				}
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByUtilisateur Article", e);
		}
		return listeArticles;
	}

	@Override
	public List<Article> selectAllByDate() throws BusinessException {
		
		List<Article> listeArticles = new ArrayList<Article>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EN_VENTE);
				ResultSet resultat = statement.executeQuery()){
			
			while(resultat.next()) {
				Article article = new Article();
				Utilisateur user = new Utilisateur();
				try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
					stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
					
					try (ResultSet resUser =stmtUser.executeQuery()){
						while (resUser.next()) {
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
				try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
					stmtCat.setInt(1, resultat.getInt("no_categorie"));
					
					try (ResultSet resCat =stmtCat.executeQuery()){
						while (resCat.next()) {
							cat.setNoCategorie(resCat.getInt("no_categorie"));
							cat.setLibelle(resCat.getString("libelle"));			
						}
					}
				}
				
				article.setNoArticle(resultat.getInt("no_article"));
				article.setNomArticle(resultat.getString("nom_article"));
				article.setDescription(resultat.getString("description"));
				article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
				article.setPrixInitial(resultat.getInt("prix_initial"));
				article.setPrixVente(resultat.getInt("prix_vente"));
				article.setUtilisateur(user);
				article.setCategorie(cat);
				
				listeArticles.add(article);
				
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectArticleEnVente Article", e);
		}
		
		return listeArticles;
	}

	@Override
	public List<Article> selectAllByFirstLetter(String recherche) throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_FIRST_LETTER)){
			statement.setString(1, recherche);
			
			try (ResultSet resultat = statement.executeQuery()){
				while(resultat.next()) {
					Article article = new Article();
					Utilisateur user = new Utilisateur();
					try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
						stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
						
						try (ResultSet resUser =stmtUser.executeQuery()){
							while (resUser.next()) {
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
					try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
						stmtCat.setInt(1, resultat.getInt("no_categorie"));
						
						try (ResultSet resCat =stmtCat.executeQuery()){
							while (resCat.next()) {
								cat.setNoCategorie(resCat.getInt("no_categorie"));
								cat.setLibelle(resCat.getString("libelle"));			
							}
						}
					}
					
					article.setNoArticle(resultat.getInt("no_article"));
					article.setNomArticle(resultat.getString("nom_article"));
					article.setDescription(resultat.getString("description"));
					article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
					article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
					article.setPrixInitial(resultat.getInt("prix_initial"));
					article.setPrixVente(resultat.getInt("prix_vente"));
					article.setUtilisateur(user);
					article.setCategorie(cat);
					
					listeArticles.add(article);
				}
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByUtilisateur Article", e);
		}
		return listeArticles;
	}

	@Override
	public List<Article> selectAllByFirstLetterAndCategorie(String recherche, int id) throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_FIRST_LETTER_AND_CATEGORIE)){
			statement.setString(1, recherche);
			statement.setInt(2,  id);
			
			
			try (ResultSet resultat = statement.executeQuery()){
				while(resultat.next()) {
					Article article = new Article();
					Utilisateur user = new Utilisateur();
					try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
						stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
						
						try (ResultSet resUser =stmtUser.executeQuery()){
							while (resUser.next()) {
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
					try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
						stmtCat.setInt(1, resultat.getInt("no_categorie"));
						
						try (ResultSet resCat =stmtCat.executeQuery()){
							while (resCat.next()) {
								cat.setNoCategorie(resCat.getInt("no_categorie"));
								cat.setLibelle(resCat.getString("libelle"));			
							}
						}
					}
					
					article.setNoArticle(resultat.getInt("no_article"));
					article.setNomArticle(resultat.getString("nom_article"));
					article.setDescription(resultat.getString("description"));
					article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
					article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
					article.setPrixInitial(resultat.getInt("prix_initial"));
					article.setPrixVente(resultat.getInt("prix_vente"));
					article.setUtilisateur(user);
					article.setCategorie(cat);
					
					listeArticles.add(article);
				}
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByUtilisateur Article", e);
		}
		return listeArticles;
	}


	@Override
	public List<Article> selectVenteNonDebuteeByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_VENTE_NON_DEBUTE_BY_UTILISATEUR)){
			statement.setInt(1, utilisateur.getNoUtilisateur());
			
			try (ResultSet resultat = statement.executeQuery()){
				if(resultat.next()) {
					Article article = new Article();
					Utilisateur user = new Utilisateur();
					try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
						stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
						
						try (ResultSet resUser =stmtUser.executeQuery()){
							while (resUser.next()) {
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
					try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
						stmtCat.setInt(1, resultat.getInt("no_categorie"));
						
						try (ResultSet resCat =stmtCat.executeQuery()){
							while (resCat.next()) {
								cat.setNoCategorie(resCat.getInt("no_categorie"));
								cat.setLibelle(resCat.getString("libelle"));			
							}
						}
					}
					
					article.setNoArticle(resultat.getInt("no_article"));
					article.setNomArticle(resultat.getString("nom_article"));
					article.setDescription(resultat.getString("description"));
					article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
					article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
					article.setPrixInitial(resultat.getInt("prix_initial"));
					article.setPrixVente(resultat.getInt("prix_vente"));
					article.setUtilisateur(user);
					article.setCategorie(cat);
					
					listeArticles.add(article);
				}
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByUtilisateur Article", e);
		}
		return listeArticles;
	}


	@Override
	public List<Article> selectVenteTermineeByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		List<Article> listeArticles = new ArrayList<Article>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_VENTE_TERMINEE_BY_UTILISATEUR)){
			statement.setInt(1, utilisateur.getNoUtilisateur());
			
			try (ResultSet resultat = statement.executeQuery()){
				while(resultat.next()) {
					Article article = new Article();
					Utilisateur user = new Utilisateur();
					try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
						stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
						
						try (ResultSet resUser =stmtUser.executeQuery()){
							while (resUser.next()) {
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
					try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
						stmtCat.setInt(1, resultat.getInt("no_categorie"));
						
						try (ResultSet resCat =stmtCat.executeQuery()){
							while (resCat.next()) {
								cat.setNoCategorie(resCat.getInt("no_categorie"));
								cat.setLibelle(resCat.getString("libelle"));			
							}
						}
					}
					
					article.setNoArticle(resultat.getInt("no_article"));
					article.setNomArticle(resultat.getString("nom_article"));
					article.setDescription(resultat.getString("description"));
					article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
					article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
					article.setPrixInitial(resultat.getInt("prix_initial"));
					article.setPrixVente(resultat.getInt("prix_vente"));
					article.setUtilisateur(user);
					article.setCategorie(cat);
					
					listeArticles.add(article);
				}
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByUtilisateur Article", e);
		}
		return listeArticles;
	}


	@Override
	public Article selectArticleByDate(int id) throws BusinessException {
		Article article = new Article();
		Utilisateur user = new Utilisateur();
		Categorie cat = new Categorie();
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ARTICLE_BY_DATE)){
			statement.setInt(1, id);
			
			try (ResultSet resultat = statement.executeQuery()){
				if(resultat.next()) {
					
					try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
						stmtUser.setInt(1, resultat.getInt("no_utilisateur"));
						
						try (ResultSet resUser =stmtUser.executeQuery()){
							while (resUser.next()) {
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
						
						try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_CATEGORIE_BY_ID)){
							stmtCat.setInt(1, resultat.getInt("no_categorie"));
							
							try (ResultSet resCat =stmtCat.executeQuery()){
								while (resCat.next()) {
									cat.setNoCategorie(resCat.getInt("no_categorie"));
									cat.setLibelle(resCat.getString("libelle"));
											
								}
								
							}
					
					article.setNoArticle(resultat.getInt("no_article"));
					article.setNomArticle(resultat.getString("nom_article"));
					article.setDescription(resultat.getString("description"));
					article.setDateDebutEncheres(resultat.getDate("date_debut_encheres").toLocalDate());
					article.setDateFinEncheres(resultat.getDate("date_fin_encheres").toLocalDate());
					article.setPrixInitial(resultat.getInt("prix_initial"));
					article.setPrixVente(resultat.getInt("prix_vente"));
					article.setUtilisateur(user);
					article.setCategorie(cat);
				}
			}
			
		}
			}catch (SQLException e) {
			throw new BusinessException("Erreur sur selectById Article", e);
		}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return article;
	}
	
	
	
	
	
	

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
