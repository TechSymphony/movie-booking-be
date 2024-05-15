FROM amazoncorretto:17 as build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw install
# run java jar file
CMD ["java", "-jar", "./target/movie_booking-0.0.1-SNAPSHOT.jar"]