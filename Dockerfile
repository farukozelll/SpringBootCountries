FROM openjdk:11-jre-slim
COPY target/Countries-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_PROFILES_ACTIVE = dev
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","/app.jar"]


