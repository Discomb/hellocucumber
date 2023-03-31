package hellocucumber;

import com.google.common.collect.Comparators;
import com.google.common.collect.Ordering;
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

    @Given("I am on the main shop page")
    public void I_visit_shop_main_page(){
        driver.get("http://localhost/litecart/");
    }

    @Given ("I am on the Countries tab of admin page")
    public void I_visit_Countries_tab(){
         login_admin();

         driver.findElement(By.cssSelector("div#sidebar li[data-code ='countries'] a")).click();

         assert driver.findElement(By.cssSelector("div.card-title")).getText().equals("Countries");
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

    @When ("I look on product images")
    public List<WebElement> get_products(){
        List<WebElement> productList = driver.findElements(By.cssSelector("article.product"));

        return productList;
    }

    @When ("I check sorting of the countries by name")
    public List<WebElement> get_countries(){
            List<WebElement> countriesList = driver.findElements(By.cssSelector((" tbody tr")));

//        form[name='countries_form']

            return countriesList;
    }

    @When ("I check if country has multiple zones")
//    Отдаем стринговый список имен стран для последующего сравнения
    public List<String> get_countries_with_zones(){
            List<WebElement> countriesList = get_countries();
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
    public List<Product> get_product_attributes(){

//        TODO:


//        1. Получаем список веб.элементов, которыый содержит всю нужную нам информацию о продукте
            List<WebElement> wlist = get_products();
            List<Product> plist= new ArrayList<>();

//        2. Циклом из каждого элемента создаем объект класса Продукт и наполняем его данными.
            for (int i = 0; i < wlist.size(); i++){
                Product product = new Product();
                plist.add(product.makeProduct(wlist.get(i)));

                System.out.println(product.name);
                System.out.println(product.link);
                System.out.println(product.originalPrice);
                System.out.println(product.salePrice);

            }


//        3. Возвращаем список объектов класса Продукт
//
        return plist;
    }

    @Then ("They are the same on the product page")
//    TODO: вызываем конструктор продуктов и по каждому продукту ходим внутрь сравнивать заголовок и цены


    @Then ("Zones in those countries are sorted by name from A to Z")
    public void check_zones_sorting_by_name() {
        List<String> countriesList = get_countries_with_zones();
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

            assert Ordering.natural().isOrdered(zonesNames);

            zonesNames.clear();

            driver.findElement(By.cssSelector("li[data-code*='countries'] a")).click();
        }

    }

    @Then ("Countries sorted by name from A to Z")
    public void check_countries_sorting_by_name(){
            List<WebElement> countriesList = get_countries();
            List<String> countries = new ArrayList<>();

            for (int i = 0; i < countriesList.size(); i++){
                String country = countriesList.get(i).findElement(By.cssSelector("a.link")).getText();
                countries.add(country);
            }

            assert Ordering.natural().isOrdered(countries);
    }

    @Then ("I see that every product has only one sticker")
    public void check_stickers(){
            List<WebElement> products = get_products();

            for (int i = 0; i < products.size(); i++){
                WebElement product = products.get(i);
//                System.out.println(product.findElement(By.cssSelector("h4.name")).getText());
//                System.out.println(product.findElement(By.cssSelector("div.sticker")).getText());

//                                synchronized (driver){
//                    try {driver.wait(3000); }
//                catch (InterruptedException e) {
//                    System.out.println(e.getMessage());
//                }}

                assert product.findElements(By.cssSelector("div.sticker")).size() == 1;
            }

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
