1.mongo数据库导出：
./bin/mongodump -h localhost -d search -o /tmp/
2.mongo数据库导入
./bin/mongorestore  -d search /data/bak/search-20150408/search