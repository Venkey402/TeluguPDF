import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class readPDF {

	@Test
	public void read() throws IOException, SQLException
	{
		String Finame = "teluguenglishdic0000revp";
		
		java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/telugu", "root", "root");
		Statement stmt = (Statement) con.createStatement();				
		PDDocument doc =PDDocument.load(new File(System.getProperty("user.dir")+"/src/test/resources/"+Finame+".pdf"));
		PDFTextStripper pdftext = new PDFTextStripper();	
		String str = pdftext.getText(doc);
		
		String[] splittedStrArr =str.split(" ");
		String query ="";
		for (String splittedStr:splittedStrArr)
		{
if (splittedStr.length()>0 && !(splittedStr.contains("\n"))&& !(splittedStr.contains("'"))
		&& !(splittedStr.contains("\\")))
			{
//				System.out.println(splittedStr);
				query = "insert into words values('"+splittedStr+"')";
				System.out.println(query);			
				stmt.execute(query);
			}
		}
		
		
	
	
	
	
	}
	
	
}
