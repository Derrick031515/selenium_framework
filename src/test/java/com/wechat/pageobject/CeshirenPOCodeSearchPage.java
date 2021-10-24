package com.wechat.pageobject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.framework.basepo.POBaseData;
import com.framework.basepo.POBasePage;
import com.framework.basetest.BaseTestCaseExcutor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    static List<POBaseData> init() throws IOException {
        log.info("Starting");
        /**
         * 读取配置文件
         */
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        baseTestCaseExcutor = mapper.readValue(
                new File("src/test/resources/model/" + getClassName() + ".yaml"),
                BaseTestCaseExcutor.class
        );

        TypeReference<List<POBaseData>> typeReference = new TypeReference<List<POBaseData>>() {
        };
        List<POBaseData> data = mapper.readValue(POBaseData.class.getResourceAsStream("src/test/resources/data/" + getClassName() + "Data.yaml"), typeReference);
        return  data;
    }



    @AfterAll
    static void tearDown(){
        /**
         * 统一断言
         */
        assertAll("",assertList.stream());
    }

    @Test
    /**
     * 获取当前类名称
     */
    public static String getClassName(){
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String[] split = className.split("\\.");
        String res = split[split.length - 1];

        return res;
    }

    @ParameterizedTest
    @MethodSource("init")
    public void get_first_title(POBaseData poBaseData) {
        //获取当前方法名称
        String classMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        assertList = baseTestCaseExcutor.run(driver,assertList,classMethodName);
    }

}
