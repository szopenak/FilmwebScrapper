package org.krotiuk.FilmwebScrapper;

public class ActorRecord {
	private String name;
	private String role;
	private String sex;
	private String film;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSex() {
		return sex;
	}
	public ActorRecord(String name, String role, String film, String url) {
		super();
		this.name = name;
		this.role = role;
		this.film = film;
		this.url = url;
	}
	@Override
	public String toString() {
		return "ActorRecord [name=" + name + ", role=" + role + ", sex=" + sex + ", film=" + film + ", url=" + url
				+ "]";
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getFilm() {
		return film;
	}
	public void setFilm(String film) {
		this.film = film;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
