FROM hub.c.163.com/library/java:8-jre
VOLUME /tmp

ADD *.jar vueadmin.jar

#暴露端口号
EXPOSE 8080

ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF8" ,"-Duser.timezone=GMT+08", "-jar", "/vueadmin.jar" ]
