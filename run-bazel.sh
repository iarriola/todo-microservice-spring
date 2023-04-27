#!/bin/bash

java -jar bazel-bin/todo-microservice_deploy.jar >> error.log 2>&1 &

# OR

# docker stop todo || true && docker rm todo || true
# echo $(docker run --name todo -d -p 8080:8080 -p 8084:8084 -p 5431:5432 --network postgres_default incode/todo-microservice:latest)
# docker logs -f todo
