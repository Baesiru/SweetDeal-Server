FROM openjdk:17-alpine
ADD /build/libs/*.jar product.jar
ENTRYPOINT ["java", "-jar", "/product.jar"]