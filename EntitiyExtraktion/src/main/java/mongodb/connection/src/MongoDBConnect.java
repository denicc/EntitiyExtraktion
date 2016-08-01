package mongodb.connection.src;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;

public class MongoDBConnect {

	public static void main(String[] args) {

		MongoDBConnect mongodbconn = new MongoDBConnect();
		try {
			mongodbconn.getRawData(mongodbconn.getConnection("localhost", 27017, "crawlDB"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void getRawData(MongoDatabase db) {
		
		System.out.println(db.getName());
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("baseUrl","http://www.bergbier.de/");
		
		FindIterable<Document> iterable = db.getCollection("webpage").find().limit(8000);

		
		
		iterable.forEach(new Block<Document>() {
		    public void apply(final Document document) {
		        System.out.println(document);
		    }
		});
		
		
		System.out.println("Done");
	}

	private MongoDatabase getConnection(String host, int port, String db) {
		MongoClient mongoClient = new MongoClient(host, port);
		MongoDatabase mongodb = mongoClient.getDatabase(db);
		
		return mongodb;
		
	}

}
