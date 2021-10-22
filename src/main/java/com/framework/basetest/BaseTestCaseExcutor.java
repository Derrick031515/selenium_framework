package com.framework.basetest;

import com.framework.basepo.POAssertModel;
import com.framework.basepo.POBasePage;
import com.framework.basepo.POStore;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

//todo: 支持数据参数化
//todo: 支持page object
//todo: web app service
//todo: 命令行参数支持
//todo: 目录读取
//todo: ...

public class BaseTestCaseExcutor {
    public static final Logger log = LoggerFactory.getLogger(BaseTestCaseExcutor.class);
    public String name;
    public List<HashMap<String, Object>> after_all;
    public List<HashMap<String, Object>> before_all;
    public List<HashMap<String, Object>> after;
    public List<HashMap<String, Object>> before;
    public List<HashMap<String, Object>> steps;
    //保存结果
    ArrayList<String> resList = new ArrayList<>();
    public ArrayList<POAssertModel> asserts;
//    public ArrayList<Executable> assertList = new ArrayList<>();
    public HashMap<String,String> assertHashMap = new HashMap<>();
    public LinkedHashMap<String,Object> valHashMap = new LinkedHashMap<>();

    public ArrayList<Executable> run(WebDriver driver,ArrayList<Executable> assertList,String classMethodName) {

        AtomicReference<By> default_by = new AtomicReference<>();
        AtomicReference<POBasePage> lastPage = new AtomicReference<>();
        System.out.println("steps大小为："+steps.size());
        steps.forEach(step -> {

            step.entrySet().forEach((entries) -> {
                log.info(entries.getKey()+","+entries.getValue());
                log.info("key***="+entries.getKey());
                log.info("classMethodName="+classMethodName);
                if(entries.getKey().contains(classMethodName)){
                    System.out.println("*****");
                    ArrayList<LinkedHashMap<String, Object>> testCaseContent = (ArrayList<LinkedHashMap<String, Object>>) entries.getValue();

                    LinkedHashMap<String, Object> assertLinkHashMap = (LinkedHashMap<String, Object>)testCaseContent.get(2);

                    //获取断言结果信息
                    ArrayList<HashMap<String, String>> assertKey = (ArrayList<HashMap<String, String>>)assertLinkHashMap.get("asserts");
                    System.out.println("assertKey大小为："+assertKey.size());
                    assertKey.forEach(assertKeyEle->{
                        assertKeyEle.entrySet().forEach(assertKeyElement->{
                            assertHashMap.put(assertKeyElement.getKey(),assertKeyElement.getValue());
                        });
                    });

                    String actual = assertHashMap.get("actual");
                    String matcher = assertHashMap.get("matcher");
                    String expect = assertHashMap.get("expect");
                    String reason = assertHashMap.get("reason");

                    testCaseContent.forEach((ele) -> {

                        ele.entrySet().forEach((ele_val)->{
                            String action = ele_val.getKey().toLowerCase();

                            if(!action.equals("asserts")){
                                Object value = ele_val.getValue();
                                log.info("$$$$"+value);
                                String[] keyArray = action.split("\\.");
                                if (keyArray.length > 1) {
                                    String poName = keyArray[0];
                                    String poMethod = keyArray[1];
                                    System.out.println(String.format("%s.%s %s", poName, poMethod, value));

                                    if (poMethod.equals("new")) {
                                        POBasePage currentPage = null;

                                        if (lastPage.get() == null) {
                                            currentPage = POBasePage.load(
                                                    (String) value,
                                                    null
                                            );

                                        } else {
                                            currentPage = POBasePage.load(
                                                    (String) value,
                                                    lastPage.get().driver
                                            );

                                        }
                                        lastPage.set(currentPage);

                                        POStore.getInstance().setPO(poName, currentPage);
                                    } else {

                                        ArrayList<LinkedHashMap<String,Object>> values = (ArrayList<LinkedHashMap<String,Object>>)value;

                                    /*values.forEach(element->{
                                        element.entrySet().forEach(assertEle->{
                                            System.out.println("+++++++++++");
                                            System.out.println("key="+assertEle.getKey()+",value="+assertEle.getValue());
                                            System.out.println("+++++++++++");
                                            valHashMap.put(assertEle.getKey(),assertEle.getValue());
                                        });
                                    });*/

                                        String actualRes = POStore.getInstance().getPO(poName).runPOMethod(poMethod,values,driver);
                                        log.info("实际结果："+actualRes);
                                        log.info("期望结果："+expect);
                                        //保存断言结果
                                        if(matcher.equals("contains")){
                                            System.out.println("开始断言吧。。。。");
                                            assertList.add(()->{assertThat(reason,actualRes,containsString(expect));});
                                            System.out.println("结束断言");
                                        }else{
                                            assertList.add(()->{assertThat(reason,actualRes,equalTo(expect));});
                                        }

                                    }

                                }
                            }


                        });

                    });
                    assertHashMap.clear();
                    valHashMap.clear();
                }
            });
        });

        return assertList;
    }
}
