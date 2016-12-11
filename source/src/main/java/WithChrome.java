import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Лешка on 08.12.2016.
 */
public class WithChrome {
    public static void main(String[] args) {
        String property = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", property);
        WebDriver driver = new ChromeDriver();
        WebDriver.Options options = driver.manage();
        options.timeouts().implicitlyWait(9, TimeUnit.SECONDS);
        driver.get("http://www.bing.com/");
        options.window().maximize();
        driver.findElement(By.cssSelector("#scpl1")).click();                            //2
        WebDriverWait explWait = new WebDriverWait(driver, 7);
        explWait.until(ExpectedConditions.titleIs("Лента изображений Bing"));

        JavascriptExecutor jse = (JavascriptExecutor) driver;    //3
        for (int i=1; i<=3; i++) {
            List<WebElement> list = driver.findElements(By.xpath("//*[@class= 'iuscp varh']"));
            jse.executeScript("window.scrollBy(0,2000)");
            explWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//*[@class= 'iuscp varh']"), list.size()));
        }

        WebElement field = driver.findElement(By.cssSelector("#sb_form_q"));          //4
        jse.executeScript("window.scrollTo(0,sb_form_q.offsetTop)");
//        Actions actions = new Actions(driver);
//        actions.moveToElement(field);
//        explWait.until(ExpectedConditions.visibilityOf(field));
        field.sendKeys("automatio");
        explWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(field, By.xpath("//*[@id='sa_ul']/li[3]")));
        driver.findElement(By.xpath("//*[@id='sa_ul']/li[3]")).click();
        explWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("dg_u")));

//        List<WebElement> images = driver.findElements(By.className("dg_u"));
        driver.findElement(By.xpath("//*[@id=\"ftrB\"]/ul/li[6]")).click();          //5
        driver.findElement(By.xpath("//*[text()='В прошлом месяце']")).click();
        explWait.until(ExpectedConditions.presenceOfElementLocated(By.className("dg_u")));

        driver.findElement(By.cssSelector("#dg_c > div > div > div:nth-child(1)")).click();           //6
        explWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("automation — Изображения Bing — сведения"));
//        driver.switchTo().frame("automation — Изображения Bing — сведения");

        WebElement line = driver.findElement(By.cssSelector("#iol_fsc"));                 //7
        String atrStyle = line.getAttribute("style");
        WebElement right = driver.findElement(By.cssSelector("#iol_navr > svg"));
//        explWait.until(ExpectedConditions.elementToBeClickable(right));
        right.click();              //проверка что атрибут размещения изменился:
        explWait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(line, "style", atrStyle)));


        WebElement left = driver.findElement(By.xpath("//*[@id=\"iol_navl\"]"));
        atrStyle=line.getAttribute("style");
        left.click();
        explWait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(line, "style", atrStyle)));

        driver.findElement(By.xpath("//*[@id=\"iol_imw\"]/div[1]/span/span/img")).click();      //8
        explWait.until(ExpectedConditions.numberOfWindowsToBe(2));

        driver.close();
        driver.quit();
    }
}
