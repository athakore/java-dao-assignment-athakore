package com.cooksys.launch;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PersonDAO {
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
	
	public static Person get(Long id) {
		Person person = null;
		try(
			Connection connection = getConnection();
			Statement statement = connection.createStatement()
		) {
			ResultSet result = statement.executeQuery("SELECT * FROM people WHERE people.id = " + id);
			result.next();
			person = new Person(result.getString(2), result.getString(3), result.getInt(4));
			person.setId(result.getLong(1));
			person.setLocation(LocationDAO.get(result.getLong(5)));
			result = statement.executeQuery("SELECT * FROM people_interest_join WHERE people_interest_join.people_id = " + id);
			while(result.next()) {
				Interest temp = InterestDAO.get(result.getLong(2));
				temp.setId(result.getLong(2));
				person.getInterests().add(temp);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return person;
	}
	
	public static void save(Person person) throws SQLException {
		try(
			Connection connection = getConnection();
			PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO people(first_name, last_name, age, location_id) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			Statement updateStatement = connection.createStatement();	
		) {
			LocationDAO.save(person.getLocation());
			for(Interest interest : person.getInterests())
				InterestDAO.save(interest);
			if(person.getId() == null) {
				insertStatement.setString(1, person.getFirstName());
				insertStatement.setString(2, person.getLastName());
				insertStatement.setInt(3, person.getAge());
				insertStatement.setLong(4, person.getLocation().getId());
				insertStatement.executeUpdate();
				try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                person.setId(generatedKeys.getLong(1));
		            }
		            else {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            }
				} 
			} else {
				int affectedRows = updateStatement.executeUpdate("UPDATE people SET first_name = '" + person.getFirstName() + "', last_name = '" + person.getLastName() + "', age = " + person.getAge() + ", location_id = " + person.getLocation().getId() + " WHERE id = " + person.getId());
				if(affectedRows == 0) {
		            throw new SQLException("Creating user failed, no rows affected.");	
				}
			}
			for(Interest i : person.getInterests()) {
				ResultSet result = updateStatement.executeQuery("SELECT * FROM people_interest_join WHERE people_id = " + person.getId() + " AND interest_id = " + i.getId());
				if(!result.next())
					updateStatement.executeUpdate("INSERT INTO people_interest_join(people_id, interest_id) VALUES( " + person.getId() + ", " + i.getId() + ")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HashSet<Person> findInterestGroup(Interest interest, Location location) {
		HashSet<Person> people = new HashSet<Person>();
		try(
			Connection connection = getConnection();
			Statement statement = connection.createStatement()
		) {
			ResultSet result = statement.executeQuery("SELECT * FROM people JOIN location ON people.location_id = location.id JOIN people_interest_join ON people.id = people_interest_join.people_id JOIN interest ON people_interest_join.interest_id = interest.id Where location.id = " + location.getId() + " AND interest.id = " + interest.getId());
			while(result.next())
				people.add(PersonDAO.get(result.getLong(1)));
			return people;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return people;
		
	}
}
