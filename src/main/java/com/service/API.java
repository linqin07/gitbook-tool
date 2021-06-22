package com.service;

import com.entity.gitee.GetRequest;
import com.entity.gitee.GetResponse;
import com.entity.gitee.UploadRequest;
import com.entity.gitee.UploadResponse;

/**
 * Description: api
 * author: 林钦
 * date: 2021/06/11
 */
public abstract class API {
    public abstract UploadResponse upload(UploadRequest uploadRequest);

    public abstract GetResponse get(GetRequest getRequest);
}
