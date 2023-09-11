package week5.Marathon2;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class TATACliq {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		ChromeOptions option=new ChromeOptions();
		option.addArguments("--disable-notifications");
		ChromeDriver driver=new ChromeDriver(option);
		driver.get("https://www.tatacliq.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		//mouse over brands & watchandAccessories & FirstBrand
		
		//driver.findElement(By.id("wzrk-cancel")).click();
		driver.switchTo().frame("wiz-iframe");
		driver.findElement(By.xpath("(//a[@class='wzrkClose'])[1]")).click();
		driver.switchTo().defaultContent();
	
		Actions builder =new Actions(driver);
		
		builder.moveToElement(driver.findElement(By.xpath("//div[text()='Brands']"))).perform();
		builder.moveToElement(driver.findElement(By.xpath("//div[text()='Watches & Accessories']"))).perform();
		builder.moveToElement(driver.findElement(By.xpath("(//img[@class='Image__actual'])[1]"))).perform();
		driver.findElement(By.xpath("(//img[@class='Image__actual'])[1]")).click();
		
		//click on new arrivals
		driver.findElement(By.xpath("//div[@class='SelectBoxDesktop__base']")).click();
		Select drop1 =new Select(driver.findElement(By.className("SelectBoxDesktop__hideSelect")));
		drop1.selectByIndex(3);
		
		//click on men
		driver.findElement(By.xpath("(//div[@class='CheckBox__base'])[1]")).click();
		Thread.sleep(5000);
		
		//PRINT all watch price
		List<WebElement> watch = driver.findElements(By.xpath("//div[contains(@class,'ProductDescription__priceHolder')]"));
		System.out.println("watch prices count:" + watch.size());
		for (int i = 0; i <watch.size(); i++) 
		System.out.print(watch.get(i).getText().replaceAll("[^0-9]", "  "));
		
		
		//PRINT THE price of the SECOND watch using index --1
		String price1 = watch.get(1).getText().replaceAll("[^0-9]", " ");
		System.out.println("price in old window :" + price1);
		//Thread.sleep(5000);
		
		//click on SECOND loaded result
		driver.findElement(By.xpath("(//div[@class='ProductModule__base'])[2]")).click();
		//Handle the New Window
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> window1 =new ArrayList<String>(windowHandles);
		driver.switchTo().window(window1.get(1));
		//print the price of SECOND the watch
		String pricenew = driver.findElement(By.xpath("//div[@class='ProductDetailsMainCard__price']//h3")).getText().replaceAll("[^0-9]", " ");
		System.out.println("Second Watch Price: " + pricenew);
		
		//VERIFY THE PRICE CHANGE OR NOT
		if(price1.equals(pricenew))
			System.out.print("price is same in both th window");
		//CLICK ADD TO BAG
		driver.findElement(By.xpath("(//div[@class='Button__base'])[3]")).click();
		String cartCount = driver.findElement(By.xpath("//span[@class='DesktopHeader__cartCount']")).getText();
		 System.out.println("CartCount: " +cartCount);
		 driver.findElement(By.xpath("//div[@class='DesktopHeader__myBagShow']")).click();
		 //TAKE A SNAPSHOT
		 File source = driver.getScreenshotAs(OutputType.FILE);
		 
		File dest =new File("./snap/TATACliq.png");
		FileUtils.copyFile(source, dest);
		
		driver.close();
		driver.quit();
		
		
		
		
		
		
		
		
		
		
	}

}
