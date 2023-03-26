package read;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class readFromPDF {

	Statement stmt;
	String userdir ;
	WebDriver driver ;
	List<WebElement> urls_webelements;
	ArrayList<String> urls_string;

	@Test
	public void insertPDFtoDB() throws IOException, SQLException
	{
		String filename = "sankar_narayan_dict";

		String textFrpmPDF =readPDF(filename);
		stmt = createDBconnection();
		paragraphToWords(textFrpmPDF,stmt);
	}

	
	@Test
	public void readfromweb_AP() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/andhra-pradesh";
		readfromweb(entireURL);		
	}

	@Test
	public void readfromweb_national() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/national";
		readfromweb(entireURL);	
	}
	@Test
	public void readfromweb_sports() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/sports";
		readfromweb(entireURL);		
	}
	@Test
	public void readfromweb_navya() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/navya";
		readfromweb(entireURL);	
	}
	
	@Test
	public void readfromweb_editorial() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/editorial";
		readfromweb(entireURL);		
	}
	
	@Test
	public void readfromweb_business() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/business";
		readfromweb(entireURL);			
	}
	@Test
	public void readfromweb_politics() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/politics";
		readfromweb(entireURL);		
	}
	
	@Test
	public void readfromweb_vantalu() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/vantalu";
		readfromweb(entireURL);
	}
	
	@Test
	public void readfromweb_health() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/health";
		readfromweb(entireURL);		
	}
	
	@Test
	public void readfromweb_education() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/education";
		readfromweb(entireURL);	
	}
	
	@Test
	public void readfromweb_crime() throws AWTException, InterruptedException, IOException, SQLException
	{
		String entireURL = "https://www.andhrajyothy.com/crime";			
		readfromweb(entireURL);
	}
	public void readfromweb(String entireURL) throws AWTException, InterruptedException, IOException, SQLException
	{
		stmt = createDBconnection();
		userdir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", userdir + "/src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();		
		
		driver.get(entireURL);
		System.out.println(entireURL);
		List<WebElement> urls_webelements = driver.findElements(By.xpath("//a[contains(@href,'.html')]"));
		ArrayList<String> urls_string = new  ArrayList<String>();
		loopthroughWebPages(urls_webelements,urls_string,stmt);				
	}
	
	public void loopthroughWebPages(List<WebElement> urls_webelements,ArrayList<String> urls_string,Statement stmt) throws SQLException
	{
		for(WebElement we:urls_webelements)
		{
			urls_string.add(we.getAttribute("href"));
		}
		for(String st:urls_string)
		{
			driver.get(st);
			List<WebElement> bodyContainsData = driver.findElements(By.xpath("//body//*"));
			if(bodyContainsData.size()>0)
			{
				String textFrpmWeb = driver.findElement(By.xpath("//*[@class='articleBodyCont']")).getText();
				System.out.println(textFrpmWeb);
				paragraphToWords(textFrpmWeb,stmt);
			}
		}
	}

	public String readPDF(String filename) throws IOException
	{
		PDDocument doc =PDDocument.load(new File(System.getProperty("user.dir")+"/src/test/resources/"+filename+".pdf"));
		PDFTextStripper pdftext = new PDFTextStripper();	
		String str = pdftext.getText(doc);
		return str;
	}

	public Statement createDBconnection() throws SQLException
	{
		java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/telugu", "root", "root");
		Statement stmt = (Statement) con.createStatement();	
		return stmt;
	}
	
	public void paragraphToWords(String textFrpmPDF,Statement stmt) throws SQLException
	{
		String[] splitted_words =textFrpmPDF.split(" ");
		String query ="";
		
		for (String splitted_word:splitted_words)
		{
			if (splitted_word.length()>0 && !(splitted_word.contains("\n"))&& !(splitted_word.contains("'"))
					&& !(splitted_word.contains("\\")))
			{
				//********* System.out.println(splittedStr);
				query = "insert into words values('"+splitted_word+"')";
				stmt.execute(query);
				System.out.println("inserted "+splitted_word);		
			}
		}
				
	}
	
	public void paragraphToSentences(String textFrpmPDF,Statement stmt) throws SQLException
	{
		String[] splitted_sentences =textFrpmPDF.split("//.");
		String query ="";
				
		for (String splitted_sentence:splitted_sentences)
		{
			if (splitted_sentence.length()>0)
			{
				query = "insert into sentences values('"+splitted_sentence+"')";
				System.out.println("The sentense is : "+splitted_sentence);			
				stmt.execute(query);
			}
		}
		
	}
}
