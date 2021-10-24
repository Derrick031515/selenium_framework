package com.wechat.testcase;

import com.wechat.pageobject.CeshirenPOCodeSearchPage;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import static org.junit.platform.engine.discovery.ClassNameFilter.excludeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.*;

public class TestCaseMain {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("com.wechat.pageobject"),
                        selectClass(CeshirenPOCodeSearchPage.class)
//                        selectMethod("examples.packageA.ClassATest#testCaseA")
                ).build();
        Launcher launcher = LauncherFactory.create();

        /*// Register a listener of your choice
        TestExecutionListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);*/

        launcher.execute(request);
    }
}
