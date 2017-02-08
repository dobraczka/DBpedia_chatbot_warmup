package dbpedia_chatbot_warmup;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is the starting point of the chatbot. It contains the dialogue loop in the main and does the conversion of the numbers
 * @author Daniel Obraczka
 *
 */
public class RomanNumberConverter {

	
	private static int[] arabicNumbers = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

	private static String[] romanLetters = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

	/**
	 * Upper limit to avoid to big numbers
	 */
	private static final int upperLimit = 1000000;

	/**
	 * matches numbers that are seperated via comma or space
	 */
	private static final String seperatedNumbersRegex = "(-*\\d+,*\\s*)+";

	/**
	 * contains the dialogue options
	 */
	private Responder responder;

	/**
	 * parses the commands
	 */
	private CommandParser cmdParser;

	/**
	 * Constructor initializes responder and command parser
	 */
	public RomanNumberConverter() {
		this.responder = new Responder();
		this.cmdParser = new CommandParser(responder);
	}

	/**
	 * Converts an integer to a roman numeral
	 * 
	 * @param arabicNumber
	 *            integer that is going to be converted to a roman number
	 * @return roman roman number as String
	 */
	public String convertToRomanNumber(int arabicNumber) throws NumberFormatException {
		if (arabicNumber < 1)
			responder.respond(Responder.PhraseKey.NUMBER_TOO_SMALL);
		if (arabicNumber > upperLimit)
			responder.respond(Responder.PhraseKey.NUMBER_TOO_BIG);
		String roman = "";
		// part that still has to be converted
		int remaining = arabicNumber;
		for (int i = 0; i < arabicNumbers.length; i++) {
			while (remaining >= arabicNumbers[i]) {
				roman += romanLetters[i];
				remaining -= arabicNumbers[i];
			}
		}
		return roman;
	}

	/**
	 * Processes the user request by calling the parser and proceed with the result accordingly
	 * @param line user input
	 * @return answer containing the roman numerals or empty string if something went wrong
	 */
	private String processRequest(String line) {
		String numbersFromCommand = cmdParser.parseCommand(line);
		if (numbersFromCommand == null)
			return "";
		// Regex checks if there are comma seperated numbers or one number
		// Negative numbers are getting checked later
		if (!Pattern.matches(seperatedNumbersRegex, numbersFromCommand)) {
			// Try to find something useful
			Pattern pattern = Pattern.compile(seperatedNumbersRegex);
			Matcher matcher = pattern.matcher(numbersFromCommand);
			if (matcher.find()) {
				if (!askUser(matcher.group())) {
					return "";
				}else{
					numbersFromCommand = matcher.group();
				}
			} else {
				responder.respond(Responder.PhraseKey.NUMBER_FORMAT_ERROR);
				return "";
			}
		}
		String[] numbers = splitNumbers(numbersFromCommand);
		String answer = "";
		for (int i = 0; i < numbers.length; i++) {
			String converted = convertToRomanNumber(Integer.parseInt(numbers[i].trim()));
			if (converted.equals(""))
				return "";
			answer += converted + "\n";
		}
		return answer;
	}

	/**
	 * If you found some numbers in the user input ask user if they should be translated (only gets called if there were keywords) 
	 * @param numbers presumed numbers that should be translated
	 * @return true if user answered yes false otherwise
	 */
	private boolean askUser(String numbers) {
		responder.respond(Responder.PhraseKey.UNCERTAIN);
		Scanner input = new Scanner(System.in);
		String line = "";
		System.out.println("Do you want me to translate these numbers?");
		System.out.println(numbers);
		do {
			line = input.nextLine();
			line = line.trim().toLowerCase();
			if (line.equals("yes") || line.equals("y") || line.equals("yep")) {
				return true;
			} else if (line.equals("no") || line.equals("nope") || line.equals("n")) {
				responder.respond(Responder.PhraseKey.TRY_AGAIN);
				return false;
			} else {
				responder.respond(Responder.PhraseKey.DID_NOT_UNDERSTAND);
			}
		} while (true);

	}

	/**
	 * Splits the string containing the numbers
	 * Can handle comma or space seperated strings
	 * @param numbersString containing the numbers
	 * @return numbers array with inividual numbers
	 */
	private String[] splitNumbers(String numbersString) {
		//Replace multiple spaces with single spaces
		numbersString = numbersString.replaceAll("\\s\\s*", " ");
		String numbers[] = null;
		String delimiter = "";
		//single number
		if(Pattern.matches("-*\\d+", numbersString)){
			numbers = new String[]{numbersString};
		}
		// ===== FIND DELIMITER ======

		if (Pattern.matches("(-*\\d+,\\s*)+", numbersString)) {
			numbers = numbersString.split(",");
		} else if (Pattern.matches("(-*\\d+\\s*)+", numbersString)) {
			numbers = numbersString.split(" ");
		}
		// If there are spaces and commata mixed as delimiters split accordingly
		else {
			String tmpNumbers[] = numbersString.split("\\s");
			ArrayList<String> tmpFinal = new ArrayList<String>();
			for (String s : tmpNumbers) {
				String[] smallSplit = s.split(",");
				for (String small : smallSplit) {
					tmpFinal.add(small);
				}
			}
			numbers = new String[tmpFinal.size()];
			numbers = tmpFinal.toArray(numbers);

		}
		return numbers;
	}

	public static void main(String[] args) {
		RomanNumberConverter rnc = new RomanNumberConverter();
		Scanner input = new Scanner(System.in);
		String line = "";
		System.out.println("Ave! I am a simple chatbot to translate arabic numbers into roman numerals \n"
				+ "Ask me to translate arabic numbers or press 'q' to exit");
		do {
			System.out.print(">");
			line = input.nextLine();
			if (line.equals("q")) {
				break;
			} else {
				System.out.println(rnc.processRequest(line));
			}

		} while (true);
		System.out.println("Vale!");
	}

}
