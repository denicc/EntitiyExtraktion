package opennlp.src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Example {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File fout = new File("outputTest/tokensImpressumTest.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	
		
		InputStream modelIn = new FileInputStream("classifier/opennlp/de-token.bin");

		try {
			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);
			String tokens[] = tokenizer.tokenize("LMC-Caravan | Impressum Impressum Toggle navigation Wohnwagen Reisemobile Aktionsmodelle Cruiser ComfortLine T 683 LLT iBus connect Service Infomaterial Katalogbestellung Händlersuche Unsere Partner Werksbesichtigung Markenshop Unternehmen Markenwerte Historie Kontakt Karriere Finanzierung Jubiläum Roncalli LMC Club Gewinnspiel Newsletter Meine Welt Aktuell Reiselust FREEONTOUR Erwin Hymer Museum Termine Mein PLATZ Suchmaschine Anzahl der Schlafplätze beliebig 4 6 Art der Schlafplätze Einzelbett Doppelbett Etagenbett (optional) Gesamtlänge von   600 cm 650 cm 700 cm 750 cm 800 cm 850 cm 900 cm bis   700 cm 750 cm 800 cm 850 cm 900 cm Gewicht von   1300 kg 1400 kg 1500 kg 1600 kg 1700 kg bis   1300 kg 1400 kg 1500 kg 1600 kg 1700 kg 1800 kg 1900 kg Auswahl zurücksetzen Alle unsere Wohnwagen sind mit der Long Life Technologie ausgestattet. Für weitere Informationen Hier klicken! Suchmaschine Anzahl der Schlafplätze beliebig 2 3 4 5 6 Art der Schlafplätze Einzelbett Doppelbett Alkoven/Hubbett Etagenbett (optional) Gesamtlänge von   500 cm 600 cm 700 cm 800 cm 900 cm bis   600 cm 700 cm 800 cm 900 cm Gewicht von   2500 kg 2600 kg 2700 kg 2800 kg 2900 kg 3000 kg 3100 kg 3200 kg 3300 kg 3400 kg bis   2500 kg 2600 kg 2700 kg 2800 kg 2900 kg 3000 kg 3100 kg 3200 kg 3300 kg 3400 kg 3500 kg Alle unsere Reisemobile sind mit der Long Life Technologie ausgestattet. Für weitere Informationen Hier klicken! Impressum Startseite Impressum LMC-Impressum LMC Caravan GmbH & Co. KG Rudolf-Diesel-Str. 4 48336 Sassenberg Telefon: (+49) 2583 27-0 Telefax: (+49) 2583 27-138 Geschäftsführer: Ulrich Schoppmann    Eingetragen im Handelsregister: Amtsgericht Münster HRA / 7444 Gerichtsstand Münster USt Ident-Nr.: DE 126733941 Ansprechpartner für Inhalte: Ulrich Stephan internet@lmc-caravan.de   Rechtliche Hinweise LMC prüft und aktualisiert die Informationen auf seinen Webseiten ständig. Trotz aller Sorgfalt können sich die Daten inzwischen verändert haben. Eine Haftung oder Garantie für die Aktualität, Richtigkeit und Vollständigkeit der zur Verfügung gestellten Informationen kann daher nicht übernommen werden. Des Weiteren behält sich LMC das Recht vor, Änderungen oder Ergänzungen der bereitgestellten Informationen vorzunehmen. Inhalt und Struktur der LMC Webseiten sind urheberrechtlich geschützt. Die Vervielfältigung von Informationen oder Daten, insbesondere die Verwendung von Texten, Textteilen oder Bildmaterial, bedarf der vorherigen schriftlichen Zustimmung von LMC. DAS KOENNTE SIE AUCH INTERESSIEREN Reisemobile Übersicht FREEONTOUR LLT LMC-Newsletter Links Wohnwagen Reisemobile Kontakt Impressum WebKat Pressebereich Händler Login Suche so finden Sie uns auch Kontakt LMC Caravan GmbH & Co. KG Rudolf-Diesel-Str. 4 48336 Sassenberg Telefon: (+49) 2583 27-0 Telefax: (+49) 2583 27-138 E-Mail: info@lmc-caravan.de");
			
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
