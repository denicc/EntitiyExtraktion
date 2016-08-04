package elastic.dataflow.src;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import stanfordnlp.src.StanfordExample;

public class ElasticConnect {

	private static String ELASTIC_HOST = "138.201.125.161";
	//Muss 9300 sein, da die JAVA-API diesen Port nutzt
	private static int ELASTIC_PORT = 9300;
	private String dateFormat = "yyyy-MM-dd'T'HH:mm:ssX";
	private SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
	private String timeStamp;
	private long JobStartTime;
	private long JobEndTime;
	private long JobTime;
	
	//ElasticSearch
	private QueryBuilder queries;
	private SearchResponse response;
	private Map<String, String> content;
	
	
	
	public static void main(String[] args) {

		ElasticConnect elastic = new ElasticConnect();
		
		Client client = elastic.getClient(ELASTIC_HOST, ELASTIC_PORT);
		elastic.getAllHosts(client);
		//elastic.getElasticData(client);
		
	}
	

	
	
	private void getAllHosts(Client client) {
		// TODO Auto-generated method stub
		timeStamp = timeStampFormat.format(Calendar.getInstance().getTime());
		System.out.println("Job getAllHosts started  at " + timeStamp);
		JobStartTime = System.currentTimeMillis();
		
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

			//hostList.forEach(System.out::println);
			
	
			getDataPerHost(client, hostList);
			
	}

	public void getDataPerHost(Client client, Set<String> hostList){	
		System.out.println(hostList.size());
		//String hostname = "www.buergerbraeu.com";
		int z = 0;
		content = new HashMap<String, String>();
		
		for ( String host :  hostList) {

			 queries = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("host", host))
					.must(QueryBuilders.matchQuery("title", "Impressum.*"));
			
			
			
			 response = client.prepareSearch("nutch").setSearchType(
			          SearchType.DFS_QUERY_THEN_FETCH).setQuery(
			          queries).setFrom(0).setSize(60).setExplain(true)
			          .execute().actionGet();
			
			      SearchHit[] docs = response.getHits().getHits();
	      
			    if (docs.length == 1) {
					
					Map<String, Object> map = docs[0].sourceAsMap();
					
					content.put(host, map.get("content").toString());
				    z++;
				}else{
				
					for (int j = 0; j < docs.length; j++) {
				    	  
						z++;
				      }
				}
			    
		}
		
		// content.forEach( (k,v) -> System.out.println("Key: " + k + ": Value: " + v));
		
		System.out.println("Total Hits: " + z);
		JobEndTime = System.currentTimeMillis();
		JobTime = JobEndTime - JobStartTime;
		System.out.println("Job Hosts found: " + hostList.size());
		System.out.println("Job Took: " + JobTime + " Millis");

		client.close();

		//Stanford Connection
		StanfordExample example = new StanfordExample();
		
		example.getTokenRegex(content.get("www.axelspringer.de"));

		
		
//		try {
//			example.getNE(content.get("www.axelspringer.de"));
//		} catch (ClassCastException | ClassNotFoundException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}

	

	public Client getClient(String ELASTIC_HOST, int ELASTIC_PORT){
		
		Client client = new TransportClient()
		        .addTransportAddress(new InetSocketTransportAddress(ELASTIC_HOST, ELASTIC_PORT));
		// on shutdown

		return client;
		
		
	}



}
