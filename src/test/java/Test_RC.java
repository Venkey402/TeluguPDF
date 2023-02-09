import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
public class Test_RC {
	
	@Test
	public void getRCdetails()
	{
		int rcno=999;
		for(rcno=806;rcno>=806;rcno--)
		{
			
			//rcno=rcno+i;
			
			System.out.println("RC Number = "+"https://aepos.ap.gov.in/ePos/Check_RC_Details.jsp?rcno=2"+rcno+"004250");
		given()
		
		.when()
			.get("https://aepos.ap.gov.in/ePos/Check_RC_Details.jsp?rcno=2"+rcno+"004250")		
		.then()
			.log().body();
		
		}
	}

}
