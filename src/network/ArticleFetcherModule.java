package network;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class ArticleFetcherModule extends NetworkModule {
	
	private String stockRssURL = null;
	
	private static final String ITEM_TAG = "item";
	
	private static final String LINK_TAG = "link";
	
	private static final String TITLE_TAG = "title";
	
	private static final String DATE_TAG = "pubDate";
	

	public ArticleFetcherModule(String stockAbbr) {
		this.stockRssURL = this.STOCK_RSS_PREFIX_URL + stockAbbr;
	}
	
	public ArticleFetcherModule() {
		
	}
	
	public String getRssURL() {
		return stockRssURL;
	}
	
	public void setRssURL(String stockAbbr) {
		this.stockRssURL = this.STOCK_RSS_PREFIX_URL + stockAbbr;
	}
	
	public ArrayList<String> getArticleWords(Date date) throws ParserConfigurationException, SAXException, IOException {
		ArrayList<String> res = new ArrayList<>();
		/*
		 * we need to do XML parsing to find URLs of articles
		 */
		
		NodeList itemNodes = fetchNodes(stockRssURL);
		
		for (int i = 0; i < itemNodes.getLength(); i++) {
			Element element = (Element) itemNodes.item(i);
			try {
				URL articleURL = new URL(element.getElementsByTagName(LINK_TAG).
						item(0).getTextContent());
				String articleName = element.getElementsByTagName(TITLE_TAG).item(0).getTextContent();
				if (dateNotApplicable(element, date))
					continue;
				
				System.out.println("I am fetching article with name "  + articleName + "...");
				String articleText = ArticleExtractor.INSTANCE.getText(articleURL);
				res.add(articleText);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	
	private boolean dateNotApplicable(Element element, Date date) throws ParseException {
		String rawDate = element.getElementsByTagName(DATE_TAG).item(0).getTextContent().
				substring(5, 16);

		DateFormat format = new SimpleDateFormat("dd MMM yyyy");
		
		String currDateS = format.format(date);
		
		System.out.println(rawDate+"-"+currDateS);
		
		return !currDateS.equals(rawDate);
		
	}
	
	private NodeList fetchNodes(String rssURL) throws ParserConfigurationException, SAXException, IOException {
		Document doc = this.getDocument(rssURL);
		
		NodeList nList = doc.getElementsByTagName(ITEM_TAG);
		
		return nList;
		
	}

}
