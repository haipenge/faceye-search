#!/bin/bash
ROOT=$(dirname $(cd "$(dirname "$0")";pwd))
echo $ROOT
cd $ROOT/faceye-search-manager
build_env='product'
if [[ -n $1 ]];then
	build_env=$1
fi
_count=`ps -ef | grep jetty | grep -v "grep" | wc -l`
echo 'Jetty Process count is:'$_count
echo 'Now,Build Env is:',$build_env
echo 'Jetty is killed,rebuild project and start jetty now'
mvn clean compile jetty:run -D maven.test.skip=true -D jetty.port=8081 -P $build_env