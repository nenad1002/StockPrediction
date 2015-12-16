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
	
	public boolean saveDictionary(HashMap<String, Integer> dictionary) throws SQLException {
		Statement statement = null;
	    ResultSet resultSet = null;
	    
	    int affectedRows = 0;
	    
	    statement = connection.createStatement();
	    for (String key : dictionary.keySet()) {
	    	int value = dictionary.get(key);
	    	
	    	affectedRows += statement.executeUpdate("INSERT INTO all_features VALUES(\"" +
	    	key + "\", " + value +  ");");
	    	
	    	
	    }
	    
	    return (affectedRows != 0);
		
	}
	
	

}
