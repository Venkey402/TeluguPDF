import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

public class telugu_1841 {

	@Test
	public void test()
	{
		String s = "ஈஉஐ రాజైన";  // Tamil/Telugu text input
		List<String> characters = new ArrayList<String>();
		Pattern pat = Pattern.compile("\u0B95\u0BCD\u0BB7\\p{M}?|\\p{L}\\p{M}?");
		Matcher matcher = pat.matcher(s);
		while (matcher.find()) {
		  characters.add(matcher.group());
		}

		System.out.println(characters);
		System.out.println(characters.size());
	
	}
	
}
