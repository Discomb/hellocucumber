package hellocucumber;

import org.checkerframework.checker.nullness.qual.NonNull;
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
        product.name = welement.findElement(By.cssSelector("h4.name")).getText();
        if (welement.findElements(By.cssSelector("del.regular-price")).isEmpty()) {
            product.originalPrice = welement.findElement(By.cssSelector("span.price")).getText();
        } else {
            product.originalPrice = welement.findElement(By.cssSelector("del.regular-price")).getText();
        }
        product.originalPriceStyle = null;
        if (welement.findElements(By.cssSelector("strong.campaign-price")).isEmpty()) {
            product.salePrice = null;
        } else {
            product.salePrice = welement.findElement(By.cssSelector("strong.campaign-price")).getText();
        }
        product.salePriceStyle = null;
        product.link = welement.findElement(By.cssSelector("a.link")).getAttribute("href");

        return product;
    }

    public String getName() {
        return this.name;
    }

    public String getLink(){
        return this.link;
    }

    public String getOriginalPrice() {
        return this.originalPrice;
    }

    public String getSalePrice() {
        return this.salePrice;
    }
}
