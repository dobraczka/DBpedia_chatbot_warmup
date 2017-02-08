package dbpedia_chatbot_warmup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RomanNumberConverterTest {

	public RomanNumberConverter rnc;

	@Before
	public void initialize() {
		rnc = new RomanNumberConverter();
	}

	@Test
	public void testConvertToRomanNumber() {
		assertEquals("XX", rnc.convertToRomanNumber(20));
		assertEquals("MMCCCXIII", rnc.convertToRomanNumber(2313));
		assertNull(rnc.convertToRomanNumber(-2));
		assertNull(rnc.convertToRomanNumber(219038943));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testSplitNumbers() {
		String[] testArray = { "22", "5", "3" };
		String[] testArraySingle = { "22" };
		try {
			Class[] cArgs = new Class[1];
			cArgs[0] = String.class;
			Method method = RomanNumberConverter.class.getDeclaredMethod("splitNumbers", cArgs);
			method.setAccessible(true);

			Assert.assertArrayEquals(testArray, (String[]) method.invoke(rnc, "22, 5, 3"));
			Assert.assertArrayEquals(testArray, (String[]) method.invoke(rnc, "22 5 3"));
			Assert.assertArrayEquals(testArray, (String[]) method.invoke(rnc, "22,5 3"));
			Assert.assertArrayEquals(testArraySingle, (String[]) method.invoke(rnc, "22"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
