package stanfordnlp.src;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.CoreMapExpressionExtractor;
import edu.stanford.nlp.ling.tokensregex.MatchedExpression;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.util.StringUtils;

public class StanfordExample {
	
	final Logger LOG = LoggerFactory.getLogger(StanfordExample.class);

	public static void main(String[] args) {
			
		
		
	}
	
	public void getNE(String content) throws ClassCastException, ClassNotFoundException, IOException{


		String serializedClassifier = "classifier/stanford/dewac_175m_600.crf.ser.gz";
	    AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
	    //String fileContents = IOUtils.slurpFile("testFiles/Organisation-TrainingFile-With-Description.txt");
	    String fileContents = content;
	   
	    
	    String[] example = {fileContents};

	    for (String str : example) {
      		System.out.println(classifier.classifyToString(str, "tsv", false));
	    }
	    
//	    List<List<CoreLabel>> out = classifier.classify(fileContents);
//        for (List<CoreLabel> sentence : out) {
//          for (CoreLabel word : sentence) {
//            System.out.print(word.word() + '/' + word.get(AnswerAnnotation.class) + ' ');
//          }
//        }
	    
		    
	    
	}
	
	public void getTokenRegex(String content){
		
		String rules = "rules/stanford/rules_1.txt";

		 @SuppressWarnings("unchecked")
		CoreMapExpressionExtractor<MatchedExpression> extractor = CoreMapExpressionExtractor
		            .createExtractorFromFiles(TokenSequencePattern.getNewEnv(), rules);
		 
		 
	        String sampleGermanText = "LMC ist in Sassenberg";
	        Annotation germanAnnotation = new Annotation(sampleGermanText);
	        Properties germanProperties = StringUtils.argsToProperties(
	                new String[]{"-props", "properties/myProperties.properties"});
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(germanProperties);
	       
	  
	        
	        String[] tests =
	            {
	                "Partial invoice (€100,000, so roughly 40%) for the consignment C27655 we shipped on 15th August to London from the Make Believe Town depot. INV2345 is for the balance.. Customer contact (Sigourney) says they will pay this on the usual credit terms (30 days)."
	            };
	        List<String> tokens = new ArrayList<String>();

	        for (String s : tests) {

	          // run all Annotators on the passed-in text
	          Annotation document = new Annotation(s);
	          pipeline.annotate(document);

	          // these are all the sentences in this document
	          // a CoreMap is essentially a Map that uses class objects as keys and has values with
	          // custom types
	          List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	          StringBuilder sb = new StringBuilder();
	          
	          //I don't know why I can't get this code out of the box from StanfordNLP, multi-token entities
	          //are far more interesting and useful..
	          //TODO make this code simpler..
	          for (CoreMap sentence : sentences) {
	            // traversing the words in the current sentence, "O" is a sensible default to initialise
	            // tokens to since we're not interested in unclassified / unknown things..
	            String prevNeToken = "O";
	            String currNeToken = "O";
	            boolean newToken = true;
	            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
	              currNeToken = token.get(NamedEntityTagAnnotation.class);
	              String word = token.get(TextAnnotation.class);
	              // Strip out "O"s completely, makes code below easier to understand
	              if (currNeToken.equals("O")) {
	                // LOG.debug("Skipping '{}' classified as {}", word, currNeToken);
	                if (!prevNeToken.equals("O") && (sb.length() > 0)) {
	                  handleEntity(prevNeToken, sb, tokens);
	                  newToken = true;
	                }
	                continue;
	              }

	              if (newToken) {
	                prevNeToken = currNeToken;
	                newToken = false;
	                sb.append(word);
	                continue;
	              }

	              if (currNeToken.equals(prevNeToken)) {
	                sb.append(" " + word);
	              } else {
	                // We're done with the current entity - print it out and reset
	                // TODO save this token into an appropriate ADT to return for useful processing..
	                handleEntity(prevNeToken, sb, tokens);
	                newToken = true;
	              }
	              prevNeToken = currNeToken;
	            }
	          }
	          
	          //TODO - do some cool stuff with these tokens!
	          LOG.debug("We extracted {} tokens of interest from the input text", tokens.size());
	        }
	      }
	      private void handleEntity(String inKey, StringBuilder inSb, List inTokens) {
	        LOG.debug("'{}' is a {}", inSb, inKey);
	        inTokens.add(new EmbeddedToken(inKey, inSb.toString()));
	        inSb.setLength(0);
	      }


	    }
	    
	        
	       
//	        List<CoreMap> sentences = germanAnnotation.get(CoreAnnotations.SentencesAnnotation.class);
//
//	        for (CoreMap sentence : sentences) {
//	     
//	         System.out.println(extractor.getValue("SCHLAU"));
//	         System.out.println(sentence);
//	         System.out.println(extractor.extractExpressions(sentence));
//	          List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
//	          System.out.println(matchedExpressions.size());
//	          for (MatchedExpression matched:matchedExpressions) {
//	            // Print out matched text and value
//	            System.out.println("Matched expression: " + matched.getText() + " with value " + matched.getValue());
//	            // Print out token information
//	            CoreMap cm = matched.getAnnotation();
//	            for (CoreLabel token : cm.get(CoreAnnotations.TokensAnnotation.class)) {
//	              String word = token.get(CoreAnnotations.TextAnnotation.class);
//	              String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
//	              String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//	              String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
//	              System.out.println("  Matched token: " + "word="+word + ", lemma="+lemma + ", pos=" + pos + ", ne=" + ne);
//	            }
//	          }
//	        }
	        
	        
	    
		 
	