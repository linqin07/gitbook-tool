

## MarkDown 文件替换图片为图床链接

### 前言

平时我们书写博客经常性是粘贴图片到文本中，文件的地址是相对地址。发布到 GitHub 除了会受到图片大小限制，同时打开网页加载图片极慢。这时可以考虑把图片放到图床，生成图片链接地址再贴到 Markdown 文本中，但是重复的操作完全可以使用代码提高生产力。

作者使用的博客是 [vuepress](https://www.linqin.site/)，日常写作记录使用的是 Typora 这个免费工具，该工具可以直接使用截图工具如 QQ、微信等截图到文本中，非常方便。生成的地址为相对路径地址。

本工具实现功能

- 检测外链地址是否过期  http://localhost:8888/check
- 自动上传非外链地址图片到图床中  http://localhost:8888/upload
- `备份笔记目录中的外链地址图片到指定文件夹`  http://localhost:8888/bak
- `统一使用本地图片进行展示`  http://localhost:8888/local
- `统一使用外链地址进行展示`  http://localhost:8888/httpUrl

额外功能：提供生成 vuepress 博客目录的工具类，一键生成目录。



使用的图床为 https://sm.ms/ 免费图床。有可能会更新版本替换 API 注意更新项目。

### 2022.04-27

弃用smsm图床，感觉不靠谱，图片会失效。

购买了阿里云oss图床，10元1年。也可以使用github作为图床。不建议使用gitee，有防盗链（代码里面实现过，不是gitee域名看不了图片）。

上传图片使用pic-go，支持多种图床。很方便。本工具剩下的有用功能如下。

- 备份图片，记录本地地址和图床地址
- 检测图床地址是否失效
- 替换 md 文件的本地地址或者图床地址

### 实现思路

本地图片路径格式

```markdown
![下载地址](assets/1550052930676.png)
```

图床地址格式

```markdown
![下载地址](https://i.loli.net/2019/07/30/5d400294d103e20393.jpg)
```

迭代目录 md 文件匹配响应格式的进行替换，并且记录到数据库中用于本地、外链地址指定显示。方便失效后回滚。



核心类 `BaseServiceImpl`

测试类 `TestController` 按照对应的请求链接进行访问即可。



## 初始化SQL 语句

```sql
/*!40101 SET NAMES utf8 */;
CREATE DATABASE vuepress;
use vuepress;

create table `info` (
	`id` bigint (20),
	`pic_name` varchar (1536),
	`pic_local_path` varchar (1536),
	`pic_url` varchar (1536),
	`sha` varchar (1536)
); 

```

