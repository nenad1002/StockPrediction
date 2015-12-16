package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import credential.Credentials;

public class DatabaseModule {

	private static final String USER_PROPERTY = "user";
	
	private static final String PASSWORD_PROPERY = "password";
	
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	private static final String SSL_USAGE = "useSSL";
	
	private static final String NAME_COLUMN = "name";
	
	private static final String COUNT_COLUMN = "count";
	
	private static final String ALL_FEATURES_TABLE = "all_features";
	
	private static final String CATEGORIES_TABLE = "categories";
	
	private static final String POSITIVE_TABLE = "positive_features";
	
	private static final String NEGATIVE_TABLE = "negative_features";
	
	Connection connection;
	
	public DatabaseModule() {
	
		this.connection = null;
		
	}
	
	public boolean tryToConnect() throws ClassNotFoundException, SQLException {
		
		Class.forName(DRIVER_NAME); // it will throw exception in case it cannot find driver
		
		Properties props = new Properties();
		props.setProperty(USER_PROPERTY, Credentials.DATABASE_USER);
		props.setProperty(PASSWORD_PROPERY, Credentials.DATABASE_PASSWORD);
		props.setProperty(SSL_USAGE, "false");
		
		connection = DriverManager.getConnection(Credentials.DATABASE_URL, props);
		
		return connection != null;
		
	}
	
	public boolean saveDictionary(HashMap<String, Integer> dictionary, boolean arePositive) throws SQLException {
		Statement statement = null;
	    
	    int affectedRows = 0;
	    
	    statement = connection.createStatement();
	    for (String key : dictionary.keySet()) {
	    	int value = dictionary.get(key);
	    	
	    	affectedRows += saveToTable(ALL_FEATURES_TABLE, key, value, statement);
	    	
	    	if (arePositive)
	    		affectedRows += saveToTable(POSITIVE_TABLE, key, value, statement);
	    	else
	    		affectedRows += saveToTable(NEGATIVE_TABLE, key, value, statement);
	    	
	    	
	    }
	    
	    return (affectedRows != 0);
		
	}
	
	private static int saveToTable(String tableName, String key, int value, Statement statement) throws SQLException {
		int affectedRows = 0;
		
		ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE name = \"" +
		    	key + "\";");
		    	
		if (resultSet.next()) {
		    	value += Integer.parseInt(resultSet.getString(COUNT_COLUMN));
		    	affectedRows += statement.executeUpdate("DELETE FROM " + tableName + " WHERE "
		    				+ "name = \"" + key + "\";");
		    }
		    	
		affectedRows += statement.executeUpdate("INSERT INTO " + tableName + " VALUES(\"" +
		    	key + "\", " + value +  ");");
		  
		 return affectedRows;
	}
	
	

}
