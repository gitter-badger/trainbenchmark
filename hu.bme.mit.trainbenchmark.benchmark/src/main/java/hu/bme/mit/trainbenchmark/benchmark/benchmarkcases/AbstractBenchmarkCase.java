/*******************************************************************************
 * Copyright (c) 2010-2015, Benedek Izso, Gabor Szarnyas, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Benedek Izso - initial API and implementation
 *   Gabor Szarnyas - initial API and implementation
 *******************************************************************************/

package hu.bme.mit.trainbenchmark.benchmark.benchmarkcases;

import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.transformations.Transformation;
import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.transformations.TransformationLogic;
import hu.bme.mit.trainbenchmark.benchmark.checker.Checker;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.driver.Driver;
import hu.bme.mit.trainbenchmark.benchmark.util.UniqueRandom;
import hu.bme.mit.trainbenchmark.constants.Query;
import hu.bme.mit.trainbenchmark.constants.TrainBenchmarkConstants;

import java.util.Collection;
import java.util.Comparator;
import java.util.Random;

import eu.mondo.sam.core.metrics.ScalarMetric;
import eu.mondo.sam.core.metrics.TimeMetric;
import eu.mondo.sam.core.results.PhaseResult;

public abstract class AbstractBenchmarkCase<M, T> {

	protected Random random = new UniqueRandom(TrainBenchmarkConstants.RANDOM_SEED);
	protected BenchmarkConfig bc;
	protected Driver<T> driver;
	protected Checker<M> checker;
	protected Collection<M> matches;
	protected TransformationLogic<M, T, ?> transformationLogic;
	protected Transformation<?> transformation;

	public Collection<M> getMatches() {
		return matches;
	}

	// shorthands
	public Query getQuery() {
		return bc.getQuery();
	}

	// these should be implemented for each tool

	protected void init() throws Exception {

	}

	protected void destroy() throws Exception {
		if (checker != null) {
			checker.destroy();
		}
		if (driver != null) {
			driver.destroy();
		}
	}

	public void benchmarkInit(final BenchmarkConfig bc) throws Exception {
		this.bc = bc;
		init();
	}

	public void benchmarkInitTransformation() {
		transformationLogic = TransformationLogic.newInstance(bc.getScenario(), getComparator());
		if (transformationLogic != null) {
			transformationLogic.initialize(bc, driver, random);
		}
		transformationLogic.setTransformation(transformation);
	}

	// benchmark methods

	public void benchmarkRead(final PhaseResult phaseResult) throws Exception {
		final TimeMetric timer = new TimeMetric("Time");
		timer.startMeasure();
		driver.read(bc.getModelPathNameWithoutExtension());
		timer.stopMeasure();
		phaseResult.addMetrics(timer);
	}

	public void benchmarkCheck(final PhaseResult phaseResult) throws Exception {
		final TimeMetric timer = new TimeMetric("Time");
		final ScalarMetric results = new ScalarMetric("Matches");
		timer.startMeasure();
		matches = checker.check();
		timer.stopMeasure();
		results.setValue(matches.size());
		phaseResult.addMetrics(timer, results);
	}

	public void benchmarkDestroy() throws Exception {
		destroy();
	}

	public void benchmarkModify(final PhaseResult phaseResult) throws Exception {
		transformationLogic.performTransformation(phaseResult, matches);
	}

	protected final Comparator<?> getComparator() {
		switch (bc.getScenario()) {
		case BATCH:
		case INJECT:
			return driver.getElementComparator();
		case REPAIR:
			return getMatchComparator();
		default:
			throw new UnsupportedOperationException();
		}
	}

	protected abstract Comparator<?> getMatchComparator();

}
