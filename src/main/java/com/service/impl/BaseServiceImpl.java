package com.service.impl;

import com.dao.InfoMapper;
import com.entity.Info;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.service.BaseService;
import com.util.DownloadUploadPic;
import com.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/05/31
 */
@Service
@Slf4j
public class BaseServiceImpl implements BaseService {

    /**
     * 匹配： ![下载地址](https://i.loli.net/2019/07/30/5d400294d103e20393.jpg)
     */
    final static String regex1 = "\\s*!\\[.*\\]\\(https://i\\.loli.*\\)$";
    /**
     * 正则 ![下载地址](assets/1550052930676.png)
     */
    final static String regex2 = "assets\\/.*\\.(png|gif)";
    /**
     * 预先编译，多行模式 ![下载地址](assets\1550052930676.png)
     */
    final static String regex3 = "\\s*!\\[.*\\]\\(assets\\/.*\\.(png|gif)\\)$";

    /**
     * 匹配 windos 本地地址图片
     * ![下载地址](F:\GitBook\Markdown入门到放弃\bak\1550052930676.png)
     */
    final static String regex4 = "\\s*!\\[.*\\]\\([^http|^assets].*\\)$";

    private final static Pattern PATTERN1;
    private final static Pattern PATTERN2;
    private final static Pattern PATTERN3;
    private final static Pattern PATTERN4;

    static {
        PATTERN1 = Pattern.compile(regex1, Pattern.MULTILINE);
        PATTERN2 = Pattern.compile(regex2, Pattern.MULTILINE);
        PATTERN3 = Pattern.compile(regex3, Pattern.MULTILINE);
        PATTERN4 = Pattern.compile(regex4, Pattern.MULTILINE);
    }

    @Autowired
    private InfoMapper infoMapper;

    /**
     * 备份md文件的图片
     *
     * @param markDownFilePath 笔记目录
     * @param bakPath          备份图片保存的路径
     */
    @Override
    public void bak(String markDownFilePath, String bakPath) throws IOException {
        File file = new File(markDownFilePath);
        List<File> markDownFileLists = FileUtil.getMarkDownFile(file);
        markDownFileLists.forEach(System.out::println);

        for (File file1 : markDownFileLists) {
            List<String> content = FileUtil.readFileContent(file1);
            List<String> matchContent = FileUtil.matchContent(content, PATTERN1);
            matchContent.stream().map(item -> {
                int start = item.indexOf("(");
                item = item.substring(start + 1, item.length() - 1);
                return item;
            }).forEach(downUploadUrl -> {
                // 下载图片
                log.debug("下载连接{}", downUploadUrl);
                try {
                    Info info = infoMapper.selectByPicUrl(downUploadUrl);

                    String localUrl = bakPath + File.separator + info.getPicName();
                    DownloadUploadPic
                            .download(downUploadUrl, localUrl);
                    info.setPicLocalPath(localUrl);
                    String picUrlMd = info.getPicUrlMd();
                    info.setPicLocalMd(picUrlMd.replace(downUploadUrl, localUrl));
                    infoMapper.updateById(info);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    @Override
    public void upload(String markDownFilePath) throws IOException {
        File file = new File(markDownFilePath);
        List<File> markDownFileList = FileUtil.getMarkDownFile(file);

        for (File fileName : markDownFileList) {
            List<String> content = FileUtil.readFileContent(fileName);
            // 获取匹配字符串：![下载地址](assets/1550052930676.png)
            List<String> urlList = FileUtil.matchContent(content, PATTERN3);
            //获取图片的绝对路径 assets/1550052930676.png
            List<String> filePathList = FileUtil.matchContent(content, PATTERN2);
            List<String> collect = filePathList.stream().map(item -> {
                // 拼接路径
                item = fileName.getParent() + File.separator + item;
                return item;
            }).collect(Collectors.toList());

            List<String> list = Lists.newArrayList();
            for (String s : collect) {
                String uploadPath;
                try {
                    // 替换原来的图片路径
                    uploadPath = DownloadUploadPic.upload(s, 0);
                    list.add(uploadPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            list.forEach(System.out::println);

            // 更新替换文本
            if (!CollectionUtils.isEmpty(list)) {
                List<String> newContent = replacePicPath(content, filePathList, list);
                String join = Joiner.on(System.lineSeparator()).join(newContent);
                ByteArrayInputStream bis = new ByteArrayInputStream(join.getBytes());
                Files.copy(bis, fileName.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    @Override
    public void check(String path) throws IOException {
        File file = new File(path);
        List<File> markDownFileList = FileUtil.getMarkDownFile(file);
        markDownFileList.forEach(System.out::println);

        for (File item : markDownFileList) {
            List<String> content = FileUtil.readFileContent(item);
            // 匹配： ![下载地址](https://i.lsd83c93567.jpg)
            List<String> urlList = FileUtil.matchContent(content, PATTERN1);
            Map<String, String> checkMap = Maps.newHashMap();

            urlList.forEach(url -> {
                String picHttpUrl = url.substring(url.indexOf("(") + 1, url.length() - 1);
                boolean valid = DownloadUploadPic.isValid(picHttpUrl);
                log.info("地址：{} {}", picHttpUrl, valid);
                if (!valid) {
                    Map<String, Object> map = Maps.newConcurrentMap();
                    map.put("pic_url", picHttpUrl);
                    Info info = infoMapper.selectByMap(map).get(0);
                    // 上传本地备份图片，替换地址
                    if (Objects.nonNull(info.getPicLocalPath())) {
                        try {
                            String uploadPath = DownloadUploadPic.upload(info.getPicLocalPath(), 0);
                            String oldPicUrlMd = info.getPicUrlMd();
                            String oldPicMd = info.getPicLocalMd();
                            String substring = oldPicMd.substring(oldPicMd.indexOf("(") + 1, oldPicMd.indexOf(")"));
                            info.setPicUrlMd(oldPicMd.replace(substring, uploadPath)).setPicUrl(uploadPath);
                            infoMapper.updateById(info);
                            //  md 替换
                            checkMap.put(oldPicUrlMd, info.getPicUrlMd());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //  md 替换
            if (!CollectionUtils.isEmpty(checkMap)) {
                List<String> collect = content.stream().map(c -> {
                    if (checkMap.containsKey(c)) {
                        c = checkMap.get(c);
                        log.info("替换链接失效图片：{}", checkMap.get(c));
                    }
                    return c;
                }).collect(Collectors.toList());

                replaceMd(item, collect);
            }
        }

    }

    @Override
    public void local(String path) throws IOException {
        File file = new File(path);
        List<File> markDownFileList = FileUtil.getMarkDownFile(file);
        markDownFileList.forEach(System.out::println);

        for (File fileName : markDownFileList) {
            List<String> content = FileUtil.readFileContent(fileName);
            // 获取匹配字符串：![下载地址](https://i.lsd83c93567.jpg
            List<String> urlList = FileUtil.matchContent(content, PATTERN1);

            if (CollectionUtils.isEmpty(urlList)) continue;
            Map<String, String> replaceMap = urlList.stream().collect(Collectors.toMap(String::toString, item -> {
                Info info = infoMapper.selectByPicUrlMd(item);
                return info.getPicLocalMd();
            }));

            List<String> collect = getReplaceContent(content, replaceMap);

            // 替换为 md
            replaceMd(fileName, collect);
        }
    }

    private List<String> getReplaceContent(List<String> content, Map<String, String> replaceMap) {
        return content.stream().map(item -> {
            if (replaceMap.containsKey(item)) {
                return replaceMap.get(item);
            } else {
                return item;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public void httpUrl(String path) throws IOException {
        File file = new File(path);
        List<File> markDownFileList = FileUtil.getMarkDownFile(file);
        markDownFileList.forEach(System.out::println);

        for (File fileName : markDownFileList) {
            List<String> content = FileUtil.readFileContent(fileName);
            // 获取匹配字符串：![下载地址](F:\GitBook\Markdown入门到放弃\bak\1550052930676.png)
            List<String> urlModel = FileUtil.matchContent(content, PATTERN4);

            if (CollectionUtils.isEmpty(urlModel)) continue;
            Map<String, String> replaceMap = urlModel.stream().collect(Collectors.toMap(String::toString, item -> {
                Info info = infoMapper.selectByPicLocalMd(item);
                return info.getPicUrlMd();
            }));

            List<String> collect = getReplaceContent(content, replaceMap);

            // 替换为 md
            replaceMd(fileName, collect);
        }
    }


    private void replaceMd(File fileName, List<String> collect) throws IOException {
        String newCcontent = Joiner.on(System.lineSeparator()).join(collect);
        ByteArrayInputStream bis = new ByteArrayInputStream(newCcontent.getBytes(
                StandardCharsets.UTF_8));
        Files.copy(bis, fileName.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * @param filePathList assert/xxx.png
     * @param picPath      图床地址
     */
    private List<String> replacePicPath(List<String> content, List<String> filePathList,
            List<String> picPath) {
        List<String> list = Lists.newArrayList();
        for (String contentUrl : content) {
            for (int j = 0; j < filePathList.size(); j++) {
                String oldUrl = filePathList.get(j);
                if (contentUrl.contains(oldUrl)) {
                    contentUrl = contentUrl.replace(oldUrl, picPath.get(j));
                    // 保存入库
                    Info info = new Info();
                    info.setPicName(oldUrl.substring(oldUrl.indexOf("/") + 1))
                        .setPicUrl(picPath.get(j))
                        .setPicUrlMd(contentUrl);
                    infoMapper.insert(info);
                }

            }
            list.add(contentUrl);
        }
        return list;
    }

}
