package com.faceye.component.search.util;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 分词器实例化
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月15日
 */
public class AnalyzerFactory {
   public static Analyzer ANALYZER=null;
   
   public static Analyzer getAnalyzer(){
	   if(ANALYZER==null){
		   ANALYZER=new IKAnalyzer(true);
	   }
	   return ANALYZER;
   }
   
}
