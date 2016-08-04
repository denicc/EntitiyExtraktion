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
		 
	        String sampleGermanText = "...";
	        Annotation germanAnnotation = new Annotation(sampleGermanText);
	        Properties germanProperties = StringUtils.argsToProperties(
	                new String[]{"-props", "StanfordCoreNLP-german.properties"});
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(germanProperties);
	        pipeline.annotate(germanAnnotation);
	        for (CoreMap sentence : germanAnnotation.get(CoreAnnotations.SentencesAnnotation.class)) {
	            Tree sentenceTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
	            System.out.println(sentenceTree);
	        }
	    }
		 
	}