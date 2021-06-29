package com.service;

import java.io.IOException;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/05/31
 */
public interface BaseService {
    /**
     * 扫描目录，下载所有匹配外链的地址进行下载
     * @param markDownFilePath 笔记目录
     * @param bakPath 备份图片保存的路径
     */
    void bak(String markDownFilePath, String bakPath) throws IOException;

    void upload(String markDownFilePath) throws IOException;

    void check(String path) throws IOException;

    void local(String path) throws IOException;

    void httpUrl(String path1) throws IOException;

    void removeDeprecatedPic(String path, String bakPath) throws IOException;
}
