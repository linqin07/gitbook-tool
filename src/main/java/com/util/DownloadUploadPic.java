package com.util;

import com.alibaba.fastjson.JSON;
import com.entity.SMResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
import lombok.Cleanup;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Function:图片下载
 *
 * @author crossoverJie Date: 2019-05-07 00:14
 * @since JDK 1.8
 */
public class DownloadUploadPic {

    private static Logger logger = LoggerFactory.getLogger(DownloadUploadPic.class);

    private static OkHttpClient httpClient;

    static {
        httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static void download(String urlString, String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            logger.info("[{}]已下载完毕", fileName);
            return;
        } else {
            File path = new File(file.getParent());
            path.mkdirs();
        }

        URL url = null;
        @Cleanup OutputStream os = null;
        @Cleanup InputStream is = null;

        url = new URL(urlString);
        URLConnection con = url.openConnection();
        // 输入流
        is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        os = new FileOutputStream(fileName);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }


    }


    /**
     * @param path 文件的绝对路径
     * @param errorTime 错误重试次数
     */
    public static String upload(String path, int errorTime)
            throws InterruptedException, IOException {
        if (errorTime == 5) {
            logger.error("[{}]上传失败次数达到上限{}次", path, errorTime);
            return null;
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File(path));

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("smfile", "i", fileBody)
                .build();

        Request request = new Request.Builder()
                .url("https://sm.ms/api/upload")
                .header("User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            try {
                SMResponse smResponse = JSON.parseObject(body.string(), SMResponse.class);
                return smResponse.getData().getUrl();
            } catch (Exception e) {
                logger.error("上传图片[{}]失败 res=[{}]", path, body.string());
                errorTime++;
                TimeUnit.SECONDS.sleep(10000);
                return upload(path, errorTime);
            } finally {
                body.close();
            }
        }
        return null;
    }

    /**
     * 　　* 判断链接是否有效 　　* 输入链接 　　* 返回true或者false
     */
    public static boolean isValid(String strLink) {
        URL url;
        try {
            url = new URL(strLink);
            HttpURLConnection connt = (HttpURLConnection) url.openConnection();
            connt.setRequestMethod("HEAD");
            String strMessage = connt.getResponseMessage();
            if (strMessage.compareTo("Not Found") == 0) {
                return false;
            }
            connt.disconnect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}

