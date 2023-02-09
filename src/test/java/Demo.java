import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Demo {
	
	ExtentReports extent = new ExtentReports();
	ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReport.html");
   // static Logger log = Logger.getLogger(Demo.class.getName());  
	
	@BeforeMethod
	
	public void before()
	{
		extent.attachReporter(spark);
	}
	
	@AfterMethod
	
	public void after()
	{
		extent.flush();
	}
		
	@Test
	public void getUsers()
	{
		
		
		given()
		
		.when()
			.get("http://localhost:3000/users")
		
		.then()
			.log().all();
		ExtentTest test = extent.createTest("getUsers");
		//log.info("Get Users method completed successfully");
//		test.log(Status.PASS,"Get Users method completed successfully");
//		test.pass("Get Users method completed successfully");
		
		
	}
	
	@Test		
	public void createUserWithHashMap()
	{
		ExtentTest test = extent.createTest("createUserWithHashMap");
		HashMap data = new HashMap();
		data.put("Fname", "Laxmi");
		data.put("Lname", "Mamidi");
		given()
			.contentType("application/json")
			.body(data)
		
		.when()
			.post("http://localhost:3000/users")
		
		.then()
			.statusCode(201)
			.log().all();
		
		
//		test.log(Status.PASS,"createUserWithHashMap method completed successfully");
//		test.pass("createUserWithHashMap method completed successfully");
	}
	
	
	@Test
	public void createUserwithPathParms()
	{
		ExtentTest test = extent.createTest("createUserwithPathParms");
		HashMap data = new HashMap();		
		data.put("Fname", "Venkat");
		data.put("Lname", "Mamidi");
		
		given()
			.pathParam("mypath", "users")
			.contentType("application/json")
			.body(data)
		.when()
			.post("http://localhost:3000/{mypath}")
		
		.then()
			.statusCode(201)
			.log().all();
		
		
//		test.log(Status.PASS,"createUserwithPathParms method completed successfully");
//	//	test.log(Status.FAIL,"createUserwithPathParms method failed");
//		test.pass("createUserwithPathParms method completed successfully");
//		//test.fail("createUserwithPathParms method completed successfully");
	}
	
	@Test	
	public void getUsersByQueryParms()
	{
		ExtentTest test = extent.createTest("getUsersByQueryParms");
		given()
			.pathParam("myPath", "users")
			.queryParam("Fname", "Ramu")
			.queryParam("id", 6)
		.when()
			.get("http://localhost:3000/{myPath}")
		.then()
			.statusCode(200)
			.log().body();
		
		
//		test.log(Status.PASS,"getUsersByQueryParms method completed successfully");
//		test.pass("getUsersByQueryParms method completed successfully");
	}
	
	
	@Test
	public void deleteUser()
	{
		
		
		ExtentTest test = extent.createTest("deleteUser");
		try
		{
		
		given()
		
		.when()
			.delete("http://localhost:3000/users/3")
		.then()
			.statusCode(200);
		test.log(Status.FAIL,"Delete user method ran successfully");
		}
		catch(Exception e)
		{
			test.log(Status.FAIL, e);
		}
	}
	
}
