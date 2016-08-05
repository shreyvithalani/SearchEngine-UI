import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class Searcher {
	public static final String INDEX_DIRECTORY = "/Users/dhruvparmar91/Desktop/indexdir";

	public static Map<String, Double> searchIndex(String searchtext) throws IOException, ParseException {
		Directory directory = FSDirectory.open(new File(INDEX_DIRECTORY));
		IndexReader indexReader = IndexReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(indexReader);

		Analyzer stdAn = new StandardAnalyzer(Version.LUCENE_35);
		QueryParser parser = new QueryParser(Version.LUCENE_35, "contents", stdAn);
		Query q = parser.parse(searchtext);

		TopDocs hits = searcher.search(q, 20);
		ScoreDoc[] scoreDocs = hits.scoreDocs;

		final Map<String, Double> finalResultSet = new HashMap<String, Double>();

		MongoClient mongoClient = new MongoClient("localhost");
		MongoDatabase db = mongoClient.getDatabase("bigtable");

		List<String> docnames = new ArrayList<String>();
		System.out.println(searchtext);
		for (int n = 0; n < scoreDocs.length; n++) {
			ScoreDoc sd = scoreDocs[n];
			float score = sd.score;
			int docId = sd.doc;

			System.out.printf("%4.2f  %d\n", score, docId);

			Document document = indexReader.document(docId);
			// System.out.println(document);
			String docname = document.getField("path").stringValue();
			// System.out.println(docname);
			docnames.add(docname);
			// System.out.println(docname);

			System.out.println(">>>>>>>>>>>>>>>>");

			final String[] splits = docname.split("articles");

			BasicDBObject query = new BasicDBObject("url", splits[1]);
			FindIterable<org.bson.Document> iterable = db.getCollection("linkdata").find(query);
			iterable.forEach(new Block<org.bson.Document>() {

				public void apply(final org.bson.Document d) {
					System.out.println(d);
					Double rank = (Double) d.get("rank");
					Double finalRank = (score + rank) / 2.0;
					System.out.println("Score: " + score + " and rank: " + rank + " finalScore " + finalRank);
					finalResultSet.put(docname, finalRank);

				}
			});
		}

		entriesSortedByValues(finalResultSet);

		mongoClient.close();

		indexReader.close();
		searcher.close();

		return finalResultSet;

	}

	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		return sortedEntries;
	}

}
