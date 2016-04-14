/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasakhir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static tugasakhir.TugasAkhir.*;

/**
 *
 * @author Windy Amelia
 */
public class StopWords {
    private static String sentence;
    private ArrayList<String> wordList;
    private ArrayList<String> stopWords;
    private ArrayList<String> punctuation;
    private ArrayList<String> unimportantWords;
    
    //menghasilkan input sentence
    public String getSentence() {
        return sentence;
    }
    
    //menghasilkan kata-kata dari sebuah kalimat
    public ArrayList<String> getWordList () {
        return wordList;
    }    
    
    //menghasilkan list kata-kata yang tidak penting yang akan dihapus
    public ArrayList<String> getStopWords () {
        return stopWords;
    }
    
    //menghasilkan list tanda baca yang akan dihapus
    public ArrayList<String> getPunctuation () throws FileNotFoundException {
        return punctuation;
    }
    
    //menghasilkan kata-kata seperti http, @
    public ArrayList<String> getUnimportantWords () throws FileNotFoundException {
        return unimportantWords;
    }
    
    public void setSentence (String text) {
        sentence = text;
    }
    
    public void setWordList(ArrayList<String> listkata) {
        wordList = listkata;
    }
    
    public void setStopWords(ArrayList<String> stopwords) throws FileNotFoundException {
        stopWords = stopwords;
    }
    
    public void setPunctuation(ArrayList<String> punct) throws FileNotFoundException {
        punctuation = punct;
    }
    
    public void setUnimportantWords(ArrayList<String> unimportant, String fileName) throws FileNotFoundException {
        try (Scanner file = new Scanner (new File(fileName))) {
            while (file.hasNext()) {
                unimportant.add(file.next());
            }
        }
        unimportantWords = unimportant;
    }
    
    public ArrayList<String> splitSentence() {
        ArrayList<String> listkata = new ArrayList<>();
        String[] splited = sentence.split(" ");
        listkata.addAll(Arrays.asList(splited));
        return listkata;
    }
    
    public ArrayList<String> readStopWords (String fileName) throws FileNotFoundException {
        ArrayList<String> stop = null;
        try (Scanner file = new Scanner (new File(fileName))) {
            stop = new ArrayList<>();
            while (file.hasNext()) {
                stop.add(file.next());
            }
        }
        return stop;
    }
    
    // get arraylist of punctuation from file
    public ArrayList<String> readPunctuation (String fileName) throws FileNotFoundException {
        ArrayList<String> punct = new ArrayList<>();
        try (Scanner file = new Scanner (new File(fileName))) {
            while (file.hasNext()) {
                punct.add(file.next());
            }
        }
        
        return punct;
    }
    
    //mengecek sebuah kata apakah stopword atau bukan
    public Boolean isStopWord (String word) {
        boolean found = false;

        for (String stopWord : stopWords) {
            if (word.equals(stopWord)) {
                found = true;
                break;
            }
        }
        return found;
    }
    
    //mengecek sebuah kata termasuk unimportantword atau bukan
    //sebuah kata dikatakan unimportant word jika terkandung unimportant word dalam kata tersebut, misalkan http, @
    public Boolean isUnimportantWord (String word) {
        boolean found = false;
        for (String unimportantWord : unimportantWords) {
            if (word.contains(unimportantWord)) {
                found = true;
                break;
            }
        }
        return found;
    }
  
    // menghapus kata-kata yang merupakan unimportant words
    public void removeUnimportantWords() {
        int i = 0;
        while (i < wordList.size()) {
            //System.out.println(wordList);
            if (isUnimportantWord(wordList.get(i))) {
                wordList.remove(i);
            } else {
                i++;
            }
        }
        //return wordList;
    }
    
    // menghapus tanda baca pada list kata
    public void removePunctuation() {
        for (int i=0; i<wordList.size(); i++) {
//            System.out.println("wordlist: " + wordList.get(i));
            for (String punctuation1 : punctuation) {
                if (wordList.get(i).contains(punctuation1)) {
                    if ((punctuation1.equals("?")) || (punctuation1.equals(".")) || (punctuation1.equals("(")) || (punctuation1.equals(")"))) {
                        punctuation1 = "\\" + punctuation1;
                    }
                    punctuation1 = punctuation1 + "+";
//                    System.out.println("punct: " + punctuation1);
                    String tempWord = wordList.get(i).replaceAll(punctuation1, "");
                    wordList.set(i, tempWord);
//                    System.out.println("wordlist baru yg regex: " + i + " " + wordList.get(i));
                }
            }
            
            // untuk kasus contoh: hari-hari, tiba-tiba 
//            if (wordList.get(i).contains("-")) {
//                if (wordList.get(i).matches(".+-.+")) {
//                    String tempWord = wordList.get(i).replace("-", " ");
//                    wordList.set(i, tempWord);
////                    System.out.println("wordlist baru yg first - : " + i + " " + wordList.get(i));
//                } else {
//                    String tempWord = wordList.get(i).replace("-", "");
//                    wordList.set(i, tempWord);
////                    System.out.println("wordlist baru yg - kedua: " + i + " " + wordList.get(i));
//                }
//            }
        }
        
        for (int i=wordList.size()-1; i>=0; i--) {
//            int i=wordList.size()-1;
            if (wordList.get(i).trim().length()==0) {
                wordList.remove(i);
//                i--;
            }
        }
    }
    
    //menghasilkan kata-kata yang sudah dihapus stop word, punctuation, dan unimportant words
    public ArrayList<String> removeStopWords() throws FileNotFoundException {
//        removeUnimportantWords(wordList, unimportantWords);
        //System.out.println(wordList);
        removePunctuation();

        int idxWordList = 0;
        while (idxWordList < wordList.size()) {
            if ((isStopWord(wordList.get(idxWordList).toLowerCase()) || (wordList.get(idxWordList).length() <= 1))) {
                wordList.remove(idxWordList);
            } else {
                idxWordList++;
            }
        }
        
//        String newSentence = "";
//        for (String wordList1 : wordList) {
//            newSentence = newSentence.concat(wordList1 + " ");
//        }
//        return newSentence;
        return wordList;
    }
    
    public String removeStopWordsFromSentence() throws FileNotFoundException {
        
//        removeUnimportantWords(wordList, unimportantWords);
        //System.out.println(wordList);
        removePunctuation();

        int idxWordList = 0;
        while (idxWordList < wordList.size()) {
            if (isStopWord(wordList.get(idxWordList).toLowerCase())) {
                wordList.remove(idxWordList);
            } else {
                idxWordList++;
            }
        }
        
        String newSentence = "";
        for (String wordList1 : wordList) {
            newSentence = newSentence.concat(wordList1 + " ");
        }
        return newSentence;

    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        /** check stop word available in lexicon **/
//        KeywordSpotting ks = new KeywordSpotting();
//        ks.setHashMap(ks.readTextToHashmap("keywordSpotting/result_lexicon.txt"));
//        
//        ArrayList<String> array_stopWords = readTextPerLine("StopWords/stopwords_id_ks.txt");
//        String temp = "";
//        for (String str : array_stopWords) {
//            if (ks.isAvailable(str)) {
//                temp = temp + str + "\n";
//            }
//        }
//        System.out.print("kata-katanya: " + temp);
        
        /** remove stopwords di daftar kata bahasa indonesia **/
        StopWords sw = new StopWords();
        ArrayList<String> list_before = readTextPerLine("machine_learning/daftar_kata_bahasaIndonesia/daftar_kata_bahasaIndonesia.txt");
        System.out.println("size list before: " + list_before.size());
        sw.setWordList(list_before);
        sw.setStopWords(sw.readStopWords("StopWords/stopwords.txt"));
        sw.setPunctuation(sw.readPunctuation("StopWords/punctuation.txt"));
        ArrayList<String> list_indonesian_words = sw.removeStopWords();
        System.out.println("size list after: " + list_indonesian_words.size());
        writeListToFile(list_indonesian_words, "machine_learning/daftar_kata_bahasaIndonesia/daftar_kata_bahasaIndonesia_afterSW.txt");
//        String input = readText("C:/Users/Windy Amelia/Documents/NetBeansProjects/TugasAkhir/pisauBerkarat.txt");
//        stopword.setSentence(input);
//        System.out.println(stopword.getSentence());
//        stopword.setWordList(stopword.splitSentence());
//        stopword.setStopWords(stopword.readStopWords("StopWords/stopwords_id.txt"));
//        stopword.setPunctuation(stopword.readPunctuation("StopWords/punctuation.txt"));
//        String result = stopword.removeStopWords();
//        System.out.println("hasil: " + result);
//        writeToFile(result, "result-stop-word.txt");
        
        
    }
}