package org.projet.encheres.dal.utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.projet.encheres.bo.Utilisateur;
import org.projet.encheres.dal.ConnectionProvider;
import org.projet.encheres.exception.BusinessException;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	private static final String SQL_INSERT = "INSERT INTO Utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_UPDATE = "UPDATE Utilisateurs SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=?, administrateur=? WHERE no_utilisateur = ? ;";
	private static final String SQL_DELETE_UTILISATEUR = "DELETE FROM Utilisateurs WHERE no_utilisateur = ? ;";
	private static final String SQL_DELETE_ENCHERES = "DELETE FROM ENCHERES WHERE no_utilisateur = ? ;";
	private static final String SQL_DELETE_VENTES = "DELETE FROM ARTICLES_VENDUS WHERE no_utilisateur = ? ;";
	private static final String SQL_SELECT_ALL = "SELECT * FROM Utilisateurs";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM Utilisateurs WHERE no_utilisateur = ? ;";
	private static final String SQL_SELECT_PSEUDO_OR_EMAIL = "SELECT * FROM Utilisateurs WHERE pseudo = ? OR email = ? ;";

	@Override
	public void insert(Utilisateur user) throws BusinessException {

		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
			int i = 1;
			statement.setString(i++, user.getPseudo());
			statement.setString(i++, user.getNom());
			statement.setString(i++, user.getPrenom());
			statement.setString(i++, user.getEmail());
			statement.setString(i++, user.getTelephone());
			statement.setString(i++, user.getRue());
			statement.setString(i++, user.getCodePostal());
			statement.setString(i++, user.getVille());
			statement.setString(i++, user.getMotDePasse());
			statement.setInt(i++, user.getCredit());
			statement.setBoolean(i++, user.isAdministrateur());
			
			statement.executeUpdate();

			try (ResultSet result = statement.getGeneratedKeys()) {
				if (result.next()) {
					user.setNoUtilisateur(result.getInt(1));
				}
			}

		} catch (SQLException e) {
			throw new BusinessException("Erreur sur insert Utilisateur", e);
		}

	}

	@Override
	public void update(Utilisateur user) throws BusinessException {

		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
			int i = 1;
			statement.setString(i++, user.getPseudo());
			statement.setString(i++, user.getNom());
			statement.setString(i++, user.getPrenom());
			statement.setString(i++, user.getEmail());
			statement.setString(i++, user.getTelephone());
			statement.setString(i++, user.getRue());
			statement.setString(i++, user.getCodePostal());
			statement.setString(i++, user.getVille());
			statement.setString(i++, user.getMotDePasse());
			statement.setInt(i++, user.getCredit());
			statement.setBoolean(i++, user.isAdministrateur());

			statement.setInt(i++, user.getNoUtilisateur());

			statement.executeUpdate();

		} catch (SQLException e) {
			throw new BusinessException("Erreur sur update Utilisateur", e);
		}

	}

	@Override
	public void delete(Utilisateur user) throws BusinessException {

		try (Connection connection = ConnectionProvider.getConnection()) {

			connection.setAutoCommit(false);

			try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ENCHERES)) {

				statement.setInt(1, user.getNoUtilisateur());
				statement.executeUpdate();

				try (PreparedStatement statementVentes = connection.prepareStatement(SQL_DELETE_VENTES)) {
					statementVentes.setInt(1, user.getNoUtilisateur());
					statementVentes.executeUpdate();
				}

				try (PreparedStatement statementUtilisateur = connection.prepareStatement(SQL_DELETE_UTILISATEUR)) {
					statementUtilisateur.setInt(1, user.getNoUtilisateur());
					statementUtilisateur.executeUpdate();
				}

				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw new BusinessException("Erreur sur delete Utilisateur", e);
			} finally {
				connection.setAutoCommit(true);
			}

		} catch (SQLException e1) {
			throw new BusinessException("Erreur sur delete Utilisateur", e1);
		}

	}

	@Override
	public List<Utilisateur> selectAll() throws BusinessException {
		
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
				ResultSet rs = statement.executeQuery()){
			
			while(rs.next()) {
				Utilisateur user = new Utilisateur();
				user.setNoUtilisateur(rs.getInt("no_utilisateur"));
				user.setPseudo(rs.getString("pseudo"));
				user.setNom(rs.getString("nom"));
				user.setPrenom(rs.getString("prenom"));
				user.setEmail(rs.getString("email"));
				user.setTelephone(rs.getString("telephone"));
				user.setRue(rs.getString("rue"));
				user.setCodePostal(rs.getString("code_postal"));
				user.setVille(rs.getString("ville"));
				user.setMotDePasse(rs.getString("mot_de_passe"));
				user.setCredit(rs.getInt("credit"));
				user.setAdministrateur(rs.getBoolean("administrateur"));
				
				listeUtilisateurs.add(user);
				
			}
			
		}catch (SQLException e) {
			throw new BusinessException("Erreur sur selectAll Utilisateur", e);
		}
		return listeUtilisateurs;
	}

	@Override
	public Utilisateur selectByPseudoOrEmail(String name) throws BusinessException {
		
		Utilisateur user = new Utilisateur();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PSEUDO_OR_EMAIL)){
		
			statement.setString(1, name);
			statement.setString(2, name);
			
			try (ResultSet rs = statement.executeQuery()) {
				if(rs.next()) {
					
					user.setNoUtilisateur(rs.getInt("no_utilisateur"));
					user.setPseudo(rs.getString("pseudo"));
					user.setNom(rs.getString("nom"));
					user.setPrenom(rs.getString("prenom"));
					user.setEmail(rs.getString("email"));
					user.setTelephone(rs.getString("telephone"));
					user.setRue(rs.getString("rue"));
					user.setCodePostal(rs.getString("code_postal"));
					user.setVille(rs.getString("ville"));
					user.setMotDePasse(rs.getString("mot_de_passe"));
					user.setCredit(rs.getInt("credit"));
					user.setAdministrateur(rs.getBoolean("administrateur"));
				
				}
			} 
			
		}catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByPseudoOrEmail Utilisateur", e);
		}
		return user;
	}

	@Override
	public Utilisateur selectById(int id) throws BusinessException {


		Utilisateur user = new Utilisateur();
		
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)){

		
			statement.setInt(1, id);
			
			try (ResultSet rs = statement.executeQuery()) {
				if(rs.next()) {
					
					user.setNoUtilisateur(rs.getInt("no_utilisateur"));
					user.setPseudo(rs.getString("pseudo"));
					user.setNom(rs.getString("nom"));
					user.setPrenom(rs.getString("prenom"));
					user.setEmail(rs.getString("email"));
					user.setTelephone(rs.getString("telephone"));
					user.setRue(rs.getString("rue"));
					user.setCodePostal(rs.getString("code_postal"));
					user.setVille(rs.getString("ville"));
					user.setMotDePasse(rs.getString("mot_de_passe"));
					user.setCredit(rs.getInt("credit"));
					user.setAdministrateur(rs.getBoolean("administrateur"));
				
				}
			} 
			
		}catch (SQLException e) {
			throw new BusinessException("Erreur sur selectByPseudoOrEmail Utilisateur", e);
		}
		return user;

	}
	

}
