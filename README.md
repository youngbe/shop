# 部署步骤
## 1. 选择服务器
至少支持`openjdk-17-jdk`、`mysql 8.0`

这里以Ubuntu 21.04为例
## 2. 需要准备的文件
1. 网站源代码
2. tomcat服务器版本10以上
3. mysql的jdbc驱动
## 3. 安装依赖
```bash
apt update
apt --no-install-recommends -y install openjdk-18-jdk-headless mysql-server
```
## 4. 创建一个数据库(create database)给网站用
## 5. 部署Tomcat
1. 设Tomcat的根目录为`${tomcat}`，修改`${tomcat}/conf/server.xml`，设置`maxPostSize="-1" maxParameterCount="-1"`
2. 解压网站源代码到一个临时目录下，设目录为`${temp}`
3. 移动`${temp}/WEB-INF`至`${tomcat}/webapps/${自定义APP名}/`
4. 拷贝jdbc驱动至`${tomcat}/webapps/${自定义APP名}/WEB-INF/lib/`
5. 修改`${tomcat}/webapps/${自定义APP名}/WEB-INF/classes/META-INF/persistence.xml`文件，配置数据库信息

![image](https://user-images.githubusercontent.com/24429886/147379409-3e13c3d9-b9a6-4aed-a9a8-2eb63f951948.png)

6. 修改`${tomcat}/webapps/${自定义APP名}/WEB-INF/classes/db/File_pool.java`文件，修改`public final static String path`为一个目录用于存放上传的文件，请不要和网站目录重叠
7. 编译网站
```bash
cd ${tomcat}/webapps/${自定义APP名}/WEB-INF/classes/
javac -cp ".:../lib/*:../../../../lib/servlet-api.jar" $(find . -name "*.java")
```
## 6. 部署nginx
1. 安装nginx过程略
2. 选择一个目录作为网站根目录`${web_root}`
3. 移动`${temp}`中剩余文件至`${web_root}/${自定义APP名}`
4. 配置nginx
```conf
server {
    listen 443 http2;
    server_name 域名;
    ...(TLS配置)
    root ${web_root};
    client_max_body_size 0;
    location /${自定义APP名}/api/ {
        proxy_pass http://127.0.0.1:8080;
    }
}
```
## 7. 重新启动nginx，启动Tomcat，删除临时目录
## 8. 访问 https://域名/${自定义APP名}
