package hellocucumber;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Product {
    public String name;
    public String originalPrice;
    public List<String> originalPriceStyle ;
    public String salePrice;
    public List<String> salePriceStyle;
    public String link;

    private List<String> getPriceStyle(Product product, WebElement welement, String priceType){
        List<String> newPriceStyle = new ArrayList<>();

        //          В лист стилей пихаем font-weight, font-size, color, text-decoration
        newPriceStyle.add(welement.findElement(By.cssSelector(priceType)).getCssValue("font-weight"));
//        newPriceStyle.add(welement.findElement(By.cssSelector(priceType)).getCssValue("font-size"));
        newPriceStyle.add(welement.findElement(By.cssSelector(priceType)).getCssValue("color"));
        newPriceStyle.add(welement.findElement(By.cssSelector(priceType)).getCssValue("text-decoration"));

        return newPriceStyle;
    }


//    Здесь делаем функцию-конструктор продукта из веб-элемента

    public Product makeProduct(WebElement welement){
        Product product = new Product();
        product.name = welement.findElement(By.cssSelector("h4.name")).getText();
        if (welement.findElements(By.cssSelector("del.regular-price")).isEmpty()) {
            product.originalPrice = welement.findElement(By.cssSelector("span.price")).getText();
            product.originalPriceStyle = getPriceStyle(product, welement, "span.price");
        } else {
            product.originalPrice = welement.findElement(By.cssSelector("del.regular-price")).getText();
            product.originalPriceStyle = getPriceStyle(product, welement, "del.regular-price");
        }
        if (welement.findElements(By.cssSelector("strong.campaign-price")).isEmpty()) {
            product.salePrice = null;
        } else {
            product.salePrice = welement.findElement(By.cssSelector("strong.campaign-price")).getText();
            product.salePriceStyle = getPriceStyle(product, welement, "strong.campaign-price");
        }
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

    public List<String> getOriginalPriceStyle() {
        return originalPriceStyle;
    }

    public List<String> getSalePriceStyle() {
        return salePriceStyle;
    }
}
