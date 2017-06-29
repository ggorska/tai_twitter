package hello.controller;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class HelloControllerTest {
    @Test
    public void clickSubmitForm() throws MalformedURLException, URISyntaxException {
        File file = new File("C:\\Bisia\\6sem\\TAI\\tai\\src\\geckodriver-v0.17.0-win64\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8080/");
        WebElement element = driver.findElement(By.id("submit"));
        WebElement filter = driver.findElement(By.id("filter"));
        filter.sendKeys("ala");
        WebElement limit = driver.findElement(By.id("limit"));
        limit.sendKeys("10");
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("results")));

        String url = driver.getCurrentUrl();
        assertTrue(url.contains("search=ala"));
        assertTrue(url.contains("limit=10"));

        driver.close();
    }
}