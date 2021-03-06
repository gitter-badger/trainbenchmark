/*******************************************************************************
 * Copyright (c) 2010-2015, Gabor Szarnyas, Benedek Izso, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Benedek Izso - initial API and implementation
 *   Gabor Szarnyas - initial API and implementation
 *******************************************************************************/
package hu.bme.mit.trainbenchmark.benchmark.fourstore.benchmarkcases;

import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.AbstractBenchmarkCase;
import hu.bme.mit.trainbenchmark.benchmark.fourstore.config.FourStoreBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.fourstore.driver.FourStoreDriver;
import hu.bme.mit.trainbenchmark.benchmark.fourstore.transformations.FourStoreTransformation;
import hu.bme.mit.trainbenchmark.benchmark.sesame.matches.SesameMatch;
import hu.bme.mit.trainbenchmark.benchmark.sesame.matches.SesameMatchComparator;
import hu.bme.mit.trainbenchmark.constants.Scenario;

import java.io.IOException;
import java.util.Comparator;

import org.openrdf.model.URI;

public class FourStoreBenchmarkCase extends AbstractBenchmarkCase<SesameMatch, URI> {

	protected FourStoreBenchmarkConfig fsbc;
	protected FourStoreDriver fourStoreDriver;

	public FourStoreBenchmarkCase() throws IOException {
		driver = fourStoreDriver = new FourStoreDriver();
	}

	@Override
	public void init() throws IOException {
		super.init();
		this.fsbc = (FourStoreBenchmarkConfig) bc;

		checker = new FourStoreChecker(fourStoreDriver, fsbc);

		if (bc.getScenario() != Scenario.BATCH) {
			transformation = FourStoreTransformation.newInstance(fourStoreDriver, bc.getQuery(), bc.getScenario());
		}
	}

	@Override
	protected Comparator<?> getMatchComparator() {
		return new SesameMatchComparator();
	}

}
