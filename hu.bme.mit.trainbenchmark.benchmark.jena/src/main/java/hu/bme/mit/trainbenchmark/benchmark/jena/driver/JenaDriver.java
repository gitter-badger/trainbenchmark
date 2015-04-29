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
package hu.bme.mit.trainbenchmark.benchmark.jena.driver;

import static hu.bme.mit.trainbenchmark.rdf.RDFConstants.BASE_PREFIX;
import hu.bme.mit.trainbenchmark.rdf.RDFDatabaseDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

public class JenaDriver extends RDFDatabaseDriver<Resource> {

	protected Comparator<Resource> elementComparator = new ResourceComparator();
	protected Model model;

	@Override
	public void read(final String modelPathWithoutExtension) throws IOException {
		model = ModelFactory.createDefaultModel();
		model.read(modelPathWithoutExtension + getExtension());
	}

	public List<QuerySolution> runQuery(final Query query) throws IOException {
		final List<QuerySolution> results = new ArrayList<>();
		try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
			final ResultSet resultSet = queryExecution.execSelect();

			while (resultSet.hasNext()) {
				final QuerySolution qs = resultSet.next();
				results.add(qs);
			}
		}

		return results;
	}

	@Override
	public void destroy() throws IOException {
		model.close();
	}

	// read

	@Override
	public List<Resource> collectVertices(final String type) throws IOException {
		final ResIterator vertexStatements = model.listSubjectsWithProperty(RDF.type, model.getResource(BASE_PREFIX + type));
		final List<Resource> vertices = vertexStatements.toList();
		return vertices;
	}

	@Override
	protected boolean ask(final String askQuery) throws IOException {
		return false;
	}

	@Override
	public Comparator<Resource> getElementComparator() {
		return elementComparator;
	}

	public Model getModel() {
		return model;
	}

}
