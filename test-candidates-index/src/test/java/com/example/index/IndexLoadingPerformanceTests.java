package com.example.index;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import org.springframework.context.index.CandidateComponentsIndexLoader;
import org.springframework.util.StopWatch;

/**
 *
 * @author Stephane Nicoll
 */
public class IndexLoadingPerformanceTests {

	// Avoid lazy init of commons-logging (150ms!)
	private static Log logger = LogFactory.getLog(IndexLoadingPerformanceTests.class);

	@Test
	public void loadIndexPerf() {
		System.out.println(CandidateComponentsIndexLoader.COMPONENTS_RESOURCE_LOCATION);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Load Index");
		CandidateComponentsIndexLoader.loadIndex(null);
		stopWatch.stop();
		System.out.println("Loading index time --> " + stopWatch.getLastTaskTimeMillis());
	}

}
