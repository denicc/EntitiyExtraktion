package stanfordnlp.src;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.util.StringUtils;

public class TokenRegexDemo {
	
	final static Logger LOG = LoggerFactory.getLogger(TokenRegexDemo.class);
	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*(at)"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static Pattern pattern;
	private static Matcher matcher;
	
	public static void main(String[] args) throws IOException {
	    String rules;
	    

	    String text = " cancer TEMPO Fischer Stuttgart ist Stuttgart GmbH Wolfratshausen Messepiazza 1 70629 Stuttgart und Nürnberg Tel.: +49 711 18560-0 Fax: +49 711 18560-2440  info(at)messe-stuttgart.de    Online-Redaktion: Jens Kohring   jens.kohring(at)messe-stuttgart.de Informationen Aufsichtsratsvorsitzender:  Staatssekretär Peter Hofelich MdL Geschäftsführer: Ulrich Kromer von Baerle (Sprecher der Geschäftsführung) Roland Bleinroth  Amtsgericht Stuttgart HR B 585 USt-IdNr.: DE 147 866426 Inhaltshinweis: Trotz sorgfältiger inhaltlicher Kontrolle übernehmen wir keine Haftung für die Inhalte externer Links. Für den Inhalt der verlinkten Seiten sind ausschließlich deren Betreiber verantwortlich.  Datenschutzerklärung   Konzeption & Realisierung Internetauftritt   TYPO3 Internetagentur Stuttgart Schommer Media GmbH Königstraße 56 70173 Stuttgart www.schommer-media.de";
	    
	    emailValidation(text);
	    

	    
	    
	    if (args.length > 0) {
	      rules = args[0];
	    } else {
	      rules = "rules/test/disease_german.txt";
	    }
	    PrintWriter out;
	    if (args.length > 2) {
	      out = new PrintWriter(args[2]);
	    } else {
	      out = new PrintWriter(System.out);
	    }

	    @SuppressWarnings("unchecked")
		CoreMapExpressionExtractor<MatchedExpression> extractor = CoreMapExpressionExtractor
	            .createExtractorFromFiles(TokenSequencePattern.getNewEnv(), rules);

	    Annotation annotation;
	    if (args.length > 1) {
	    	
	      annotation = new Annotation(IOUtils.slurpFileNoExceptions(args[1]));
	    } else {
	      annotation = new Annotation(text);
	    }


	    
	    Properties germanProperties = StringUtils.argsToProperties(
                new String[]{"-props", "properties/myProperties.properties"});
	    
	    System.out.println(germanProperties.getProperty("ner.model"));
	    
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(germanProperties);
	    
	    //StanfordCoreNLP pipeline = new StanfordCoreNLP(
	    //        PropertiesUtils.asProperties("annotators", "tokenize,ssplit,pos,lemma,ner"));
	    
	    
	    pipeline.annotate(annotation);

	    // An Annotation is a Map and you can get and use the various analyses individually.
	    out.println();
	    // The toString() method on an Annotation just prints the text of the Annotation
	    // But you can see what is in it with other methods like toShorterString()
	    out.println("The top level annotation");
	    out.println(annotation.toShorterString());
	    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	    
	    StringBuilder sb = new StringBuilder();
        List<String> tokens = new ArrayList<String>();

        
	    String currNeToken = "O";
        String prevNeToken = "O";
        boolean newToken = true;


	    for (CoreMap sentence : sentences) {
	    	
	    	//System.out.println("SENTENCE< " + sentence);
	    	 for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
	              currNeToken = token.get(NamedEntityTagAnnotation.class);

	              String word = token.get(TextAnnotation.class);
	              
	              System.out.println(word);

	              if (!currNeToken.equals("O")) {
	 	             System.out.println("Word< " + word + " NER< " + currNeToken);

				}else{
					
				}
	              // Strip out "O"s completely, makes code below easier to understand
	    	 }
	    	
	      List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);
	      	      
	      for (MatchedExpression matched:matchedExpressions) {
	    	 
	        // Print out matched text and value
	        out.println("Matched expression: " + matched.getText() + " with value " + matched.getValue());
	        // Print out token information
	        CoreMap cm = matched.getAnnotation();
	        for (CoreLabel token : cm.get(CoreAnnotations.TokensAnnotation.class)) {
	          String word = token.get(CoreAnnotations.TextAnnotation.class);
	          String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
	          String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
	          
	          System.out.println(ne);
	          out.println("  Matched token: " + "word="+word + ", pos=" + pos + ", ne=" + ne);
	        }
	      }
	    }
	    out.flush();
	  }
	
	
	public static void emailValidation(String text){
		
		
		pattern = Pattern.compile(EMAIL_PATTERN);

		matcher = pattern.matcher(text);
		
		
		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}
}
