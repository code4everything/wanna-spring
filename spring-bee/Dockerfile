# docker build cmd: docker build -t spring-bee-docker .

# 基础镜像
FROM openjdk:8-jdk-alpine
# 作者信息
MAINTAINER "easepan easepan@qq.com"
# 工作目录
WORKDIR /
# 复制文件
COPY config.json /
# 暴露端口
EXPOSE 8099
# 添加JAR文件
ADD target/spring-bee-1.2.0.jar /app.jar
# 启动镜像自动运行程序
ENTRYPOINT ["java","-jar","app.jar"]
