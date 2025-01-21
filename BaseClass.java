package baseclass;

import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties locators = new Properties();
	public static FileReader fr1;
	public static FileReader fr2;

	public WebDriver getdriver() {
		return driver;
	}

	public void setup() throws Exception {
		if (driver == null) {
			String directory = System.getProperty("user.dir");
			System.out.println("The project part is " + directory);

			FileReader fr1 = new FileReader(
					directory + "//src//test//resources//configfiles//configuration.properties");
			FileReader fr2 = new FileReader(directory + "//src//test//resources//configfiles//locators.properties");
			config.load(fr1);
			locators.load(fr2);
		}
		if (config.getProperty("browser").equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
			driver.get(config.getProperty("testurl"));
			String currenturl = driver.getCurrentUrl();
			String expectedurl = "https://xtratheme.com/book-shop/";
			assertEquals(currenturl, expectedurl);

		} else if (config.getProperty("browser").equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--disable-notifications");
			driver = new EdgeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
			driver.get(config.getProperty("testurl"));
			String currenturl = driver.getCurrentUrl();
			String expectedurl = "https://xtratheme.com/book-shop/";
			assertEquals(currenturl, expectedurl);
		} else if (config.getProperty("browser").equalsIgnoreCase("Safari")) {
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
			driver.get(config.getProperty("testurl"));
			String currenturl = driver.getCurrentUrl();
			String expectedurl = "https://xtratheme.com/book-shop/";
			assertEquals(currenturl, expectedurl);
		}
	}
	
	public void close() throws Exception {
		Thread.sleep(1000);
		driver.close();
	}
	
}
