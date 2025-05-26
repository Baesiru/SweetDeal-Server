FROM openjdk:17-alpine
ADD /build/libs/*.jar order.jar
ENTRYPOINT ["java", "-jar", "/order.jar"]