package com.framework.basetest;

import com.framework.basedriver.BaseDriver;
import com.framework.util.PropertiesReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
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

public class BaseTestCaseInit {
    public static final Logger log = LoggerFactory.getLogger(BaseTestCaseInit.class);
    /**
     * 驱动基类
     */
    public static BaseDriver baseDriver;

    /**
     * 驱动
     * 对外暴露
     */
    public static WebDriver driver;

    static{
        try {
            String browserName = PropertiesReader.getKey("driver.browserName");
            String terminal = PropertiesReader.getKey("driver.terminal");
            String deviceName = PropertiesReader.getKey("driver.deviceName");
            int remotePort = Integer.parseInt(PropertiesReader.getKey("driver.remotePort"));
            String remoteIP = PropertiesReader.getKey("driver.remoteIP");
            String browserVersion = PropertiesReader.getKey("driver.browserVersion");
            log.info("browserName="+browserName);
            log.info("terminal="+terminal);
            log.info("deviceName="+deviceName);
            log.info("remotePort="+remotePort);
            log.info("remoteIP="+remoteIP);
            log.info("browserVersion="+browserVersion);
            /* 驱动配置 */
            baseDriver = new BaseDriver();
            driver = baseDriver.startBrowser(browserName, terminal, deviceName, remoteIP, remotePort, browserVersion);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 构造器
     */
    public BaseTestCaseInit() {
    }

    /**
     * redis 连接的工具类
     * 对外暴露
     */
//    public RedisUtil redisUtil;

    /**
     * 执行一个测试用例之前执行
     * @throws IOException
     */
    /*@BeforeEach
    public void beforeMethod() throws IOException {
        String browserName = "chrome";
        String terminal = "pc";
        String deviceName = "desktop";
        int remotePort = 4444;
        String remoteIP = "";
        String browserVersion = "";
        *//* 驱动配置 *//*
        baseDriver = new BaseDriver();
        driver = baseDriver.startBrowser(browserName, terminal, deviceName, remoteIP, remotePort, browserVersion);
        System.out.println("kk"+driver);
    }*/

    /**
     * 执行一个测试用例之后执行
     *
     * @throws InterruptedException sleep 休眠异常
     */
    /*@AfterAll()
    public static void afterTest() throws InterruptedException {
        // 驱动退出关闭浏览器
        baseDriver.closeBrowser();
        driver = null;
    }*/



    /**
     * 执行一个测试套之前执行
     * 进行测试配置文件的读取工作
     * 由于 BeforeSuite 不会多线程去执行，因此对于配置文件读取未使用线程安全的操作
     *
     * @param propertiesPath 整个项目的测试配置文件相对于项目的路径
     * @throws IOException IOException
     */
   /* @BeforeSuite(alwaysRun = true)
    @Parameters({"propertiesPath"})
    public void beforeSuite(@Optional("src/test/resources/config/config.properties") String propertiesPath) throws IOException {
        // 显示文字 webuitest4j
        WordartDisplayer.display();
        // 配置文件读取
        PropertiesReader.readProperties(propertiesPath);
        // redis 连接池初始化操作
        RedisUtil.initJedisPool();
        // todo : 这里可以自己定制其他工具初始化操作（看需要）
    }
*/
    /**
     * 执行一个测试用例之前执行
     * 这里做多线程的处理
     *
     * @param browserName    浏览器名（必传）
     * @param terminal       终端 pc/h5（默认是 pc，对于 h5 需要传 h5）
     * @param deviceName     设备名（默认是 desktop，对于 h5 需要传手机型号）
     * @param remoteIP       远端 ip（远端运行必传）
     * @param remotePort     端口（默认是 4444）
     * @param browserVersion 浏览器版本
     */
   /* @BeforeTest(alwaysRun = false)
    @Parameters({"browserName", "terminal", "deviceName", "remoteIP", "remotePort", "browserVersion"})
    public void beforeTest(@Optional("chrome") String browserName, @Optional("pc") String terminal,
                           @Optional("desktop") String deviceName, @Optional("") String remoteIP,
                           @Optional("4444") int remotePort, @Optional() String browserVersion) {
        *//* redis 新连接获取 *//*
//        redisUtil = new RedisUtil();
//        redisUtil.initJedis();
        *//* 驱动配置 *//*
        baseDriver = new BaseDriver();
        driver = baseDriver.startBrowser(browserName, terminal, deviceName, remoteIP, remotePort, browserVersion);
        // todo : 由于线程隔离设为 test，这里可以通过 new 一个对象来达到线程隔离的效果，可以做其他的扩展定制（看需要）
        *//* todo : 登录操作可以放在这里（看需要）
         * LoginPage loginPage = new LoginPage(driver);
         * loginPage.loginByUI();
         *//*
    }*/

    /**
     * 执行一个测试用例中的类方法之前执行
     */
    /*@BeforeClass(alwaysRun = false)
    public void beforeClass() {
        // todo : 登录操作可以放在这里（看需要）
//        LoginPage loginPage = new LoginPage(driver, redisUtil);
//        loginPage.enterPage();
//        loginPage.loginByAPI();
    }*/

    /**
     * 执行一个测试用例中的类方法之后执行
     */
    /*@AfterClass(alwaysRun = true)
    public void afterClass() {
        // todo : 登录的注销或其他操作可以放在这里（看需要）
    }*/



    /**
     * 执行一个测试套之后执行
     */
   /* @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        // todo : 可自己定制（看需要）
    }*/
}
