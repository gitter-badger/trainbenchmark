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
package hu.bme.mit.trainbenchmark.benchmark.sql.benchmarkcases;

import hu.bme.mit.trainbenchmark.benchmark.checker.Checker;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.sql.driver.SQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.sql.match.SQLMatch;
import hu.bme.mit.trainbenchmark.constants.Query;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class SQLChecker extends Checker<SQLMatch> {

	protected final SQLDriver driver;
	protected final Query query;
	protected final String queryDefinition;

	public SQLChecker(final SQLDriver driver, final BenchmarkConfig bc) throws IOException, SQLException {
		super();
		this.driver = driver;
		this.query = bc.getQuery();

		final String queryPath = bc.getWorkspacePath() + driver.getResourceDirectory() + "queries/" + bc.getQuery() + ".sql";
		this.queryDefinition = FileUtils.readFileToString(new File(queryPath));
	}

	@Override
	public Collection<SQLMatch> check() throws SQLException {
		return driver.runQuery(query, queryDefinition);
	}

}
