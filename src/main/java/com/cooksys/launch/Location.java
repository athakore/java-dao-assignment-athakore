package com.cooksys.launch;

public class Location {
	private Long id;
	private String city;
	private String state;
	private String country;
	public Location(String city, String state, String country) {
		super();
		this.id = null;
		this.city = city;
		this.state = state;
		this.country = country;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
