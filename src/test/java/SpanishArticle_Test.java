import Helper_classes.ArticleScraping;
import Helper_classes.VisitUrl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class SpanishArticle_Test {

    private WebDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setPlatformName("Windows");
        driver = new RemoteWebDriver(options);
        String url = "https://elpais.com/";
        String expected = "es-ES";
        VisitUrl.openUrl(driver,url,expected);


    }

    @Test
    public void testArticleScraping() throws InterruptedException {
        ArticleScraping.scrape(driver);
    }
    @AfterClass
    public void tearDown(){
        if (driver!=null){
            driver.quit();
        }
    }
}

