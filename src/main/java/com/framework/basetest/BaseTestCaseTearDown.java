package com.framework.basetest;

import com.framework.basedriver.BaseDriver;
import com.framework.basepage.BaseBrowser;
import com.framework.util.PropertiesReader;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 测试基类
 *
 * @author Derrick
 * @version 1.0.0
 * @date 2021/1/22
 */

public class BaseTestCaseTearDown extends BaseBrowser {
    public static final Logger log = LoggerFactory.getLogger(BaseTestCaseTearDown.class);
    /**
     * 驱动
     * 对外暴露
     */
    public static WebDriver driver;


    /**
     * 构造器
     */
    public BaseTestCaseTearDown() {
        this.driver = super.driver;
    }

    /**
     * 执行一个测试用例之后执行
     *
     * @throws InterruptedException sleep 休眠异常
     */
    @AfterAll()
    public static void afterTest() throws InterruptedException {
        // 驱动退出关闭浏览器
        baseDriver.closeBrowser();
        driver = null;
    }
}
