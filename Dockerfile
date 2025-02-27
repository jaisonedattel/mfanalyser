FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar mfanalyser.jar
ENTRYPOINT ["java","-jar","/mfanalyser.jar"]
