# 1) Build (usa JDK + Maven)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn -q -DskipTests package

# 2) Run (JRE leve)
FROM eclipse-temurin:21-jre
ENV PORT=8080 \
    JAVA_OPTS="-XX:MaxRAMPercentage=75 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
WORKDIR /opt/app
COPY --from=build /app/target/quarkus-app ./quarkus-app
EXPOSE 8080
CMD ["sh", "-lc", "java $JAVA_OPTS -Dquarkus.http.port=${PORT} -jar quarkus-app/quarkus-run.jar"]
