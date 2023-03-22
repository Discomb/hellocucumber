package hellocucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ShopSteps {
    private WebDriver driver;

    @Before
    public void startBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe");
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");

    }

    @Given("I am on the shop admin login page")
    public void I_visit_admin_page() {
        driver.get("http://localhost/litecart/admin/");
    }

    @When("I enter credentials")
    public void enter_creds() {
        WebElement element = driver.findElement(By.name("username"));

        element.sendKeys("admin");

        element = driver.findElement(By.name("password"));

        element.sendKeys("admin");

        element.submit();

    }

    @Then("I am logged in")
    public void checkTitle() {
        WebElement element = driver.findElement(By.xpath("//*[@id='box-apps-menu']/li[1]/a"));
        element.click();
    }

    @After
    public void close(){
        driver.quit();
    }
}