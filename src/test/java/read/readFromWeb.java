package read;
import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import com.mysql.jdbc.Statement;



public class readFromWeb {

	@Test
	public void readfromweb() throws AWTException, InterruptedException, IOException
	{


		//Statement stmt = createDBconnection();

		String userdir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", userdir + "/src/test/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("acceptInsecureCerts", "true");
		driver.get("https://www.andhrajyothy.com/2023/andhra-pradesh/ananthapuram/guttipendekallu-doubling-line-with-rs3518-crores-1035358.html");
		String textFrpmWeb = driver.findElement(By.xpath("//*[@class='articleBodyCont']")).getText();
		
		
		//String textFrpmWeb = driver.findElement(By.xpath("//div[@class='two-col-left-block box-shadow telugu_uni_body fullstory']")).getText();
		System.out.println(textFrpmWeb);
		//paragraphToWords(textFrpmWeb,stmt);
	}
}
