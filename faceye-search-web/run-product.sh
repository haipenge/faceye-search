#Auto Deploy shell.
resin_home=/app/resin/resin-search-web
deploy_path=/data/deploy/faceye-search-web
if [ $1 == "stop" ];then
  $resin_home/bin/resin.sh stop
  sleep 15
  echo `ps aux|grep resin-search-web |grep -v grep  | awk '{print $2}'|xargs kill -9`
  echo '... resin-search is stop now.'
else
  mvn clean compile war:war -D maven.test.skip=true -P product
  $resin_home/bin/resin.sh stop
  sleep 15
  echo `ps aux|grep resin-search-web |grep -v grep  | awk '{print $2}'|xargs kill -9`
  echo '... resin-search-web is stop now.'
  rm -rf $deploy_path/*
  cp target/*.war  $deploy_path/
  $resin_home/bin/resin.sh start
  echo 'Resin is start now.'
 fi
