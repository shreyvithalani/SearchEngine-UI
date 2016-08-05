
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

@WebServlet("/")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String INDEX_DIRECTORY = "indexdir";
	// private Searcher searcher;

	public search() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("docnames", new ArrayList<String>());
		request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Directory directory = FSDirectory.open(new File(INDEX_DIRECTORY));
		// IndexReader indexReader = IndexReader.open(directory);
		// IndexSearcher searcher = new IndexSearcher(indexReader);
		//
		// Analyzer stdAn = new StandardAnalyzer(Version.LUCENE_35);
		// QueryParser parser = new QueryParser(Version.LUCENE_35, "contents",
		// stdAn);
		// Query q = null;
		// try {
		// q = parser.parse(request.getParameter("test"));
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// TopDocs hits = searcher.search(q, 10);
		// ScoreDoc[] scoreDocs = hits.scoreDocs;
		//
		// System.out.println("hits = " + scoreDocs.length);
		// System.out.println("Hits (score, docId)");
		// String [] docnames = null;
		// for (int n = 0; n < scoreDocs.length; n++) {
		// ScoreDoc sd = scoreDocs[n];
		// float score = sd.score;
		// int docId = sd.doc;
		//
		// System.out.printf("%4.2f %d\n", score, docId);
		//
		// Document document = indexReader.document(docId);
		// String docname = document.getField("path").stringValue();
		// docnames[n] = docname;
		//
		// //System.out.println(docname);
		// }
		//
		// indexReader.close();
		// searcher.close();
		//
		// request.setAttribute("docnames", docnames);
		//
		// getServletContext().getRequestDispatcher("search.jsp")
		// .forward(request, response);
		String searchtext = request.getParameter("test");

		try {
			request.setAttribute("docnames", Searcher.searchIndex(searchtext).keySet());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getServletContext().getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);

		// doGet(request, response);
	}

}
