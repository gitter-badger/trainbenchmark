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
package hu.bme.mit.trainbenchmark.benchmark.drools6.checkers;

import hu.bme.mit.trainbenchmark.benchmark.checker.Checker;
import hu.bme.mit.trainbenchmark.benchmark.drools6.Drools6ResultListener;
import hu.bme.mit.trainbenchmark.benchmark.drools6.driver.Drools6Driver;
import hu.bme.mit.trainbenchmark.constants.Query;
import hu.bme.mit.trainbenchmark.emf.matches.EMFMatch;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import org.kie.api.runtime.rule.LiveQuery;

public class Drools6Checker extends Checker<EMFMatch> {

	protected final Drools6Driver driver;
	protected Collection<EMFMatch> matches = new HashSet<>();
	protected Drools6ResultListener listener;
	protected LiveQuery liveQuery;
	protected Query query;

	public Drools6Checker(final Drools6Driver driver, final Query query) {
		super();
		this.driver = driver;
		this.query = query;
	}

	@Override
	public Collection<EMFMatch> check() throws IOException {
		if (liveQuery == null) {
			listener = new Drools6ResultListener(query);
			liveQuery = driver.getKsession().openLiveQuery(query.toString(), new Object[] {}, listener);
		} else {
			// activate lazy PHREAK evaluation
			driver.getKsession().fireAllRules();
		}
		matches = listener.getMatches();
		return matches;
	}

	@Override
	public void destroy() {
		if (liveQuery != null) {
			liveQuery.close();
		}
	}

}
