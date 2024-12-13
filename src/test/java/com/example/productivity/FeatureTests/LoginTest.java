package com.example.productivity.FeatureTests;

import com.microsoft.playwright.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


@SpringBootTest
public class LoginTest {
    Page page;
    Faker faker;
    Playwright playwright;
    BrowserContext context;

    @BeforeEach
    public void setup() {//
        faker = new Faker();
        playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
        page = context.newPage();
        page.navigate("http://localhost:8080/meezchurger");//gets sent to login page
    }

    @AfterEach
    public void tearDown() {
        playwright.close();
    }

    @Test
    public void successfulSignUpAlsoLogsInUser() throws InterruptedException {
        page.setDefaultTimeout(5000);
        page.getByText("Sign up").click();
        String email = faker.name().firstName() + faker.name().lastName() + "@email.com";

//        page.screenshot(new Page.ScreenshotOptions()
//                .setPath(Paths.get("screenshot0.png"))
//                .setFullPage(true));

        page.locator("#email").fill(email);
        page.locator("#password").fill("P@s5W0rd");


        page.getByText("Continue").nth(1).click();


        page.getByText("Accept").click();
        TimeUnit.SECONDS.sleep(1);
//        System.out.println(page.url());
//        page.screenshot(new Page.ScreenshotOptions()
//                .setPath(Paths.get("screenshot1.png"))
//                .setFullPage(true));

//        System.out.println(page.content().toLowerCase());
        if (page.content().toLowerCase().contains("error")){
            throw new AssertionError();
        }
    }
}
