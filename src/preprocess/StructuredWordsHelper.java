package preprocess;

import java.util.HashSet;
import java.util.Set;

public class StructuredWordsHelper {

	
	private static String[] invalidWords = {
			"", " ", "a", "about", "also", "almost", "an", "and", "any",
			"are", "as", "at", "but", "by", "d", "else", "etc", "for", "from",
			"has", "have", "in", "is", "it", "its", "of", "on", "or", "over",
			"that", "the", "this", "to", "we", "which"
	};
	
	Set<String> invalidWordsSet;
	
	public StructuredWordsHelper() {
		invalidWordsSet = new HashSet<>();
		
		for (int i = 0; i < invalidWords.length; i++) {
			invalidWordsSet.add(invalidWords[i]);
		}
	}
	
	public boolean checkWord(String word) {
		return !invalidWordsSet.contains(word);
	}

}
