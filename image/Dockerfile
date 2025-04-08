FROM openjdk:17-alpine
ADD /build/libs/*.jar image.jar
ENTRYPOINT ["java", "-jar", "/image.jar"]