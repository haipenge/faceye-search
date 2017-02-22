#!/bin/bash
#git pull
mvn clean compile jetty:run -D jetty.port=8094 -D maven.test.skip=true -P test
