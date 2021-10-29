package com.framework.basedriver;

import com.framework.util.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

/**
 * IE 驱动配置
 * todo : 指定下载文件路径暂缺
 * todo : 手机浏览器 h5 暂缺
 *
 * @author Derrick
 * @version 1.0.0
 * @date 2020/7/29 18:54
 */
public class InternetExplorerDriverHandler extends DriverHandler {
    /**
     * 启动本地 IE
     * todo : 指定下载文件路径暂缺
     * todo : 手机浏览器 h5 暂缺
     *
     * @param browserName 浏览器名
     * @param terminal    终端 pc/h5
     * @param deviceName  设备名
     * @return WebDriver
     */
    @Override
    public WebDriver startBrowser(String browserName, String terminal, String deviceName)  throws IOException {
        /* 当不是 IE 进入责任链的下一环 */
        if (!browserName.toLowerCase().equals("ie")) {
            return next.startBrowser(browserName, terminal, deviceName);
        }

        /* 驱动配置进环境变量 */
        // 驱动根路径 /target/test-classes/driver
        String driverParentPath = this.getClass().getResource("/").getPath() + "driver" + File.separator;
        // IE 驱动路径
        String ieDriverPath = driverParentPath + PropertiesReader.getKey("driver.ieDriver");
        // 系统变量设置 IE 驱动
        System.setProperty("webdriver.ie.driver", ieDriverPath);

        /* todo : 下载地址设置 */
        String downloadPath = System.getProperty("user.dir") + File.separator + PropertiesReader.getKey("driver.downloadPath");

        /* 驱动可选项配置 */
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
        // 忽略安全
        desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions().merge(desiredCapabilities);

        /* todo : 如果要测手机浏览器 h5 */

        /* 启动 WebDriver */
        return new InternetExplorerDriver(internetExplorerOptions);
    }

}
