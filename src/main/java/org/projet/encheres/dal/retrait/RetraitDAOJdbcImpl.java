package org.projet.encheres.dal.retrait;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.projet.encheres.bo.Article;
import org.projet.encheres.bo.Retrait;
import org.projet.encheres.dal.ConnectionProvider;
import org.projet.encheres.exception.BusinessException;


public class RetraitDAOJdbcImpl implements RetraitDAO{
	
	/*
	 * public void insert(Retrait retrait) throws DALException;

		public void update(Retrait retrait) throws DALException;

		public void delete(Retrait retrait) throws DALException;
		
		public Retrait selectByArticle(Article article) throws DALException;
	 *  
	 *  */
	
	private static final String SQL_INSERT = "INSERT INTO Retraits (no_article, rue, code_postal, ville) VALUES (?, ?, ?, ?);";
	private static final String SQL_UPDATE = "UPDATE Retraits SET no_article=?, rue=?, code_postal=?, ville=? WHERE no_article = ?;";
	private static final String SQL_DELETE_RETRAIT = "DELETE FROM Retraits WHERE no_article = ? ;";
	private static final String SQL_SELECT_BY_ARTICLE = "SELECT * FROM Retraits WHERE no_article = ? ;";
	
	@Override
	public void insert(Retrait retrait) throws BusinessException {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
			
			statement.setInt(1, retrait.getArticle().getNoArticle());
			statement.setString(2, retrait.getRue());
			statement.setString(3, retrait.getCodePostal());
			statement.setString(4, retrait.getVille());
			
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new BusinessException("Erreur sur insert Retrait", e);
		}

	}

	@Override
	public void update(Retrait retrait) throws BusinessException {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)){
			
			statement.setInt(1, retrait.getArticle().getNoArticle());
			statement.setString(2, retrait.getRue());
			statement.setString(3, retrait.getCodePostal());
			statement.setString(4, retrait.getVille());
			
			statement.setInt(5, retrait.getArticle().getNoArticle());
			
			statement.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur update Article", e);
		}
	
	}

	@Override
	public void delete(Retrait retrait) throws BusinessException {
		
		try (Connection connection = ConnectionProvider.getConnection()){
			
			connection.setAutoCommit(false);
			
			try (PreparedStatement statementRetrait = connection.prepareStatement(SQL_DELETE_RETRAIT)){
				
				statementRetrait.setInt(1, retrait.getArticle().getNoArticle());
				statementRetrait.executeUpdate();
				
				connection.commit();	
				
				
			} catch (SQLException e) {
				connection.rollback();
				throw new BusinessException("Erreur sur delete Retrait - Rollback activé", e);
			}finally {
				connection.setAutoCommit(true);
			}
			
		} catch (SQLException ex) {
			throw new BusinessException("Erreur sur delete Retrait - problème de connexion probable", ex);
		}

	}

	@Override
	public Retrait selectByArticle(Article article) throws BusinessException {
		
		Retrait retrait = new Retrait();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ARTICLE)){
			
			statement.setInt(1, article.getNoArticle());
			
			try (ResultSet rs = statement.executeQuery()){
				if(rs.next()) {
					
					retrait.getArticle().setNoArticle(rs.getInt("no_article"));
					retrait.setRue(rs.getString("rue"));
					retrait.setRue(rs.getString("code_postal"));
					retrait.setRue(rs.getString("ville"));
					
				}
			}

			
		} catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByArticle", e);
		}
		
		
		return retrait;
	}

}
