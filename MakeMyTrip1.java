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

public class MakeMyTrip1{

	public static void main(String[] args) throws InterruptedException, IOException {
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--disable-notification");
		ChromeDriver driver = new ChromeDriver(opt);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		Actions act = new Actions(driver);

		
		driver.get("https://www.makemytrip.com/");

		//Close the Sweet Alert.
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='webengage-notification-container']/iframe")));
		//System.out.println("Inside Frame");
		driver.findElement(By.className("close")).click();
		driver.switchTo().defaultContent();

		//Click on the Makemytrip logo.

		WebElement logo = driver.findElement(By.xpath("//img[@alt='Make My Trip']"));
		driver.executeScript("arguments[0].click();",logo);
		driver.findElement(By.xpath("//span[text()='Holiday Packages']")).click();

		driver.findElement(By.id("fromCity")).click();
        driver.findElement(By.xpath("//li[text()='Chennai']")).click();
		driver.findElement(By.id("toCity")).click();
		driver.findElement(By.xpath("(//div[@class='dest-city-name'])[2]")).click();

		//Pick a date from the calendar.
		driver.findElement(By.xpath("(//div[@class='DayPicker-Day'])[1]")).click();
        //Adjust the number of adults to the maximum allowed.
		driver.findElement(By.xpath("//div[@data-testid='adult-increment-counter']")).click();
        //Click on the apply button. 
		driver.findElement(By.className("applyBtn")).click();

		//Set the trip duration to 3-5 nights.
		WebElement start = driver.findElement(By.xpath("//div[@class='rc-slider-handle rc-slider-handle-1']"));
		act.dragAndDropBy(start, 66, 0).build().perform();
		WebElement end = driver.findElement(By.xpath("//div[@class='rc-slider-handle rc-slider-handle-2']"));
		act.dragAndDropBy(end, -198, 0).build().perform();

		//Click on the apply button
		WebElement apply = driver.findElement(By.xpath("//button[text()='APPLY']"));
		driver.executeScript("arguments[0].click();", apply);

		//Click on the search button.
		 driver.findElement(By.xpath("//button[text()='Search']")).click();
		

		//Skip an automated assistance prompt.
		driver.findElement(By.xpath("//button[text()='SKIP']")).click();
		driver.findElement(By.xpath("//span[@class='close closeIcon']")).click();

		//Capture a screenshot of the first vacation package.
		act.scrollToElement(driver.findElement(By.xpath("//h2[text()='Go for Land Only Goa Packages!']"))).build().perform();
		File source1 = driver.getScreenshotAs(OutputType.FILE);
		File target1 = new File("./snap/allpackages.png");
		FileUtils.copyFile(source1, target1);
		
       //click on first package image and handle new window
		driver.findElement(By.xpath("(//div[@class='sliderCard'])[1]//img")).click();
		driver.findElement(By.xpath("//div[text()='With Flight']")).click();
		Set<String> packages = driver.getWindowHandles();
		List<String> holiday = new ArrayList<String>(packages);
		driver.switchTo().window(holiday.get(1));
		driver.findElement(By.xpath("//button[text()='SKIP']")).click();

		File source2 = driver.getScreenshotAs(OutputType.FILE);
		File target2 = new File("./snap/makemytrip.png");
		FileUtils.copyFile(source2, target2);

		//Print the current page title.
		System.out.println("Title - " + driver.getTitle());

		// Close the web browser.
		driver.close();
		driver.switchTo().window(holiday.get(0));
		driver.close();


	}

}