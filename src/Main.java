import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import network.ArticleFetcherModule;
import preprocess.BagOfWordsHelper;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		

		
		ArticleFetcherModule a  = new ArticleFetcherModule("msft");
		
		ArrayList<String> list = a.getArticleWords(
				new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
		
		System.out.println(list);
		//ArrayList<String> list = new ArrayList<String>();
		
		//list.add("dsfsf sdfsd qweqe'sdfdfDF");
		//list.add("sf sdf. dsfdfsd''sdsdf");
		
		ArrayList<String> secondList = BagOfWordsHelper.processWords(list);
		
		Collections.sort(secondList);
		
		HashMap<String, Integer> dictionary = BagOfWordsHelper.buildDictonary(secondList);
		
		System.out.println(dictionary);
		

	}

}
