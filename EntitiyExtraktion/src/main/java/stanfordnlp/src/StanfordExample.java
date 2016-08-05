package stanfordnlp.src;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
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
	       
	  
	        pipeline.annotate(germanAnnotation);
	       
	        List<CoreMap> sentences = germanAnnotation.get(CoreAnnotations.SentencesAnnotation.class);

	        for (CoreMap sentence : sentences) {
	     
	         System.out.println(extractor.getValue("SCHLAU"));
	         System.out.println(sentence);
	         System.out.println(extractor.extractExpressions(sentence));
	          List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
	          System.out.println(matchedExpressions.size());
	          for (MatchedExpression matched:matchedExpressions) {
	            // Print out matched text and value
	            System.out.println("Matched expression: " + matched.getText() + " with value " + matched.getValue());
	            // Print out token information
	            CoreMap cm = matched.getAnnotation();
	            for (CoreLabel token : cm.get(CoreAnnotations.TokensAnnotation.class)) {
	              String word = token.get(CoreAnnotations.TextAnnotation.class);
	              String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
	              String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
	              String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
	              System.out.println("  Matched token: " + "word="+word + ", lemma="+lemma + ", pos=" + pos + ", ne=" + ne);
	            }
	          }
	        }
	        
	        
	    }
		 
	}