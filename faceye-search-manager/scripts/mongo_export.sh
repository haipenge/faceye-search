mongo_bin=/data/tools/mongo/mongodb-linux-x86_64-3.0.7/bin
#./mongoexport -d search -c search_article -o /data/bak/mongo/search_article_20140930.bak
cd $mongo_bin
./mongoexport -d search -c security_user -o /data/bak/search/security_user.bak
./mongoexport -d search -c security_role -o /data/bak/search/security_role.bak
./mongoexport -d search -c security_resource -o /data/bak/search/security_resource.bak
./mongoexport -d search -c stock_stock -o /data/bak/search/stock_stock.bak
./mongoexport -d search -c stock_category -o /data/bak/search/stock_category.bak
./mongoexport -d search -c stock_dailyData -o /data/bak/search/stock_dailyData.bak
./mongoexport -d search -c spider_site -o /data/bak/search/spider_site.bak
./mongoexport -d search -c spider_matcherConfig -o /data/bak/search/spider_matcherConfig.bak
./mongoexport -d search -c filter_word -o /data/bak/search/filter_word.bak
./mongoexport -d search -c faceye_11_book_author -o /data/bak/search/faceye_11_book_author.bak
./mongoexport -d search -c faceye_11_book_book -o /data/bak/search/faceye_11_book_book.bak
./mongoexport -d search -c faceye_11_book_bookcategory -o /data/bak/search/faceye_11_book_bookcategory.bak
./mongoexport -d search -c faceye_11_book_booktag -o /data/bak/search/faceye_11_book_booktag.bak
./mongoexport -d search -c faceye_11_book_downloadresource -o /data/bak/search/faceye_11_book_downloadresource.bak
exit 0


