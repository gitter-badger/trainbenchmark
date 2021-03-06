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
package hu.bme.mit.trainbenchmark.benchmark.neo4j.matches;

import java.util.Arrays;
import java.util.Map;

import org.neo4j.graphdb.Node;

public abstract class Neo4jMatch {

	protected Map<String, Object> match;

	public Neo4jMatch(final Map<String, Object> match) {
		this.match = match;
	}

	public abstract Node[] toArray();

	@Override
	public String toString() {
		return "Neo4jMatch [match=" + Arrays.toString(toArray()) + "]";
	}
	
}
