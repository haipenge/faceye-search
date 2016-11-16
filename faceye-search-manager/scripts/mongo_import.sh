mongo_bin=/data/tools/mongo/mongodb-linux-x86_64-3.0.7/bin
#./mongoimport -d search -c search_article  /data/bak/mongo/search_article_20140930.bak
cd $mongo_bin
./mongoimport -d search -c security_user  /data/bak/search/security_user.bak
./mongoimport -d search -c security_role  /data/bak/search/security_role.bak
./mongoimport -d search -c security_resource  /data/bak/search/security_resource.bak
./mongoimport -d search -c stock_stock  /data/bak/search/stock_stock.bak
./mongoimport -d search -c stock_category  /data/bak/search/stock_category.bak
./mongoimport -d search -c stock_dailyData  /data/bak/search/stock_dailyData.bak
./mongoimport -d search -c spider_site  /data/bak/search/spider_site.bak
./mongoimport -d search -c spider_matcherConfig  /data/bak/search/spider_matcherConfig.bak
./mongoimport -d search -c filter_word  /data/bak/search/filter_word.bak
./mongoimport -d search -c faceye_11_book_author  /data/bak/search/faceye_11_book_author.bak
./mongoimport -d search -c faceye_11_book_book  /data/bak/search/faceye_11_book_book.bak
./mongoimport -d search -c faceye_11_book_bookcategory  /data/bak/search/faceye_11_book_bookcategory.bak
./mongoimport -d search -c faceye_11_book_booktag  /data/bak/search/faceye_11_book_booktag.bak
./mongoimport -d search -c faceye_11_book_downloadresource  /data/bak/search/faceye_11_book_downloadresource.bak
exit 0
