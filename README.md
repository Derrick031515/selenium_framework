# Selenium UI自动化框架

<a name="YVkPx"></a>
## 一、GitHub链接
项目网址链接：https://github.com/Derrick031515/selenium_framework.git

## 二、Web UI 框架结构图
封装的底层框架用到Java+Maven+Selenium+Junit5+Log4j+Allure，支持多种浏览器的测试，包括谷歌、火狐、IE、Safari等。同时框架可根据配置文件【包括业务和用例】自动解析、生成并执行测试用例，且用例实现参数化。在框架封装基础之上，仅关注业务开发。<br />

![框架结构图](/Users/hupo/Library/Application Support/typora-user-images/image-20211025175550811.png![image-20211027224825227](/Users/hupo/Library/Application Support/typora-user-images/image-20211027224825227.png)

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
    - constant
      - **TestConstant**：封装测试项目常量类
    - exception
      - **BrowserNameException**：封装异常类
    - util
      - **PropertiesReader**：封装读取配置文件操作方法
  - resources
      - **config.properties**：项目基础配置信息
      - **log4j2.xml**：log4j2的配置文件、控制台输出和文件滚动输出
  
- test
  - java
    - testcase
      - **CeshirenPOCodeSearchPage**：项目业务模块
  - resources
    - data
      - **CeshirenPOCodeSearchPageData.yaml**：项目业务模块用例参数化数据
    - model
      - **CeshirenPOCodeSearchPage.yaml**：项目业务模块配置
  
- **pom.xml**：Maven配置文件
  <a name="YoWRl"></a>
  
  ## 三、企业微信项目开发示例

1、项目需求描述

针对企业微信通讯录模块完成部门和部门成员增删改查UI测试

2、具体实现

（1）设计通讯录模块PO测试类，实现通讯录模块初始化、加载模块配置文件进行用例自动解析、生成并参数化形式执行、最后统一断言【src/test/java/com/wechat/pageobject/testcase/WeChatContactTest.java】

（2）设计通讯录模块配置，完成用例步骤设计【src/test/resources/model/WeChatContactTest.yaml】

（3）设计通讯录模块用例参数化配置【src/test/resources/data/WeChatContactTestData.yaml】

（4）执行命令，生成报告【 #mvn clean test -Dtest=WeChatContactTest】
