package com.faceye.component.parse.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析文章级别常量
	 * 设置解析结果的级别
	 * 
	 * 文章 级别
	 * 1级：不包含禁词，标题长度大于10个符号，内容长度大于500个字符，归属于具体分类
	 * 2级：不包含禁词，标题长度大于10个字符，内容长度在200-500字符，归属于具体分类
	 * 3级：不包含禁词，标题长度大于10个字符，内容长度大于500个字符，归属于其它分类
	 * 4级：不包含禁词，标题长度小于10个字符，长度不限，有分类或无分类
	 * -------------------------------------------------------------------
	 * 5级:包含禁词,标题长度大于10个符号，内容长度大于500个字符。归属于具体分类
	 * 6级,包含禁词，标题长度大于10字符,内容长度在200-500字符，归属于具体分类
	 * 7级,包含禁词，标题长度大于10个字符，内容长度大于500字符，归属于其它分类
	 * 8级,包含禁词，标题长度小于10个字符，长度不限。有分类或无分类
	 * -------------------------------------------------------------------
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年1月28日
 */
public class LevelUtils {

	private static List<Map<String, String>> levels = new ArrayList<Map<String, String>>();

	static {
		for (int i = 0; i <= 8; i++) {
			Map<String, String> map = new HashMap<String, String>();
			switch (i) {
			case 1:
				map.put("level", ""+i);
				map.put("name", ""+i+"级");
				map.put("tips", "不包含禁词，标题长度大于10个符号，内容长度大于500个字符，归属于具体分类");
				break;
			case 2:
				map.put("level", ""+i);
				map.put("name", ""+i+"级");
				map.put("tips", "不包含禁词，标题长度大于10个字符，内容长度在200-500字符，归属于具体分类");
			case 3:
				map.put("level", ""+i);
				map.put("name", ""+i+"级");
				map.put("tips", "不包含禁词，标题长度大于10个字符，内容长度大于500个字符，归属于其它分类");
			case 4:
				map.put("level", ""+i);
				map.put("name", ""+i+"级");
				map.put("tips", "不包含禁词，标题长度小于10个字符，长度不限，有分类或无分类");
			case 5:
				map.put("level", ""+i);
				map.put("name", ""+i+"级");
				map.put("tips", "包含禁词,标题长度大于10个符号，内容长度大于500个字符。归属于具体分类");
			case 6:
				map.put("level", ""+i);
				map.put("name", ""+i+"级");
				map.put("tips", "包含禁词，标题长度大于10字符,内容长度在200-500字符，归属于具体分类");
			case 7:
				map.put("level", ""+i);
				map.put("name", ""+i+"级");
				map.put("tips", "包含禁词，标题长度大于10个字符，内容长度大于500字符，归属于其它分类");
			case 8:
				map.put("level", ""+i);
				map.put("name", ""+i+"级");
				map.put("tips", "包含禁词，标题长度小于10个字符，长度不限。有分类或无分类");
			default:
				map.put("level", ""+i);
				map.put("name", ""+i+"级");
				map.put("tips", "其它");
				break;
			}

			levels.add(map);
		}
	}

	public static List<Map<String, String>> getLevels() {
		return levels;
	}
}
