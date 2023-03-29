package com;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DataCollection {
    public static final String DRIVER = "webdriver.chrome.driver";
    public static final String DRIVER_PATH = "src/main/resources/chromedriver";

    private WebDriver driver;

    @BeforeTest
    public void initDriver() {
        System.setProperty(DRIVER, DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void collectSchedule() throws InterruptedException {
        driver.get("http://elct.lnu.edu.ua/rozk/");
        driver.switchTo().frame("desctop");
        WebElement element = driver.findElement(By.xpath("//input[@name='search']"));
        element.click();
        Thread.sleep(5000);
        Map<String, String> data = new HashMap<>();
        String locator = "(//table[@bgcolor='black']/tbody/tr/td/table/tbody/tr)";
        List<String> results = new ArrayList();
        String pairNumber = null;
        String nameAndTeacher = null;
        String fullWeekday = null;
        String group = null;
        List<Map<String, String>> cellContent = new ArrayList();
        for (int column = 2; column < 47; column++) {
            for (int row = 1; row < 36; row++) {
                nameAndTeacher = "";
                pairNumber = "";
                try {
                    pairNumber = driver
                            .findElement(By.xpath(locator + "[" + row + "]/td[1]"))
                            .getText();
                    nameAndTeacher = driver
                            .findElement(By.xpath(locator + "[" + row + "]/td[" + column + "]"))
                            .getText();
                } catch (NoSuchElementException e) {
                    fullWeekday = driver
                            .findElement(By.xpath(locator + "[" + row + "]/th[1]"))
                            .getText();
                    group = driver.findElement(By.xpath(locator + "[" + row + "]/th[" + column + "]"))
                            .getText().substring(4);
                }
                if (nameAndTeacher != null && !nameAndTeacher.isEmpty()) {
                    results = Arrays.stream(nameAndTeacher.split("\n")).filter(s -> !s.isEmpty())
                            .map(s -> s.replaceAll("([№\\d]+)", ",$1").replace("№", "к.к."))
                            .collect(Collectors.toList());
                }
                if (!Objects.equals(pairNumber, "")) {
                    if (results.size() == 2) {
                        System.out.println(String.format("%s, %s, NULL, NULL, NULL, %s, %s", pairNumber, String.join(",", results), fullWeekday, group));
                    } else if (results.size() == 4) {
                        System.out.println(String.format("%s, %s, %s, %s", pairNumber, String.join(",", results), fullWeekday, group));
                    } else {
                        System.out.println(String.format("%s, NULL, NULL, NULL, NULL, NULL, NULL, %s, %s", pairNumber, fullWeekday, group));
                    }
                }
                results = new ArrayList<>();
            }
        }
        System.out.println("end");

    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }

}