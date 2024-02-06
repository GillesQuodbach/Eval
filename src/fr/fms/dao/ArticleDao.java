/**
 * Composant d'accès aux données de la table T_Articles dans la base de données Shop
 * @author El babili - 2023
 * 
 */

package fr.fms.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import fr.fms.entities.Article;

public class ArticleDao implements Dao<Article> {


	public ArticleDao() {
	}

	/**
	 * Méthode qui crée un article en base sans prendre en compte l'id (généré automatiquement)
	 * @param Article à ajouter dans la table des articles
	 */
	@Override
	public boolean create(Article obj) {
		String str = "INSERT INTO T_Articles (Name, Description, Duration, Format, Price, IdCategory) VALUES (?,?,?,?,?,?);";	
		try (PreparedStatement ps = connection.prepareStatement(str)){
			ps.setString(1, obj.getName());
			ps.setString(1, obj.getDescription());
			ps.setInt(4, obj.getDuration());
			ps.setString(2, obj.getFormat());
			ps.setDouble(3, obj.getPrice());	
			ps.setInt(4, obj.getCategory());
			if( ps.executeUpdate() == 1)	return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'un article " + e.getMessage());
		} 	
		return false;
	}

	/**
	 * Méthode qui renvoi toutes les infos d'un article à partir de son id s'il existe dans la table T_Articles
	 * @param id de l'article 
	 * @return article si trouvé, null sinon
	 */

	@Override
	public Article read(int id) {
		try (Statement statement = connection.createStatement()){
			String str = "SELECT * FROM T_Articles where IdArticle=" + id + ";";									
			ResultSet rs = statement.executeQuery(str);
			if(rs.next()) return new Article(rs.getInt(1) , rs.getString(2) , rs.getString(3) , rs.getInt(4), rs.getString(5), rs.getDouble(6), rs.getInt(7));
		} catch (SQLException e) {
			logger.severe("pb sql sur la lecture d'un article " + e.getMessage());
		} 	
		return null;
	}

	/**
	 * Méthode qui met à jour un article s'il existe (à partir de son id) dans la table T_Articles
	 * @param l'article concerné
	 * @return vrai si trouvé, faux sinon
	 */

	@Override
	public boolean update(Article obj) {
		String str = "UPDATE T_Articles set Name=? , Description=? , Duration=? , Format=?  Price=? , IdCategory=? where idArticle=?;";
		try (PreparedStatement ps = connection.prepareStatement(str)){				
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			ps.setInt(3, obj.getDuration());
			ps.setString(4, obj.getFormat());
			ps.setDouble(5, obj.getPrice());
			ps.setInt(6, obj.getCategory());
			ps.setInt(7, obj.getId());
			if( ps.executeUpdate() == 1)	return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la mise à jour d'un article " + e.getMessage());
		} 	
		return false;
	}

	/**
	 * Méthode qui supprime un article à partir de son id (s'il existe) dans la table T_Articles
	 * @param article concerné
	 * @return vrai si suppression ok faux sinon
	 */
	@Override
	public boolean delete(Article obj) {
		try (Statement statement = connection.createStatement()){
			String str = "DELETE FROM T_Articles where IdArticle=" + obj.getId() + ";";									
			statement.executeUpdate(str);		
			return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la suppression d'un article " + e.getMessage());
		} 	
		return false;
	}
	
	/**
	 * Méthode qui renvoi tous les articles de la table T_Articles
	 * @return liste d'articles
	 */
	@Override
	public ArrayList<Article> readAll() {
		ArrayList<Article> articles = new ArrayList<Article>();
		String strSql = "SELECT * FROM T_Articles";		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					int rsDuration = resultSet.getInt(4);
					String rsFormat= resultSet.getString(5);
					double rsPrice = resultSet.getDouble(6);		
					int rsIdCategory = resultSet.getInt(7);
					articles.add((new Article(rsId,rsName,rsDescription,rsDuration,rsFormat,rsPrice,rsIdCategory)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur revoi de tous articles " + e.getMessage());
		}	
		return articles;
	}
	
	/**
	 * Méthode qui renvoi tous les articles d'une catégorie
	 * @param id de la catégorie
	 * @return liste d'articles
	 */

	public ArrayList<Article> readAllByCat(int id) {
		ArrayList<Article> articles = new ArrayList<Article>();
		String strSql = "SELECT * FROM T_Articles where idCategory=" + id;		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					int rsDuration = resultSet.getInt(4);
					String rsFormat= resultSet.getString(5);
					double rsPrice = resultSet.getDouble(6);		
					int rsIdCategory = resultSet.getInt(7);
					articles.add((new Article(rsId,rsName,rsDescription,rsDuration,rsFormat,rsPrice,rsIdCategory)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur renvoir des articles d'une catégorie " + e.getMessage());
		}			
		return articles;
	}
	
	/**
	 * Méthode qui renvoi toutes les formations par mot clé
	 * @param keyword (mot recherché)
	 * @return liste d'articles
	 */

	public ArrayList<Article> readByKeyWord(String keyword) {
		ArrayList<Article> articles = new ArrayList<Article>();
		String strSql = "SELECT * FROM T_Articles where Name LIKE " +"'"+ keyword+"%'";		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					int rsDuration = resultSet.getInt(4);
					String rsFormat= resultSet.getString(5);
					double rsPrice = resultSet.getDouble(6);		
					int rsIdCategory = resultSet.getInt(7);
					articles.add((new Article(rsId,rsName,rsDescription,rsDuration,rsFormat,rsPrice,rsIdCategory)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur renvoi des formations par mot clé " + e.getMessage());
		}			
		return articles;
	}
	
}
