package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class NetworkModule {
	
	protected static final String STOCK_RSS_PREFIX_URL = "http://finance.yahoo.com/rss/headline?s=";

	
	protected static final String[] STOCK_CURR_DATA_PARTS_URL = {"https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20%3D%20%22", 
			"%22&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys"};
	
	protected static final String[] STOCK_PREV_DATA_PARTS_URL = {
			"https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22","%22%20and%20startDate%20%3D%20%22", "%22%20and%20endDate%20%3D%20%22",
			"%22&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys" };
	
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
	
	protected Document getDocument(String urlS) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.parse(urlS);
		
		doc.getDocumentElement().normalize();
		
		return doc;
	}
	

}
