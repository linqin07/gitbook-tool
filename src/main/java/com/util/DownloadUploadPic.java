package com.util;

import com.alibaba.fastjson.JSON;
import com.entity.SMResponse;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import lombok.Cleanup;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;


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
                                       .connectTimeout(60, TimeUnit.SECONDS)
                                       .readTimeout(60, TimeUnit.SECONDS)
                                       .writeTimeout(60, TimeUnit.SECONDS)
                                       .retryOnConnectionFailure(true)
                                       .build();
    }

    public static void download(String urlString, String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists() && picIsValid(fileName)) {
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
        con.addRequestProperty("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        con.setReadTimeout(50000);
        con.setConnectTimeout(50000);
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

        logger.info("\n");

    }


    /**
     * @param path      文件的绝对路径
     * @param errorTime 错误重试次数
     */
    public static String upload(String path, int errorTime, String token)
            throws InterruptedException, IOException {
        if (errorTime == 0) {
            logger.error("[{}]上传失败次数达到上限次", path, errorTime);
            return null;
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File(path));

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("smfile", path.substring(path.lastIndexOf("/") + 1), fileBody)
                .build();

        Request request = new Request.Builder()
                .url("https://sm.ms/api/v2/upload")
                .header("Authorization", token)
                .header("User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();
        ResponseBody body = response.body();
        String string = new String(body.bytes(), Charset.defaultCharset());

        if (response.isSuccessful()) {
            try {
                SMResponse smResponse = JSON.parseObject(string, SMResponse.class);
                return smResponse.getData().getUrl();
            } catch (Exception e) {
                logger.error("上传图片[{}]失败 res=[{}]", path, string);
                e.printStackTrace();
                errorTime--;
                Thread.sleep(20 * 1000);
                return upload(path, errorTime, token);
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

    public static boolean picIsValid(String localStr) {
        try {
            InputStream inputStream = null;
            if (!localStr.contains("http")) {
                File f = new File(localStr);
                inputStream = new FileInputStream(f);
            } else {
                inputStream = new URL(localStr).openStream();
            }
            try {
                BufferedImage sourceImg = ImageIO.read(inputStream);//判断图片是否损坏
                int picWidth = sourceImg.getWidth(); //确保图片是正确的（正确的图片可以取得宽度）
                return true;
            } catch (Exception e) {
                //关闭IO流才能操作图片
                inputStream.close();
                // e.printStackTrace();
                return false;
            } finally {
                //最后一定要关闭IO流
                inputStream.close();
            }
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // String upload = upload("F:\\hexo\\vuepress\\docs\\Markdown入门到放弃\\vuepress搭建博客\\assets\\1563681083699.png", 1, "ghBpNYi13XbbnmSi0Ionk4qGBtjxv7f1");
        // System.out.println(upload);

        // String url = "F:\\hexo\\vuepress\\docs\\.vuepress\\picBak\\image-20210319180100026.png";
        String url = "F:\\hexo\\vuepress\\docs\\.vuepress\\picBak\\mul_thread.gif";
        System.out.println(picIsValid(url));
        // download();
    }
}

