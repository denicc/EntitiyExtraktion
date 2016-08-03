package treetagger.src;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerWrapper;

public class Example {
	
	public static void main(String[] args) throws Exception
	{
		// Point TT4J to the TreeTagger installation directory. The executable is expected
		// in the "bin" subdirectory - in this example at "/opt/treetagger/bin/tree-tagger"
		System.setProperty("treetagger.home", "/apps/TreeTagger");
		TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
		try {
			tt.setModel("models/german-utf8.par");
			
			tt.process(new String[] { "Das", "ist", "ein", "Test", "." });
		}
		finally {
			tt.destroy();
		}
	}
	

}
