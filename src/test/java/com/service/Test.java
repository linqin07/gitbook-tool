package com.service;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/06/05
 */
public class Test {

    public static void main(String[] args) {
        String picUrl = "![下载地址](https://i.loli.net/2019/06/01/5cf16e87f1c2a16290.jpg)";
        String replace = picUrl
                .replace("https://i.loli.net/2019/06/01/5cf16e87f1c2a16290.jpg", "www.baidu.com");
        System.out.println(replace);
    }

}
