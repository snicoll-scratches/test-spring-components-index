package com.example.index;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

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
		createPuWithScan("com.example.domain", "org.springframework");
		stopWatch.stop();
		System.out.println("Scan time --> " + stopWatch.getLastTaskTimeMillis());
	}

	@Test
	public void indexPerformance() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Index");
		createPuWithIndex();
		stopWatch.stop();
		System.out.println("Index time --> " + stopWatch.getLastTaskTimeMillis());
	}


	private void createPuWithScan(String... packages) {
		DefaultPersistenceUnitManager puManager = new DefaultPersistenceUnitManager();
		puManager.setPackagesToScan(packages);
		puManager.preparePersistenceUnitInfos();
	}

	private void createPuWithIndex() {
		DefaultPersistenceUnitManager puManager = new DefaultPersistenceUnitManager();
		puManager.setUseIndex(true);
		puManager.preparePersistenceUnitInfos();
	}
}
