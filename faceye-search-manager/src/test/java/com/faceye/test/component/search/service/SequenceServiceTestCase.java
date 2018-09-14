package com.faceye.test.component.search.service;

import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.feature.service.SequenceService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class SequenceServiceTestCase extends BaseServiceTestCase {

	@Autowired
	private SequenceService sequenceServcie=null;
	
	@Test
	public void testGetNextSequence() throws Exception{
		int loopISum=0;
		long seqSum=0;
		for(int i=0;i<10;i++){
			loopISum+=(i+1);
			Long nextSeq=this.sequenceServcie.getNextSequence("intest");
			seqSum+=nextSeq;
		}
		Assert.isTrue(loopISum==new Long(seqSum).intValue());
	}
}
