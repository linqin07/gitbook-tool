package com.service;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/06/05
 */
public class Test {


    public static void main(String... args) {
        xx();
    }

    // 可变参数不会null，只会lenth=0
    public static void xx(String... xx) {
        System.out.println(xx == null);
        System.out.println(xx.length);
    }

}
