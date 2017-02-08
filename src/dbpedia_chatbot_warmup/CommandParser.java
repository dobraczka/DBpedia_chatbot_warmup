package dbpedia_chatbot_warmup;

import dbpedia_chatbot_warmup.Responder;

/**
 * Parses the user input, prunes it and searches for keywords
 * @author Daniel Obraczka
 *
 */
public class CommandParser {


	/**
	 * If these are contained the bot translates the numbers
	 */
	public static final String[] keywords = { "convert", "translate", "what is", "what are", "in roman numerals",
			"in roman" };

	/**
	 * these will be ignored
	 */
	public static final String[] courtesyWords = {"could you", "would you", "could", "would", "kindly", "hi", "hello", "good", "morning", "evening", "day", "please", "for me", "dear", "chatbot", "bot"};

	private Responder responder;
	
	
	/**
	 * Constructor
	 * @param responder corresponding responder
	 */
	public CommandParser(Responder responder){
		this.responder = responder;
	}

	/**
	 * Deletes courtesyWords, "!","?","and"
	 * @param command
	 * @return
	 */
	private String prune(String command) {
		command = command.toLowerCase();
		command = command.replace("!", "");
		command = command.replace("?", "");
		command = command.replace("and","");

		for (String c : courtesyWords) {
			command = command.replaceAll(c + "\\,*", "");
		}
		return command;
	}

	/**
	 * Searched for keywords
	 * @param command user input
	 * @return command without keywords or null if there weren't any keywords
	 */
	private String processKeywords(String command) {
		int initialLength = command.length();
		for (String c : keywords) {
			command = command.replace(c, "");
		}
		// Command gets shortened if keyword is contained
		if (initialLength == command.length())
			return null;
		return command.trim();
	}

	/**
	 * Calls the helper methods and calls responder if there were no keywords
	 * @param command
	 * @return
	 */
	public String parseCommand(String command) {
		String processed = processKeywords(prune(command));
		if (processed == null) {
			responder.respond(Responder.PhraseKey.DID_NOT_UNDERSTAND);
		}
		return processed;
	}

}
