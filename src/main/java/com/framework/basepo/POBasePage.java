package com.framework.basepo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.framework.basepage.BasePage;
import com.framework.util.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import java.util.concurrent.atomic.AtomicBoolean;
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
    public String runPOMethod(ArrayList<LinkedHashMap<String, Object>> mapList, WebDriver driver, HashMap<String, String> caseData, String env) {
        ArrayList<String> resList = new ArrayList<>();
        AtomicReference<WebElement> default_by = new AtomicReference<>();

        mapList.forEach(element->{
            element.entrySet().forEach(assertEle->{
                String action = assertEle.getKey().toLowerCase(); // key= asserts
                Object value = assertEle.getValue();
                String result = "";

                switch (action) {
                    case "get":
                        String url = (String) value;
                        if(url.contains("${param}")){
                            System.out.println("******"+url);
                            url = url.replaceFirst("\\$\\{param\\}",env);
                        }
                        driver.get(url);
                        break;
                    case "find":
                        long webDriverWait = 0;
                        try {
                            webDriverWait = Long.parseLong(PropertiesReader.getKey("driver.timeouts.webDriverWait"));
                            // 显示等待
                            driver.manage().timeouts().implicitlyWait(webDriverWait, TimeUnit.SECONDS);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ArrayList<String> values = (ArrayList<String>) value;
                        String locator_by = values.get(0);
                        String locator_value = values.get(1);
                        if (locator_by.equals("id")) {
                            WebElement elementId = driver.findElement(By.id(locator_value));
                            default_by.set(elementId);
                        } else if(locator_by.contains("ids_")){
                            String[] indexStr = locator_by.split("_");
                            int index = Integer.parseInt(indexStr[1]);
                            WebElement elementsId = driver.findElements(By.id(locator_value)).get(index);
                            default_by.set(elementsId);
                        }  else if (locator_by.equals("css")) {
                            WebElement elementCSS = driver.findElement(By.cssSelector(locator_value));
                            default_by.set(elementCSS);
                        } else if (locator_by.equals("link_text")){
                            WebElement elementLinkText = driver.findElement(By.partialLinkText(locator_value));
                            default_by.set(elementLinkText);
                        } else if(locator_by.equals("xpath")){
                            WebElement elementXpath = driver.findElement(By.xpath(locator_value));
                            default_by.set(elementXpath);
                        } else if(locator_by.equals("name")){
                            WebElement elementXpath = driver.findElement(By.name(locator_value));
                            default_by.set(elementXpath);
                        } else if(locator_by.equals("iframe")){
                            WebElement elementXpath = driver.findElement(By.xpath(locator_value));
                            driver.switchTo().frame(elementXpath);
                            default_by.set(elementXpath);
                        } else if(locator_by.equals("iframe_out")){
                           driver.switchTo().defaultContent();
                        } else if(locator_by.equals("currentpage")){
                            driver.get(driver.getCurrentUrl());
                        }
                        break;
                    case "click":
                        default_by.get().click();
                        break;
                    case "wait":
                        //强制等待
                        String time = (String) value;
                        try {
                            Thread.sleep(Integer.parseInt(time)*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "refresh":
                        //刷新页面操作
                        driver.navigate().refresh();
                        break;
                    case "sendkeys":
                        String keys = (String) value;
                        if(keys.contains("${")){
                            keys = keys.substring(2,keys.length()-1);
                            keys = caseData.get(keys);
                        }
                        default_by.get().sendKeys(keys);
                        break;
                    case "getassertstext":
                        ArrayList<String> valuesText = (ArrayList<String>) value;
                        String locator_by_key = valuesText.get(0);
                        String locator_by_value = valuesText.get(1);
                        if (locator_by_key.equals("id")) {
                            result = "结果包含断言字段：" + driver.findElement(By.id(locator_by_value)).getText();
                        } else if(locator_by_key.equals("ids_size")){
                            result = "集合元素列表大小为：" + driver.findElements(By.xpath(locator_by_value)).size()+"";
                        } else if(locator_by_key.contains("ids_index_")){
                            String[] indexStr = locator_by_key.split("_");
                            int index = Integer.parseInt(indexStr[2]);
                            result = "结果包含断言字段：" + driver.findElements(By.id(locator_by_value)).get(index).getText();
                        } else if(locator_by_key.contains("ids_fieldNameValue")){//--TODO 有问题
                            String[] indexStr = locator_by_key.split("_");
                            AtomicBoolean flag = new AtomicBoolean(false);
                            driver.findElements(By.id(locator_by_value)).forEach(ele->{
                                if(ele.getText().contains(indexStr[1])){
                                    flag.set(true);
                                }
                            });
                            if(flag.get()){
                                result = "结果包含断言字段：" + indexStr[1];
                            }
                            result = "结果不包含断言字段："+indexStr[1];
                        } else if (locator_by_key.equals("css")) {
                            result = "结果包含断言字段：" + driver.findElement(By.cssSelector(locator_by_value)).getText();
                        } else if (locator_by_key.equals("link_text")){
                            result = "结果包含断言字段：" + driver.findElement(By.partialLinkText(locator_by_value)).getText();
                        } else if (locator_by_key.equals("name")){
                            result = "结果包含断言字段：" + driver.findElement(By.name(locator_by_value)).getText();
                        } else if(locator_by_key.equals("xpath")){
                            result = "结果包含断言字段：" + driver.findElement(By.xpath(locator_by_value)).getText();
                        } else if(locator_by_key.equals("xpath_size")){
                            result = "集合元素列表大小为：" + driver.findElements(By.xpath(locator_by_value)).size()+"";
                        } else if(locator_by_key.contains("xpath_index_")){
                            String[] indexStr = locator_by_key.split("_");
                            int index = Integer.parseInt(indexStr[2]);
                            result = "结果包含断言字段：" + driver.findElements(By.id(locator_by_value)).get(index).getText();
                        } else if(locator_by_key.contains("xpath_fieldNameValue")){//--TODO 有问题
                            String[] indexStr = locator_by_key.split("_");
                            AtomicBoolean flag = new AtomicBoolean(false);
                            driver.findElements(By.id(locator_by_value)).forEach(ele->{
                                if(ele.getText().contains(indexStr[1])){
                                    flag.set(true);
                                }
                            });
                            if(flag.get()){
                                result = "结果包含断言字段："+indexStr[1];
                            }
                            result = "结果不包含断言字段："+indexStr[1];
                        }

                        resList.add(result);
                        break;
                }
            });
        });

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
