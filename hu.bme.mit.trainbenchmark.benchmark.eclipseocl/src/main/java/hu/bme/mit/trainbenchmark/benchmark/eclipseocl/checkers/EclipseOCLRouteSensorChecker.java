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
package hu.bme.mit.trainbenchmark.benchmark.eclipseocl.checkers;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.emf.EMFDriver;
import hu.bme.mit.trainbenchmark.emf.matches.EMFRouteSensorMatch;
import hu.bme.mit.trainbenchmark.railway.RailwayPackage;
import hu.bme.mit.trainbenchmark.railway.Route;
import hu.bme.mit.trainbenchmark.railway.Sensor;
import hu.bme.mit.trainbenchmark.railway.Switch;
import hu.bme.mit.trainbenchmark.railway.SwitchPosition;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.util.Bag;
import org.eclipse.ocl.util.Tuple;

public class EclipseOCLRouteSensorChecker extends EclipseOCLChecker<EMFRouteSensorMatch> {

	public EclipseOCLRouteSensorChecker(final EMFDriver driver, final BenchmarkConfig bc) throws Exception {
		super(driver, bc);
	}

	@Override
	protected EClassifier getContext() {
		return RailwayPackage.eINSTANCE.getRoute();
	}

	@Override
	public Collection<EMFRouteSensorMatch> check() {
		matches = new ArrayList<>();

		final Bag<Tuple<?, ?>> bag = (Bag<Tuple<?, ?>>) queryEvaluator.evaluate(driver.getContainer());
		for (final Tuple<?, ?> tuple : bag) {
			final Route route = (Route) tuple.getValue("route");
			final Sensor sensor = (Sensor) tuple.getValue("sensor");
			final SwitchPosition swP = (SwitchPosition) tuple.getValue("swP");
			final Switch sw = (Switch) tuple.getValue("sw");
			matches.add(new EMFRouteSensorMatch(route, sensor, swP, sw));
		}
		
		return matches;
	}

}
