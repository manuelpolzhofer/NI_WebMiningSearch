
package textminer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import stemmer.*;

public class Preprocesser {

	public static final String language = "english";
	
	public static final int BOOLEAN = 1;
	public static final int VSR = 2;
	
	
	/**
	 * Stems the words of a given query
	 * @param queryWords
	 * @return
	 * @throws Throwable
	 */
	private static ArrayList<String> stemQuery(ArrayList<String> queryWords) throws Throwable{
	
		// Create output list
		ArrayList<String> stemmedQuery = new ArrayList<String>();
		
		Class stemClass = Class.forName("stemmer.ext." + language + "Stemmer");
		
		SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
					
		int repeat = 1;
			
		
		for (String w : queryWords){
		
			String wordToStem = w.toLowerCase();
			stemmer.setCurrent(wordToStem);
			for (int j = repeat; j != 0; j--) {
				stemmer.stem();
			}
			
			stemmedQuery.add(stemmer.getCurrent());
		}
		
		
		return stemmedQuery;
		
	}
	/**
	 * Given a file list, passes the stemmer to all the files
	 * using the language given. 
	 * @param fileList
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static File applyStemmer(File[] fileList) throws Throwable{
		
		//Variables
		Reader reader;
		
		// Create output directory
		File dir = new File("korpus_stemmed");
		dir.mkdirs();
		
		Class stemClass = Class.forName("stemmer.ext." + language + "Stemmer");
		
		for(int i=0; i<fileList.length; i++){
		
			SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
			
			String readFilePath = fileList[i].getParent() + "/" + fileList[i].getName();
			
			reader = new InputStreamReader(new FileInputStream(readFilePath));
			reader = new BufferedReader(reader);
			
			StringBuffer input = new StringBuffer();

			String filename = dir.toString() + "/" + fileList[i].getName();
			
		    OutputStream outstream = new FileOutputStream(filename);
		    
		    Writer output = new OutputStreamWriter(outstream);
			output = new BufferedWriter(output);

			int repeat = 1;
			

			Object[] emptyArgs = new Object[0];
			
			int character;
			while ((character = reader.read()) != -1) {
				
			    char ch = (char) character;
			    if (Character.isWhitespace((char) ch)) {
			    	if (input.length() > 0) {
			    		stemmer.setCurrent(input.toString());
			    		for (int j = repeat; j != 0; j--) {
			    			stemmer.stem();
			    		}
			    		
			    		output.write(stemmer.getCurrent());
			    		output.write('\n');
			    		input.delete(0, input.length());
			    	}
			    	
			    } else {
			    	input.append(Character.toLowerCase(ch));
			    }
			}
			
			output.flush();
			reader.close(); //Avoid leaks
			
		}//for
		
		return dir;
	}
	
	/**
	 * Returns a File with all the texts in the fileList
	 * without the stop words
	 * @param fileList
	 * @param stopWordsFile
	 * @return
	 * @throws IOException 
	 */
	public static File removeStopWords(File[] fileList, String stopWordsFile) throws IOException{
		
		ArrayList stopWords = getStopWordsList(stopWordsFile);
		ArrayList<String> validWordsInFile = new ArrayList<String>(); 
		
		String line;
		
		// Create output directory
		File dir = new File("korpus_clean");
		dir.mkdirs();
	
		
		// Remove stop words in each file
		for (int i = 0; i < fileList.length; i++) {
			
			// Create a reader for the file
			try {
				// Reads all the lines of the input file
				BufferedReader br = new BufferedReader(new FileReader(fileList[i]));
				
				// Store complete file name
				String fileName = dir.toString() + "/" + fileList[i].getName();
				
				// Writes files without stop words
				BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			
				// Read all lines and remove the stopwords from each line
				while ((line = br.readLine()) != null){
					String[] words = line.split(",|\\.|\\s+"); //store all the words in an array
					
					//Store non empty words in the array list of the file
					for (int j=0; j< words.length;j++){
				
						if (words[j].isEmpty()){
							continue;
						}
						
						//Delete numbers
						try {
							Integer aux = Integer.parseInt(words[j]);
						
						} catch (NumberFormatException e) {
						
							validWordsInFile.add(words[j]); //Only add if its not a number
						}
													
					}
				
					//Remove stop words
					for(String w : validWordsInFile){
					
						if (stopWords.contains(w)){
							continue;
						}
						
						bw.write(w + " ");						
					}//for
					
					validWordsInFile.clear(); // Clear the words of that line
					
					bw.newLine(); //Preserve file structure
					bw.flush();
				}//while
				
				//Avoid resource leaks
				br.close();
				bw.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
						
		}
		
		return dir;
	
	}/*removeStopWords*/
	
	/**
	 * Returns an Array List of all the Stop words in the given file
	 * @param stopWordsFile
	 * @return File with stop words
	 */
	static ArrayList<String> getStopWordsList(String stopWordsFile){
		
		String line;
		ArrayList<String> wordList = new ArrayList<String>();
		
		try {
			BufferedReader stopWFile = new BufferedReader(new FileReader(stopWordsFile));
			try {
				// Read the whole file and store stop words
				while ((line = stopWFile.readLine()) != null){
					
					//If the line contains a comment, ignore it
					if (line.contains("|")){
						
						line = line.substring(0, line.indexOf("|"));
					}
					
					line = line.trim();
					
					if (line.equals(" ") || (line.isEmpty())) {
						continue;
					}
					else {
						wordList.add(line);
					}
			
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return wordList;
	
	}/* getStopWordsList */
	
}/*Preprocesser*/
