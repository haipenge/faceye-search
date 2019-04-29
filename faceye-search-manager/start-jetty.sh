#!/bin/bash
echo `ps aux|grep jetty |grep -v grep  | awk '{print $2}'|xargs kill -9`
build_env = 'product'
if [[ -n $1 ]];then
	build_env=$1
fi
mvn clean compile jetty:run -D maven.test.skip=true -D jetty.port=8081 -P $build_env &