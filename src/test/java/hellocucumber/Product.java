package hellocucumber;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Product {
    public String name;
    public String originalPrice;
    public String originalPriceStyle;
    public String salePrice;
    public String salePriceStyle;
    public String link;

//    Здесь делаем функцию-конструктор продукта из веб-элемента

    public Product makeProduct(WebElement welement){
        Product product = new Product();
        this.name = welement.findElement(By.cssSelector("h4.name")).getText();
        if (welement.findElements(By.cssSelector("del.regular-price")).isEmpty()) {
            this.originalPrice = welement.findElement(By.cssSelector("span.price")).getText();
        } else {
            this.originalPrice = welement.findElement(By.cssSelector("del.regular-price")).getText();
        }
        this.originalPriceStyle = null;
        if (welement.findElements(By.cssSelector("strong.campaign-price")).isEmpty()) {
            this.salePrice = null;
        } else {
            this.salePrice = welement.findElement(By.cssSelector("strong.campaign-price")).getText();
        }
        this.salePriceStyle = null;
        this.link = welement.findElement(By.cssSelector("a.link")).getAttribute("href");

        return product;
    }
}
