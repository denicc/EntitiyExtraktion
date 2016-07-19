package mongodb.connection.src;

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
			mongodbconn.getRawData(mongodbconn.getConnection("localhost", 27017));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void getRawData(MongoDatabase mongoDatabase) {
		
		System.out.println("Done");
	}

	private MongoDatabase getConnection(String host, int port) {
		MongoClient mongoClient = new MongoClient(host, port);
		MongoDatabase mongodb = mongoClient.getDatabase("crawlDB");
		
		return mongodb;
		
	}

}
