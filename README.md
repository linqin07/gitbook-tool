#SQL 语句

CREATE DATABASE 
USE `mybatis-plus`;

DROP TABLE IF EXISTS `info`;

CREATE TABLE `info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pic_name` varchar(128) DEFAULT NULL COMMENT '图片名名称',
  `pic_local_path` varchar(128) DEFAULT NULL COMMENT '本地图片地址',
  `pic_local_md` varchar(256) DEFAULT NULL COMMENT '本地图片md字符串',
  `pic_url` varchar(128) DEFAULT NULL COMMENT '图片外链',
  `pic_url_md` varchar(256) DEFAULT NULL COMMENT '图片外链md字符串',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

#修改数据库链接
#触发事件都为web请求。参数可以直接修改配置文件。
请求地址：
http://localhost:8080/test
http://localhost:8080/upload
http://localhost:8080/bak
http://localhost:8080/check
http://localhost:8080/local
http://localhost:8080/httpUrl
