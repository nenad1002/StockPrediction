package preprocess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;




public class BagOfWordsHelper extends StructuredWordsHelper { // NLP technique Bag of Words
			
	
	public BagOfWordsHelper() {
		super();
	}

	// method for processing words, in future possible use of weka library
	public ArrayList<String> processWords(ArrayList<String> articleTextList) {
		
		ArrayList<String> originWords = separateWords(articleTextList);
		
		ArrayList<String> resWords = new ArrayList<>();
		
		for (int i = 0; i < originWords.size(); i++) {
			String newS = setLowerCases(originWords.get(i));
			newS = newS.replaceAll("[^a-z]", "");
			if (!checkWord(newS))
				continue;
			resWords.add(newS);
		}
		
		ArrayList<String> tmpWords = new ArrayList<>(resWords);
		
		for (int i = 0; i < tmpWords.size() - 1; i++) {
			resWords.add(tmpWords.get(i) + " " + tmpWords.get(i + 1));
		}
		
		return resWords;
		
	}
	
	public HashMap<String, Integer> buildDictonary(ArrayList<String> articlesText) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for (String word : articlesText) {
			if (!checkWord(word))
				continue;
			if (!map.containsKey(word)) {
				map.put(word, 0);
			}
			map.put(word, map.get(word) + 1);
		}
		
		
		return map;
		
	}
	
	private ArrayList<String> separateWords(ArrayList<String> articleTextList) {
		ArrayList<String> resWords = new ArrayList<>();
		
		for (String s : articleTextList) {
			String[] textWords = s.split(" ");
			resWords.addAll(Arrays.asList(textWords));
		}
		
		return resWords;
	}
	
	
	
	/*
	 Converts all upper-case letters into lower-case
	 */
	
	private String setLowerCases(String s) {
		StringBuilder res = new StringBuilder();
		
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
				res.append((char)(s.charAt(i) - 'A' + 'a'));
			else
				res.append(s.charAt(i));
		}
		
		return res.toString();
		
	}

}
