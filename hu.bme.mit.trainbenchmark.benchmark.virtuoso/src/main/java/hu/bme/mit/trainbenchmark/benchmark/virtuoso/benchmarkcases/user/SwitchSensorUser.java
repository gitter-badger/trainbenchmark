package hu.bme.mit.trainbenchmark.benchmark.virtuoso.benchmarkcases.user;

import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.Transformation;
import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.TransformationBenchmarkCase;
import hu.bme.mit.trainbenchmark.benchmark.virtuoso.util.SesameData;
import hu.bme.mit.trainbenchmark.benchmark.util.Util;
import hu.bme.mit.trainbenchmark.benchmark.virtuoso.benchmarkcases.SwitchSensor;
import hu.bme.mit.trainbenchmark.constants.ModelConstants;
import hu.bme.mit.trainbenchmark.rdf.RDFConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;



public class SwitchSensorUser extends SwitchSensor implements TransformationBenchmarkCase {

	@Override
	public void modify() throws IOException {
		final long nElemToModify = Util.calcModify(vbc, vbc.getModificationConstant(), bmr);
		bmr.addModifyParams(nElemToModify);
		// modify
		final long start = System.nanoTime();

		final ValueFactory f = myRepository.getValueFactory();
		final URI switchOC = f.createURI(RDFConstants.BASE_PREFIX + ModelConstants.SWITCH);
		RepositoryResult<Statement> switchesIter;

		try {
			switchesIter = con.getStatements(null, RDF.TYPE, switchOC, true);

			final List<Resource> switches = new ArrayList<Resource>();
			for (final Statement s : switchesIter.asList()) {
				switches.add(s.getSubject());
			}

			final List<Resource> switchesToModify = Transformation.pickRandom(nElemToModify, switches);	
			final List<SesameData> itemsToRemove = new ArrayList<>();
			
			for (final Resource aSwitch : switchesToModify) {

				final URI actualState = f.createURI(RDFConstants.BASE_PREFIX + ModelConstants.TRACKELEMENT_SENSOR);
				final RepositoryResult<Statement> statementsToRemove = con.getStatements(aSwitch, actualState, null, true);

				final SesameData jd = new SesameData();
				jd.setStatements(statementsToRemove.asList());
				itemsToRemove.add(jd);
			}

			// edit
			final long startEdit = System.nanoTime();
			for (final SesameData jd : itemsToRemove) {
				con.remove(jd.getStatements());
			}
			con.commit();
			final long end = System.nanoTime();
			bmr.addEditTime(end - startEdit);
			bmr.addModificationTime(end - start);
		} catch (final RepositoryException e) {
			throw new IOException(e);
		}
		
	}

}