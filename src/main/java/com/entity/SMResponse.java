package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: sm图床消息返回
 * @author: LinQin
 * @date: 2019/05/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SMResponse {

    private boolean success;
    private String code;
    private String message;
    private DataBean data;
    private String RequestId;

    @NoArgsConstructor
    @Data
    public static class DataBean {

        private int file_id;
        private int width;
        private int height;
        private String filename;
        private String storename;
        private int size;
        private String path;
        private String hash;
        private String url;
        private String delete;
        private String page;
    }
}

