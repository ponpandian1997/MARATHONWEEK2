package week5.Marathon2;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.sukgu.Shadow;

public class ServiceNow {

	public static void main(String[] args) throws IOException {

		//Initialization
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		//Launch ServiceNow application 
		driver.get("https://dev124621.service-now.com");

		//Login with valid credentials
		driver.findElement(By.id("user_name")).sendKeys("admin");
		driver.findElement(By.id("user_password")).sendKeys("Ak^x88vhDMV=");
		driver.findElement(By.id("sysverb_login")).click();

		//Click-All Enter Service catalog in filter navigator and press enter or Click the ServiceCatalog
		Shadow shadow = new Shadow(driver);
		shadow.setImplicitWait(30);
		shadow.findElementByXPath("//div[text()='All']").click();
		shadow.setImplicitWait(10);
		shadow.findElementByXPath("//span[text()='Service Catalog']").click();

		//Click on  mobiles
		shadow.setImplicitWait(10);
		WebElement frame = shadow.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(frame);
		driver.findElement(By.xpath("(//div[@class='homepage_category_only'])[8]/a")).click();


		//Select Apple iPhone 13 Pro 
		driver.findElement(By.xpath("((//table[@role='presentation']//table)[8]//a)[1]")).click();


		driver.findElement(By.xpath("(//div[@class='row sc-row']//label)[1]")).click();
		driver.findElement(By.xpath("(//div[@class='row sc-row']//input)[6]")).sendKeys("99");

		//Choose monthly data allowance as unlimited using select class value
		WebElement drop = driver.findElement(By.xpath("//select[@class='form-control cat_item_option ']"));
		Select opt = new Select(drop);
		opt.selectByValue("unlimited");

		//Update color field to Sierra Blue and storage field to 512GB
		driver.findElement(By.xpath("(//div[@class='sc-radio'])[7]//label")).click();
		driver.findElement(By.xpath("(//span[@class='input-group-radio'])[10]/label")).click();

		//Click on Order now option
		driver.findElement(By.xpath("//button[@id='oi_order_now_button']")).click();

		//Verify order is placed 
		String request = driver.findElement(By.xpath("//a[@id='requesturl']/b")).getText();
		System.out.println("Request Id - "+request);

		// Take a Snapshot of order placed page.
		File source = driver.getScreenshotAs(OutputType.FILE);
		File target = new File("./snap/servicenow.png");
		FileUtils.copyFile(source, target);
		//driver.close();

	}

}