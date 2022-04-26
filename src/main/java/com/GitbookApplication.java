package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dao")
public class GitbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitbookApplication.class, args);
    }

    /**
     * 1. 准备环境，根据不同的环境创建不同的Environment
     * 2. 准备、加载上下⽂，为不同的环境选择不同的Spring Context，然后加载资源，配置Bean
     * 3. 初始化，这个阶段刷新Spring Context，启动应⽤
     * 4. 最后结束流程
     */

}
