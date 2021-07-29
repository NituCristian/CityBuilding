package model.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class NLPManagement {

	POSModel model;
	POSTaggerME tagger;
	
	public Intent createIntent(String sentence) {
		
		InputStream inputStream;
		
		try {	
			String entity = null;
			String action = null;
			
			inputStream = new 
					FileInputStream("openNLP\\en-pos-maxent.bin");
			
			POSModel model = new POSModel(inputStream); 
			POSTaggerME tagger = new POSTaggerME(model); 
			WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE; 
			
			String[] tokens = whitespaceTokenizer.tokenize(sentence); 
			       
			//Generating tags 
			String[] tags = tagger.tag(tokens);
			
			for (int i = 0; i < tokens.length; i++) {
			     String word = tokens[i].trim();
			     String tag = tags[i].trim();
			     
			     if(tag.equals("VB") || tag.equals("VBP")) {
			    	 action = word;
			     }
			     
			     //get the singular of the noun
			     if (tag.equals("NNS")) {
			    	if(word.equalsIgnoreCase("amenities")) {
			    		entity = "amenity";
			    	}
			    	
			    	else {
			    		entity = word.substring(0, word.length() - 1);
			    	}
			     }
			     
			     if(tag.equals("NN")) {
			    	 entity = word;
			     }
			     
			     System.out.print(tag + ":" + word + "  ");
			}
			
			//create an intent object
			Intent intent = new Intent(entity, action);
			
			return intent;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 	
		
		return null;
	}
	
}
