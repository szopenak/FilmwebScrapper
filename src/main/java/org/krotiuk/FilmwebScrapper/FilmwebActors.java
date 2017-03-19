package org.krotiuk.FilmwebScrapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FilmwebActors implements DataHandler {
	private final Logger logger = Logger.getLogger(FilmwebActors.class);
	private final String MALE_URL = "http://www.filmweb.pl/rankings/person/actors/male";
	private final String FEMALE_URL = "http://www.filmweb.pl/rankings/person/actors/female";
	private List <ActorRecord> actorRecords = new ArrayList<ActorRecord>();
	
	private DatabaseConnector db;
	
	public void prepareElements() {
		// TODO Auto-generated method stub
		logger.info("Preparing elements for actors images table");
		logger.info("Trying to connect to the filmweb service");
		try {
			Document doc = Jsoup.connect(MALE_URL)
					.maxBodySize(3000000)
					.get();
			parseHTML(doc, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error during fetching data: "+e);
		}
		
		try {
			Document doc = Jsoup.connect(FEMALE_URL)
					.maxBodySize(3000000)
					.get();
			parseHTML(doc, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error during fetching data: "+e);
		}	
	}
	
	public void saveToDB() {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = DatabaseConnector.getConnection();
			logger.info("Adding new records do the database...");
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
			String myDateString = sdf.format(myDate);
			logger.info("Starting at: "+myDate);
		for (ActorRecord a:actorRecords) {
				
				PreparedStatement st = con.prepareStatement("insert into actors_images (Name,Role,Sex,Film,Url) values (?,?,?,?,?);");
				st.setString(1,a.getName());
				st.setString(2,a.getRole());
				st.setString(3,a.getSex());
				st.setString(4,a.getFilm());
				st.setString(5,a.getUrl());
				
				st.executeUpdate();
		}
		myDate = new Date();
		myDateString = sdf.format(myDate);
		logger.info("End at: "+myDate);
		logger.info("Added "+actorRecords.size()+" new records to the database");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Something wrong happened during adding data to the DB!"+e.toString());
		} finally {
			if (con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error("Could not close the DB connection!");
				}
		}

	}
	
	private void parseHTML(Document doc, boolean isMale) {
		int counter = actorRecords.size();
		int errors = 0;
		Elements results = doc.select("div.element > div.area > div[href]");
		for (int i = 0; i<results.size(); i++) {
			Element e = results.get(i);
			String who, img, role, film;
			String [] parts = e.attr("title").split(" jako ");
			who = parts[0];
			role = parts [1];
			if (role.contains("internal")) {
				errors++;
				continue;
			}
			img = e.attr("href");
			film = e.select("a").text();
			ActorRecord actor = new ActorRecord(who, role, film, img);
			if (isMale) { actor.setSex("M"); } else {actor.setSex("F");}
			actorRecords.add(actor);
		}
		if (actorRecords.size()-results.size() != counter) {
			logger.error("Not every information could be parsed! Number of errors: "+errors);
		}
		logger.info("Parsed "+ (actorRecords.size()-counter-errors) + (isMale? " male":" female")+" records!");
	}
	
	
	
}
