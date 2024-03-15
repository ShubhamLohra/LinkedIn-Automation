package Prod_Conversation_Count;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LinkedIn_Automation {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\SALESKEN\\selenium\\chromedriver.exe");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		chromeOptions.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();

//		Open LinkedIN
		driver.get("https://www.linkedin.com/login");
		System.out.println("Opened LinkedIn");

//		Enter Email, Pass and login
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Wait up to 10 seconds
		driver.findElement(By.id("username")).sendKeys("officialshubhamlohra@gmail.com");
		driver.findElement(By.id("password")).sendKeys("Bhurkunda@12345");
		driver.findElement(By.xpath("//button[text()='Sign in']")).click();
		System.out.println("Logged In Successfuly");

//		close the chats
		Thread.sleep(2000);
		driver.findElement(By.xpath("//header[@class='msg-overlay-bubble-header']")).click();


//	Search jobs according to the experience and easy apply enabled
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Wait up to 10 seconds
		driver.findElement(By.xpath("//span[text()='Jobs']")).click();
		WebElement inputElement = driver.findElement(By.xpath("//input[contains(@id,'search-box-key')]"));
//		Enter testing as keyword
		inputElement.sendKeys("Testing");
		inputElement.sendKeys(Keys.ENTER);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Wait up to 10 seconds
		System.out.println("Testing as keyword is entered");
//		Enter Experience
		driver.findElement(By.id("searchFilter_experience")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Associate']")).click();
		Thread.sleep(1000);
		List<WebElement> showResults = driver.findElements(By.xpath(
				"//button[@data-test-reusables-filter-apply-button='true']//span[@class='artdeco-button__text']"));
		Thread.sleep(1000);
		showResults.get(1).click();
		System.out.println("Experience is selected");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Wait up to 10 seconds
//		Enter Easy apply
		driver.findElement(By.xpath("//button[text()='Easy Apply']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Wait up to 10 seconds
		System.out.println("Easy apply is selected");

//scroll slowly the job cards		
	       WebElement element = driver.findElement(By.className("scaffold-layout__list-container"));

	        // Create Actions instance
	        Actions actions = new Actions(driver);

	        // Move to the element to make it visible
	        actions.moveToElement(element).perform();

	        // Get the current Y position of the element
	        int currentY = element.getLocation().getY();

	        // Scroll slowly through the element
	        for (int i = 0; i < 10; i++) {
	            // Scroll by a small offset
	            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, arguments[0])", currentY + i * 10);
	            // Add a small delay
	            try {
	                Thread.sleep(500); // Adjust the delay as needed
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
		
//			Check number of job cards
			List<WebElement> jobCards = driver
					.findElements(By.xpath("//div[contains(@class,'job-card-container--clickable')]"));
			System.out.println("jobCards size : "+jobCards.size());
//			job title			
			List<WebElement>jobTitle = driver.findElements(By.xpath("//a[@class='disabled ember-view job-card-container__link job-card-list__title']//strong"));

	
//		Check for the next pages
		List<WebElement> nextPage = driver.findElements(
				By.xpath("//ul[@class='artdeco-pagination__pages artdeco-pagination__pages--number']//li"));
		System.out.println("Number of pages : "+ nextPage.size());

//		Apply jobs
		for (int k = 0; k < nextPage.size(); k++) {
			System.out.println("entered k loop");
			for (int j = 0; j < jobCards.size(); j++) {
				Thread.sleep(2000);
				System.out.println("entered j loop");
				String jobTitleText = jobTitle.get(j).getText();
				if(j > 0 && (jobTitleText.contains("Test") || jobTitleText.contains("QA"))) {
					System.out.println(jobTitleText+" Job card is clicked");
					jobCards.get(j).click();
				}
//				Job status (Applied or not)
				try {
					System.out.println("Inside try of job applied or not");
					WebElement appliedJob = driver
							.findElement(By.xpath("//span[@class='artdeco-inline-feedback__message']"));
					if (appliedJob.getText().contains("Applied")) {
						System.out.println(jobTitleText+" job is already applied");
						continue;
					}
				} catch (Exception e) {
					System.out.println("inside catch of job applied or not");
				}

				driver.findElement(By.xpath("//button[contains(@class,'jobs-apply-button')]")).click();
				System.out.println("clicked on Easy Apply button");
				Thread.sleep(1000);
				try {
				driver.findElement(By.xpath("//button[@aria-label='Continue to next step']")).click();
				Thread.sleep(1000);
				String progress =	driver.findElement(By.xpath("//span[contains(@aria-label,'progress')]")).getText();
				if(progress.contains("50")) {
//					Click on review button
					Thread.sleep(1000);
					driver.findElement(By.xpath("//span[text()='Review']")).click();

//					Click on submit button
					Thread.sleep(1000);
					driver.findElement(By.xpath("//span[text()='Submit application']")).click();

//					Click on Done Button
					Thread.sleep(1000);
					driver.findElement(By.xpath("//span[text()='Done']")).click();
					System.out.println("Job - "+jobTitleText+" is applied");
					
					continue;
				}
				driver.findElement(By.xpath("//button[@aria-label='Continue to next step']")).click();
//				Enter Experience
				List<WebElement> enterExperience = driver.findElements(By.className("artdeco-text-input--input"));

				for (int i = 0; i < enterExperience.size(); i++) {
					Thread.sleep(1000);
					enterExperience.get(i).sendKeys("3");
				}

				List<WebElement> multipleChoice = driver.findElements(By.xpath("//select"));

				for (int i = 0; i < multipleChoice.size(); i++) {
					Thread.sleep(1000);
					multipleChoice.get(i).click();
					driver.findElement(By.xpath("//*[@value='Yes']")).click();
				}

//				Click on review button
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[text()='Review']")).click();

//				Click on submit button
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[text()='Submit application']")).click();

//				Click on Done Button
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[text()='Done']")).click();
				System.out.println("Job - "+jobTitleText+" is applied");
			}catch(Exception e) {
				driver.findElement(By.xpath("//button[@aria-label='Dismiss']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[@data-control-name='discard_application_confirm_btn']")).click();
			}
			}

			nextPage.get(k).click();

		}

	}

}
