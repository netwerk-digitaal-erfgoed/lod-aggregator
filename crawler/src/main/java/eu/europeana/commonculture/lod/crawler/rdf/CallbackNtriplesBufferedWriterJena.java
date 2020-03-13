package eu.europeana.commonculture.lod.crawler.rdf;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.parser.Callback;

import eu.europeana.commonculture.lod.crawler.rdf.RdfUtil.Jena;

public class CallbackNtriplesBufferedWriterJena implements Callback {

	final BufferedWriter _bw;

	long _cnt = 0;
	long _time, _time1;
	final boolean _close;

	Model statements;
	/**
		 * ...you handle closing the BufferedWriter outside
		 */
		public CallbackNtriplesBufferedWriterJena(BufferedWriter out) {
			this(out, false);
		}

	/**
		 * ...if close flag is set true, endDocument() will close
		 * the BufferedWriter
		 */
		public CallbackNtriplesBufferedWriterJena(BufferedWriter out, boolean close) {
			_bw = out;
			_close = close;
			statements=Jena.createModel();
		}

	public synchronized void processStatement(Node[] nx) {
//		try {
		String s="";
		int i=0;
			for (Node n : nx) {
				s+=" "+n.toN3();
				i++;
				if(i>=3) break;
			}
			Model stmMdl = RdfUtil.readRdf(s+" .", Lang.N3);
			statements.add(stmMdl);
//			_bw.write(DOTNEWLINE);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		_cnt++;
	}

	public void startDocument() {
		_time = System.currentTimeMillis();
	}

	public void endDocument() {
		try {
			RdfUtil.writeRdf(statements, Lang.NTRIPLES, _bw);
			System.out.println(RdfUtil.writeRdfToString(statements, Lang.NTRIPLES));
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
