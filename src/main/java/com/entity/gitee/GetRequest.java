package com.entity.gitee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: LinQin
 * @date: 2021/06/17
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetRequest {
    private String access_token;
    private String path;
}
