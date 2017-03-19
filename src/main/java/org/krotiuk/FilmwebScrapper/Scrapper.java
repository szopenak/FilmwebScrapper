package org.krotiuk.FilmwebScrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class Scrapper 
{
	final static Logger logger = Logger.getLogger(Scrapper.class);
	
    public static void main( String[] args )
    {
    	logger.info("Starting scrapper...");
        if (args.length != 0) {
        	String action = args[0];
        	switch (action) {
            case "update":
                // do the update
                break;
            case "create":
            	logger.info("Creating new data tables in DB");
            	DataHandler data = new FilmwebActors();
            	data.prepareElements();
            	data.saveToDB();
            	logger.info("Job's done");
            	break;
            default:
                logger.error("Please specify correct action: update or create new data. Program terminated.");
        	}
        }
    
        
     
    }
}
