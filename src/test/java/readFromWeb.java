import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


public class readFromWeb {

	@Test
	public void readfromweb() throws AWTException, InterruptedException, IOException
	{
		String userdir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", userdir + "/src/test/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.eenadu.net/telugu-news/sports/rohit-sharma-said-ipl-franchises-now-own-players-they-have-to-manage-workload-in-world-cup-year/0400/123051156");

		String str = driver.findElement(By.xpath("//div[@class='two-col-left-block box-shadow telugu_uni_body fullstory']")).getText();
		System.out.println(str);
}
}
