package com.kirillkabylov.NauJava;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginLogoutSeleniumTest {
    WebDriver driver;
    WebDriverWait wait;

    @LocalServerPort
    int port;

    @BeforeAll
    static void setUp(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void loginAndLogoutTest(){
        driver.get("http://localhost:" + port + "/login");

        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username"))
        );
        usernameField.sendKeys("TestUser1");
        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("password"))
        );
        passwordField.sendKeys("123123");

        WebElement loginButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("primary"))
        );
        loginButton.click();

        WebElement nameLabel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );
        Assertions.assertEquals("Добро пожаловать, Alica Alisovna!", nameLabel.getText());

        driver.get("http://localhost:" + port + "/logout");
        WebElement logoutButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("primary"))
        );
        logoutButton.click();

        WebElement loginPageUsernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username"))
        );

        Assertions.assertTrue(loginPageUsernameField.isDisplayed());
    }
}
