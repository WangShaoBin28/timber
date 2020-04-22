FROM harbor.arrail.com/maven-docker-images/frolvlad-alpine-oraclejdk8_192:v2
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY docker-entrypoint.sh /usr/local/bin/
RUN ln -s /usr/local/bin/docker-entrypoint.sh /entrypoint.sh 	
ENTRYPOINT ["sh", "/entrypoint.sh"]
