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

package hu.bme.mit.trainbenchmark.generator.config;

import hu.bme.mit.trainbenchmark.config.TrainBenchmarkConfig;

import org.apache.commons.cli.ParseException;

public class GeneratorConfig extends TrainBenchmarkConfig {

	public GeneratorConfig(final String[] args) throws ParseException {
		super(args);
	}

	@Override
	protected void initOptions() {
		super.initOptions();
	}

	@Override
	protected void processArguments(final String[] args) throws ParseException {
		super.processArguments(args);
	}

}
