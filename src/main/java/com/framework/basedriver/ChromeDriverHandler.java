package com.framework.basedriver;

import com.framework.util.PropertiesReader;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 谷歌驱动配置
 *
 * @author Derrick
 * @version 1.0.0
 * @date 2020/7/29 18:52
 */
public class ChromeDriverHandler extends DriverHandler {
    /**
     * 启动 chrome
     *
     * @param browserName 浏览器名
     * @param terminal    终端 pc/h5
     * @param deviceName  设备名
     * @return WebDriver
     */
    @Override
    public WebDriver startBrowser(String browserName, String terminal, String deviceName) throws IOException {
        /* 当不是 chrome 进入责任链的下一环 */
        if (!browserName.toLowerCase().equals("chrome")) {
            return next.startBrowser(browserName, terminal, deviceName);
        }

        /* 下载地址设置 */
        String downloadPath = System.getProperty("user.dir") + File.separator + PropertiesReader.getKey("driver.downloadPath");
        Map<String, Object> downloadMap = new HashMap<>();
        downloadMap.put("download.default_directory", downloadPath);

        /* 驱动可选项配置 */
        ChromeOptions chromeOptions = new ChromeOptions();
        /*// 配置下载路径
        chromeOptions.setExperimentalOption("prefs", downloadMap);
        // --no-sandbox
        chromeOptions.addArguments("--no-sandbox");
        // --disable-dev-shm-usage
        chromeOptions.addArguments("--disable-dev-shm-usage");
//        chromeOptions.addArguments("--headless");*/
        //解决DevToolsActivePort文件不存在的报错
        chromeOptions.addArguments("--no-sandbox");
        //指定浏览器分辨率
        chromeOptions.addArguments("window-size=1920x3000") ;
        //谷歌文档提到需要加上这个属性来规避bug
        chromeOptions.addArguments("--disable-gpu") ;
        //隐藏滚动条, 应对一些特殊页面
        chromeOptions.addArguments("--hide-scrollbars") ;
        //不加载图片, 提升速度
        chromeOptions.addArguments("blink-settings=imagesEnabled=false");
        //浏览器不提供可视化页面. linux下如果系统不支持可视化不加这条会启动失败
        chromeOptions.addArguments("--headless") ;

        /* 如果要测手机浏览器 h5 */
        if (terminal.toLowerCase().equals("h5")) {
            Map<String, String> mobileMap = new HashMap<>();
            mobileMap.put("deviceName", deviceName);
            // 配置 h5 的手机机型等
            chromeOptions.setExperimentalOption("mobileEmulation", mobileMap);
        }

        /* 启动 WebDriver */
        return new ChromeDriver(chromeOptions);
    }
}
