FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/save_food-0.0.1.jar
COPY ${JAR_FILE} app_savefood.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_savefood.jar"]