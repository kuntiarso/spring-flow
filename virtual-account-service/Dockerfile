# run stage
FROM eclipse-temurin:21-jdk-jammy
LABEL maintainer="gilang.grk17@gmail.com"
WORKDIR /app

# copy the built JAR from the build stage
COPY /target/*.jar virtual-account-service.jar

# run the application
EXPOSE 43202
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -javaagent:/otel/opentelemetry-javaagent.jar -jar virtual-account-service.jar"]