import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class readFromPDF {

	@Test
	public void insertPDFtoDB() throws IOException, SQLException
	{
		String filename = "teluguenglishdic0000revp";
		
		String textFrpmPDF =readFromPDF(filename);
		Statement stmt = createDBconnection();
		paragraphToWords(textFrpmPDF,stmt);
	}

	@Test
	public void insertWebtoDB() throws IOException, SQLException
	{
		String filename = "teluguenglishdic0000revp";

		Statement stmt = createDBconnection();
		String textFrpmPDF =readFromPDF(filename);
		paragraphToWords(textFrpmPDF,stmt);
	}

	
	
	public String readFromPDF(String filename) throws IOException
	{
		PDDocument doc =PDDocument.load(new File(System.getProperty("user.dir")+"/src/test/resources/"+filename+".pdf"));
		PDFTextStripper pdftext = new PDFTextStripper();	
		String str = pdftext.getText(doc);
		return str;
	}

	public Statement createDBconnection() throws SQLException
	{
		java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/telugu", "root", "root");
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
