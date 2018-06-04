package com.cooksys.launch;

import java.util.HashSet;

public class Person {
	private Long id;
	private String firstName;
	private String lastName;
	private Integer age;
	private Location location;
	private HashSet<Interest> interests;
	public Person(String firstName, String lastName, Integer age) {
		super();
		this.id = null;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.location = null;
		this.interests = new HashSet<Interest>();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public HashSet<Interest> getInterests() {
		return interests;
	}
	public void setInterests(HashSet<Interest> interests) {
		this.interests = interests;
	}
}
