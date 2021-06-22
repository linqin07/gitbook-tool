package com.entity.gitee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: LinQin
 * @date: 2021/06/11
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UploadRequest {
    private String access_token;
    // base64
    private String content;
    // 上面base64的名称
    private String name;
    // commit 信息
    private String message;

    // runtime
    private String filePath;

}
