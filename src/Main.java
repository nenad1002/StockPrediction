import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import database.DatabaseModule;
import network.ArticleFetcherModule;
import preprocess.BagOfWordsHelper;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException {
		

		
		
		ArticleFetcherModule a  = new ArticleFetcherModule("msft");
		
		ArrayList<String> list = a.getArticleWords(
				new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
		
		System.out.println(list);
		//ArrayList<String> list = new ArrayList<String>();
		
		//list.add("dsfsf sdfsd qweqe'sdfdfDF");
		//list.add("sf sdf. dsfdfsd''sdsdf");
		
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
		
		System.out.println(db.saveDictionary(dictionary));

	}

}
