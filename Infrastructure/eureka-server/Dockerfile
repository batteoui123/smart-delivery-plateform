FROM eclipse-temurin:17-jre

WORKDIR /app

# L'argument JAR_FILE pointe toujours vers target/*.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Port par défaut d'Eureka
EXPOSE 8761

ENTRYPOINT ["java","-jar","/app/app.jar"]