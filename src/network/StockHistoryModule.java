package network;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class StockHistoryModule extends NetworkModule {
	
	private String currentStockURLStr = null;
	
	private String historyStockURLStr = null;
	
	private static final String CLOSE_TAG = "Close"; 
	
	private static final String BID_TAG = "Bid";
	
	private static final int DAY_DURATION = 24 * 60 * 60 * 1000; // duration of day in milliseconds

	public StockHistoryModule() {
		
	}
	
	public boolean isStockIncreasing(String stockIndex) throws ParserConfigurationException, SAXException, IOException {
		
		double currentStockPrice = getCurrentStockPrice(stockIndex);
		
		double prevStockPrice = getPrevStockPrice(stockIndex, new Date(System.currentTimeMillis() - 
				DAY_DURATION));
		
		System.out.println("current price" + currentStockPrice);
		
		System.out.println("prev price" + prevStockPrice);
		
		return (currentStockPrice > prevStockPrice); 
		
	}
	
	
	private double getPrevStockPrice(String stockIndex, Date date) throws ParserConfigurationException, SAXException, IOException {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		String dateStr = format.format(date);
		
		historyStockURLStr = this.STOCK_PREV_DATA_PARTS_URL[0] + stockIndex + this.STOCK_PREV_DATA_PARTS_URL[1] +
				dateStr + this.STOCK_PREV_DATA_PARTS_URL[2]  + dateStr + this.STOCK_PREV_DATA_PARTS_URL[3];
		
		Document doc = getDocument(historyStockURLStr);
		
		NodeList nList = doc.getElementsByTagName(CLOSE_TAG);
		
		Element el = (Element) nList.item(0);
		
		double res = Double.parseDouble(el.getTextContent());
		
		return res;
		
	}
	
	private double getCurrentStockPrice(String stockIndex) throws ParserConfigurationException, SAXException, IOException {
		
		currentStockURLStr =  this.STOCK_CURR_DATA_PARTS_URL[0] + stockIndex + this.STOCK_CURR_DATA_PARTS_URL[1];
		
		Document doc = getDocument(currentStockURLStr);
		

		NodeList nList = doc.getElementsByTagName(BID_TAG);
		
		Element el = (Element) nList.item(0);
		
		double res = Double.parseDouble(el.getTextContent());
		
		return res;
		
	}

}
