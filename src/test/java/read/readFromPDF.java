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
		String filename = "teluguenglishdic0000revp";

		String textFrpmPDF =readPDF(filename);
		Statement stmt = createDBconnection();
		paragraphToWords(textFrpmPDF,stmt);
	}

	
	@Test
	public void readfromweb() throws AWTException, InterruptedException, IOException, SQLException
	{
		stmt = createDBconnection();
		userdir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", userdir + "/src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		ArrayList<String> url_pages = new ArrayList<String>();
		url_pages.add("national");
		url_pages.add("andhra-pradesh");
		url_pages.add("sports");
		url_pages.add("navya");
		url_pages.add("editorial");
		url_pages.add("business");
		url_pages.add("politics");
		url_pages.add("vantalu");
		url_pages.add("health");
		url_pages.add("education");
		url_pages.add("crime");

		for(String url:url_pages)
		{
			String entireURL = "https://www.andhrajyothy.com/"+url;
			driver.get(entireURL);
			System.out.println(entireURL);
			urls_webelements = driver.findElements(By.xpath("//a[contains(@href,'.html')]"));
			urls_string = new  ArrayList<String>();
			loopthroughWebPages(urls_webelements,urls_string,stmt);			
		}		
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
		String[] splittedStrArr =textFrpmPDF.split(" ");
		String query ="";
		for (String splittedStr:splittedStrArr)
		{
			if (splittedStr.length()>0 && !(splittedStr.contains("\n"))&& !(splittedStr.contains("'"))
					&& !(splittedStr.contains("\\")))
			{
				//********* System.out.println(splittedStr);
				query = "insert into words values('"+splittedStr+"')";
				System.out.println(query);			
				stmt.execute(query);
			}
		}
	}
}
