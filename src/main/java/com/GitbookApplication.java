package com;

import java.util.Arrays;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dao")
public class GitbookApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GitbookApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 预加载
        System.out.println("预加载");
        System.out.println("1 " + Arrays.asList(args).toString());
//
//        if (args[0].equals(Model.BAK.name())) {
//
//        }


    }
}
