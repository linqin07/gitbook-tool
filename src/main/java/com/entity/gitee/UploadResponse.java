package com.entity.gitee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description:
 * @author: LinQin
 * @date: 2021/06/11
 */
@NoArgsConstructor
@Data
public class UploadResponse {

    /**
     * content : {"name":"ds.png","path":"ds.png","size":11211,"sha":"77fe3276b5849323cfb6a353de02e99f1adc1443","type":"file","url":"https://gitee.com/api/v5/repos/linqin07/pic/contents/ds.png","html_url":"https://gitee.com/linqin07/pic/blob/master/ds.png","download_url":"https://gitee.com/linqin07/pic/raw/master/ds.png","_links":{"self":"https://gitee.com/api/v5/repos/linqin07/pic/contents/ds.png","html":"https://gitee.com/linqin07/pic/blob/master/ds.png"}}
     * commit : {"sha":"39cdb706af693eb55443611337f87d58e1d634e1","author":{"name":"LinQin","date":"2021-06-11T13:00:34+08:00","email":"woshilinqin163@163.com"},"committer":{"name":"Gitee","date":"2021-06-11T13:00:34+08:00","email":"noreply@gitee.com"},"message":"asdasd","tree":{"sha":"57035376381f51db6a52ddd5cd8e66793ee5adcd","url":"https://gitee.com/api/v5/repos/linqin07/pic/git/trees/57035376381f51db6a52ddd5cd8e66793ee5adcd"},"parents":[{"sha":"44331022254e489f53573d78abab058e278f7a63","url":"https://gitee.com/api/v5/repos/linqin07/pic/commits/44331022254e489f53573d78abab058e278f7a63"}]}
     */

    private ContentBean content;
    private CommitBean commit;

    @NoArgsConstructor
    @Data
    public static class ContentBean {
        /**
         * name : ds.png
         * path : ds.png
         * size : 11211
         * sha : 77fe3276b5849323cfb6a353de02e99f1adc1443
         * type : file
         * url : https://gitee.com/api/v5/repos/linqin07/pic/contents/ds.png
         * html_url : https://gitee.com/linqin07/pic/blob/master/ds.png
         * download_url : https://gitee.com/linqin07/pic/raw/master/ds.png
         * _links : {"self":"https://gitee.com/api/v5/repos/linqin07/pic/contents/ds.png","html":"https://gitee.com/linqin07/pic/blob/master/ds.png"}
         */

        private String name;
        private String path;
        private int size;
        private String sha;
        private String type;
        private String url;
        private String html_url;
        private String download_url;
        private LinksBean _links;

        @NoArgsConstructor
        @Data
        public static class LinksBean {
            /**
             * self : https://gitee.com/api/v5/repos/linqin07/pic/contents/ds.png
             * html : https://gitee.com/linqin07/pic/blob/master/ds.png
             */

            private String self;
            private String html;
        }
    }

    @NoArgsConstructor
    @Data
    public static class CommitBean {
        /**
         * sha : 39cdb706af693eb55443611337f87d58e1d634e1
         * author : {"name":"LinQin","date":"2021-06-11T13:00:34+08:00","email":"woshilinqin163@163.com"}
         * committer : {"name":"Gitee","date":"2021-06-11T13:00:34+08:00","email":"noreply@gitee.com"}
         * message : asdasd
         * tree : {"sha":"57035376381f51db6a52ddd5cd8e66793ee5adcd","url":"https://gitee.com/api/v5/repos/linqin07/pic/git/trees/57035376381f51db6a52ddd5cd8e66793ee5adcd"}
         * parents : [{"sha":"44331022254e489f53573d78abab058e278f7a63","url":"https://gitee.com/api/v5/repos/linqin07/pic/commits/44331022254e489f53573d78abab058e278f7a63"}]
         */

        private String sha;
        private AuthorBean author;
        private CommitterBean committer;
        private String message;
        private TreeBean tree;
        private List<ParentsBean> parents;

        @NoArgsConstructor
        @Data
        public static class AuthorBean {
            /**
             * name : LinQin
             * date : 2021-06-11T13:00:34+08:00
             * email : woshilinqin163@163.com
             */

            private String name;
            private String date;
            private String email;
        }

        @NoArgsConstructor
        @Data
        public static class CommitterBean {
            /**
             * name : Gitee
             * date : 2021-06-11T13:00:34+08:00
             * email : noreply@gitee.com
             */

            private String name;
            private String date;
            private String email;
        }

        @NoArgsConstructor
        @Data
        public static class TreeBean {
            /**
             * sha : 57035376381f51db6a52ddd5cd8e66793ee5adcd
             * url : https://gitee.com/api/v5/repos/linqin07/pic/git/trees/57035376381f51db6a52ddd5cd8e66793ee5adcd
             */

            private String sha;
            private String url;
        }

        @NoArgsConstructor
        @Data
        public static class ParentsBean {
            /**
             * sha : 44331022254e489f53573d78abab058e278f7a63
             * url : https://gitee.com/api/v5/repos/linqin07/pic/commits/44331022254e489f53573d78abab058e278f7a63
             */

            private String sha;
            private String url;
        }
    }
}
