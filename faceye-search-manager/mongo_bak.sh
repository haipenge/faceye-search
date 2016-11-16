current_date=`date +%Y%m%d-%H%M%S`
cd ~
midr data/bak/$current_date
./app/mongo/mongodb-linux-x86_64-2.6.6/bin/mongodump -h localhost -d search -o data/bak/$current_date
tar -zcvf data/bak/search-$current_date.tar.gz data/bak/$current_date