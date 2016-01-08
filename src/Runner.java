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
import bayes.Classification;
import bayes.Classifier;
import database.DatabaseModule;
import network.ArticleFetcherModule;
import network.StockHistoryModule;
import preprocess.BagOfWordsHelper;

public class Runner {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException {
	
		
		// aapl
		// goog
		// msft
		// yhoo
		// fb
		
		// amzn
		// intc
		// ibm
		// twtr
		// nvda
		//saveStuffIntoDatabase("nvda");
		
	Classifier classifier = new BayesClassifier();
		
		learn(classifier, "intc");
		
		classify(classifier, "intc");
		
		
	}
	
	public static class StockInfo {
		boolean isIncreasing;
		
		boolean classified;
		
		StockInfo(boolean isIncreasing, boolean classified) {
			this.isIncreasing = isIncreasing;
			this.classified = classified;
		}
	}
	public static StockInfo classify(String stockIndex) throws ParserConfigurationException, SAXException, IOException {
		Classifier classifier = new BayesClassifier();
		
		StockHistoryModule stock = new StockHistoryModule();
		
		boolean increasing = stock.isStockIncreasing(stockIndex);
		
		learn(classifier, stockIndex);
		
		Classification classification = classify(classifier, stockIndex);
		
		return new StockInfo(increasing, classification.getCategory().equals("positive") ? true : false);
	}
	
	private static Classification classify(Classifier classifier, String stockIndex) throws
	ParserConfigurationException, SAXException, IOException {	
		
		ArticleFetcherModule a  = new ArticleFetcherModule(stockIndex);
		
		ArrayList<String> list = a.getArticleWords(
				new Date());

		BagOfWordsHelper bag = new BagOfWordsHelper();
		
		ArrayList<String> secondList = bag.processWords(list);
	
		
		System.out.println(classifier.classify(secondList));
		
		return classifier.classify(secondList);
		
	}
	
	private static void learn(Classifier classifier, String stockIndex) {
		
		try {
			classifier.learn(stockIndex);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveStuffIntoDatabase(String stockIndex) throws ParserConfigurationException, SAXException, IOException {

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
			System.out.println(db.saveDictionary(dictionary, increasing, stockIndex));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
