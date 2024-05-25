#!/bin/sh

while inotifywait -r -e modify /app/src/main/;
do
  mvn compile -o -DskipTests;
done >/dev/null 2>&1 &

mvn clean prepare-package
mvn spring-boot:run
