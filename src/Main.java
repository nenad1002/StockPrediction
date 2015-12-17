import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bayes.BayesClassifier;
import bayes.Classifier;
import database.DatabaseModule;
import network.ArticleFetcherModule;
import network.StockHistoryModule;
import preprocess.BagOfWordsHelper;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException {
	
		
		
		saveStuffIntoDatabase("MSFT");
		
		//Classifier classifier = new BayesClassifier();
		
		//learn(classifier);
		
		//classify(classifier, "msft");
		
		
	}
	
	private static void classify(Classifier classifier, String stockIndex) throws
	ParserConfigurationException, SAXException, IOException {
		StockHistoryModule stock = new StockHistoryModule();
		
		
		
		ArticleFetcherModule a  = new ArticleFetcherModule(stockIndex);
		
		ArrayList<String> list = a.getArticleWords(
				new Date());

		BagOfWordsHelper bag = new BagOfWordsHelper();
		
		ArrayList<String> secondList = bag.processWords(list);
	
		
		System.out.println(secondList);
		System.out.println(classifier.classify(secondList));
		
	}
	
	private static void learn(Classifier classifier) {
		
		try {
			classifier.learn();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void saveStuffIntoDatabase(String stockIndex) throws ParserConfigurationException, SAXException, IOException {

		StockHistoryModule stock = new StockHistoryModule();
		
		boolean increasing = stock.isStockIncreasing(stockIndex);
		
		
		ArticleFetcherModule a  = new ArticleFetcherModule(stockIndex);
		
		ArrayList<String> list = a.getArticleWords(
				new Date(System.currentTimeMillis()));

		BagOfWordsHelper bag = new BagOfWordsHelper();
		
		ArrayList<String> secondList = bag.processWords(list);
		
		Collections.sort(secondList);
		
		HashMap<String, Integer> dictionary = bag.buildDictonary(secondList);
		
		System.out.println(dictionary);
		
		DatabaseModule db = new DatabaseModule();
		
		try {
			System.out.println(db.tryToConnect());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(db.saveDictionary(dictionary, increasing));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
