package com.example.index;

import com.example.index.support.CandidateComponentsTestClassLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.StopWatch;

/**
 *
 * @author Stephane Nicoll
 */
@Ignore("https://jira.spring.io/browse/SPR-14606")
public class ComponentPerformanceTests {

	// Avoid lazy init of commons-logging (150ms!)
	private static Log logger = LogFactory.getLog(ComponentPerformanceTests.class);

	private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

	@Before
	public void setup() {
		// Sample class names in different packages
		context.setBeanNameGenerator(new TestBeanNameGenerator());
	}

	@Test
	public void scanPerformance200() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Scan");
		this.context.setResourceLoader(new DefaultResourceLoader(
				CandidateComponentsTestClassLoader.disableIndex(getClass().getClassLoader())));
		this.context.scan("com.example.service.sample200");
		stopWatch.stop();
		System.out.println("Scan time --> " + stopWatch.getLastTaskTimeMillis());
		this.context.refresh();
		System.out.println("Registered beans -> " + this.context.getBeanDefinitionCount());
	}

	@Test
	public void scanPerformance700() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Scan");
		this.context.setResourceLoader(new DefaultResourceLoader(
				CandidateComponentsTestClassLoader.disableIndex(getClass().getClassLoader())));
		this.context.scan("com.example.service");
		stopWatch.stop();
		System.out.println("Scan time --> " + stopWatch.getLastTaskTimeMillis());
		this.context.refresh();
		System.out.println("Registered beans -> " + this.context.getBeanDefinitionCount());
	}

	@Test
	public void indexPerformance200() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Scan");
		this.context.scan("com.example.service.sample200");
		stopWatch.stop();
		System.out.println("Index time --> " + stopWatch.getLastTaskTimeMillis());
		this.context.refresh();
		System.out.println("Registered beans -> " + this.context.getBeanDefinitionCount());
	}

	@Test
	public void indexPerformance700() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("Scan");
		this.context.scan("com.example.service");
		stopWatch.stop();
		System.out.println("Index time --> " + stopWatch.getLastTaskTimeMillis());
		this.context.refresh();
		System.out.println("Registered beans -> " + this.context.getBeanDefinitionCount());
	}

	private static class TestBeanNameGenerator implements BeanNameGenerator {
		@Override
		public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
			return definition.getBeanClassName();
		}
	}
}
