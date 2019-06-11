package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/05/30
 */
@Slf4j
@Service
public class CoreService {

    // 正则
    final static String regex1 = "^!\\[.+\\]\\(assets\\/.+\\.(png|gif)\\)$";
    final static String regex2 = "assets\\/.+\\.(png|gif)";
    // 预先编译，多行模式
    final static Pattern pattern1 = Pattern.compile(regex1, Pattern.MULTILINE);
    final static Pattern pattern2 = Pattern.compile(regex2, Pattern.MULTILINE);

    public static void main(String[] args) throws IOException {
        String path = "F:\\GitBook\\Markdown入门到放弃";
        String path1 = "F:\\GitBook\\Markdown入门到放弃\\1.node.js介绍\\README.md";
        File file = new File(path1);
        // getMarkDownFile(file);
        // readFileContent(new File(path1));


        @Cleanup FileInputStream fis = new FileInputStream(file);
        fis.read();
    }








}
