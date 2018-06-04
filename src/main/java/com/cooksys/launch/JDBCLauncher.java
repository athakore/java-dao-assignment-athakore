package com.cooksys.launch;

import java.sql.SQLException;
import java.util.HashSet;

public class JDBCLauncher {
	public static void main(String[] args) throws SQLException {
//		Person person = PersonDAO.get((long)13);
//		System.out.println(person.getFirstName() + " " + person.getLastName());
//		System.out.println("Age: " + person.getAge());
//		System.out.println("Location: " + person.getLocation().getCity() + ", " + person.getLocation().getState() + ", " + person.getLocation().getCountry());
//		System.out.println("Interests:");
//		for(Interest i : person.getInterests())
//			System.out.println("\t" + i.getTitle());
//		Person newPerson = new Person("That", "Girl", 24);
//		newPerson.setLocation(new Location("Athens", "Attica", "Greece"));
//		newPerson.getInterests().add(new Interest("That Thing"));
//		newPerson.getInterests().add(new Interest("The Other Thing"));
//		newPerson.getInterests().add(new Interest("That Other Thing"));
//		PersonDAO.save(newPerson);
		
		Interest interest = InterestDAO.get((long)6);
		Location location = LocationDAO.get((long)4);
		HashSet<Person> people = PersonDAO.findInterestGroup(interest, location);
		for(Person p: people)
			System.out.println(p.getFirstName());
	}
}
