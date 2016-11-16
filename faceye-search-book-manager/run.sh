####################################
#Desc:自动部署与启动停止任务
#Author:@haipenge
#Date:2015.01.03
####################################
echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
echo $1
if [ $1 == "stop" ];then
   echo '>>>Will Stop Rsin....'
   /tools/resin/resin-search-manager/bin/resin.sh stop
   sleep 15
   echo `ps aux|grep resin-search-manager |grep -v grep  | awk '{print $2}'|xargs kill -9`
   echo '... resin-search-maanger is stop now.'
   exit 0
else
echo '开始检测磁盘是否就绪'
if [ -d '/Volumes/Back' ];then
   echo '-----/Volumes/Back ...就绪'
 else
   #如果磁盘不存在，则退出
   echo '-----/Volumes/Back 磁盘未找到'
   #exit 0
fi

echo '>>>>开始检测MySQL是否启动'
#如果MySQL已启动，则继续执行
#如果MySQL未启动，则启动mysql服务
process=`ps axu | grep mysql | grep -v grep`
#port=`netstat  -nltp | grep 3306 | grep mysql`
#if [[ $process && $port ]];then
if [ "$process" != "" ];then
  echo 'MySQL已启动...就绪'
else
  echo '-----MySQL未启动----'
fi
echo '>>>>开始检测MongoDB是否启动'
#如果MongoDb已启动，则继续执行
#如果MongDB未启动，则启动MongoDB
mon_process=`ps axu | grep mongodb | grep -v grep`
if [ "$mon_process" != "" ];then
  echo 'MongoDb已启动....就绪'
else
  echo '....MongoDB 未启动...'
  exit 0
  #echo '/etc/init.d/mongodb start'
fi

echo '开始系统清理、打包、构建'
mvn clean compile war:war -P test -D maven.test.skip=true
/tools/resin/resin-search-manager/bin/resin.sh stop
sleep 15
echo `ps aux|grep resin-search-manager |grep -v grep  | awk '{print $2}'|xargs kill -9`
echo '... resin-search-maanger is stop now.'
rm -rf   /tools/resin/search/manager/*
rm -rf /tools/resin/logs/faceye-search-manager/*
cp target/faceye-search-manager.war  /tools/resin/search/manager/
/tools/resin/resin-search-manager/bin/resin.sh start
echo 'Resin is start now.'
echo '<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<'
fi