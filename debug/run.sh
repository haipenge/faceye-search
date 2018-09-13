#Auto Deploy shell.
resin_home=/tools/resin/resin-search
deploy_path=/tools/resin/search/web
if [ $1 == "stop" ];then
  $resin_home/bin/resin.sh stop
  sleep 15
  echo `ps aux|grep resin-search |grep -v grep  | awk '{print $2}'|xargs kill -9`
  echo '... resin-search is stop now.'
else
  mvn clean compile war:war -D maven.test.skip=true -P test
  $resin_home/bin/resin.sh stop
  sleep 15
  echo `ps aux|grep resin-search |grep -v grep  | awk '{print $2}'|xargs kill -9`
  echo '... resin-search is stop now.'
  rm -rf $deploy_path/*
  cp target/*.war  $deploy_path/
  $resin_home/bin/resin.sh start
  echo 'Resin is start now.'
 fi