FROM eclipse-temurin:17-jre
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Port de la Gateway (souvent 8080 ou 9090)
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]