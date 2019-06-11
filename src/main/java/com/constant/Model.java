package com.constant;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/05/31
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Model {

    BAK("bak", "备份模式", ""),
    UPLOAD("upload", "上传文件模式", ""),
    CHECK("check", "检测url链接模式", ""),
    LOCAL("local", "还原外链为本地连接，保证无网络情况可以看", ""),
    HTTPURL("httpUrl", "还原本地连接为外链", "");

    private String model;
    private String desc;
    private String impl;


    public static Map<String, Object> getAllModel() {
        Map<String, Object> map = Maps.newConcurrentMap();
        for (Model item : Model.values()) {
            map.put(item.name(), item.impl);
        }
        return map;
    }

    public static String getImplByModel(String model) {
        Map<String, Object> allModel = getAllModel();
        if (!allModel.containsKey(model))
            throw new RuntimeException("没有该[" + model + "]模式");
        return (String) allModel.get(model);
    }
}
