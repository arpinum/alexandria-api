#!/bin/bash

./gradlew clean stage
foreman start -p 8080 -e dev.env
