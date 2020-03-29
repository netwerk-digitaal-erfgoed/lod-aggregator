package eu.europeana.commonculture.lod.crawler.rdf;

import java.util.ArrayList;

public abstract class RetryExec<RET, EXCEPTION extends Exception> {
	private int maxRetries=3;
	
	public RetryExec(int maxRetries) {
		super();
		this.maxRetries = maxRetries;
	}

	public RetryExec() {
	}

	ArrayList<EXCEPTION> retryErrors=null;
	
	protected abstract RET doRun() throws EXCEPTION;
	
	public RET run() throws EXCEPTION {
		int attempt=0;
		while (attempt<maxRetries) {
			try {
				attempt++;
				return doRun();
			} catch (Exception e) {
				if (attempt<maxRetries) {
					if(retryErrors==null) retryErrors=new ArrayList<>();
					retryErrors.add((EXCEPTION) e);
				} else
					throw (EXCEPTION) e;
			}
		}
		return null;
	}
}
