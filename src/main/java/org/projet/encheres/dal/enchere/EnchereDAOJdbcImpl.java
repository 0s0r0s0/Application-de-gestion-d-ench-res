package org.projet.encheres.dal.enchere;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Categorie;
import org.projet.encheres.bo.Enchere;
import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.dal.ConnectionProvider;
import org.projet.encheres.exception.BusinessException;

public class EnchereDAOJdbcImpl implements EnchereDAO {
	
	private static final String SQL_INSERT = "INSERT INTO Encheres ( no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?);";

	
	private static final String SQL_SELECT_UTILISATEUR_BY_ID = "SELECT * FROM Utilisateurs WHERE no_utilisateur = ? ;";
	private static final String SQL_SELECT_ARTICLE_BY_ID = "SELECT * FROM Articles_vendus WHERE no_article =?;";
	private static final String SQL_SELECT_BY_ARTICLE_MONTANT_HAUT ="SELECT TOP 1 * FROM ENCHERES WHERE no_article = ? ORDER BY montant_enchere DESC;";
	private static final String SQL_SELECT_ARTICLE_VENTE_TERMINEE = "SELECT no_article FROM Articles_vendus WHERE  date_fin_encheres < CURRENT_TIMESTAMP AND no_utilisateur != ?;";
	private static final String SQL_UPDATE = "UPDATE Encheres SET date_enchere=?, montant_enchere=? WHERE no_utilisateur=? AND no_article=?;";
	private static final String SQL_SELECT_BY_UTILISATEUR = "SELECT * FROM Encheres WHERE no_utilisateur = ?;";
	private static final String SQL_SELECT_BY_ARTICLE = "SELECT * FROM Encheres WHERE no_article = ?;";
	private static final String SQL_SELECT_BY_ARTICLE_AND_UTILISATEUR = "SELECT * FROM Encheres WHERE no_article = ? AND no_utilisateur=?;";
	private static final String SQL_SELECT_BY_ARTICLE_ENCHERE_MAX = "SELECT TOP 1 * FROM ENCHERES WHERE no_article = ? ORDER BY montant_enchere DESC;";
	private static final String SQL_SELECT_BY_ARTICLE_ID= "SELECT no_article FROM Encheres WHERE no_utilisateur = ?;";
	private static final String SQL_SELECT_ARTICLE_VENTE_EN_COURS = "SELECT * FROM Articles_vendus WHERE date_debut_encheres <= CURRENT_TIMESTAMP AND date_fin_encheres >= CURRENT_TIMESTAMP AND no_article = ?;";


	public void insert(Enchere enchere) throws BusinessException {
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
			
			statement.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
			statement.setInt(2, enchere.getArticle().getNoArticle());
			statement.setTimestamp(3,Timestamp.valueOf(enchere.getDateEnchere()));
			statement.setInt(4, enchere.getMontantEnchere());
			
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new BusinessException("Erreur sur insert Enchere", e);
		}

		
	}
	
	public void update(Enchere enchere) throws BusinessException {
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
			
			statement.setTimestamp(1,Timestamp.valueOf(enchere.getDateEnchere()));
			statement.setInt(2, enchere.getMontantEnchere());
			statement.setInt(3, enchere.getUtilisateur().getNoUtilisateur());
			statement.setInt(4, enchere.getArticle().getNoArticle());
			
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new BusinessException("Erreur sur update Enchere", e);
		}
	
	}

	public List<Enchere> selectByUtilisateur(Utilisateur utilisateur) throws BusinessException {

		List<Enchere> listEncheres = new ArrayList<Enchere>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_UTILISATEUR)){
		
			statement.setInt(1, utilisateur.getNoUtilisateur());
			
			try (ResultSet rs = statement.executeQuery()) {
				while(rs.next()) {
					Enchere enchere = new Enchere();
					Utilisateur user = new Utilisateur();
					try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
						stmtUser.setInt(1, rs.getInt("no_utilisateur"));
						
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
					Article article = new Article();
					try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_ARTICLE_BY_ID)){
						stmtCat.setInt(1, rs.getInt("no_article"));
						
						try (ResultSet resArticle =stmtCat.executeQuery()){
							while (resArticle.next()) {
								article.setNoArticle(resArticle.getInt("no_article"));
								article.setNomArticle(resArticle.getString("nom_article"));
								article.setDescription(resArticle.getString("description"));
								article.setDateDebutEncheres(resArticle.getDate("date_debut_encheres").toLocalDate());
								article.setDateFinEncheres(resArticle.getDate("date_fin_encheres").toLocalDate());
								article.setPrixInitial(resArticle.getInt("prix_initial"));
								article.setPrixVente(resArticle.getInt("prix_vente"));
							}
						}
					}
					

					enchere.setUtilisateur(user);
					enchere.setArticle(article);
					enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime());
					enchere.setMontantEnchere(rs.getInt("montant_enchere"));
					
					listEncheres.add(enchere);
					                                                                                                                                                  
				}
			} 
			
		}catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByUtilisateur Enchere", e);
		}
		return listEncheres ;
	}


	public List<Enchere> selectByArticle(Article article) throws BusinessException {
		
		List<Enchere> listEncheres = new ArrayList<Enchere>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ARTICLE)){
		
			statement.setInt(1, article.getNoArticle());
			
			try (ResultSet rs = statement.executeQuery()) {
				if(rs.next()) {
					Enchere enchere = new Enchere();
					
					enchere.getUtilisateur().setNoUtilisateur(rs.getInt("no_utilisateur"));
					enchere.getArticle().setNoArticle(rs.getInt("no_article"));
					enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime());
					enchere.setMontantEnchere(rs.getInt("montant_enchere"));
					
					listEncheres.add(enchere);
					                                                                                                                                                  
				}
			} 
			
		}catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByArticle Enchere", e);
		}
		return listEncheres ;
	}
	
	public Enchere selectByArticleAndUtilisateur(Article article, Utilisateur utilisateur) throws BusinessException {
		Enchere enchere = new Enchere();
		
		try (Connection connection = ConnectionProvider.getConnection();PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ARTICLE_AND_UTILISATEUR)) {
			statement.setInt(1, article.getNoArticle());
			statement.setInt(2, utilisateur.getNoUtilisateur());
			
			
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					enchere.setArticle(article);
					enchere.setUtilisateur(utilisateur);
					enchere.setDateEnchere(result.getTimestamp("date_enchere").toLocalDateTime());
					enchere.setMontantEnchere(result.getInt("montant_enchere"));
					
				}
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectMaxByArticle Enchere", e);
		}
		
		return enchere;
	} 
	
	public Enchere selectMaxByArticle(Article article) throws BusinessException {
		Enchere enchere = new Enchere();
		
		try (Connection connection = ConnectionProvider.getConnection();PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ARTICLE_ENCHERE_MAX)) {
			statement.setInt(1, article.getNoArticle());
			
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					enchere.setArticle(article);
					Utilisateur utilisateur = new Utilisateur();
					utilisateur.setNoUtilisateur(result.getInt("no_utilisateur"));
					enchere.setUtilisateur(utilisateur);
					enchere.setDateEnchere(result.getTimestamp("date_enchere").toLocalDateTime());
					enchere.setMontantEnchere(result.getInt("montant_enchere"));
				}
			}
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectMaxByArticle Enchere", e);
		}
		
		return enchere;
	}

	@Override
	public List<Enchere> selectEnchereGagne(Utilisateur utilisateur) throws BusinessException {
		List<Enchere> listEncheres = new ArrayList<Enchere>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ARTICLE_VENTE_TERMINEE)){
		
			statement.setInt(1, utilisateur.getNoUtilisateur());
			
			try (ResultSet rs = statement.executeQuery()){
				while (rs.next()) {
					try (PreparedStatement stmt = connection.prepareStatement(SQL_SELECT_BY_ARTICLE_MONTANT_HAUT)){
						stmt.setInt(1, rs.getInt("no_article"));
						
						try(ResultSet rs2 = stmt.executeQuery()){
							while(rs2.next()) {
								if(rs2.getInt("no_utilisateur") == utilisateur.getNoUtilisateur()) {
									
								
								Enchere enchere = new Enchere();
								
								Utilisateur user = new Utilisateur();
								try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
									stmtUser.setInt(1, rs2.getInt("no_utilisateur"));
									
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
								
								Article article = new Article();
								try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_ARTICLE_BY_ID)){
									stmtCat.setInt(1, rs2.getInt("no_article"));
									
									try (ResultSet resArticle =stmtCat.executeQuery()){
										while (resArticle.next()) {
											article.setNoArticle(resArticle.getInt("no_article"));
											article.setNomArticle(resArticle.getString("nom_article"));
											article.setDescription(resArticle.getString("description"));
											article.setDateDebutEncheres(resArticle.getDate("date_debut_encheres").toLocalDate());
											article.setDateFinEncheres(resArticle.getDate("date_fin_encheres").toLocalDate());
											article.setPrixInitial(resArticle.getInt("prix_initial"));
											article.setPrixVente(resArticle.getInt("prix_vente"));
											
											Utilisateur userArticle = new Utilisateur();
											try (PreparedStatement stmtArtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
												stmtArtUser.setInt(1, resArticle.getInt("no_utilisateur"));
												
												try (ResultSet resArtUser =stmtArtUser.executeQuery()){
													while (resArtUser.next()) {
														userArticle.setNoUtilisateur(resArtUser.getInt("no_utilisateur"));
														userArticle.setPseudo(resArtUser.getString("pseudo"));
														userArticle.setNom(resArtUser.getString("nom"));
														userArticle.setPrenom(resArtUser.getString("prenom"));
														userArticle.setEmail(resArtUser.getString("email"));
														userArticle.setTelephone(resArtUser.getString("telephone"));
														userArticle.setRue(resArtUser.getString("rue"));
														userArticle.setCodePostal(resArtUser.getString("code_postal"));
														userArticle.setVille(resArtUser.getString("ville"));
														userArticle.setMotDePasse(resArtUser.getString("mot_de_passe"));
														userArticle.setCredit(resArtUser.getInt("credit"));
														userArticle.setAdministrateur(resArtUser.getBoolean("administrateur"));

													}
												}
											}
											
											article.setUtilisateur(userArticle);
										}
									}
								}
								
								enchere.setUtilisateur(user);
								enchere.setArticle(article);
								enchere.setDateEnchere(rs2.getTimestamp("date_enchere").toLocalDateTime());
								enchere.setMontantEnchere(rs2.getInt("montant_enchere"));
								
								listEncheres.add(enchere);
								
							}
							}
						}
						
					}
				}
				
			}
			
			
			
		}catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByArticle Enchere", e);
		}
		return listEncheres ;
	}

	@Override
	public List<Enchere> selectByUtilisateurVenteEnCours(Utilisateur utilisateur) throws BusinessException {
		List<Enchere> listEncheres = new ArrayList<Enchere>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ARTICLE_ID)){
		
			statement.setInt(1, utilisateur.getNoUtilisateur());
			
			try (ResultSet rs = statement.executeQuery()){
				while (rs.next()) {
					try (PreparedStatement stmt = connection.prepareStatement(SQL_SELECT_ARTICLE_VENTE_EN_COURS)){
						stmt.setInt(1, rs.getInt("no_article"));
						
						try(ResultSet rs2 = stmt.executeQuery()){
							while(rs2.next()) {
								if(rs2.getInt("no_utilisateur") == utilisateur.getNoUtilisateur()) {
									
								
								Enchere enchere = new Enchere();
								
								Utilisateur user = new Utilisateur();
								try (PreparedStatement stmtUser = connection.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID)){
									stmtUser.setInt(1, rs2.getInt("no_utilisateur"));
									
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
								
								Article article = new Article();
								try (PreparedStatement stmtCat = connection.prepareStatement(SQL_SELECT_ARTICLE_BY_ID)){
									stmtCat.setInt(1, rs2.getInt("no_article"));
									
									try (ResultSet resArticle =stmtCat.executeQuery()){
										while (resArticle.next()) {
											article.setNoArticle(resArticle.getInt("no_article"));
											article.setNomArticle(resArticle.getString("nom_article"));
											article.setDescription(resArticle.getString("description"));
											article.setDateDebutEncheres(resArticle.getDate("date_debut_encheres").toLocalDate());
											article.setDateFinEncheres(resArticle.getDate("date_fin_encheres").toLocalDate());
											article.setPrixInitial(resArticle.getInt("prix_initial"));
											article.setPrixVente(resArticle.getInt("prix_vente"));
										}
									}
								}
								
								enchere.setUtilisateur(user);
								enchere.setArticle(article);
								enchere.setDateEnchere(rs2.getTimestamp("date_enchere").toLocalDateTime());
								enchere.setMontantEnchere(rs2.getInt("montant_enchere"));
								
								listEncheres.add(enchere);
								
							}
							}
						}
						
					}
				}
				
			}
			
			
			
		}catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByArticle Enchere", e);
		}
		return listEncheres ;
	}
	
	
	
	

}
