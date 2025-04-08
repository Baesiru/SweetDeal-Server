FROM openjdk:17-alpine
ADD /build/libs/*.jar gateway.jar
ENTRYPOINT ["java", "-jar", "/gateway.jar"]