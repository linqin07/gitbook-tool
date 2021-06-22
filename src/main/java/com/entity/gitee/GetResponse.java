package com.entity.gitee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: LinQin
 * @date: 2021/06/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetResponse {

    /**
     * type : file
     * encoding : base64
     * size : 663787
     * name : ai-breakpoint.gif
     * path : ai-breakpoint.gif
     * content : xx
     * sha : 6dec13cc58e4a02beea0bc43a2bea1bd5233bdee
     * url : https://gitee.com/api/v5/repos/linqin07/pic/contents/ai-breakpoint.gif
     * html_url : https://gitee.com/linqin07/pic/blob/master/ai-breakpoint.gif
     * download_url : https://gitee.com/linqin07/pic/raw/master/ai-breakpoint.gif
     * _links : {"self":"https://gitee.com/api/v5/repos/linqin07/pic/contents/ai-breakpoint.gif","html":"https://gitee.com/linqin07/pic/blob/master/ai-breakpoint.gif"}
     */

    private String type;
    private String encoding;
    private int size;
    private String name;
    private String path;
    private String content;
    private String sha;
    private String url;
    private String html_url;
    private String download_url;
    private LinksBean _links;

    @NoArgsConstructor
    @Data
    public static class LinksBean {
        /**
         * self : https://gitee.com/api/v5/repos/linqin07/pic/contents/ai-breakpoint.gif
         * html : https://gitee.com/linqin07/pic/blob/master/ai-breakpoint.gif
         */

        private String self;
        private String html;
    }
}
