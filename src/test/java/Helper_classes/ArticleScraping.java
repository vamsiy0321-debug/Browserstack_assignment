package Helper_classes;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArticleScraping {

    public static void scrape(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // call to static method to handle the cookie consent popup
        handleConsent(driver,wait);

        try{
            // call to static method to navigate to opinion section of the page
            String opinionSourceActual = openOpinionSection(driver);
            String expected = "https://elpais.com/opinion/";
            Assert.assertEquals(opinionSourceActual,expected,"Web page source is not displayed as expected");
            System.out.println("Navigated to Opinion section");
        }
        catch (Exception e){
            System.out.println("Unable to open opinion section");
        }

        List<WebElement> articles = driver.findElements(By.xpath("//section[@class='_g _g-md _g-o b b-d']/div/article//h2/a"));
        // call to static method to open first five articles
        openArticlesInTabs(driver,articles);
        // scraping each web page for the article title and the content
        String parentWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> translatedHeaders = new ArrayList<>();
        int i=-1;
        for (String handle : windowHandles) {
            i++;
            if (!handle.equals(parentWindow)) {
                driver.switchTo().window(handle);
                String articleTitle = driver.findElement(By.tagName("h1")).getText();
                System.out.println("Article "+i+" title: "+articleTitle);
                System.out.println("Article content: '''"+driver.findElement(By.xpath("//article[@id='main-content']/div[2]")).getText()+" '''");
                // call to static method for downloading cover images of articles as part of scraping
                downloadImage(driver,i);
                // call to helper class method to translate the article titles
                translatedHeaders.addAll(Translation.translate(articleTitle));
                driver.close();
            }
            System.out.println("----------------------------------------------");
        }
        driver.switchTo().window(parentWindow);
        // call to helper class method to analyse the translated headers
        AnalyzeHeaders.analyze(translatedHeaders);
    }
    // Method to handle cookie consent popup
    public static void handleConsent(WebDriver driver,WebDriverWait wait){
        WebElement acceptConsent = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='didomi-notice-agree-button']")
        ));
        acceptConsent.click();
    }
    // Method for navigation to the opinion section of the web page
    public static String openOpinionSection(WebDriver driver){
        WebElement opinionSectionEle =  driver.findElement(By.xpath("//a[@href='https://elpais.com/opinion/']"));
        opinionSectionEle.click();
        return driver.getCurrentUrl();
    }
    // Method to open articles in new tabs
    public static void openArticlesInTabs(WebDriver driver,List<WebElement> articles){
        Actions actions = new Actions(driver);
        for (WebElement article : articles) {
            actions.scrollToElement(article);
            actions.keyDown(Keys.CONTROL).click(article).keyUp(Keys.CONTROL).build().perform();
        }
    }
    // Method to download cover images of opened articles
    public static void downloadImage(WebDriver driver,int i){

        WebElement imageEle;
        String imageUrl;
        String filePath;
        try {
            imageEle = driver.findElement(By.xpath("(//span[@class='a_m_w _db']/img)[1]"));
            imageUrl = imageEle.getAttribute("src");
            filePath = "Artcile"+i+"_image"+".jpg";
            FileUtils.copyURLToFile(new URL(imageUrl), new File(filePath));
            System.out.println();
            System.out.println("-> Image downloaded for this article: "+filePath);
        } catch (Exception e) {
            System.out.println("-> Cover image unavailable or failed to download image");
        }
    }

}
