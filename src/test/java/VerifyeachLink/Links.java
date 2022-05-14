package VerifyeachLink;


import org.apache.hc.core5.http.MalformedChunkCodingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import javax.imageio.IIOException;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

class ChromeTest {

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();


    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @AfterEach
    void teardown() {
        driver.close();
        driver.quit();
    }

    @Test
    void test() {
        // test logic here
        driver.get("https://www.ebay.com/");
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println(links.size());

        for(WebElement link: links){
            String url = link.getAttribute("href");
            System.out.println(url);
            if(url==null || url.isEmpty()){
                System.out.println("URL is empty");
                continue;

            }
            HttpsURLConnection huc;
            try {
                huc = (HttpsURLConnection)(new URL(url).openConnection());
                huc.connect();
                if(huc.getResponseCode()>=400){
                    System.out.println(url + " " + "URL is broken");
                }
                else{
                    System.out.println(url + " " + "URL is valid");
                }
            } catch (MalformedChunkCodingException e) {
                e.printStackTrace();
            } catch (IIOException e){
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}