package stanfordnlp.src;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.CoreMapExpressionExtractor;
import edu.stanford.nlp.ling.tokensregex.MatchedExpression;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;

public class SimpleTokensRegexRules {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleTokensRegexRules st = new SimpleTokensRegexRules();
		st.rulesAnnotation();
	}
	
	
	public void rulesAnnotation(){
			String rules = "rules/test/colors.rules.german.txt";
			String rules_second = "rules/test/cities.rules.german.txt";

			@SuppressWarnings("unchecked")
			CoreMapExpressionExtractor<MatchedExpression> extractor = CoreMapExpressionExtractor
		            .createExtractorFromFiles(TokenSequencePattern.getNewEnv(),rules_second);

			
			  Properties germanProperties = StringUtils.argsToProperties(
		                new String[]{"-props", "properties/myProperties.properties"});
			    
			
//		 Properties properties = new Properties();
//		    properties.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,tokensregexdemo");
//		    properties.setProperty("customAnnotatorClass.tokensregexdemo", "edu.stanford.nlp.pipeline.TokensRegexAnnotator");
//		    properties.setProperty("tokensregexdemo.rules", rules);
		    StanfordCoreNLP pipeline = new StanfordCoreNLP(germanProperties);
		    Annotation annotation;
		    
		    annotation = new Annotation("Die Farbe lila ist in Nürnberg eine besondere Farbe die manchmal wie hellgrün aussieht.");

		    pipeline.annotate(annotation);

		    // An Annotation is a Map and you can get and use the various analyses individually.
		    // The toString() method on an Annotation just prints the text of the Annotation
		    // But you can see what is in it with other methods like toShorterString()
		    System.out.println("The top level annotation");
		    System.out.println(annotation.toShorterString());
		    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

		    for (CoreMap sentence : sentences) {
		      // NOTE: Depending on what tokensregex rules are specified, there are other annotations
		      //       that are of interest other than just the tokens and what we print out here
		      List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
		      
		    	
//		      for (CoreLabel token:sentence.get(CoreAnnotations.TokensAnnotation.class)) {
//		        // Print out words, lemma, ne, and normalized ne
//		        String word = token.get(CoreAnnotations.TextAnnotation.class);
//		        String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
//		        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//		        String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
//		        String normalized = token.get(CoreAnnotations.NormalizedNamedEntityTagAnnotation.class);
//		        
//			    System.out.println("token: " + "word="+word + ", lemma="+lemma + ", pos=" + pos + ", ne=" + ne + ", normalized=" + normalized);
//		      }
//		      
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

}
