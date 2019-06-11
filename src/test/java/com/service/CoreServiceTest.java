package com.service;

import com.GitbookApplication;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Description:
 * author: 林钦
 * date: 2019/05/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GitbookApplication.class)
public class CoreServiceTest {

    @Autowired
    private CoreService coreService;

    @Test
    public void handle() throws IOException {
        String path = "F:\\GitBook\\Markdown入门到放弃";
        String path1 = "F:\\GitBook\\Markdown入门到放弃\\1.node.js介绍\\README.md";
        File file = new File(path1);
    }
}