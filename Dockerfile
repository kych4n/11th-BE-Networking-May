FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/weather-0.0.1-SNAPSHOT.jar /app/cotato-weather-server.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=prod", "cotato-weather-server.jar"]