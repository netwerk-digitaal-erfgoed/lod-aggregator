package eu.europeana.commonculture.lod.crawler.rdf;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.jena.riot.Lang;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.parser.Callback;

public class CallbackTriplesQuadsBufferedWriter implements Callback {
	final BufferedWriter _bw;

	long _cnt = 0;
	long _time, _time1;
	final boolean _close;

	public final static String DOTNEWLINE = "."
			+ System.getProperty("line.separator");

	Lang nxOption;
	
	/**
	 * ...you handle closing the BufferedWriter outside
	 */
	public CallbackTriplesQuadsBufferedWriter(BufferedWriter out, Lang triplesOrQuads) {
		this(out, false, triplesOrQuads);
	}

	/**
	 * ...if close flag is set true, endDocument() will close
	 * the BufferedWriter
	 */
	public CallbackTriplesQuadsBufferedWriter(BufferedWriter out, boolean close, Lang triplesOrQuads) {
		_bw = out;
		_close = close;
		nxOption=triplesOrQuads;
	}

	public synchronized void processStatement(Node[] nx) {
		try {
			int maxNodes=nxOption==Lang.NTRIPLES ? 3 : 4;
			for(int i=0; i<maxNodes; i++) {
				_bw.write(nx[i].toN3());
				_bw.write(' ');
			}
			_bw.write(DOTNEWLINE);
		} catch (IOException e) {
			e.printStackTrace();
		}

		_cnt++;
	}

	public void startDocument() {
		_time = System.currentTimeMillis();
	}
	
	public void endDocument() {
		try {
			if (_close)
				_bw.close();
			else
				_bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		_time1 = System.currentTimeMillis();
	}

	public String toString() {
		return _cnt + " tuples in " + (_time1 - _time) + " ms";
	}
}
