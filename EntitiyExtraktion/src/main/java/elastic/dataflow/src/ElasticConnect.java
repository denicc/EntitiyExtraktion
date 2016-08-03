package elastic.dataflow.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.engine.Engine.Get;
import org.elasticsearch.index.mapper.object.ObjectMapper;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ElasticConnect {

	private static String ELASTIC_HOST = "138.201.125.161";
	//Muss 9300 sein, da die JAVA-API diesen Port nutzt
	private static int ELASTIC_PORT = 9300;
	private String dateFormat = "yyyy-MM-dd'T'HH:mm:ssX";
	
	
	public static void main(String[] args) {

		
		ElasticConnect elastic = new ElasticConnect();
		
		Client client = elastic.getClient(ELASTIC_HOST, ELASTIC_PORT);
		elastic.getAllHosts(client);
		//elastic.getElasticData(client);
		
	}
	

	
	
	private void getAllHosts(Client client) {
		// TODO Auto-generated method stub
		SearchResponse response = client.prepareSearch("nutch")
			    .setTypes("doc")
			    .setSource("host")             
			    .setFrom(0)
			    .setSize(1000)              
			    .execute().actionGet();
			Set<String> hostList = new HashSet<String>();

			for (SearchHit hit : response.getHits()) { 
				hostList.add(hit.getSource().get("host").toString());
			    // do something with the id value
			}

			hostList.forEach(System.out::println);
	
			getDataPerHost(client, hostList);
			
	}




	public void getDataPerHost(Client client, Set<String> hostList){
		
		System.out.println(hostList.size());
		
		
		
		SearchResponse response = client.prepareSearch("nutch").setSearchType(
		          SearchType.DFS_QUERY_THEN_FETCH).setQuery(
		          QueryBuilders.matchQuery("host", "www.ahlers-ag.com")).setFrom(0).setSize(60).setExplain(true)
		          .execute().actionGet();
		 
		      SearchHit[] docs = response.getHits().getHits();
		      
		      for (int i = 0; i < docs.length; i++) {
				System.out.println(docs[i].sourceAsString());
		      }
		      
		      System.out.println("size is " + docs.length);
		      client.close();

		
		
	}
	 
		
	

	public Client getClient(String ELASTIC_HOST, int ELASTIC_PORT){
		
		Client client = new TransportClient()
		        .addTransportAddress(new InetSocketTransportAddress(ELASTIC_HOST, ELASTIC_PORT));
		// on shutdown

		return client;
		
		
	}



}
