package dbpedia_chatbot_warmup;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResponderTest {

	Responder r;

	@Before
	public void initialize() {
		r = new RomanNumberConverter().getResponder();
	}
	
	@Test
	public void testGetPhraseArray(){
		try {
			Class[] cArgs = new Class[1];
			cArgs[0] = Responder.PhraseKey.class;
			Method method = Responder.class.getDeclaredMethod("getPhraseArray", cArgs);
			method.setAccessible(true);
			Assert.assertArrayEquals(Responder.phrasesDecimalPoint, (String[]) method.invoke(r, Responder.PhraseKey.DECIMAL_POINT));
			Assert.assertArrayEquals(Responder.phrasesDidNotUnderstand, (String[]) method.invoke(r, Responder.PhraseKey.DID_NOT_UNDERSTAND));
			Assert.assertArrayEquals(Responder.phrasesMissingNumbers, (String[]) method.invoke(r, Responder.PhraseKey.MISSING_NUMBERS));
			Assert.assertArrayEquals(Responder.phrasesWrongNumbers, (String[]) method.invoke(r, Responder.PhraseKey.NUMBER_FORMAT_ERROR));
			Assert.assertArrayEquals(Responder.phrasesToBigNumber, (String[]) method.invoke(r, Responder.PhraseKey.NUMBER_TOO_BIG));
			Assert.assertArrayEquals(Responder.phrasesToSmallNumber, (String[]) method.invoke(r, Responder.PhraseKey.NUMBER_TOO_SMALL));
			Assert.assertArrayEquals(Responder.phrasesTryAgain, (String[]) method.invoke(r, Responder.PhraseKey.TRY_AGAIN));
			Assert.assertArrayEquals(Responder.phrasesUncertain, (String[]) method.invoke(r, Responder.PhraseKey.UNCERTAIN));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
