package com.faceye.component.index.service.impl;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import com.faceye.component.index.service.Word;

public class WordComparator implements Comparator<Word> {

	@Override
	public int compare(Word o1, Word o2) {
		
		return o2.getCount().compareTo(o1.getCount());
	}

}
