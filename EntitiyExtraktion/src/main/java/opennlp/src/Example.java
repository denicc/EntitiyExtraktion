package opennlp.src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Example {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		InputStream modelIn = new FileInputStream("models/de-token.bin");

		try {
			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);
			String tokens[] = tokenizer.tokenize("Das ist ein Test.");
			
			for (int i = 0; i < tokens.length; i++) {
				System.out.println(tokens[i]);
			}
			
			
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
