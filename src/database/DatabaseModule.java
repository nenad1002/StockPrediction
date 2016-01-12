package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Properties;

import bayes.Classifier;
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
	
	private static final String POSITIVE = "positive";
	
	private static final String NEGATIVE = "negative";
	
	Connection connection = null;
	
	private static DatabaseModule instance = null;
	
	private DatabaseModule() {
		
	}
	
	public static DatabaseModule getInstance() { //Singleton for database connection
		if (instance == null)
			instance = new DatabaseModule();
		
		return instance;
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
	
	public boolean saveDictionary(HashMap<String, Integer> dictionary, boolean arePositive, 
			String stockIndex) throws SQLException {
		Statement statement = null;
	    
	    int affectedRows = 0;
	    
	    statement = connection.createStatement();
	    for (String key : dictionary.keySet()) {
	    	int value = dictionary.get(key);
	    	
	    	affectedRows += saveToTable(ALL_FEATURES_TABLE, key, value, stockIndex, statement);
	    	
	    	
	    	if (arePositive) {
	    		affectedRows += saveToTable(CATEGORIES_TABLE, POSITIVE, value, stockIndex, statement);
	    		affectedRows += saveToTable(POSITIVE_TABLE, key, value, stockIndex, statement);
	    	}
	    	else {

	    		affectedRows += saveToTable(CATEGORIES_TABLE, NEGATIVE, value, stockIndex, statement);
	    		affectedRows += saveToTable(NEGATIVE_TABLE, key, value, stockIndex, statement);
	    	}
	    	
	    	
	    }
	    
	    return (affectedRows != 0);
		
	}
	
	public int loadData(Dictionary<String, Dictionary<String, Integer>> featureCountPerCategory, 
			 Dictionary<String, Integer> totalFeatureCount, Dictionary<String, Integer> totalCategoryCount,
			 String stockIndex) throws SQLException {
		
		int count = populateData(totalFeatureCount, this.ALL_FEATURES_TABLE, stockIndex);
		
		count = populateData(totalCategoryCount, this.CATEGORIES_TABLE, stockIndex);
		
		populateAdditionalData(featureCountPerCategory, this.POSITIVE_TABLE, 
				Classifier.CLASSIFICATION_POSITIVE, stockIndex);
		
		populateAdditionalData(featureCountPerCategory, this.NEGATIVE_TABLE,
				Classifier.CLASSIFICATION_NEGATIVE, stockIndex);
	
		
		return count;
	}
	
	private int populateData(Dictionary<String, Integer> dict, String table, 
			String stockIndex) throws SQLException {
		ResultSet resultSet = fetchTable(table, stockIndex);
		
		int res = 0;
		
		while (resultSet.next()) {
			String name = resultSet.getString(NAME_COLUMN);
			int count = Integer.parseInt(resultSet.getString(COUNT_COLUMN));
			res += count;
			dict.put(name, count);
		}
		
		return res;
	}
	
	private void populateAdditionalData(Dictionary<String, Dictionary<String, Integer>> featureCountPerCategory, 
			String table, String additional, String stockIndex) throws SQLException {
		ResultSet resultSet = fetchTable(table, stockIndex);
		
		while(resultSet.next()) {
			String name = resultSet.getString(NAME_COLUMN);
			int count = Integer.parseInt(resultSet.getString(COUNT_COLUMN));
			Dictionary<String, Integer> dict = featureCountPerCategory.get(additional);
			dict.put(name, count);
		}
	}
	
	
	private ResultSet fetchTable(String tableName, String stockIndex) throws SQLException {
		// for now we don't worry about possible SQL injections 
		Statement statement = null;
		
		statement = connection.createStatement();
		
		
		ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE stock = \"" +
		stockIndex + "\";");
		
		return resultSet;
	}
	
	private static int saveToTable(String tableName, String key, int value, String stockIndex, 
			Statement statement) throws SQLException {
		int affectedRows = 0;
		ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE name = \"" +
		    	key + "\" AND stock = \"" + stockIndex + "\";");
		    	
		if (resultSet.next()) {
		    	value += Integer.parseInt(resultSet.getString(COUNT_COLUMN));
		    	affectedRows += statement.executeUpdate("DELETE FROM " + tableName + " WHERE "
		    				+ "name = \"" + key + "\" AND stock = \"" + stockIndex +"\";");
		    }
		    	
		affectedRows += statement.executeUpdate("INSERT INTO " + tableName + " VALUES(\"" +
		    	key + "\", " + value +  ",\"" + stockIndex + "\");");
		  
		 return affectedRows;
	}
	
	

}
