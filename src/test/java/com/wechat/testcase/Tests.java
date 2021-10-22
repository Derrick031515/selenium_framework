package com.wechat.testcase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Tests {
    @BeforeAll
    static void test(){
        System.out.println("hello");
    }

    @AfterAll
    static void tearDown(){
        System.out.println("Stopping");
    }
}
