package dbpedia_chatbot_warmup;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class CommandParserTest {

	public CommandParser cmdParser;

	@Before
	public void initialize() {
		cmdParser = new RomanNumberConverter().getCmdParser();
	}

	@Test
	public void testParseCommand() {
		assertEquals("23  4", cmdParser.parseCommand("Please translate 23 and 4!"));
		assertEquals("", cmdParser.parseCommand("Please translate"));
		assertNull(cmdParser.parseCommand("Please "));
		assertNull(cmdParser.parseCommand("Pskqpnkle 23"));
	}

	@Test
	public void testPrune() {
		try {
			Class[] cArgs = new Class[1];
			cArgs[0] = String.class;
			Method method = CommandParser.class.getDeclaredMethod("prune", cArgs);
			method.setAccessible(true);
			assertEquals("   it works", (String) method.invoke(cmdParser, "Please !would and it works?"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testProcessKeywords() {
		try {
			Class[] cArgs = new Class[1];
			cArgs[0] = String.class;
			Method method = CommandParser.class.getDeclaredMethod("processKeywords", cArgs);
			method.setAccessible(true);
			assertEquals("23, 4", (String) method.invoke(cmdParser, "translate 23, 4"));
			assertEquals("23 4", (String) method.invoke(cmdParser, "translate 23 4"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
