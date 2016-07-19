package mongodb.connection.src;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnect {

	public static void main(String[] args) {

		MongoDBConnect mongodbconn = new MongoDBConnect();
		try {
			mongodbconn.getRawData(mongodbconn.getConnection());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void getRawData(MongoDatabase mongoDatabase) {
		
		System.out.println("Done");
	}

	private MongoDatabase getConnection() {
		MongoClient mongoClient = new MongoClient("localhost, 27017");
		MongoDatabase mongodb = mongoClient.getDatabase("crawlDB");
		
		return mongodb;
		
	}

}
