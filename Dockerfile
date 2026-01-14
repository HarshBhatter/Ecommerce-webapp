# ---------- BUILD STAGE ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven config
COPY ecomMyself/pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY ecomMyself/src ./src
RUN mvn clean package -DskipTests


# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/ecomMyself-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

# FROM maven:3.9.6-eclipse-temurin-21 AS build
# WORKDIR /app
# COPY pom.xml .
# RUN mvn dependency:go-offline
# COPY src ./src
# RUN mvn clean package -DskipTests
# FROM eclipse-temurin:21-jre
# WORKDIR /app
# COPY --from=build /app/target/ecomMyself-0.0.1-SNAPSHOT.jar .
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","/app/ecomMyself-0.0.1-SNAPSHOT.jar"]
