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

package hu.bme.mit.trainbenchmark.benchmark.blazegraph;

import java.io.IOException;

import org.apache.commons.cli.ParseException;

public class BlazegraphBenchmarkMain {

	public static void main(String[] args) throws ParseException, IOException {
		BlazegraphBenchmarkLogic benchmarkLogic = new BlazegraphBenchmarkLogic(args);
		benchmarkLogic.runBenchmark();
	}

}
