package opennlp.src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class Example {

	private Pattern VALID_EMAIL_ADDRESS_REGEX;

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File fout = new File("outputTest/tokensImpressumTest.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	

		
		String input = "LMC-Caravan | Impressum Impressum Toggle navigation Wohnwagen Reisemobile Aktionsmodelle Cruiser ComfortLine T 683 LLT iBus connect Service Infomaterial Katalogbestellung Händlersuche Unsere Partner Werksbesichtigung Markenshop Unternehmen Markenwerte Historie Kontakt Karriere Finanzierung Jubiläum Roncalli LMC Club Gewinnspiel Newsletter Meine Welt Aktuell Reiselust FREEONTOUR Erwin Hymer Museum Termine Mein PLATZ Suchmaschine Anzahl der Schlafplätze beliebig 4 6 Art der Schlafplätze Einzelbett Doppelbett Etagenbett (optional) Gesamtlänge von   600 cm 650 cm 700 cm 750 cm 800 cm 850 cm 900 cm bis   700 cm 750 cm 800 cm 850 cm 900 cm Gewicht von   1300 kg 1400 kg 1500 kg 1600 kg 1700 kg bis   1300 kg 1400 kg 1500 kg 1600 kg 1700 kg 1800 kg 1900 kg Auswahl zurücksetzen Alle unsere Wohnwagen sind mit der Long Life Technologie ausgestattet. Für weitere Informationen Hier klicken! Suchmaschine Anzahl der Schlafplätze beliebig 2 3 4 5 6 Art der Schlafplätze Einzelbett Doppelbett Alkoven/Hubbett Etagenbett (optional) Gesamtlänge von   500 cm 600 cm 700 cm 800 cm 900 cm bis   600 cm 700 cm 800 cm 900 cm Gewicht von   2500 kg 2600 kg 2700 kg 2800 kg 2900 kg 3000 kg 3100 kg 3200 kg 3300 kg 3400 kg bis   2500 kg 2600 kg 2700 kg 2800 kg 2900 kg 3000 kg 3100 kg 3200 kg 3300 kg 3400 kg 3500 kg Alle unsere Reisemobile sind mit der Long Life Technologie ausgestattet. Für weitere Informationen Hier klicken! Impressum Startseite Impressum LMC-Impressum LMC Caravan GmbH & Co. KG Rudolf-Diesel-Str. 4 48336 Sassenberg Telefon: (+49) 2583 27-0 Telefax: (+49) 2583 27-138 Geschäftsführer: Ulrich Schoppmann    Eingetragen im Handelsregister: Amtsgericht Münster HRA / 7444 Gerichtsstand Münster USt Ident-Nr.: DE 126733941 Ansprechpartner für Inhalte: Ulrich Stephan internet@lmc-caravan.de   Rechtliche Hinweise LMC prüft und aktualisiert die Informationen auf seinen Webseiten ständig. Trotz aller Sorgfalt können sich die Daten inzwischen verändert haben. Eine Haftung oder Garantie für die Aktualität, Richtigkeit und Vollständigkeit der zur Verfügung gestellten Informationen kann daher nicht übernommen werden. Des Weiteren behält sich LMC das Recht vor, Änderungen oder Ergänzungen der bereitgestellten Informationen vorzunehmen. Inhalt und Struktur der LMC Webseiten sind urheberrechtlich geschützt. Die Vervielfältigung von Informationen oder Daten, insbesondere die Verwendung von Texten, Textteilen oder Bildmaterial, bedarf der vorherigen schriftlichen Zustimmung von LMC. DAS KOENNTE SIE AUCH INTERESSIEREN Reisemobile Übersicht FREEONTOUR LLT LMC-Newsletter Links Wohnwagen Reisemobile Kontakt Impressum WebKat Pressebereich Händler Login Suche so finden Sie uns auch Kontakt LMC Caravan GmbH & Co. KG Rudolf-Diesel-Str. 4 48336 Sassenberg Telefon: (+49) 2583 27-0 Telefax: (+49) 2583 27-138 E-Mail: info@lmc-caravan.de";
		String input_new = "SÜNNER BRAUEREI: SÜNNER BRAUEREI ÜBER UNS . Über uns . SÜNNER Brauerei . SÜNNER Brennerei . virtueller Rundgang . Brunnen . Sudhaus . Gärung . Bierreifung . Flaschenabfüllung . Fasslager . Logistik . Dampfmaschine . Brennerei . Likör-Keller . Biergarten . Historie . Die Anfänge in Deutz . Von Deutz nach Kalk . Der Aufschwung . Wiederaufbau . SÜNNER heute . Galerie Historie . Klimaneutral . PRODUKTE . SÜNNER-Biere . SÜNNER Kölsch . SÜNNER Malz . SÜNNER Hefeweizen . SÜNNER Lager . SÜNNER-Spirituosen . SÜNNER Dry Gin No. 260 . SÜNNER Dry Gin Sinner Strength . SÜNNER Vodka No. 260 . SÜNNER Kräuter No. 260 . SÜNNER No.1 . SÜNNER Akrobat . SÜNNER Wacholdergeist . SÜNNER Feiner Korn . SÜNNER Doppelkorn . SÜNNER Gespritzter . SÜNNER Granatapfel Ice . SÜNNER Limoncello . SÜNNER Kölsches Wasser . SÜNNER Limonaden . Kölsches Wasser [pink]* . Kölsches Wasser [orange]* . Kölsches Wasser [grün]* . BILDDATENBANK . SÜNNER Logos . SÜNNER Logos . Kölsch . Wasser . Limonaden . SÜNNER Produkte . Flaschen . Sixpacks . Kästen . Fässer . Spirituosen . GALERIE . SÜNNER Brauerei . Aussenansicht . Sudhaus . Gärkeller . Lagerkeller . Fassabfüllung . Flaschenabfüllung . Brennerei . SÜNNER Lager . SÜNNER Historie . Historie Übersicht . Mein Bild mit SÜNNER . Archiv . Bild einsenden . Weiberfastnacht 2013 . AKTUELLES . Aktuelles . Biergarten . Brauerei Besichtigung . Veranstaltungs-Equipment . Sitemap . JOBS . Übersicht Stellenangebote . Immobilienkaufmann (m/w) . Destillateur (m/w) . SÜNNER KELLER: Koch (m/w) . KONTAKT . Kontakt . Ansprechpartner . Impressum . Datenschutz . SHOP . © 2006-2016 Gebr. Sünner GmbH & Co. KG, Kalker Hauptstraße 260, 51103 Köln |  Impressum  |  Datenschutz         teilen:  Facebook  Twitter  Tumblr  XING  RSS Feed  Druckansicht SÜNNER KELLER 500 Jahre Reinheitsgebot - der Film (Deutscher Brauer-Bund e.V.)";
		String basics = "Für weitere Informationen Hier klicken! Impressum Startseite Impressum LMC-Impressum LMC Caravan GmbH & Co. KG Rudolf-Diesel-Str. 4 48336 Sassenberg Telefon: (+49) 2583 27-0 Telefax: (+49) 2583 27-138 Geschäftsführer: Ulrich Schoppmann";
		
		
		Example ex = new Example();
		
		//ex.extractMail(input);
		
		//ex.extractStrasse(input);
		//ex.getSentences(bw, input);
		
		ex.getTokens(bw, input_new);
		
	
	}
	
	private void extractStrasse(String input) {

		Pattern strasse = Pattern.compile("",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = strasse.matcher(input);
		Set<String> strassen = new HashSet<String>();
		while(matcher.find()){
			strassen.add(matcher.group());
		}
		strassen.forEach(System.out::println);

		
	}

	private void extractMail(String input){
		
//		VALID_EMAIL_ADDRESS_REGEX = 
//			    Pattern.compile("^.+@.+(\\.[^\\.]+)+$", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(input);
//
//		
//		if (matcher.find()) {
//			System.out.print(matcher.group(1));
//		}
		Pattern mail = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
			    Pattern.CASE_INSENSITIVE);
			Matcher matcher = mail.matcher(input);
			Set<String> emails = new HashSet<String>();
			while(matcher.find()) {
			  emails.add(matcher.group());
			}
			emails.forEach(System.out::println);
		
	
	}
	
	private void getSentences(BufferedWriter bw, String input) throws FileNotFoundException {
		InputStream modelIn = new FileInputStream("classifier/opennlp/de-sent.bin");
		
		
		SentenceModel model;
		try {
			model = new SentenceModel(modelIn);
			SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
			String sentences[] = sentenceDetector.sentDetect(input);
			Span sentencesSpan[] = sentenceDetector.sentPosDetect("  First sentence. Second sentence. ");

			for (int i = 0; i < sentences.length; i++) {
				
				bw.write(sentences[i]);
				System.out.println(sentences[i]);
				bw.newLine();
			}
			
			bw.close();


		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}

	private void getTokens(BufferedWriter bw, String input) throws FileNotFoundException{
		
		InputStream modelIn = new FileInputStream("classifier/opennlp/de-token.bin");

		try {
			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);
			String tokens[] = tokenizer.tokenize(input);
			
			for (int i = 0; i < tokens.length; i++) {
				System.out.println(tokens[i]);
				bw.write(tokens[i]);
				bw.newLine();
			}
			
			bw.close();
	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
				}
			}
		}

		
	}
	


}
