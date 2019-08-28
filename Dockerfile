FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/restaurant-1.0-SNAPSHOT.jar restaurant.jar
ENTRYPOINT ["java", "-jar", "restaurant.jar"]
