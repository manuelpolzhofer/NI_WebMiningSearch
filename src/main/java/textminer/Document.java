package textminer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Document {

	/* Variables */
	
	// Contains the total amount of words of the documents
	static Map<String,Integer> wordList;

	// Contains the words in the document
	private Map<String,Integer> wordsInDocument;

	// Map of the words in the document and the tfidf measure
	private Map<String,Double> documentVector;
	
	private String fileName;
	
	/* Methods */
	
	//Constructor
	
	public Document()
	{
		
	}
	
	
	public Document(String fileName, Map<String,Integer> wordList, Map<String,Double> documentVector){
		
		this.fileName = fileName;
		this.wordsInDocument =  wordList;
		this.documentVector = documentVector;
	}
	
	
	/**
	 * Stores the words in a document and updates the total word list
	 * @param dir
	 * @return
	 */
	public ArrayList<Document> processAllDocuments(File dir){
		
		String line;
		wordList = new TreeMap<String,Integer>();
		ArrayList<String> wList = null;
		Map<String,Integer> wordsInDocument  = null;
		ArrayList<Document> documentList =  new ArrayList<Document>();
		
		try {
			
			for(File textFile: dir.listFiles()) {
				
				wList = new ArrayList<String>();
				wordsInDocument  = new TreeMap<String,Integer>();
				
				String readFilePath = textFile.getParent() + "/" + textFile.getName();
				BufferedReader br = new BufferedReader(new FileReader(readFilePath));
				
				try {
					// Read the whole file and store words
					while ((line = br.readLine()) != null){
						
						line = line.trim();
						
						if (line.equals(" ") || (line.isEmpty())) {
							continue;
						}
						else {
							
							wList.add(line);
							
							// Add word to this document word list
							if (wordsInDocument.containsKey(line)){
								
								int count = wordsInDocument.get(line);
								count++;
								wordsInDocument.put(line, count);
							}
							
							else{
								wordsInDocument.put(line, 1);
							}
							
							//Add word to the overall count
							addWordToWordList(line);
						
						}//else
				
					}//while
				
					Document e = new Document(textFile.getName(),wordsInDocument, null);
					documentList.add(e);
					
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
								
				br.close();
			}//for
						
		}catch (IOException e) {
			e.printStackTrace();
		}
				
		setTFIDF(documentList);
		/*System.out.println("Vector 1:");
		System.out.println(documentList.get(0).documentVector);
		System.out.println("Vector 2:");
		System.out.println(documentList.get(1).documentVector);
		*/
	//	System.out.println("Cos distance between d1 and d2: " + documentList.get(0).computeCosDistance(documentList.get(1)));
		
		return documentList;
		
	}
	
	/**
	 * Adds or updates a word to the total word list
	 * @param word
	 */
	public void addWordToWordList(String word){
		
		if (wordList.containsKey(word)){
			
			int count = wordList.get(word);
			count++;
			wordList.put(word, count);
		}
		
		else{
			wordList.put(word, 1);
		}
		
		return;
	}
	
	/**
	 * Computes the TFIDF measure for all the documents
	 * of a given list of documents
	 * @param documentList
	 */
	public void setTFIDF(ArrayList<Document> documentList){
		

		for (Document d : documentList){
			
			List<String> keys = new ArrayList<String>(d.wordsInDocument.keySet());
			Map<String,Double> docVector = new TreeMap<String, Double>();
			
			// For all the words in the document set the TFIDF vector			
			for (String key: keys) {

				double tf = (d.wordsInDocument.get(key) / computeAmountOfWords(d));
				double idf = Math.log(documentList.size() / numDocumentsContainingWord(documentList, key));
							
				double tfIdf = tf * idf;

				
				docVector.put(key, tfIdf);
			}
			
			d.setDocumentVector(docVector);
		}
		
		for (Document d : documentList){
			//Make length of the vector match the total dimension
			List<String> totalKeys = new ArrayList<String>(wordList.keySet());
						
			for (String key: totalKeys) {
		    
				if (d.documentVector.containsKey(key)){
					continue;
				}
				
				//If the word is not there, add it with 0 tfidf
				d.documentVector.put(key, 0.0);
			}
			
//			System.out.println(d.documentVector);
		
		}
		
		// Normalize vectors
		for (Document d : documentList){
			
			normalizeVector(d.documentVector);
						
		}		
	}
	
	/**
	 * Computes the amount of words in a document
	 * @param doc
	 * @return
	 */
	public double computeAmountOfWords(Document doc){
		
		double count = 0;
		
		List<String> keys = new ArrayList<String>(doc.wordsInDocument.keySet());
		
		for (String key : keys){
			
			count += doc.wordsInDocument.get(key);
		}
		
		return count;
	}
	
	/**
	 * Returns the number of documents containing a given term
	 * @param documentList
	 * @param word
	 * @return
	 */
	public double numDocumentsContainingWord(ArrayList<Document> documentList, String word){
		
		double count = 0;
		
		for (Document d : documentList){
			
			if (d.wordsInDocument.containsKey(word)){
				count++;
			}
			
		}
		
		return count;
	}
	
	/**
	 * Normalizes the words vector for a given document
	 * @param vector
	 */
	public void normalizeVector(Map<String, Double> vector){
		
		List<String> keys = new ArrayList<String>(vector.keySet());
		double sum=0;
		
		
		for (String k:keys){
			
			sum += vector.get(k);
		}
		
		for (String k:keys){
			
			double auxVal = vector.get(k);
			auxVal = auxVal / sum;
			vector.put(k, auxVal);
		}
		
	}
	
	/**
	 * Computes cosinus distance between this document 
	 * and another given one.
	 * @param d
	 * @return
	 */
	public double computeCosDistance(Document d){
		
		double distanceTop = 0;
		double distanceLowD1 = 0;
		double distanceLowD2 = 0;
		double distanceLow = 0;
		
		List<String> keys = new ArrayList<String>(this.documentVector.keySet());
		
		for (String k : keys){
			
			distanceTop = distanceTop + (this.documentVector.get(k)* d.documentVector.get(k));
			
			distanceLowD1 += Math.pow(this.documentVector.get(k),2);
			distanceLowD2 += Math.pow(d.documentVector.get(k),2);
		}


		distanceLow = distanceLowD1 * distanceLowD2;
		
		if (distanceLow <= 0){
			return 0.0;
		}
		
		else return (distanceTop/distanceLow);
	}

	
	/****************************************/
	/* *** Query processing functions  **** */
	/****************************************/
	
	
	
	
	// Getters and Setters
	public Map<String,Integer> getWordsInDocument() {
		return wordsInDocument;
	}

	public void setWordsInDocument(Map<String,Integer> wordsInDocument) {
		this.wordsInDocument = wordsInDocument;
	}

	public Map<String,Double> getDocumentVector() {
		return documentVector;
	}

	public void setDocumentVector(Map<String, Double> docVector) {
		this.documentVector = docVector;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
