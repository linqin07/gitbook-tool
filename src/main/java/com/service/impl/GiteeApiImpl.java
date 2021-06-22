package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.entity.gitee.GetRequest;
import com.entity.gitee.GetResponse;
import com.entity.gitee.UploadRequest;
import com.entity.gitee.UploadResponse;
import com.service.API;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description: https://gitee.com/api/v5/swagger#/getV5ReposOwnerRepoContents(Path)
 * @author: LinQin
 * @date: 2021/06/11
 */
@Slf4j
@Service
public class GiteeApiImpl extends API {

    @Value("${gitee.owner:linqin07}")
    private String owner;

    @Value("${gitee.repo:pic}")
    private String repo;

    public UploadResponse upload(UploadRequest uploadRequest) {
        String url = "https://gitee.com/api/v5/repos/%s/%s/contents/%s";
        url = String.format(url, owner, repo, uploadRequest.getName());
        log.debug("gitee upload url: {}", url);

        MediaType json = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON.toJSONString(uploadRequest));
        Request request = new Builder().url(url).post(body)
                                       .build();
        try (Response response = client.newCall(request).execute()) {
            log.debug("responseBody: {}", response.toString());
            if (!response.isSuccessful()) {
                // 查询有没有上传成功
                GetRequest getRequest = new GetRequest(uploadRequest.getAccess_token(), uploadRequest.getName());
                GetResponse getResponse = get(getRequest);
                if (getResponse == null) {
                    log.error("上传文件失败: {}, {}", uploadRequest.getFilePath(), uploadRequest.getName());
                } else {
                    UploadResponse.ContentBean contentBean = new UploadResponse.ContentBean();
                    contentBean.setDownload_url(getResponse.getDownload_url());
                    contentBean.setSha(getResponse.getSha());
                    contentBean.setPath(getResponse.getPath());
                    contentBean.setName(getResponse.getName());
                    UploadResponse uploadResponse = new UploadResponse();
                    uploadResponse.setContent(contentBean);
                    return uploadResponse;
                }
                return null;
            }

            if (response.code() == 400) {
                log.debug("重复上传失败：{}", response.message());
                return null;
            }
            String responseBody = response.body().string();
            UploadResponse uploadResponse = JSON.parseObject(responseBody, UploadResponse.class);
            return uploadResponse;

        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("\r\n");
        return null;
    }

    @Override
    public GetResponse get(GetRequest getRequest) {
        // https://gitee.com/api/v5/swagger#/getV5ReposOwnerRepoContents(Path)
        String url = "https://gitee.com/api/v5/repos/%s/%s/contents/%s?access_token=%s";
        url = String.format(url, owner, repo, getRequest.getPath(), getRequest.getAccess_token());
        log.debug("gitee get url: {}", url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("查询文件失败: {}", response.message());
                return null;
            }
            String responseBody = response.body().string();
            GetResponse getResponse = JSON.parseObject(responseBody, GetResponse.class);
            return getResponse;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
