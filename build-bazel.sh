#!/bin/bash

bazel clean --expunge

bazel build //:todo-microservice_deploy.jar