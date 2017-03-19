package org.krotiuk.FilmwebScrapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DatabaseConnector {
	private static DatabaseConnector database;
	Properties props = new Properties();
	InputStream fis;
	Connection con;
	static Logger logger = Logger.getLogger(DatabaseConnector.class);
	
	private DatabaseConnector() {
		
		fis = getClass().getClassLoader().getResourceAsStream("database.properties");

		try {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Could not load properties file!");
		}
		try {
			Class.forName(props.getProperty("DB_DRIVER_CLASS"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Could not find DB_DRIVER_CLASS property!");
		}
	};
	
	static Connection getConnection() throws SQLException {
		if (database == null) {
			logger.info("Creating DatabaseConnector");
			database = new DatabaseConnector();
		}
		
		database.con = DriverManager.getConnection(database.props.getProperty("DB_URL"),
				database.props.getProperty("DB_USERNAME"),
				database.props.getProperty("DB_PASSWORD"));

		return database.con;
		
	}
	
	
	
}
