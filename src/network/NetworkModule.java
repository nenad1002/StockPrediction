package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkModule {
	
	protected static final String STOCK_RSS_PREFIX_URL = "http://finance.yahoo.com/rss/headline?s=";

	protected static final String STOCK_HISTORICAL_DATA_PREFIX_URL = "SOMETHING";
	
	public NetworkModule() {
		
	}
	
	public boolean checkAvailability(String urlS) throws IOException {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlS);
			conn = (HttpURLConnection) url.openConnection();
		}
		catch(IOException exception) {
			exception.printStackTrace();
		}
		
		return (conn != null);
	}
	

}
