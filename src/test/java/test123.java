import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.testng.annotations.Test;

public class test123 {

	@Test
	public void test() throws UnsupportedEncodingException, IOException
	{
//		String str = "hey\u6366";
		
		String str_en = "తెలుగు";
//		ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(str_en);
//		System.out.println("encode is :"+ byteBuffer.);
		byte[] charset = str_en.getBytes("UTF8");
		String result = new String(charset, "UTF-8");
		System.out.println(str_en);
		
	}
	
	static String toHex(String b) {
		String s="";
		for (int i=0; i<b.length(); ++i) s+=String.format("%04X",b.charAt(i)&0xffff);
		    return s;
		}
	
}
