FROM openjdk:17-alpine
ADD /build/libs/*.jar store.jar
ENTRYPOINT ["java", "-jar", "/store.jar"]