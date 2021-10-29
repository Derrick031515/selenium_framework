package com.framework.basedriver;

import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * 驱动责任链模式 handler 抽象类
 *
 * @author Derrick
 * @version 1.0.0
 * @date 2020/7/29 18:38
 */
public abstract class DriverHandler {


    /**
     * 后继 DriverHandler 结点
     */
    public DriverHandler next;

    /**
     * @param browserName    浏览器名
     * @param terminal       终端 pc/h5
     * @param deviceName     设备名
     * @return WebDriver
     */
    public WebDriver start(String browserName, String terminal, String deviceName) throws IOException {
        // terminal 为 pc 可以允许 deviceName 为空
        return startBrowser(browserName, terminal, deviceName);
    }

    /**
     * 运行本地
     *
     * @param browserName 浏览器名
     * @param terminal    终端 pc/h5
     * @param deviceName  设备名
     * @return WebDriver
     */
    public abstract WebDriver startBrowser(String browserName, String terminal, String deviceName) throws IOException;

    /**
     * 后继结点赋值
     *
     * @param next 后继结点
     */
    public DriverHandler setNext(DriverHandler next) {
        this.next = next;
        return this.next;
    }
}