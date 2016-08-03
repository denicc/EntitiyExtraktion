package elastic.dataflow.src;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.engine.Engine.Get;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ElasticConnect {

	private static String ELASTIC_HOST = "127.0.0.1";
	//Muss 9300 sein, da die JAVA-API diesen Port nutzt
	private static int ELASTIC_PORT = 9300;
	
	
	
	


	public static void main(String[] args) {

		
		ElasticConnect elastic = new ElasticConnect();
		
		Client client = elastic.getClient(ELASTIC_HOST, ELASTIC_PORT);
		elastic.startElasticExtraktion(client);
		
	}
	

	public void startElasticExtraktion(Client client){
		
		SearchResponse response = client.prepareSearch("nutch")
		        .setTypes("text/html")
		        .execute()
		        .actionGet();
		
		
		
		
		
	}
	
	
	private void getElasticData(Client client) {

		
		
	}


	public Client getClient(String ELASTIC_HOST, int ELASTIC_PORT){
		
		Client client = new TransportClient()
		        .addTransportAddress(new InetSocketTransportAddress(ELASTIC_HOST, ELASTIC_PORT));
		// on shutdown

		return client;
		
		
	}



}
