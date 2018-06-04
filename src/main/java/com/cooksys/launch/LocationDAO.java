package com.cooksys.launch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocationDAO {

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
	
	public static Location get(Long id) {
		Location location = null;
		try(
			Connection connection = getConnection();
			Statement statement = connection.createStatement()
		) {
			ResultSet result = statement.executeQuery("SELECT * FROM location WHERE location.id = " + id);
			result.next();
			location = new Location(result.getString(2), result.getString(3), result.getString(4));
			location.setId(result.getLong(1));
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}
	public static void save(Location location) throws SQLException {
		try(
				Connection connection = getConnection();
				PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO location(city, state, country) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				Statement updateStatement = connection.createStatement();	
			) {
				ResultSet result = updateStatement.executeQuery("SELECT * FROM location WHERE city = '" + location.getCity() + "' AND state = '" + location.getState() + "' AND country = '" + location.getCountry() + "'");
				if(result.next()) {
					System.out.println(location.getCity() + " is already in the table!");
					location.setId(result.getLong(1));
				}
				else if(location.getId() == null) {
					insertStatement.setString(1, location.getCity());
					insertStatement.setString(2, location.getState());
					insertStatement.setString(3, location.getCountry());
					insertStatement.executeUpdate();
					try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
			            if (generatedKeys.next()) {
			                location.setId(generatedKeys.getLong(1));
			            }
			            else {
			                throw new SQLException("Creating location failed, no ID obtained.");
			            }
					} 
				} else {
					int affectedRows = updateStatement.executeUpdate("UPDATE location SET city = '" + location.getCity() + ", state = '" + location.getState() + ", country = '" + location.getCountry() + " WHERE id = " + location.getId());
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
