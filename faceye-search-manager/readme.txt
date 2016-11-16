./mongoexport -d search -c spider_parse_result -o ~/data/bak/mongo/spider_parse_result_20150403.bak

mongo 索引的创建：
db.spider_link.dropIndexes();
db.spider_link.ensureIndex({_id:1,url:-1,isCrawled:1,createDate:-1,type:1,siteId:1,isCrawlSuccess:1},{name:'union_link_index'});

db.spider_crawl_result.dropIndexes();
db.spider_crawl_result.ensureIndex({_id:-1,isParse:1,crawlDate:-1,linkId:1,isParseSuccess:1,siteId:1,linkType:1},{name:'union_crawl_result_Index'});

db.spider_parse_result.dropIndexes();
db.spider_parse_result.ensureIndex({_id:-1,createDate:-1,isPush2Mongo:1,categoryId:1,crawlResultId:1,isPush2ProductEnv:1,level:1,isRemove:1,isAllow:1},{name:'union_parse_result_index'});
