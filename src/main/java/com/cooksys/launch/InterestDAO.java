package com.cooksys.launch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InterestDAO {

	private static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/somethingaboutinterestandpeople", "postgres", "bondstone");
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Interest get(Long id) {
		Interest interest = null;
		try(
			Connection connection = getConnection();
			Statement statement = connection.createStatement()
		) {
			ResultSet result = statement.executeQuery("SELECT * FROM interest WHERE interest.id = " + id);
			result.next();
			interest = new Interest(result.getString(2));
			interest.setId(result.getLong(1));
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return interest;
	}
	public static void save(Interest interest) throws SQLException {
		try(
				Connection connection = getConnection();
				PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO interest(title) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
				Statement updateStatement = connection.createStatement();	
			) {
				ResultSet result = updateStatement.executeQuery("SELECT * FROM interest WHERE title = '" + interest.getTitle() + "'");
				if(result.next()) {
					System.out.println(interest.getTitle() + " is already in the table!");
					interest.setId(result.getLong(1));
				}
				else if(interest.getId() == null) {
					insertStatement.setString(1, interest.getTitle());
					insertStatement.executeUpdate();
					try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
			            if (generatedKeys.next()) {
			                interest.setId(generatedKeys.getLong(1));
			            }
			            else {
			                throw new SQLException("Creating interest failed, no ID obtained.");
			            }
					} 
				} else {
					int affectedRows = updateStatement.executeUpdate("UPDATE interest SET title = '" + interest.getTitle() + " WHERE id = " + interest.getId());
					if(affectedRows == 0) {
			            throw new SQLException("Creating interest failed, no rows affected.");	
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
