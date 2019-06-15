package com.util;

import com.google.common.collect.Lists;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/05/31
 */
@Slf4j
public class FileUtil {



    /**
     * 获取后缀是 .md 的 markdown 文件
     */
    public static List<File> getMarkDownFile(File file) {
        List<File> list = Lists.newArrayList();
        getMarkDownFileList(file, list);
        return list;
    }

    private static void getMarkDownFileList(File file, List<File> list) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            // 递归
            assert files != null;
            for (File item : files) {
                getMarkDownFileList(item, list);
            }
        } else {
            if (file.getName().endsWith(".md") && !file.getPath().contains("node_modules")) {
                list.add(file);
            }
        }
    }

    /**
     * 按行读取 markdown 文件
     */
    public static List<String> readFileContent(File file) throws IOException {
        List<String> list = Lists.newArrayList();
        @Cleanup InputStream is = new FileInputStream(file.getPath());
        @Cleanup InputStreamReader isr = new InputStreamReader(is, Charset.forName("utf-8"));
        @Cleanup BufferedReader br = new BufferedReader(isr, 1024);

        String data;
        while ((data = br.readLine()) != null) {
            list.add(data);
        }


        return list;
    }

    /**
     * 获取匹配的路径字符集合
     */
    public static List<String> matchContent(List<String> content, Pattern pattern) {
        List<String> list = Lists.newArrayList();
        content.forEach(item -> {
            Matcher matcher = pattern.matcher(item);
            // 匹配符合 markdown 的字符串
            if (matcher.find()) {
                String urlContent = matcher.group(0);
                list.add(urlContent);
            }
        });
        return list;
    }

    public static void main(String[] args) throws IOException {
        List<String> content = readFileContent(new File("F:\\GitBook\\Linux\\防火墙\\防火墙.md"));
        System.out.println(content.toString());
    }
}
