package com.cooksys.launch;

public class Interest {
	private Long id;
	private String title;
	public Interest(String title) {
		super();
		this.id = null;
		this.title = title;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
