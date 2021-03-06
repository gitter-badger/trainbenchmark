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
package hu.bme.mit.trainbenchmark.benchmark.emfincquery.transformations.repair;

import hu.bme.mit.trainbenchmark.benchmark.emfincquery.PosLengthMatch;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.driver.EMFIncQueryDriver;
import hu.bme.mit.trainbenchmark.benchmark.emfincquery.transformations.EMFIncQueryTransformation;

import java.io.IOException;
import java.util.Collection;

public class EMFIncQueryTransformationRepairPosLength extends EMFIncQueryTransformation<PosLengthMatch> {

	public EMFIncQueryTransformationRepairPosLength(final EMFIncQueryDriver<?> driver) {
		super(driver);
	}

	@Override
	public void rhs(final Collection<PosLengthMatch> matches) throws IOException {
		for (final PosLengthMatch match : matches) {
			final int newLength = -match.getSegment().getLength() + 1;
			match.getSegment().setLength(newLength);
		}
	}

}
