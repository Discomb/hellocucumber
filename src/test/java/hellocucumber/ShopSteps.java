package hellocucumber;

import com.google.common.collect.Ordering;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopSteps {



    private WebDriver driver;

        @Before
    public void startBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe");
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
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
    public void iVisitAdminPage() {
        driver.get("http://localhost/litecart/admin/");
    }

    @Given ("I am logged into shop admin page")
    public void loginAdmin(){
            iVisitAdminPage();
            enterCreds();
            checkTitle();
    }

    @Given("I am on the main shop page")
    public void iVisitShopMainPage(){
        driver.get("http://localhost/litecart/");
    }

    @Given ("I am on the Countries tab of admin page")
    public void iVisitCountriesTab(){
         loginAdmin();

         driver.findElement(By.cssSelector("div#sidebar li[data-code ='countries'] a")).click();

         Assertions.assertEquals(driver.findElement(By.cssSelector("div.card-title")).getText(), "Countries");
    }

    @When ("I go through menu")
    public List<WebElement> getMenuElements(){
            List<WebElement> menuList = driver.findElements(By.cssSelector("div#sidebar li a"));

            return menuList;
    }

    @When("I enter credentials")
    public void enterCreds() {
        WebElement element = driver.findElement(By.name("username"));

        element.sendKeys("admin");

        element = driver.findElement(By.name("password"));

        element.sendKeys("admin");

        element.submit();

    }

    @When ("I look on product images")
    public List<WebElement> getProducts(){
        List<WebElement> productList = driver.findElements(By.cssSelector("article.product"));

        return productList;
    }

    @When ("I check sorting of the countries by name")
    public List<WebElement> getCountries(){
            List<WebElement> countriesList = driver.findElements(By.cssSelector((" tbody tr")));

//        form[name='countries_form']

            return countriesList;
    }

    @When ("I check if country has multiple zones")
//    Отдаем стринговый список имен стран для последующего сравнения
    public List<String> getCountriesWithZones(){
            List<WebElement> countriesList = getCountries();
            List<String> countriesWithZones = new ArrayList<>();

        for (int i = 0; i < countriesList.size(); i++) {
            if (Integer.parseInt(countriesList.get(i).findElement(By.cssSelector("td.text-center")).getText()) > 0) {
                countriesWithZones.add(countriesList.get(i).findElement(By.cssSelector("a")).getText());
//                System.out.println(countriesList.get(i).findElement(By.cssSelector("a")).getText());
            }
        }

            return countriesWithZones;
    }

    @When ("I check product attributes on the main page")
    public List<Product> getProductAttributes(){

//        TODO:


//        1. Получаем список веб.элементов, которыый содержит всю нужную нам информацию о продукте
            List<WebElement> wlist = getProducts();
            List<Product> plist= new ArrayList<>();

//        2. Циклом из каждого элемента создаем объект класса Продукт и наполняем его данными.
            for (int i = 0; i < wlist.size(); i++){
                Product product = new Product();
                plist.add(product.makeProduct(wlist.get(i)));

//                System.out.println(product.name);
//                System.out.println(product.link);
//                System.out.println(product.originalPrice);
//                System.out.println(product.salePrice);

            }

//        3. Возвращаем список объектов класса Продукт
//
        return plist;
    }

    @Then ("The price and name are the same on the product page")
    public void checkProdNameAndPricesAndStyles() {
            List<Product> plist = getProductAttributes();
            List<String> origStyle = new ArrayList<>();
            List<String> saleStyle = new ArrayList<>();

            for (int i = 0; i < plist.size(); i++){
                Product product = plist.get(i);
                driver.get(Objects.requireNonNull(plist.get(i).getLink()));

                Assertions.assertEquals(product.getName(), driver.findElement(By.cssSelector("h1.title")).getText());
//                Здесь иф на случай отсутствия акционной цены
                if (driver.findElements(By.cssSelector("del.regular-price")).isEmpty()) {
                    origStyle = getProductPagePricesStyle("span.price");
                    Assertions.assertEquals(product.getOriginalPrice(), driver.findElement(By.cssSelector("span.price")).getText());
//                    System.out.println(product.getOriginalPriceStyle());
//                    System.out.println(origStyle);
                    Assertions.assertEquals(product.getOriginalPriceStyle(),origStyle);
                } else {
                    origStyle = getProductPagePricesStyle("del.regular-price");
                    saleStyle = getProductPagePricesStyle("strong.campaign-price");
                    Assertions.assertEquals(product.getOriginalPrice(), driver.findElement(By.cssSelector("del.regular-price")).getText());
                    Assertions.assertEquals(product.getSalePrice(), driver.findElement(By.cssSelector("strong.campaign-price")).getText());
//                    System.out.println(product.getOriginalPriceStyle());
//                    System.out.println(origStyle);
//                    System.out.println(product.getSalePriceStyle());
//                    System.out.println(saleStyle);
                    Assertions.assertEquals(product.getOriginalPriceStyle(), origStyle);
                    Assertions.assertEquals(product.getSalePriceStyle(), saleStyle);
                }
            }

    }

    public List<String> getProductPagePricesStyle(String priceType){
            List<String> newPriceStylesList = new ArrayList<>();
//       В лист стилей пихаем font-weight, font-size, color, text-decoration
            newPriceStylesList.add(driver.findElement(By.cssSelector(priceType)).getCssValue("font-weight"));
//            newPriceStylesList.add(driver.findElement(By.cssSelector(priceType)).getCssValue("font-size"));
            newPriceStylesList.add(driver.findElement(By.cssSelector(priceType)).getCssValue("color"));
            newPriceStylesList.add(driver.findElement(By.cssSelector(priceType)).getCssValue("text-decoration"));

            return newPriceStylesList;
    }

//    @Then ("They have the same style on product page")



//    public void comparePricesStyles(List<String> mainPage, List<String> productPage){
//            System.out.println(mainPage);
//            System.out.println(productPage);
//            assert mainPage.equals(productPage);
//    }

    @Then ("Zones in those countries are sorted by name from A to Z")
    public void checkZonesSortingByName() {
        List<String> countriesList = getCountriesWithZones();
        List<String> zonesNames = new ArrayList<>();

        for (int i = 0; i < countriesList.size(); i++ ){
//            DOM меняется при возврату к странице со странами, следовательно, вебэлемент сохраненный в списке уже устарел.
//               Следовательно, можно не держать список вебэлементов, а держать список стринговых имен нужных стран
//               И оттуда уже подстановкой названия находить на странице нужные элементы

            driver.findElement(By.xpath("//a[contains(text(), '" + countriesList.get(i) +"')]")).click();

            List<WebElement> zonesList = driver.findElements(By.cssSelector("tbody input[name*='name']"));


            for (int j = 0 ; j < zonesList.size(); j++ ){
                zonesNames.add(zonesList.get(j).getAttribute("value"));
//                System.out.println(zonesList.get(j).getAttribute("value"));
            }

            Assertions.assertTrue(Ordering.natural().isOrdered(zonesNames));

            zonesNames.clear();

            driver.findElement(By.cssSelector("li[data-code*='countries'] a")).click();
        }

    }

    @Then ("Countries sorted by name from A to Z")
    public void checkCountriesSortingByName(){
            List<WebElement> countriesList = getCountries();
            List<String> countries = new ArrayList<>();

            for (int i = 0; i < countriesList.size(); i++){
                String country = countriesList.get(i).findElement(By.cssSelector("a.link")).getText();
                countries.add(country);
            }

            Assertions.assertTrue(Ordering.natural().isOrdered(countries));
    }

    @Then ("I see that every product has only one sticker")
    public void checkStickers(){
            List<WebElement> products = getProducts();

            for (int i = 0; i < products.size(); i++){
                WebElement product = products.get(i);
//                System.out.println(product.findElement(By.cssSelector("h4.name")).getText());
//                System.out.println(product.findElement(By.cssSelector("div.sticker")).getText());

//                                synchronized (driver){
//                    try {driver.wait(3000); }
//                catch (InterruptedException e) {
//                    System.out.println(e.getMessage());
//                }}

                Assertions.assertEquals(product.findElements(By.cssSelector("div.sticker")).size(), 1);
            }

    }


    @Then("I am logged in")
    public void checkTitle() {
            WebElement element = driver.findElement(By.xpath("//*[@id='box-apps-menu']/li[1]/a"));
        element.click();
    }

    @Then ("Every title has a header")
    public void checkHeaders() {
        List<WebElement> elements = getMenuElements();

            for (int i = 0; i < elements.size(); i++ ){
                elements = getMenuElements();
                WebElement element = elements.get(i);
//                System.out.println(element.findElement(By.cssSelector("span.name")).getText());
                element.click();
//                synchronized (driver){
//                    try {driver.wait(3000); }
//                catch (InterruptedException e) {
//                    System.out.println(e.getMessage());
//                }}
                Assertions.assertTrue(driver.findElement(By.cssSelector(".card-header")).isDisplayed());
            }
    }


    @After
    public void close(){
        driver.quit();
    }

}
