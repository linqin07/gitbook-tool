package com.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/06/27
 */
@Data
@Accessors(chain = true)
public class Sidebar {
    // 控制序列化顺序
    @JSONField(ordinal = 1)
    private String title;
    /* 是否展开 */
    @JSONField(ordinal = 2)
    private Boolean collapsable;

    @JSONField(ordinal = 3)
    private List<Object> children;
}
