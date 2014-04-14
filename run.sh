#!/bin/bash

./gradlew clean stage
foreman start -p 8182 -e dev.env
