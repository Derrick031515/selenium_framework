package com.wechat.pageobject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.framework.basepo.POBasePage;
import com.framework.basetest.BaseTestCaseExcutor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;

public class CeshirenPOCodeSearchPage extends POBasePage {
    WebDriver driver;
    static BaseTestCaseExcutor baseTestCaseExcutor;
    static ArrayList<Executable> assertList = new ArrayList<Executable>();

    /**
     * 构造器
     * 获取浏览器驱动
     */
    public CeshirenPOCodeSearchPage(){
        driver = super.driver;
    }

    @BeforeAll
    static void init() throws IOException {
        log.info("Starting");
        /**
         * 读取配置文件
         */
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        baseTestCaseExcutor = mapper.readValue(
                new File("src/test/resources/model/" + getClassName() + ".yaml"),
                BaseTestCaseExcutor.class
        );
    }



    @AfterAll
    static void tearDown(){
        /**
         * 统一断言
         */
        assertAll("",assertList.stream());
    }

    /**
     * 获取当前类名称
     */
    public static String getClassName(){
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String[] split = className.split("\\.");
        String res = split[split.length - 1];

        return res;
    }

    /**
     * 获取当前类方法
     * @return
     */
    public static String getClassMethodName(){
        String classMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        return classMethodName;
    }

    @Test
    public void get_first_title() {
        String classMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        assertList = baseTestCaseExcutor.run(driver,assertList,classMethodName);
//        String text = driver.findElement(By.cssSelector(".topic-title")).getText();
        System.out.println("text");
    }

//    @Test
    void tests(){
        System.out.println("Hello world");
    }
}
