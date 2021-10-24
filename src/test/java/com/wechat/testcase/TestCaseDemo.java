package com.wechat.testcase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.framework.basepo.POBaseData;
import com.framework.basetest.BaseTestCaseExcutor;
import com.framework.util.WordartDisplayer;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;

public class TestCaseDemo {
    Logger log = LoggerFactory.getLogger(TestCaseDemo.class);
    @Test
    void test()  {
        System.setProperty("webdriver.chrome.driver", "/Derrick/hogwarts/software/chromedriver");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.baidu.com");
        String text = "";
        try {
            Thread.sleep(2000);
//            text = driver.findElementByClassName("hot-refresh-tex").getText();
            text = driver.findElementByXPath("//*[@id='s-top-left']/a[1]").getText();
            System.out.println(text);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e){
            log.info("没有找到元素");
        }

        System.out.println(text);
    }

    @Test
    void test2(){
        String actual = "hello world";
        String expect = "hello";
        assertThat(actual, Matchers.containsString(expect));
    }

    public String tests = this.getClass().getName();
    @Test
    void test3(){
//        String className = Thread.currentThread().getStackTrace()[1].getClassName();
//        String[] split = className.split("\\.");
//        String res = split[split.length - 1];
//        System.out.println(res);
//        String classNameAll = this.getClass().getName().split(".").[-1];
//        System.out.println(classNameAll);
//        System.out.println(WordartDisplayer.getCurrentClassName(tests));

//        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        String test = "testCase_search";
        if(test.contains("search")){
            System.out.println("good");
        }

        HashMap<String,String> testHashMap = new HashMap<>();
        testHashMap.put("a——hello","b");
        testHashMap.put("a","b");
        testHashMap.put("a","b");
        testHashMap.entrySet().forEach(entry->{
            System.out.println(entry.getValue());
        });
    }

    List<POBaseData> data;
    public List<POBaseData> getData() throws IOException {
        /**
         * 读取配置文件
         */
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<POBaseData>> typeReference = new TypeReference<List<POBaseData>>() {
        };
        this.data = mapper.readValue(POBaseData.class.getResourceAsStream("src/test/resources/data/CeshirenPOCodeSearchPageData.yaml"), typeReference);
        return data;
    }


    /*@ParameterizedTest
    @MethodSource
    HashMap<String, HashMap<String, String>> init(POBaseData poBaseData){
        HashMap<String, HashMap<String, String>> dataMap = poBaseData.getDataMap();

        return dataMap;

    }*/

    @Test
    void test4() throws IOException {
//        List<POBaseData> init = data;
        /*init.forEach(ele->{
            HashMap<String, HashMap<String, String>> dataMap = ele.dataMap;
            dataMap.entrySet().forEach(entry->{
                System.out.println(entry.getKey());
            });

        });*/
    }


}
