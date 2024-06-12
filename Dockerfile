FROM maven:3-eclipse-temurin-17-alpine AS deps

WORKDIR /app

COPY pom.xml ./

RUN mvn go-offline:resolve-dependencies


FROM maven:3-eclipse-temurin-17-alpine AS dev

WORKDIR /app
COPY --from=deps /root/.m2/repository /root/.m2/repository
COPY ./docker-entrypoint.sh /docker-entrypoint.sh

COPY pom.xml ./
COPY src ./src

RUN apk add inotify-tools
RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]



FROM amazoncorretto:17 as build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw install
# run java jar file
CMD ["java", "-jar", "./target/movie_booking-0.0.1-SNAPSHOT.jar"]
