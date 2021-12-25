# 部署步骤
## 1. 选择服务器
至少支持`openjdk-17-jdk`、`mysql 8.0`

这里以Ubuntu 21.04为例
## 2. 安装依赖
```bash
apt update
apt --no-install-recommends -y install openjdk-18-jdk-headless mysql-server
```
## 3. 在服务器上下载源代码并解压
## 4. 配置源代码
1. 在mysql上创建一个数据库给网站用
2. 下载mysql jdbc驱动，放到源代码的`WEB-INF/lib`文件夹下
3. 修改`WEB-INF/classes/META-INF/persistence.xml`文件
![image](https://user-images.githubusercontent.com/24429886/147379409-3e13c3d9-b9a6-4aed-a9a8-2eb63f951948.png)
4. 修改`WEB-INF/classes/db/File_pool.java`文件

修改`public final static String path`为一个目录用于存放上传的文件，请不要和网站目录重叠
## 3. 部署nginx
配置nginx
```conf
server {
    listen 443 http2;
    server_name 域名;
    ...(TLS配置)
    root /usr/local/nginx/html/xybnb.com;
    client_max_body_size 0;
    location /api/ {
        proxy_pass http://127.0.0.1:8080;
    }
}
```
## 1. 安装依赖
服务器使用Ubuntu 21.04版本

nginx client_max_bod_size 0;

tomcat cont/server.xml maxPostSize="-1" maxParameterCount="-1"
