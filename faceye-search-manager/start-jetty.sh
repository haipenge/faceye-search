#!/bin/bash
ROOT=$(dirname $(cd "$(dirname "$0")";pwd))
echo $ROOT
cd $ROOT/faceye-search-manager
mvn jetty:stop
build_env='product'
if [[ -n $1 ]];then
	build_env=$1
fi
echo 'Now,Build Env is:',$build_env
git pull
mvn clean compile jetty:run -D maven.test.skip=true -D jetty.port=8081 -P $build_env &