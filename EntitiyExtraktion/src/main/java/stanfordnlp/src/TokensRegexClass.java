package stanfordnlp.src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.tokensregex.CoreMapExpressionExtractor;
import edu.stanford.nlp.ling.tokensregex.MatchedExpression;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;

public class TokensRegexClass {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
	
	    TokensRegexClass token = new TokensRegexClass();
	    try {
			token.StanfordPipeline("textFiles/testFile01.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	     
	}
	
	private void StanfordPipeline(String filepath) throws IOException{
		// TODO Auto-generated method stub
		String everything = null;
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    System.out.println(line);

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		    System.out.println(everything);
		} finally {
		    br.close();
		}
		//Rules können mehrere  sein
	    String rule1 = "rules/test/testrules1.txt";
	    String rule2 = "rules/test/testrules2.txt";	
	
		@SuppressWarnings("unchecked")
		CoreMapExpressionExtractor extractor = CoreMapExpressionExtractor
	            .createExtractorFromFiles(TokenSequencePattern.getNewEnv(), rule2, rule2);

	    Annotation annotation;
	    annotation = new Annotation(everything);
	  

	    Properties germanProperties = StringUtils.argsToProperties(
                new String[]{"-props", "properties/myProperties.properties"});
	    	    
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(germanProperties);
	    
	    pipeline.annotate(annotation);
	    
	    //Hier könnte man eine andere Sentence Komponente Nehmen
	    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	
	    for(CoreMap sentence:sentences){
	    	
	    	String prevNeToken = "O";
    		String currNeToken = "O";
    		boolean newToken = true;
    		
	    	
	    	//System.out.println("SENTENCE< " + sentence);
	    	 for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
	              currNeToken = token.get(NamedEntityTagAnnotation.class);

	              String word = token.get(TextAnnotation.class);

	              if (!currNeToken.equals("O")) {
	 	             System.out.println("Word< " + word + " NER< " + currNeToken);

				}else{
					
				}
	              // Strip out "O"s completely, makes code below easier to understand
	    	 }
	    	
	    	
	    	
	    	@SuppressWarnings("unchecked")
			List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
	    	
		      for (MatchedExpression matched:matchedExpressions) {
		      
		          // Print out matched text and value
			        System.out.println("Matched expression: " + matched.getText() + " with value " + matched.getValue());
			        // Print out token information
			        CoreMap cm = matched.getAnnotation();
			        for (CoreLabel token : cm.get(CoreAnnotations.TokensAnnotation.class)) {
			          String word = token.get(CoreAnnotations.TextAnnotation.class);
			          String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
			          String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
			          
			          System.out.println(ne);
			          System.out.println("  Matched token: " + "word="+word + ", pos=" + pos + ", ne=" + ne);
			        }
		      
		      }
	    	
	    }
	}

	private void handleEntity(String inKey, StringBuilder inSb, List inTokens) {
  		inTokens.add(new EmbeddedToken(inKey, inSb.toString()));
  		inSb.setLength(0);
  	}


}
