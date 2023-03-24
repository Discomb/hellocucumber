package hellocucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;



import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;


public class ExampleSteps {

    private WebDriver driver;

//    @Before
//    public void startBrowser() {
//        System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe");
//        System.setProperty("webdriver.http.factory", "jdk-http-client");
//
//        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--remote-allow-origins=*");
//
//    }

//    @Before
//    public void startBrowser(){
//        System.setProperty("webdriver.edge.driver", "C:/Tools/msedgedriver.exe");
//
//
//        driver = new EdgeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//    }

    @Given("I am on the Google search page")
    public void I_visit_google() {
        driver.get("https://www.google.com");
    }

    @When("I search for {string}")
    public void search_for(String query) {
        WebElement element = driver.findElement(By.name("q"));

        element.sendKeys(query);

        element.submit();
    }

    @Then("the page title should start with {string}")
    public void checkTitle(String titleStartsWith) {
        Duration duration = Duration.of(10, ChronoUnit.SECONDS);
        new WebDriverWait(driver, duration).until((ExpectedCondition<Boolean>) d -> d.getTitle().toLowerCase().startsWith(titleStartsWith));
    }

//    @After
//    public void close(){
//        driver.quit();
//    }
}
