package com.util;

import com.alibaba.fastjson.JSON;
import com.entity.Sidebar;
import com.github.underscore.lodash.U;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 本工具类用于生成vuepress博客侧边栏。根据文件夹嵌套生成组
 * @author: LinQin
 * @date: 2019/06/26
 */
@Slf4j
public class VuePressUtil {
    public static final String SEPARATOR = "/";
    public static final String README_MD = "README.md";
    public static final String SUMMARY = "SUMMARY.md";

    // 排除的文件夹
    final static String regex = "picBak|assets|node_modules|_book|.git|.vuepress|bak";

    public static void main(String[] args) throws IOException {

        String path = "F:\\hexo\\vuepress\\docs";
        if (args.length > 0 && !StringUtils.hasText(args[0])) {
            path = args[0];
            log.info("目标:{}",path);
        }
        System.setProperty("root", path);
        File file = new File(path);


        File[] files = file.listFiles();

        List<String> list = Lists.newLinkedList();

        // gitbook 第一层
        for (File item : files) {
            if (!item.isDirectory() || item.getName().startsWith(".")) continue;
            String catalog = SEPARATOR + item.getName() + SEPARATOR;
            List fileList = getFileList(item);
            // fileList.add(0, catalog);
            String temp = JSON.toJSONString(fileList);
            list.add("\"" + catalog + "\"" + ":" + temp);
        }

        String formatJson = U.formatJson("{" + Joiner.on(",").join(list) + "}");
        String vueConfig = "module.exports = " + formatJson;
        // System.out.println(formatJson);
        ByteArrayInputStream bis = new ByteArrayInputStream(vueConfig.getBytes(Charsets.UTF_8));
        File output = new File(file.getParent() + File.separator  + "config/sidebarConf.js");
        log.info("生成目标文件目录:{}", output.getPath());
        Files.copy(bis, output.toPath(), StandardCopyOption.REPLACE_EXISTING);

    }


    private static List getFileList(File item) {
        List list = Lists.newLinkedList();
        File[] files = item.listFiles();

        for (File child : files) {
            // md文件
            if (child.getName().endsWith(".md")) {
                String[] mdStr = new String[2];
                String root = System.getProperty("root");
                if (README_MD.equals(child.getName())) {
                    mdStr[0] = child.getAbsolutePath().replace(root, "").replace("\\", "/").replace(README_MD, "");
                    mdStr[1] = child.getName().replace(".md", "");
                    ((LinkedList) list).addFirst(mdStr);
                } else if (SUMMARY.equals(child.getName())) {
                    mdStr[0] = child.getAbsolutePath().replace(root, "").replace("\\", "/");
                    mdStr[1] = child.getName().replace(".md", "");
                    list.add(1, mdStr);
                } else {
                    mdStr[0] = child.getAbsolutePath().replace(root, "").replace("\\", "/");
                    mdStr[1] = child.getName().replace(".md", "");
                    list.add(mdStr);
                }

            }
            // 子目录
            if (!child.getName().matches(regex) && child.isDirectory()) {

                List str = getFileList(child);
                Sidebar sidebar = new Sidebar();
                sidebar.setTitle(child.getName())
                       .setCollapsable(true)
                       .setChildren(str);
                list.add(sidebar);
            }
        }

        return list;
    }

}
