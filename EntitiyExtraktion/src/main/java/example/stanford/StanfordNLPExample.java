package example.stanford;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.CoreMapExpressionExtractor;
import edu.stanford.nlp.ling.tokensregex.MatchedExpression;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordNLPExample {

	Set<String> patterns;
	String[] ustIdPattern = {"(DE) [0-9]{9}","(DE)[0-9]{9}"};
	String[] telefonPattern = {""};
	String[] plzPattern = {""};
	String[] emailPattern = {""};
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String impressum = "LMC-Caravan | Impressum Impressum Toggle navigation Wohnwagen Reisemobile Aktionsmodelle Cruiser ComfortLine T 683 LLT iBus connect Service Infomaterial Katalogbestellung Händlersuche Unsere Partner Werksbesichtigung Markenshop Unternehmen Markenwerte Historie Kontakt Karriere Finanzierung Jubiläum Roncalli LMC Club Gewinnspiel Newsletter Meine Welt Aktuell Reiselust FREEONTOUR Erwin Hymer Museum Termine Mein PLATZ Suchmaschine Anzahl der Schlafplätze beliebig 4 6 Art der Schlafplätze Einzelbett Doppelbett Etagenbett (optional) Gesamtlänge von   600 cm 650 cm 700 cm 750 cm 800 cm 850 cm 900 cm bis   700 cm 750 cm 800 cm 850 cm 900 cm Gewicht von   1300 kg 1400 kg 1500 kg 1600 kg 1700 kg bis   1300 kg 1400 kg 1500 kg 1600 kg 1700 kg 1800 kg 1900 kg Auswahl zurücksetzen Alle unsere Wohnwagen sind mit der Long Life Technologie ausgestattet. Für weitere Informationen Hier klicken! Suchmaschine Anzahl der Schlafplätze beliebig 2 3 4 5 6 Art der Schlafplätze Einzelbett Doppelbett Alkoven/Hubbett Etagenbett (optional) Gesamtlänge von   500 cm 600 cm 700 cm 800 cm 900 cm bis   600 cm 700 cm 800 cm 900 cm Gewicht von   2500 kg 2600 kg 2700 kg 2800 kg 2900 kg 3000 kg 3100 kg 3200 kg 3300 kg 3400 kg bis   2500 kg 2600 kg 2700 kg 2800 kg 2900 kg 3000 kg 3100 kg 3200 kg 3300 kg 3400 kg 3500 kg Alle unsere Reisemobile sind mit der Long Life Technologie ausgestattet. Für weitere Informationen Hier klicken! Impressum Startseite Impressum LMC-Impressum LMC Caravan GmbH & Co. KG Rudolf-Diesel-Str. 4 48336 Sassenberg Telefon: (+49) 2583 27-0 Telefax: (+49) 2583 27-138 Geschäftsführer: Ulrich Schoppmann    Eingetragen im Handelsregister: Amtsgericht Münster HRA / 7444 Gerichtsstand Münster USt Ident-Nr.: DE 126733941 Ansprechpartner für Inhalte: Ulrich Stephan internet@lmc-caravan.de   Rechtliche Hinweise LMC prüft und aktualisiert die Informationen auf seinen Webseiten ständig. Trotz aller Sorgfalt können sich die Daten inzwischen verändert haben. Eine Haftung oder Garantie für die Aktualität, Richtigkeit und Vollständigkeit der zur Verfügung gestellten Informationen kann daher nicht übernommen werden. Des Weiteren behält sich LMC das Recht vor, Änderungen oder Ergänzungen der bereitgestellten Informationen vorzunehmen. Inhalt und Struktur der LMC Webseiten sind urheberrechtlich geschützt. Die Vervielfältigung von Informationen oder Daten, insbesondere die Verwendung von Texten, Textteilen oder Bildmaterial, bedarf der vorherigen schriftlichen Zustimmung von LMC. DAS KOENNTE SIE AUCH INTERESSIEREN Reisemobile Übersicht FREEONTOUR LLT LMC-Newsletter Links Wohnwagen Reisemobile Kontakt Impressum WebKat Pressebereich Händler Login Suche so finden Sie uns auch Kontakt LMC Caravan GmbH & Co. KG Rudolf-Diesel-Str. 4 48336 Sassenberg Telefon: (+49) 2583 27-0 Telefax: (+49) 2583 27-138 E-Mail: info@lmc-caravan.de";

		
		StanfordNLPExample ex = new StanfordNLPExample();
		ex.preprocessing(impressum);
		//ex.getAnnotations(impressum);
		
	}

	private Set<String> preprocessing(String impressum) {
		
		
		
		System.out.println("UstId Pattern EXTRACTING");
		for (int i = 0; i < ustIdPattern.length; i++) {
			System.out.println(ustIdPattern[i]);
			Pattern pattern = Pattern.compile(ustIdPattern[i]);
			Matcher matcher = pattern.matcher(impressum);
				while (matcher.find()) {
					  System.out.println(impressum.substring(
					    matcher.start(), matcher.end()));
			}
			
		}
		// Finde all Wörter mit mindestens zwei 
		// Großbuchstaben (Unicode)
		
		return patterns;

		
	}

	@SuppressWarnings("unchecked")
	private void getAnnotations(String impressum) {
		
		
		String rules1 = "rules/stanford/expres.rules.txt";
		String rules2 = "rules/stanford/disease.txt";
		String rules3 = "rules/stanford/colors.rules.txt";
		
		 CoreMapExpressionExtractor<MatchedExpression> extractor = CoreMapExpressionExtractor
		            .createExtractorFromFiles(TokenSequencePattern.getNewEnv(), rules1, rules2, rules3);

		    StanfordCoreNLP pipeline;
		    
		    Properties props = new Properties();

		    props.setProperty("annotators","tokenize,ssplit,pos,lemma,ner,regexner");
		    props.setProperty("regexner.mapping", "models/stanford/nlp/locations.txt");
		    props.setProperty("ner.model", "models/stanford/nlp/propertymodels/ner/german.dewac_175m_600.crf.ser.gz");

		    Annotation annotation;
		    annotation = new Annotation(impressum);
		   
		    pipeline = new StanfordCoreNLP(props);
		    pipeline.annotate(annotation);

		    // An Annotation is a Map and you can get and use the various analyses individually.
		    // The toString() method on an Annotation just prints the text of the Annotation
		    // But you can see what is in it with other methods like toShorterString()
		    System.out.println("Pattern Precessing");
		    
		    patterns = preprocessing(impressum);
		    
		    System.out.println("The top level annotation");
		    System.out.println(annotation.toShorterString());
		    
		    
		    
		    //Sentences
		    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

		    
		    for (CoreMap sentence : sentences) {
		    
			      List<MatchedExpression> matchedExpressions = extractor.extractExpressions(sentence);

		    	  for (CoreLabel token:sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				        // Print out words, lemma, ne, and normalized ne
				        String word = token.get(CoreAnnotations.TextAnnotation.class);
				        String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
				        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
				        String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
				        String normalized = token.get(CoreAnnotations.NormalizedNamedEntityTagAnnotation.class);
				        
					    System.out.println("token: " + "word="+word + ", pos=" + pos + ", ne=" + ne + ", normalized=" + normalized);
		    	
		    	  }
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
		    System.out.flush();
		  }		
	}

