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

import java.time.Duration;
import java.util.List;

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

//    @Before
//    public void startBrowser(){
//        System.setProperty("webdriver.edge.driver", "C:/Tools/msedgedriver.exe");
//
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setBrowserName("MicrosoftEdge");
//        capabilities.setPlatform(Platform.WINDOWS);
//        capabilities.setVersion("111.0.1661.51");
//
//        EdgeOptions edgeOptions = new EdgeOptions();
//        edgeOptions.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
//
//        driver = new EdgeDriver(edgeOptions);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//    }

//    @Before
//    public void setup(){
//        System.setProperty("webdriver.gecko.driver", "C:/Tools/geckodriver.exe");
//
//        FirefoxOptions ffOptions = new FirefoxOptions();
//        ffOptions.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
//
//        driver = new FirefoxDriver(ffOptions);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//    }



    @Given("I am on the shop admin login page")
    public void I_visit_admin_page() {
        driver.get("http://localhost/litecart/admin/");
    }

    @Given ("I am logged into shop admin page")
    public void login_admin(){
            I_visit_admin_page();
            enter_creds();
            checkTitle();
    }

    @When ("I go through menu")
    public List<WebElement> get_menu_elements(){
            List<WebElement> menuList = driver.findElements(By.cssSelector("div#sidebar li a"));

            return menuList;
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

    @Then ("Every title has a header")
    public void checkHeaders() {
        List<WebElement> elements = get_menu_elements();

            for (int i = 0; i < elements.size(); i++ ){
                elements = get_menu_elements();
                WebElement element = elements.get(i);
//                System.out.println(element.findElement(By.cssSelector("span.name")).getText());
                element.click();
//                synchronized (driver){
//                    try {driver.wait(3000); }
//                catch (InterruptedException e) {
//                    System.out.println(e.getMessage());
//                }}
                assert driver.findElement(By.cssSelector(".card-header")).isDisplayed();
            }
    }


    @After
    public void close(){
        driver.quit();
    }

}
