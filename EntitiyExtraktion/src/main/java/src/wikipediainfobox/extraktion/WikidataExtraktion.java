package src.wikipediainfobox.extraktion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WikidataExtraktion {
	
	Map<String, Object> json;
	URL url;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WikidataExtraktion data = new WikidataExtraktion();
		try {
			data.getWikidata();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void getWikidata() throws IOException{
		File fout = new File("outList/outList.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		
			BufferedReader br = new BufferedReader(new FileReader("outList/OrganisationsListe.txt"));
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String subject = line;
		    	
		
		    	//URL url = new URL("https://de.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", "%20"));
				

		    	url = new URL("https://de.wikipedia.org/w/index.php?action=raw&title=" + subject.replace(" ", ""));
				
		    	json = new HashMap<String, Object>();

		    	String text = "";
				BufferedReader br2;
				try {
					br2 = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
				 
					
				    String line2 = null;
				    String tempKey = null;
				    String tempValue = null;
				    
				    boolean infobox = false;
				    
				    
				    while (null != (line2 = br2.readLine())) {
				        line2 = line2.trim();
				        
				        if (line2.startsWith("{{Infobox Unternehmen")) {
							infobox=true;

							
				        }else  if (line2.equals("}}")) {
				        	break;
							
						}else if (infobox){
							line2 = line2.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("<small>", "").replaceAll("</small>", "");
							if (line2.contains("<ref")){
								line2 = line2.substring(0, line2.indexOf("<ref"));
								
							}else if(line2.contains("<br")){
								
							}
							tempKey = line2.substring(1, line2.indexOf("=")-1).trim();
							tempValue = line2.substring(line2.indexOf("=")+1).trim();
							//bw.write(tempKey + ";" + tempValue);
							if (!tempValue.equals("") && !tempKey.equals("Logo")) {
						     System.out.println(tempKey + ":" +  tempValue);
						    // json.put(tempKey,tempValue);


							}
								
					    	//IndexJSON(json);

					        }
				       

						}

				    
				    } catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				

				

		    bw.close();

				}
		    

//				

//				
//	
			
	}

	private void IndexJSON(Map<String, Object> json) {
		// TODO Auto-generated method stub
		json.put("url", url);
		json.forEach((k,v) -> System.out.println(k + " | " + v));
		System.out.println();

	}
}
		
	
