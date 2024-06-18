FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY build/libs/*.jar /app/wishlist-app.jar

EXPOSE 8000
ENTRYPOINT ["java","-jar","/app/wishlist-app.jar"]
