package com.controller;

import com.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/05/30
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private BaseService baseService;

    @Value("${markdown.bak-path}")
    private String bakPath;

    @Value("${markdown.path}")
    private String path;

    @RequestMapping("/test")
    public String test() {
        System.out.println();
        return "hello world!";
    }


    @RequestMapping("/upload")
    public void upload() throws IOException {
        String path1 = "F:\\GitBook\\Markdown入门到放弃";
        baseService.upload(path);
    }

    @RequestMapping("/bak")
    public void bak() throws IOException {
        // String path1 = "F:\\GitBook\\Markdown入门到放弃";
        baseService.bak(path, bakPath);
    }

    @RequestMapping("/check")
    public void check() throws IOException {
        // String path1 = "F:\\GitBook\\Markdown入门到放弃\\1.node.js介绍\\README.md";
        baseService.check(path);
    }

    @RequestMapping("/local")
    public void local() throws IOException {
        // String path1 = "F:\\GitBook\\Markdown入门到放弃\\1.node.js介绍\\README.md";
        baseService.local(path);
    }

    @RequestMapping("/httpUrl")
    public void httpUrl() throws IOException {
        // String path1 = "F:\\GitBook\\Markdown入门到放弃\\1.node.js介绍\\README.md";
        baseService.httpUrl(path);
    }


    /**
     * 移除废弃的图片，用于编辑过程中截图过多后没有主动删除冗余图片,针对的是编辑的本地对象
     * @throws IOException
     */
    @RequestMapping("/removeDeprecatedPic")
    public void removeDeprecatedPic() throws IOException {
        baseService.removeDeprecatedPic(path, bakPath);
    }
}
