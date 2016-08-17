package example.stanford;

import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.ErasureUtils;

public class MyAnnotations {
	  // My custom tokens
	  public static class MyTokensAnnotation implements CoreAnnotation<List<? extends CoreMap>> {
	    @Override
	    public Class<List<? extends CoreMap>> getType() {
	      return ErasureUtils.<Class<List<? extends CoreMap>>> uncheckedCast(List.class);
	    }
	  }

	  // My custom type
	  public static class MyTypeAnnotation implements CoreAnnotation<String> {
	    @Override
	    public Class<String> getType() {
	      return ErasureUtils.<Class<String>> uncheckedCast(String.class);
	    }
	  }

	  // My custom value
	  public static class MyValueAnnotation implements CoreAnnotation<String> {
	    @Override
	    public Class<String> getType() {
	      return ErasureUtils.<Class<String>> uncheckedCast(String.class);
	    }
	  }
	}