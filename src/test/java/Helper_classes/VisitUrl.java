package Helper_classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class VisitUrl {

    public static void openUrl(WebDriver driver, String url,String expected){

        try {
            driver.get(url);
            String actual = driver.findElement(By.tagName("html")).getAttribute("lang");
            Assert.assertEquals(actual,"es-ES","Web page language does not match expected");
            System.out.println("Page loaded with language: "+actual);
        } catch (Exception e) {
            System.out.println("Unable to open url "+e.getMessage());
        }


    }
}
