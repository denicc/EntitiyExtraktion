package stanfordnlp.src;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.util.CoreMap;

public class CustomClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CustomClass cc = new CustomClass();
		cc.sentenceSplit("Stuttgart GmbH Wolfratshausen Messepiazza 1 70629 Stuttgart und Nürnberg Tel.: +49 711 18560-0 Fax: +49 711 18560-2440  info(at)messe-stuttgart.de    Online-Redaktion: Jens Kohring   jens.kohring(at)messe-stuttgart.de Informationen Aufsichtsratsvorsitzender:  Staatssekretär Peter Hofelich MdL Geschäftsführer: Ulrich Kromer von Baerle (Sprecher der Geschäftsführung) Roland Bleinroth  Amtsgericht Stuttgart HR B 585 USt-IdNr.: DE 147 866426 Inhaltshinweis: Trotz sorgfältiger inhaltlicher Kontrolle übernehmen wir keine Haftung für die Inhalte externer Links. Für den Inhalt der verlinkten Seiten sind ausschließlich deren Betreiber verantwortlich.  Datenschutzerklärung   Konzeption & Realisierung");
		
	}
	
	public String[] sentenceSplit(String text){
		
		Properties properties = new Properties();
	    properties.setProperty("annotators", "tokenize, ssplit, parse");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
	    List<CoreMap> sentences = pipeline.process(text)
	    .get(CoreAnnotations.SentencesAnnotation.class);    
	    // I just gave a String constant which contains sentences.
	    for (CoreMap sentence : sentences) {
	            System.out.println(sentence.toString());
	    }
		
		
		return null;
		
		
	}

}
