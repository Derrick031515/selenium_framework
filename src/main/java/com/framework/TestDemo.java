package com.framework;

import org.openqa.selenium.chrome.ChromeDriver;

public class TestDemo {
    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");
    }
}
