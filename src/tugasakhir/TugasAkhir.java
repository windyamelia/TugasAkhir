/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasakhir;

import IndonesianNLP.IndonesianSentenceTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Windy Amelia
 */
public class TugasAkhir {
    
    public TugasAkhir() {
        
    }
    
    public static String readText(String fileName) throws FileNotFoundException, IOException {
        String text;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            text = sb.toString();
        } finally {
            br.close();
        }
        return text;
    }
    
    public static ArrayList<String> readTextPerLine(String fileName) throws FileNotFoundException, IOException {
        BufferedReader fileReader = null;
        ArrayList<String> list = new ArrayList<>();
        fileReader = new BufferedReader(new FileReader(fileName));
        
        String line = "";
        while((line = fileReader.readLine()) != null) {
                list.add(line);
        }
        
        return list;
    }
    
    public static void writeToFile (String text, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(text);
        }
    }
    
    public static void writeToAppend (String text, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(text);
        }
    }
    
    public static void splitTextIntoSentences(String text) {
        Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher reMatcher = re.matcher(text);
        while (reMatcher.find()) {
            System.out.println(reMatcher.group());
        }
    }
    
    public static ArrayList<String> splitTextIntoWords(String text) throws FileNotFoundException, IOException {
        ArrayList<String> splittedWords = new ArrayList<>();
        String[] words = text.split("( +|\n+)", -1);
//        System.out.println("length: " + words.length);
        for (String str : words) {
//            System.out.println(str);
            splittedWords.add(str.toLowerCase());
//            writeToAppend(str.toLowerCase()+"\n", fileNameOutput);
        }
        
        for (int i=splittedWords.size()-1; i>=0; i--) {
            if (splittedWords.get(i).trim().length() == 0) {
                splittedWords.remove(i);
            }
        }
        return splittedWords;
    }
    
    
    
    public static String removeCharacter(String text, String character) {
        String str = text.replaceAll(character, "");
        return str;
    }

    public static ArrayList<String> insertKutip(ArrayList<String> list) throws FileNotFoundException, IOException {
        ArrayList<String> listResult = new ArrayList<>();
        for (String text : list) {
            text = "\"" + text + "\"";
            listResult.add(text);
        }
        
        return listResult;
    }
    
    public static void writeListToFile (ArrayList<String> list, String output_fileName) throws IOException {
        String temp = "";
        for (String str : list) {
            temp = temp + str + "\n";
        }
        writeToFile(temp, output_fileName);
    }
    
    public static boolean isAvailabeWordInList(String word, ArrayList<String> list) {
        boolean found = false;
        for (String str : list) {
            if (str.equals(word)) {
                found = true;
                break;
            }
        }
        return found;
    }
    
    public static ArrayList<String> praprosesWords(ArrayList<String> wordList) throws FileNotFoundException, IOException {
        StopWords stopword = new StopWords();
        stopword.setWordList(wordList);
        stopword.setStopWords(stopword.readStopWords("StopWords/stopwords.txt"));
        stopword.setPunctuation(stopword.readPunctuation("StopWords/punctuation.txt"));
        stopword.removePunctuation();
        
        Formalization formalizer = new Formalization();
        ArrayList<String> wordlist_after_formalization = new ArrayList<>();
        for (String str : stopword.getWordList()) {
            wordlist_after_formalization.add(formalizer.formalize(str));
        }
        
        writeListToFile(wordlist_after_formalization, "data_training/wordlist_after_formalization.txt");
        
        ArrayList<String> wordlist_after_stemming = new ArrayList<>();
        Stemming stemmer = new Stemming();
//        System.out.println("wordlist " + wordlist_after_formalization);
        for (String wordList1 : wordlist_after_formalization) {
//            System.out.println("sebelum stemming: " + wordList1);
            wordlist_after_stemming.add(stemmer.stemWord(wordList1.trim()));
//            System.out.println("setelah stemming: " + stemmer.stemWord(wordList1.trim()));
        }
        
        for (int i=0; i<wordlist_after_stemming.size(); i++) {
            if (wordlist_after_stemming.get(i).contains("-")) {
                wordlist_after_stemming.set(i, wordlist_after_stemming.get(i).replaceAll(".*-", ""));
            }
        }
        writeListToFile(wordlist_after_stemming, "data_training/wordlist_after_stemming.txt");
        
        stopword.setWordList(wordlist_after_stemming);
        ArrayList<String> wordlist_after_stopword = stopword.removeStopWords();
//        System.out.println("wordlist after stopword: ");
        return wordlist_after_stopword;
    }
    
    public static ArrayList<String> featureExtraction(String fileName) throws IOException {
        String sentences = readText(fileName);
        IndonesianSentenceTokenizer tokenizer = new IndonesianSentenceTokenizer();
        ArrayList<String> wordList = tokenizer.tokenizeSentenceWithCompositeWords(sentences.toLowerCase());
        writeListToFile(wordList, "data_training/result_splitText.txt");
        
        ArrayList<String> wordList_after_praproses = praprosesWords(wordList);
        
        writeListToFile(wordList_after_praproses, "data_training/listKata_after_praproses.txt");
        
        ArrayList<String> wordList_new = new ArrayList<>();
        for (String str : wordList_after_praproses) {
            if (!isAvailabeWordInList(str, wordList_new)) {
                wordList_new.add(str);
            }
        }
        
        writeListToFile (wordList_new, "data_training/word_result_extraction.txt");
        return wordList_new;
    }
    
    public static String resultFileCsv(ArrayList<String> features) throws IOException {
        String temp = "";
//        temp = temp + "sentence,";
        for (String fitur : features) {
            temp = temp + fitur + ",";
        }
        temp = temp + "emotion" + "\n";
        
        ArrayList<String> list_emotion = readTextPerLine("data_training/emotions-sentences.txt");
        
        ArrayList<String> list_sentences = readTextPerLine("data_training/sentences-cerpen.txt");
        for (int i=0; i<list_sentences.size(); i++) {
//            temp = temp + list_sentences.get(i) + ",";
            IndonesianSentenceTokenizer tokenizer = new IndonesianSentenceTokenizer();
            ArrayList<String> words_sentence = praprosesWords(tokenizer.tokenizeSentenceWithCompositeWords(list_sentences.get(i).toLowerCase()));
//            int i=0;
            for (String str_feature : features) {
                if (isAvailabeWordInList(str_feature, words_sentence)) {
                    temp = temp + "1,";
                } else {
                    temp = temp + "0,";
                }
            }
            temp = temp + list_emotion.get(i).toLowerCase() + "\n";
        }
        return temp;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        /** MENGHASILKAN FILE ARFF **/
        ArrayList<String> features = featureExtraction("data_training/sentences-cerpen.txt");
        System.out.println(resultFileCsv(features));
        writeToFile(resultFileCsv(features), "data_training/data_training_sentences.csv");
        
        /*** RAPIHIN ANTONIM ***/
//        ArrayList<String> listAntonim = readTextPerLine("keywordSpotting/antonim/antonim.txt");
//        ArrayList<String> first_word = new ArrayList<>();
//        ArrayList<String> antonim_firstWord = new ArrayList<>();
//        for (String str : listAntonim) {
//            String[] splitted_antonim = str.split(" ");
//            first_word.add(splitted_antonim[0]);
//            antonim_firstWord.add(splitted_antonim[2]);
//        }
        
//        String temp_firstWord = "";
//        System.out.println("first word");
//        for (String str : first_word) {
//            System.out.println(str);
//            temp_firstWord = temp_firstWord + str + "\n";
//        }
//        writeToFile(temp_firstWord, "keywordSpotting/antonim/first_word.txt");
//        System.out.println(first_word.size());
        
//        String result_antonim = "";
//        for (int i=0; i<antonim_firstWord.size(); i++) {
//            result_antonim = result_antonim + antonim_firstWord.get(i).toLowerCase() + " " + first_word.get(i).toLowerCase() + "\n";
//        }
//        writeToFile(result_antonim, "keywordSpotting/antonim/result_antonim_reverse.txt");
//        System.out.println(antonim_firstWord.size());
//        
//        /*** RAPIHIN LEXICON ***/
//        ArrayList<String> listLexicon = readTextPerLine("C:\\Users\\Windy Amelia\\Documents\\NetBeansProjects\\TugasAkhir\\keywordSpotting\\lexicon-noStemming-belumrapi.txt");
//        String result_temp = "";
//        for (int i=0; i<listLexicon.size(); i++) {
//            if (!listLexicon.get(i).trim().isEmpty()) {
//              String temp = listLexicon.get(i).replace("\t", " ");
//              if (temp.contains(",")) {
//                  String[] list_words = temp.split(",");
//                  String last_word = list_words[list_words.length-1].trim();
//                  Scanner scanner = new Scanner(last_word);
//                  String last_word_temp = scanner.next();
//                  String nilai_emosi = "";
//                  while (scanner.hasNext()) {
//                      String scanner_temp = scanner.next();
//                      if (!scanner_temp.equals("0") && !scanner_temp.equals("1")) {
//                        last_word_temp = last_word_temp + " " + scanner_temp;
//                      } else {
//                          break;
//                      }
//                  }
//                  nilai_emosi = last_word.substring(last_word.length()-11, last_word.length());
//                  for (int j=0; j<list_words.length-1; j++) {
//                      result_temp = result_temp + list_words[j].trim() + " " + nilai_emosi + "\n";
//                  }
//                  result_temp = result_temp + last_word_temp + " " + nilai_emosi + "\n";
//              } else {
//                result_temp = result_temp + temp + "\n";
//              }
//            }
//        }
//        System.out.println(result_temp);
//        writeToFile(result_temp, "keywordSpotting/result_lexicon_noStemming.txt");
        
        /**** INSERT KUTIP ****/
//        ArrayList<String> sentences = readCsvFile("C:\\Users\\Windy Amelia\\Documents\\NetBeansProjects\\TugasAkhir\\data training\\sentences-cerpen.txt");
//        ArrayList<String> sentencesWithKutip = insertKutip(sentences);
//        for (String sentence : sentencesWithKutip) {
//            writeToAppend(sentence+"\n", "senteces-with-kutip.csv");
//        }
        
        /*** BENERIN FILE CERPEN ***/
//        ArrayList<String> textPerLine = readTextPerLine("C:\\Users\\Windy Amelia\\Documents\\NetBeansProjects\\TugasAkhir\\cerpen\\lupaBacaDoaSebelumTidur.txt");
//        String temp = "";
//        for (String str : textPerLine) {
//            temp = temp + " " + str;
//        }
//        writeToFile(temp, "C:\\Users\\Windy Amelia\\Documents\\NetBeansProjects\\TugasAkhir\\cerpen\\lupaBacaDoaSebelumTidur.txt");
        
        /**** STEMMING TO DATA TRAINING ****/
//        ArrayList<String> sentences = readTextPerLine("C:\\Users\\Windy Amelia\\Documents\\NetBeansProjects\\TugasAkhir\\data training\\cerpen.txt");
//        Formalization formalizer = new Formalization();
//        Stemming stemmer = new Stemming();
//        ArrayList<String> resultSentences = new ArrayList<>();
//        for (String sentence : sentences) {
////            StopWords sw = new StopWords();
////            sw.setSentence(sentence);
////            sw.setWordList(sw.splitSentence());
////            sw.setStopWords(sw.readStopWords("StopWords/stopwords_id.txt"));
////            sw.setPunctuation(sw.readPunctuation("StopWords/punctuation.txt"));
////            String result = sw.removeStopWordsFromSentence();
////            String resultFormalize = formalizer.formalizeSentence(result);
//            String resultStemming = stemmer.stemSentences(sentence);
//            
//            
//            resultSentences.add(resultStemming);
//        }
//        
//        ArrayList<String> emotions = readTextPerLine("C:\\Users\\Windy Amelia\\Documents\\NetBeansProjects\\TugasAkhir\\data training\\emotions-cerpen.txt");
//        
//        String resultTemp = "";
//        int i=0;
//        for (String result : resultSentences) {
//            String temp = emotions.get(i);
//            String emotion = Character.toString(temp.charAt(0)).toUpperCase() + temp.substring(1);
//            resultTemp = resultTemp + "\"" + result + "\", " + emotion + "\n";
//            i++;
//        }
//        writeToFile(resultTemp, "data training/result-stemming-cerpen.txt");
        
        /**** MACHINE LEARNING ****/
//        String output = readText("C:\\Users\\Windy Amelia\\Documents\\NetBeansProjects\\TugasAkhir\\data training\\sentences-cerpen.txt");
//        output = output.replace("\"", "");
//        output = output.replace("“", "");
//        output = output.replace("”", "");
//        output = output.replace("‘", "");
//        output = output.replace("’", "");
//        System.out.println(output);
//        writeToFile(output, "data training/new-sentences.txt");
//        
//        splitTextIntoSentences(output);
        
//        System.out.println("****SPLIT TEXT INTO WORDS****");
//        ArrayList<String> splitedWords = splitTextIntoWords(output);
        
//        StopWords stopword = new StopWords();
//        stopword.setWordList(splitedWords);
//        stopword.setStopWords(stopword.readStopWords("StopWords/stopwords.txt"));
//        stopword.setPunctuation(stopword.readPunctuation("StopWords/punctuation.txt"));
//        ArrayList<String> resultStopWord = stopword.removeStopWords();
//        for (String str : resultStopWord) {
//            System.out.println("result stop word: " + str);
//            if (!(str.equals(" "))) {
//                writeToAppend(str+"\n", "result-stop-word-ML.txt");
//            }
//        } 
//        
//        Formalization formalisasi = new Formalization();
//        ArrayList<String> resultFormalization = new ArrayList<>();
//        for (String str : resultStopWord) {
//            String temp = formalisasi.formalize(str);
//            resultFormalization.add(temp);
//        }
//        for (String str : resultFormalization) {
//            if (!(str.equals(" ")) || !(str.equals("\n"))) {
//                writeToAppend(str.toLowerCase()+"\n", "result-formalization-ML.txt");
//            }
//        }
        
//        Stemming stem = new Stemming();
//        ArrayList<String> resultStemming = new ArrayList<>();
//        for (String str : splitedWords) {
//            String temp = stem.stemWord(str);
//            resultStemming.add(temp);
//        }
//        for (String str : resultStemming) {
//            if (str.length()>0) {
//                writeToAppend(str+"\n", "result-stemming-ML.txt");
//            }
//        }
        
        
        /*** KEYWORD SPOTTING ***/    
//        String output = readText("C:/Users/Windy Amelia/Documents/NetBeansProjects/TugasAkhir/lemariRahasia.txt");
//        System.out.println(output);
//        
//        System.out.println("****SPLIT TEXT INTO WORDS****");
//        ArrayList<String> splitedWords = splitTextIntoWords(output);
//        
//        StopWords stopword = new StopWords();
//        stopword.setWordList(splitedWords);
//        stopword.setStopWords(stopword.readStopWords("StopWords/stopwords.txt"));
//        stopword.setPunctuation(stopword.readPunctuation("StopWords/punctuation.txt"));
//        ArrayList<String> resultStopWord = stopword.removeStopWords();
//        for (String str : resultStopWord) {
//            System.out.println("result stop word: " + str);
//            if (!(str.equals(" ")) || !(str.equals("\n"))) {
//                writeToAppend(str+"\n", "result-stop-word.txt");
//            }
//        } 
//        
//        Formalization formalisasi = new Formalization();
//        ArrayList<String> resultFormalization = new ArrayList<>();
//        for (String str : resultStopWord) {
//            String temp = formalisasi.formalize(str);
//            resultFormalization.add(temp);
//        }
//        for (String str : resultFormalization) {
//            if (!(str.equals(" ")) || !(str.equals("\n"))) {
//                writeToAppend(str.toLowerCase()+"\n", "result-formalization.txt");
//            }
//        }
//        
//        Stemming stem = new Stemming();
//        ArrayList<String> resultStemming = new ArrayList<>();
//        for (String str : splitedWords) {
//            String temp = stem.stemWord(str);
//            resultStemming.add(temp);
//        }
//        for (String str : resultStemming) {
//            if (!(str.equals(" ")) || !(str.equals("\n"))) {
//                writeToAppend(str+"\n", "result-stemming.txt");
//            }
//        }       
   }
}
