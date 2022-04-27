package com.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Description:
 * @author: LinQin
 * @date: 2021/06/11
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rule")
public class RuleConfig {
    List<String> upload;
    boolean checkReplace;
    // 已经是http的是否上传
    boolean httpUpload;

    List<String> whiteList;
}
