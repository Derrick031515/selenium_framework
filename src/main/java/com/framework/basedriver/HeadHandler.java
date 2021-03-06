package com.framework.basedriver;

import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * 驱动责任链头结点
 *
 * @author Derrick
 * @version 1.0.0
 * @date 2020/7/29 18:52
 */
public class HeadHandler extends DriverHandler {
    /**
     * 启动本地浏览器
     *
     * @param browserName 浏览器名
     * @param terminal    终端 pc/h5
     * @param deviceName  设备名
     * @return WebDriver
     */
    @Override
    public WebDriver startBrowser(String browserName, String terminal, String deviceName)  throws IOException {
        return next.startBrowser(browserName, terminal, deviceName);
    }

}
