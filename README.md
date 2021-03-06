# 信息
姓名：谢元彬

学号：201930344121

网站：https://www.xybnb.com/myshop
# 部署步骤
## 1. 选择服务器
至少支持`openjdk-17-jdk`、`mysql 8.0`

这里以Ubuntu 21.10为例
## 2. 需要准备的文件
1. 网站源代码
2. tomcat服务器版本10以上
3. mysql的jdbc驱动
## 3. 安装依赖
```bash
apt update
apt --no-install-recommends -y install openjdk-18-jdk-headless mysql-server
```
## 4. 创建一个空白的数据库(create database)给网站用
## 5. 部署Tomcat
1. 设Tomcat的根目录为`${tomcat}`，修改`${tomcat}/conf/server.xml`，设置`maxPostSize="-1" maxParameterCount="-1"`
2. 将网站源代码中`web`目录下的内容解压/移动到一个临时目录下，设临时目录为`${temp}`
3. 在`${tomcat}/webapps/`目录下创建一个文件夹`${自定义APP名}`
4. 移动`${temp}/WEB-INF`至`${tomcat}/webapps/${自定义APP名}/`
5. 拷贝jdbc驱动至`${tomcat}/webapps/${自定义APP名}/WEB-INF/lib/`
6. 修改`${tomcat}/webapps/${自定义APP名}/WEB-INF/classes/META-INF/persistence.xml`文件，配置数据库信息

![image](img/1.png)

6. 修改`${tomcat}/webapps/${自定义APP名}/WEB-INF/classes/db/File_pool.java`文件，修改`public final static String path`为一个目录用于存放上传的文件，目录的后面一定要加上`/`，请不要和网站目录重叠
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
