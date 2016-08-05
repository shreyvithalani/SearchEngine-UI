import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.xml.sax.SAXException;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoJdbc {
	private MongoClient mongoClient;

	public MongoJdbc() {
		this.mongoClient = new MongoClient("localhost");
	}

	public void getdb(String url,String title) throws IOException {

		System.out.println(url);

		// MongoClient mongoClient = new MongoClient("localhost");
		MongoDatabase db = mongoClient.getDatabase("bigtable");
		
		// mongoClient.close();

	}

	

	public void closeConnection() {
		this.mongoClient.close();
	}
}