FROM openjdk:17-alpine
ADD /build/libs/*.jar user.jar
ENTRYPOINT ["java", "-jar", "/user.jar"]