/*
* Copyright 2002-2017 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.springframework.benchmark;

import java.util.HashSet;
import java.util.Set;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Stephane Nicoll
 */
@BenchmarkMode(Mode.AverageTime)
public class ComponentsIndexBenchmark {


	@State(Scope.Benchmark)
	static public class ScanningState {

		ClassPathScanningCandidateComponentProvider indexProvider;

		ClassPathScanningCandidateComponentProvider classpathScanningProvider;

		@Setup(Level.Iteration)
		public void setup() {
			StandardEnvironment environment = new StandardEnvironment();
			this.indexProvider = new ClassPathScanningCandidateComponentProvider(false,
					environment);
			this.indexProvider.setResourceLoader(
					new DefaultResourceLoader(getClass().getClassLoader()));
			this.classpathScanningProvider =
					new ClassPathScanningCandidateComponentProvider(false, environment);
			this.classpathScanningProvider.setResourceLoader(new DefaultResourceLoader(
					CandidateComponentsTestClassLoader.disableIndex(getClass().getClassLoader())));
		}

		@Setup(Level.Invocation)
		public void prepare() {

		}

		@TearDown(Level.Iteration)
		public void shutdown() {
			this.indexProvider = null;
			this.classpathScanningProvider = null;
		}

	}

	@Benchmark
	public void baseline(Blackhole blackHole, ScanningState scanningState) {
		blackHole.consume(scanningState.classpathScanningProvider);
		blackHole.consume(scanningState.indexProvider);
	}

	@Benchmark
	public Set<BeanDefinition> classpath200Components(ScanningState scanningState) {
		return scan(scanningState.classpathScanningProvider,
				new AnnotationTypeFilter(Component.class),
				"com.example.service.sample200");
	}

	@Benchmark
	public Set<BeanDefinition> index200Components(ScanningState scanningState) {
		return scan(scanningState.indexProvider,
				new AnnotationTypeFilter(Component.class),
				"com.example.service.sample200");
	}

	@Benchmark
	public Set<BeanDefinition> classpath200ComponentsWithNoise(ScanningState scanningState) {
		return scan(scanningState.classpathScanningProvider,
				new AnnotationTypeFilter(Component.class),
				"com.example.service.sample200", "com.example.noise.sample500");
	}

	@Benchmark
	public Set<BeanDefinition> index200ComponentsWithNoise(ScanningState scanningState) {
		return scan(scanningState.indexProvider,
				new AnnotationTypeFilter(Component.class),
				"com.example.service.sample200", "com.example.noise.sample500");
	}

	@Benchmark
	public Set<BeanDefinition> classpath500Components(ScanningState scanningState) {
		return scan(scanningState.classpathScanningProvider,
				new AnnotationTypeFilter(Component.class),
				"com.example.service.sample500");
	}

	@Benchmark
	public Set<BeanDefinition> index500Components(ScanningState scanningState) {
		return scan(scanningState.indexProvider,
				new AnnotationTypeFilter(Component.class),
				"com.example.service.sample500");
	}

	@Benchmark
	public Set<BeanDefinition> classpath500ComponentsWithNoise(ScanningState scanningState) {
		return scan(scanningState.classpathScanningProvider,
				new AnnotationTypeFilter(Component.class),
				"com.example.service.sample500", "com.example.noise.sample500");
	}

	@Benchmark
	public Set<BeanDefinition> index500ComponentsWithNoise(ScanningState scanningState) {
		return scan(scanningState.indexProvider,
				new AnnotationTypeFilter(Component.class),
				"com.example.service.sample500", "com.example.noise.sample500");
	}

	private Set<BeanDefinition> scan(ClassPathScanningCandidateComponentProvider provider,
			TypeFilter includeFilter, String... packages) {
		provider.resetFilters(false);
		provider.addIncludeFilter(includeFilter);

		Set<BeanDefinition> definitions = new HashSet<>();
		for (String pkg : packages) {
			definitions.addAll(provider.findCandidateComponents(pkg));
		}
		return definitions;
	}

	public static void main(String[] args) {
		ScanningState scanningState = new ScanningState();
		scanningState.setup();

		ComponentsIndexBenchmark bench = new ComponentsIndexBenchmark();
		Set<BeanDefinition> classpathDefinitions = bench.classpath200Components(scanningState);
		System.out.println("--> classpath " + classpathDefinitions.size());

		Set<BeanDefinition> indexDefinitions = bench.index200Components(scanningState);
		System.out.println("--> index " + indexDefinitions.size());
	}

}
