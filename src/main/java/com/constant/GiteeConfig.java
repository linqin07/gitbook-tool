package com.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author: LinQin
 * @date: 2021/06/11
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gitee")
public class GiteeConfig {
    private String accessToken;
    private String owner;
    private String repo;
    private String ref;
}
