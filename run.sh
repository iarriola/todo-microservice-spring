#!/bin/bash
mvn clean -q

VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

BUILD=${VERSION:4:1}

ONE=1

NEW_BUILD=$(($BUILD+$ONE))

NEW_VERSION=${VERSION//[$BUILD]/$NEW_BUILD}

echo "Setting new version to: ${NEW_VERSION}"

echo $(mvn versions:set -DgenerateBackupPoms=false -DnewVersion=$NEW_VERSION -q)

docker stop todo || true && docker rm todo || true

echo 'Building image...'
mvn -DskipTests -q spring-boot:build-image 

echo 'Running container'
echo $(docker run --name todo -d -p 8080:8080 -p 8084:8084 -p 5431:5432 --network postgres_default incode/todo-microservice:${NEW_VERSION})

docker logs -f todo
