package com.framework.basepo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.framework.basepage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


public class POBasePage extends BasePage {
    public static final Logger log = LoggerFactory.getLogger(POBasePage.class);
    public String name;
    public HashMap<String, List<HashMap<String, Object>>> methods;

    public static WebDriver driver;
    Integer retryTimes = 3;

    public POBasePage() {
        this.driver = super.driver;
    }

    public static POBasePage load(String name, WebDriver driver) {
        /**
         * 从po的yaml文件中读取数据，并生成一个BasePage的实例
         */

        POBasePage page = null;

        String path = String.format("src/test/java/framework/po/%s.yaml", name);
        if (new File(path).exists()) {
            page = loadFromFile(path);

        } else {
            page = loadFromClassloader(name);
        }

        page.driver = driver;
        return page;
    }

    public static POBasePage loadFromFile(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            return mapper.readValue(new File(path), POBasePage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static POBasePage loadFromClassloader(String className) {
        /**利用反射冲生成page实例*/
        try {
            return (POBasePage) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO 每个用例执行结束后返回一个断言结果【用string表示】
    public String runPOMethod(String methodName, ArrayList<LinkedHashMap<String,Object>> mapList, WebDriver driver) {
        System.out.println("&&&&&&");
        log.info("methods大小="+methods);
        log.info("ClassName="+methodName);
        ArrayList<String> resList = new ArrayList<>();

        /*if (methods == null){
            log.info("Derrick1-------");
            try {
                Object invoke = this.getClass().getMethod(methodName).invoke(this, null);
                System.out.println("&&&&&&&&&&&&&&"+invoke);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }*/

        AtomicReference<By> default_by = new AtomicReference<>();
        log.info("******####******");
//        methods.get(methodName).forEach(step -> {
            log.info("Derrick2-------");
//            step.entrySet().forEach(entry -> {
        mapList.forEach(element->{
            element.entrySet().forEach(assertEle->{
                System.out.println("此处开始yaml处理"+assertEle);

                String action = assertEle.getKey().toLowerCase();
                Object value = assertEle.getValue();
                String result = "";
                switch (action) {
                    case "get":
                        driver.get((String) value);
                        System.out.println("Success");
                        break;
                    case "find":
                        ArrayList<String> values = (ArrayList<String>) value;
                        String locator_by = values.get(0);
                        String locator_value = values.get(1);

                        if (locator_by.equals("id")) {
//                            By.id(locator_value);
                            driver.findElement(By.id(locator_value));
                            default_by.set(By.id(locator_value));
                        } else if (locator_by.equals("css")) {
                            driver.findElement(By.cssSelector(locator_value));
                            default_by.set(By.cssSelector(locator_value));
                        } else if (locator_by.equals("link_text")){
                            driver.findElement(By.partialLinkText(locator_value));
                            default_by.set(By.partialLinkText(locator_value));
                        } else if(locator_by.equals("xpath")){
                            driver.findElement(By.xpath(locator_value));
                            default_by.set(By.xpath(locator_value));
                        }
                        System.out.println("Success");
                        break;
                    case "click":
//                        System.out.println("click="+default_by.get());
                        driver.findElement(default_by.get()).click();
//                        click(default_by.get());
                        System.out.println("Success");
                        break;
                    case "sendkeys":
                        String keys = (String) value;
                        log.info("%%%%%"+keys);
                        log.info("%%%%%*******"+default_by.get());
                        driver.findElement(default_by.get()).sendKeys(keys);
//                        sendKeys(default_by.get(), keys);
                        System.out.println("Success");
                        break;
                    case "gettext":
                        System.out.println("&&Starting&&");
                        ArrayList<String> valuesText = (ArrayList<String>) value;
                        String locator_by_key = valuesText.get(0);
                        String locator_by_value = valuesText.get(1);
                        System.out.println("key***"+locator_by_key);
                        System.out.println("value***"+locator_by_value);
                        if (locator_by_key.equals("id")) {
                            result = driver.findElement(By.id(locator_by_value)).getText();
                        } else if (locator_by_key.equals("css")) {
                            result = driver.findElement(By.cssSelector(locator_by_value)).getText();
                        } else if (locator_by_key.equals("link_text")){
                            result = driver.findElement(By.partialLinkText(locator_by_value)).getText();
                        } else if(locator_by_key.equals("xpath")){
                            result = driver.findElement(By.xpath(locator_by_value)).getText();
                        }

                        System.out.println("result***"+result);
                        System.out.println("Success");
                        resList.add(result);
                        break;
                }

            });
        });
        /*map.entrySet().forEach(entry -> {
                System.out.println("此处开始yaml处理"+entry);

                String action = entry.getKey().toLowerCase();
                Object value = entry.getValue();
                String result = "";
                switch (action) {
                    case "get":
                        driver.get((String) value);
                        break;
                    case "find":
                        ArrayList<String> values = (ArrayList<String>) value;
                        String locator_by = values.get(0);
                        String locator_value = values.get(1);

                        if (locator_by.equals("id")) {
                            default_by.set(By.id(locator_value));
                        } else if (locator_by.equals("css")) {
                            default_by.set(By.cssSelector(locator_value));
                        } else if (locator_by.equals("link_text")){
                            default_by.set(By.partialLinkText(locator_value));
                        }
                        break;
                    case "click":
                        click(default_by.get());
                        break;
                    case "sendkeys":
                        String keys = (String) value;
                        log.info("%%%%%"+keys);
                        log.info("%%%%%*******"+default_by.get());
                        sendKeys(default_by.get(), keys);
                        break;
                    case "getText":
                        ArrayList<String> valuesText = (ArrayList<String>) value;
                        String locator_by_key = valuesText.get(0);
                        String locator_by_value = valuesText.get(1);

                        if (locator_by_key.equals("id")) {
                            result = driver.findElement(By.id(locator_by_value)).getText();
                        } else if (locator_by_key.equals("css")) {
                            result = driver.findElement(By.cssSelector(locator_by_value)).getText();
                        } else if (locator_by_key.equals("link_text")){
                            result = driver.findElement(By.partialLinkText(locator_by_value)).getText();
                        }

                        break;
                }
                resList.add(result);
            });
//        });*/

        System.out.println("返回的结果为："+resList.get(0));

        return resList.get(0);
    }

    public boolean click(By by) {
        //todo: 突然的弹框阻挡、异常处理、流程调整
        //todo: find找不到 弹框阻挡 加载延迟
        //todo: click的时候报错
        //todo: click的时候不生效
        //todo: click的时候被弹框阻挡
        try {
            driver.findElement(by).click();
        } catch (Exception e) {
            e.printStackTrace();
            retryTimes += 1;
            if (retryTimes < 4) {
                //todo: 解决弹框
                this.handleAlert();
                return click(by);
            } else {
                retryTimes = 0;
                return false;

            }

        }
        return true;
    }

    public void clickUntil(By by, By next) {
        //todo: 用来解决前几次点击不生效，后面点击生效的过程，使用显式等待
    }

    public void sendKeys(By by, String content) {
        driver.findElement(by).sendKeys(content);
    }

    public void handleAlert() {
//        List black= Arrays.asList("//*[@text='dddd']", "//*[@text='dddd']");
//        driver.getPageSource()


//        List black= Arrays.asList(By.id("ddd"), By.name("ddd"));
//        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
//        driver.findElement()

    }
}
