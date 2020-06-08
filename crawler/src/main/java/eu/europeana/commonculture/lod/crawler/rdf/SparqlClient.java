package eu.europeana.commonculture.lod.crawler.rdf;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;


public class SparqlClient {
	public static abstract class Handler {
		//return true to continue to next URI, false to abort
		public boolean handleSolution(QuerySolution solution) throws Exception { return true; };
	}
	
	protected final String baseUrl; 
	protected final Dataset dataset; 
	protected String queryPrefix;
	protected boolean debug=false;
	protected int retries=3;
	
	public SparqlClient(String baseUrl, String queryPrefix) {
		super();
		this.baseUrl = baseUrl;
		this.dataset = null;
		this.queryPrefix = queryPrefix ==null? "" : queryPrefix;
	}
	
	public SparqlClient(String baseUrl, Map<String, String> queryPrefixes) {
		super();
		this.baseUrl = baseUrl;
		this.dataset = null;
		String tmp="";		
		for(Entry<String, String> ns : queryPrefixes.entrySet()) {
			tmp+=String.format("PREFIX %s: <%s>\n", ns.getKey(), ns.getValue());
		}
		queryPrefix=tmp;
	}
	public SparqlClient(Dataset dataset, String queryPrefix) {
		super();
		this.dataset = dataset;
		this.baseUrl = null;
		this.queryPrefix = queryPrefix;
	}
	
	public SparqlClient(Dataset dataset, Map<String, String> queryPrefixes) {
		super();
		this.baseUrl = null;
		this.dataset = dataset;
		String tmp="";		
		for(Entry<String, String> ns : queryPrefixes.entrySet()) {
			tmp+=String.format("PREFIX %s: <%s>\n", ns.getKey(), ns.getValue());
		}
		queryPrefix=tmp;
	}
	
	public int query(String queryString, Handler handler) {
		int wdCount=0;
		String fullQuery = queryPrefix + queryString;
		if(debug)
			System.out.println(fullQuery);
		QueryExecution qexec = createQueryExecution(fullQuery);
		try {
			ResultSet results = qexec.execSelect();
//            ResultSetFormatter.out(System.out, results, query);
			while(results.hasNext()) {
				Resource resource = null;
				try {
					QuerySolution hit = results.next();
					if (!handler.handleSolution(hit)) {
						if(debug)
							System.out.println("RECEIVED HANDLER ABORT");
						break;
					}
					wdCount++;
				} catch (Exception e) {
					System.err.println("Error on record: "+(resource==null ? "?" : resource.getURI()));
					e.printStackTrace();
					System.err.println("PROCEEDING TO NEXT URI");
				}
			}
			if(debug)
				System.out.printf("QUERY FINISHED - %d resources\n", wdCount);            
		} catch (Exception ex) {
			System.err.println("Error on query: "+fullQuery);
			ex.printStackTrace();
		} finally {
			qexec.close();
		}
		return wdCount;
	}
	
	private QueryExecution createQueryExecution(String fullQuery) {
		if(baseUrl!=null)
			return QueryExecutionFactory.sparqlService(this.baseUrl, fullQuery);
		return QueryExecutionFactory.create(fullQuery, dataset);
	}

	public int queryWithPaging(String queryString, int resultsPerPage, String orderVariableName, Handler handler) throws Exception {
		int[] offsett=new int[] {0};
		boolean concluded=false;
		while (!concluded) {
			String fullQuery = String.format("%s %s\n%s" + 
					"LIMIT %d\n" + 
					"OFFSET %d ", queryPrefix, queryString, (orderVariableName ==null ? "" : "ORDER BY ("+orderVariableName+")\n"), resultsPerPage, offsett[0]);
			if(debug)
				System.out.println(fullQuery);
			RetryExec<Boolean, Exception> exec=new RetryExec<Boolean, Exception>(retries) {
				@Override
				protected Boolean doRun() throws Exception {
					QueryExecution qexec = createQueryExecution(fullQuery);
					try {
						ResultSet results = qexec.execSelect();
			//            ResultSetFormatter.out(System.out, results, query);
						if(!results.hasNext())
							return true;
						while(results.hasNext()) {
							Resource resource = null;
							QuerySolution hit = results.next();
							try {
								if (!handler.handleSolution(hit)) {
									System.err.println("RECEIVED HANDLER ABORT");
									return true;
//									break;
								}
							} catch (Exception e) {
								System.err.println("Error on record handler: "+(resource==null ? "?" : resource.getURI()));
								e.printStackTrace();
								System.err.println("PROCEEDING TO NEXT URI");
							}
							offsett[0]++;
						}
						if(debug)
							System.out.printf("QUERY FINISHED - %d resources\n", offsett);            
						return false;
					} catch (Exception ex) {
						System.err.println("WARN: (will retry 3x) Error on query: "+fullQuery);
						throw ex;
					} finally {
						qexec.close();
					}
				}
			};
			concluded=exec.run();
		}
		return offsett[0];
	}
	
	public void createAllStatementsAboutAndReferingResource(String resourceUri, Model createInModel) {
		createAllStatementsAboutResource(resourceUri, createInModel);
		createAllStatementsReferingResource(resourceUri, createInModel);
	}	
	public void createAllStatementsAboutResource(String resourceUri, Model createInModel) {
		final Resource subjRes = createInModel.createResource(resourceUri);
		query("SELECT ?p ?o WHERE {<" + resourceUri + "> ?p ?o}", new Handler() {
			@Override
			public boolean handleSolution(QuerySolution solution) throws Exception {
				Resource pRes = solution.getResource("p");
				Resource oRes = null;
				Literal oLit = null;
				try {
					oRes = solution.getResource("o");
				} catch (Exception e) {
					oLit = solution.getLiteral("o");
				}
				createInModel.add(createInModel.createStatement(subjRes, createInModel.createProperty(pRes.getURI()), oRes==null ? oLit : oRes));
				return true;
			}
		});
	}

	public void createAllStatementsReferingResource(String resourceUri, Model createInModel) {
		final Resource subjRes = createInModel.createResource(resourceUri);
		query("SELECT ?s ?p WHERE {?s ?p <" + resourceUri + ">}", new Handler() {
			@Override
			public boolean handleSolution(QuerySolution solution) throws Exception {
				Resource pRes = solution.getResource("p");
				Resource sRes = solution.getResource("s");
				createInModel.add(createInModel.createStatement(sRes, createInModel.createProperty(pRes.getURI()), subjRes));
				return true;
			}
		});
	}
	
	
	public Model getAllStatementsAboutAndReferingResource(String resourceUri) {
		final Model model=ModelFactory.createDefaultModel();
		createAllStatementsAboutAndReferingResource(resourceUri, model);
		return model;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public int queryModel(String queryString, Model mdl, Handler handler) {
		int wdCount=0;
		String fullQuery = queryPrefix + queryString;
		if(debug)
			System.out.println(fullQuery);

	    QueryExecution qexec = QueryExecutionFactory.create(fullQuery, mdl);
		try {
			ResultSet results = qexec.execSelect();
//            ResultSetFormatter.out(System.out, results, query);
			while(results.hasNext()) {
				Resource resource = null;
				try {
					QuerySolution hit = results.next();
					if (!handler.handleSolution(hit)) {
						if(debug)
							System.out.println("RECEIVED HANDLER ABORT");
						break;
					}
					wdCount++;
				} catch (Exception e) {
					System.err.println("Error on record: "+(resource==null ? "?" : resource.getURI()));
					e.printStackTrace();
					System.err.println("PROCEEDING TO NEXT URI");
				}
			}
			if(debug)
				System.out.printf("QUERY FINISHED - %d resources\n", wdCount);            
		} catch (Exception ex) {
			System.err.println("Error on query: "+fullQuery);
			ex.printStackTrace();
		} finally {
			qexec.close();
		}
		return wdCount;
	}

	public void setRetries(int i) {
		this.retries=i;
	}
}
