package com.wechat.pageobject.testcase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.framework.basedriver.BaseDriver;
import com.framework.basepage.BaseBrowser;
import com.framework.basepo.POBaseData;
import com.framework.basepo.POBasePage;
import com.framework.basetest.BaseTestCaseExcutor;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * 企微通讯录模块
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WeChatAnnouncementTest extends POBasePage {
    //驱动
    public static WebDriver driver;
    //驱动基类
    public static BaseDriver baseDriver;
    //用例执行单元封装类
    static BaseTestCaseExcutor baseTestCaseExcutor;
    //断言内容
    static ArrayList<Executable> assertList = new ArrayList<Executable>();
    //统计当前模块执行用例数
    static int caseNum = 0;
    //当前用例执行环境变量
    static String env;
    /**
     * 构造器
     * 获取浏览器驱动
     */
    public WeChatAnnouncementTest(){
    }

    @BeforeAll
    static void init() throws IOException {
        log.info("项目模块{ " + getClassName() + " }开始启动执行");
        log.info("开始初始化...");
        BaseBrowser baseBrowser = new BaseBrowser();
        driver = baseBrowser.driver;
        baseDriver = baseBrowser.baseDriver;

        /**
         * 读取配置文件
         */
        log.info("项目基础配置加载中...");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        baseTestCaseExcutor = mapper.readValue(
                new File("src/test/resources/model/" + getClassName() + ".yaml"),
                BaseTestCaseExcutor.class
        );
        log.info("项目基础配置加载成功");

        /**
         * 读取用例执行的环境变量
         */
//        env = PropertiesReader.getKey("pro.env");

        log.info("开始数据清理");
        clearData();
        log.info("清理数据完成");

        /**
         * 企微登录
         */
        loadCookie();
        log.info("初始化结束...");
    }

    @AfterAll
    static void tearDown() throws InterruptedException {
        /**
         * 统一断言
         */
        log.info("项目模块{ " + getClassName() + " }共执行{ " + caseNum + " }条用例，测试用例开始统一断言");
        assertAll("",assertList.stream());
        log.info("统一断言结束");
        log.info("开始释放资源");
        // 驱动退出关闭浏览器
        baseDriver.closeBrowser();
        driver = null;
        log.info("释放资源结束");
    }

    /**
     * 数据清理
     */
    public static void clearData(){
        //TODO-- 连接数据库，清理表数据
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
     * 获取测试用例数据
     */
    public static List<POBaseData> getTestCaseData() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<POBaseData>> typeReference = new TypeReference<List<POBaseData>>() {
        };
        List<POBaseData> data = mapper.readValue(POBaseData.class.getResourceAsStream("/data/" + getClassName() + "Data.yaml"), typeReference);

        return  data;
    }

    /**
     * 测试方法封装主体内容
     * @param poBaseData
     * @param methodName
     * @param baseTestCaseExcutor
     * @param driver
     */
    public static void startMethod(POBaseData poBaseData, String methodName, BaseTestCaseExcutor baseTestCaseExcutor, WebDriver driver){
        //获取用例数据
        List<LinkedHashMap<String, Object>> data = poBaseData.getData();
        log.info("当前用例测试执行方法为{ " + methodName + " }");
        data.forEach(ele->{
            if(ele.containsKey(methodName)){
                ArrayList<HashMap<String,String>> caseDataList = (ArrayList<HashMap<String,String>>)ele.get(methodName);
                caseNum += caseDataList.size();
                caseDataList.forEach(caseData->{
                    log.info("第{ " + caseData.get("num") + " }条测试用例开始执行");
                    assertList = baseTestCaseExcutor.run(driver,assertList,methodName,caseData,env);
                });
            }
        });
        log.info("方法{ " + methodName + " }测试用例执行结束");
    }


    /**
     * 企微登录方法
     */
    public static void loadCookie() {
        saveCookie();
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<HashMap<String, Object>>> typeReference = new TypeReference<List<HashMap<String, Object>>>() {
        };
        List<HashMap<String, Object>> cookies = null;
        try {
            cookies = objectMapper.readValue(
                    new File("cookie.yaml"),
                    typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        cookies.forEach(cookie -> {
            driver.manage().addCookie(new Cookie(cookie.get("name").toString(), cookie.get("value").toString()));
        });

        driver.navigate().refresh();

    }

    /**
     * 保存登录cookie
     */
    public static void saveCookie(){
        try {
            driver.get("https://work.weixin.qq.com/wework_admin/loginpage_wx?from=myhome");
            Thread.sleep(10000);
            Set<Cookie> cookies = driver.manage().getCookies();
            driver.navigate().refresh();
            ObjectMapper objectMapper=new ObjectMapper(new YAMLFactory());
            //todo: 使用getResource代替
            objectMapper.writeValue(new File("cookie.yaml"),cookies);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Description("企业微信项目")
    @Story("应用管理")
    @DisplayName("公告功能")
    @ParameterizedTest
    @MethodSource("getTestCaseData")
//    @Tag("test")
//    @Order(1)
    @Disabled
    public void addAnnouncement(POBaseData poBaseData) {
        //获取当前方法名称
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        startMethod(poBaseData,methodName,baseTestCaseExcutor,driver);
    }
}
