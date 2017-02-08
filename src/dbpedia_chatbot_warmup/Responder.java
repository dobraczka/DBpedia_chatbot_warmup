package dbpedia_chatbot_warmup;

import java.util.HashMap;

/**
 * Contains the dialogue phrases
 * @author Daniel Obraczka
 *
 */
public class Responder {

	public static final String[] phrasesDidNotUnderstand = { "Hmmm... I'm not really sure what you want from me",
			"You have to state your intent a little bit clearer", "This looks a bit like gibberish to me",
			"Mea culpa! I did not understand this. Please try again!", "Sorry I did not get that",
			"Could you reformulate this?", "What? Please tell me more clearly what I should do!" 
			};

	public static final String[] phrasesWrongNumbers = { "Something is wrong with your numbers",
			"I can convert arabic numbers but these are odd....", "Are you sure these are arabic numbers?" 
			};

	public static final String[] phrasesToSmallNumber = {
			"Ever heard of negative roman numerals or a zero numeral? Me neither",
			"Ooops the numbers should be bigger than 1",
			"Sorry, but the smallest possible number is 1"
			};
	
	public static final String[] phrasesToBigNumber = {
			"Guess the romans would have run out of stone for this one. Try something smaller!",
			"Now you are just messing with me. Try something smaller!",
			"There would be a lot of \"M\"'s in this one. I would prefer a smaller number!"
			};
	
	public static final String[] phrasesDecimalPoint = {
			"Don't why you put points in here, but I'm going to ignore them",
			"You can't use decimal points here, but nevermind"
			};
	
	public static final String[] phrasesUncertain = {
			"Hmmm... I'm not sure if I got that correctly", "I'm not positive I understood this",
			"I would lie if I said I got everything from this", "Pheeew, I'm a simple bot, but this looks a bit like gibberish to me"
	};
	
	public static final String[] phrasesTryAgain = {
			"Okay, try again", "Give it another go", "Let's start from the beginning", "Okidoki let's start again", "Tabula rasa!"
	};

	/**
	 * Contains the PhraseKeys and maps them with an integer to avoid giving the same response twice
	 */
	private HashMap<PhraseKey, Integer> phrasesAndResponses = new HashMap<PhraseKey, Integer>();

	/**
	 * Constructor fills {@link #phrasesAndResponses}
	 */
	public Responder() {
		//Map PhraseKeys with initial previous response 0
		for(PhraseKey key: PhraseKey.values()){
			phrasesAndResponses.put(key, 0);
		}
	}

	/**
	 * Gives a respond out of the according String array matching the key
	 * @param key belonging to phrase array
	 */
	public void respond(PhraseKey key) {
		String[] phraseArray = getPhraseArray(key);
		int nextResponse = 0;
		while (nextResponse == phrasesAndResponses.get(key)) {
			nextResponse = (int) (Math.random() * phraseArray.length);
		}
		phrasesAndResponses.put(key, nextResponse);
		System.out.println(phraseArray[nextResponse]);
	}

	public enum PhraseKey {
		DID_NOT_UNDERSTAND, NUMBER_FORMAT_ERROR, NUMBER_TOO_SMALL, NUMBER_TOO_BIG, DECIMAL_POINT, UNCERTAIN, TRY_AGAIN
	}

	/**
	 * Returns the appropriate array for a PhraseKey
	 * @param key
	 * @return
	 */
	private String[] getPhraseArray(PhraseKey key) {
		switch (key) {
		case DID_NOT_UNDERSTAND:
			return phrasesDidNotUnderstand;
		case NUMBER_FORMAT_ERROR:
			return phrasesWrongNumbers;
		case NUMBER_TOO_SMALL:
			return phrasesToSmallNumber;
		case NUMBER_TOO_BIG:
			return phrasesToBigNumber;
		case DECIMAL_POINT:
			return phrasesDecimalPoint;
		case UNCERTAIN:
			return phrasesUncertain;
		case TRY_AGAIN:
			return phrasesTryAgain;
		default:
			System.err.println("This should definitely not happen");
			return null;
		}
	}

}
