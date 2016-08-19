package com.example.index;

import com.example.index.support.CandidateComponentsTestClassLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.util.StopWatch;

/**
 *
 * @author Stephane Nicoll
 */
public class JpaPerformanceTests {

	// Avoid lazy init of commons-logging (150ms!)
	private static Log logger = LogFactory.getLog(JpaPerformanceTests.class);

	@Test
	public void scanPerformance() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Scan");
		createPuWithScan(false, "com.example.domain", "org.springframework");
		stopWatch.stop();
		System.out.println("Scan time --> " + stopWatch.getLastTaskTimeMillis());
	}

	@Test
	public void indexPerformance() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Index");
		createPuWithScan(true, "com.example.domain", "org.springframework");
		stopWatch.stop();
		System.out.println("Index time --> " + stopWatch.getLastTaskTimeMillis());
	}


	private void createPuWithScan(boolean useIndex, String... packages) {
		DefaultPersistenceUnitManager puManager = new DefaultPersistenceUnitManager();
		if (!useIndex) {
			puManager.setResourceLoader(new DefaultResourceLoader(
					CandidateComponentsTestClassLoader.disableIndex(getClass().getClassLoader())));
		} else {
			puManager.setResourceLoader(new DefaultResourceLoader(getClass().getClassLoader()));
		}
		puManager.setPackagesToScan(packages);
		puManager.preparePersistenceUnitInfos();
	}

}
