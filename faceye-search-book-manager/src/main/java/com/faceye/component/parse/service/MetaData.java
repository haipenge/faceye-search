package com.faceye.component.parse.service;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * 页面Meta
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年9月20日
 */
public class MetaData implements java.io.Serializable {
	
	private Logger logger=LoggerFactory.getLogger(MetaData.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5524435404463723176L;

	private Map<String, String> meta = null;

	public MetaData() {
		if (MapUtils.isEmpty(meta)) {
			meta = Maps.newHashMap();
		}
	}

	public String get(String key) {
		String res = "";
		if(StringUtils.isNotEmpty(key)){
			key=key.toLowerCase();
		}
		res = MapUtils.getString(meta, key);
		return res;
	}

	public void put(String key, String value) {
		if(StringUtils.isNotEmpty(key)){
			key=key.toLowerCase();
		}
		if(StringUtils.isNotEmpty(value)){
			value=value.toLowerCase();
		}
		meta.put(key, value);
	}
	public boolean isEmpty(){
		return MapUtils.isEmpty(meta);
	}
	
	public void println(){
		if(MapUtils.isNotEmpty(meta)){
			Iterator<String> it=meta.keySet().iterator();
			while(it.hasNext()){
				String key =it.next();
				String value=meta.get(key);
				logger.debug(">>FaceYe---->"+key+" = "+value);
			}
		}
	}
}
