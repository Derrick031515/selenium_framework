# Java Selenium UI自动化框架

<a name="YVkPx"></a>
## 一、GitHub链接
项目网址链接：https://github.com/Derrick031515/selenium_framework.git

## 二、Web UI 框架结构图
封装的底层框架用到Java+Maven+Selenium+Junit5+Log4j+Allure，支持多种浏览器的测试，包括谷歌、火狐、IE、Safari等。同时框架可根据配置文件【包括业务和用例】自动解析、生成并执行测试用例，且用例实现参数化。在框架封装基础之上，仅关注业务开发。<br />

![框架结构图](/Users/hupo/Library/Application Support/typora-user-images/image-20211025175550811.png)

- main
  - java
    - basedriver
      - **ChromeDriverHandler**：封装启动谷歌浏览器的类
      - **EdgeDriverHandler**：封装Edge浏览器的类
      - **FirefoxDriverHandler**：封装火狐浏览器的类
      - **InternetExplorerDriverHandler**：封装启动IE浏览器的类
      - **OperaDriverHandler**：封装Safari浏览器的类
    - basepage
      - **BaseBrowser**：封装浏览器中界面上最基本操作
      - **BasePage**：封装基本页面操作方法
    - basepo
      - **POBaseData**：封装解析测试用例Yaml数据，封装对象
      - **POBasePage**：封装解析测试用例执行的具体操作步骤，并执行用例
      - **POStore**：封装保存所有的po， poName: POBasePage实例
    - basetest
      - **BaseTestCaseExcutor**：封装解析测试用例整体操作步骤，包括断言
      - **BaseTestCaseTearDown**：封装关闭浏览器操作类
    - constant
      - **TestConstant**：封装测试项目常量类
    - exception
      - **BrowserNameException**：封装异常类
    - util
      - **PropertiesReader**：封装读取配置文件操作方法
  - resources
      - **config**：项目基础配置信息
      - **drivers**：浏览器驱动文件
      - **log4j2.xml**：log4j2的配置文件、控制台输出和文件滚动输出
- test
  - java
    - pageobject
      - **CeshirenPOCodeSearchPage**：项目业务模块
    - testcase
      - **TestCaseMain**：测试用例主类
  - resources
    - data
      - **CeshirenPOCodeSearchPageData.yaml**：项目业务模块用例参数化数据
    - model
      - **CeshirenPOCodeSearchPage.yaml**：项目业务模块配置
- **pom.xml**：Maven配置文件
  <a name="YoWRl"></a>
