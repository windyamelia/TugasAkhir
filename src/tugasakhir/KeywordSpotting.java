/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasakhir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import static tugasakhir.TugasAkhir.*;

/**
 *
 * @author Windy Amelia
 */
public class KeywordSpotting {
    private HashMap hmKeywordSpotting;
    private int[] hasilNilaiEmosi;
    
    public KeywordSpotting() {
        hasilNilaiEmosi = new int[6];
    }
    
    public HashMap getHashMap() {
        return hmKeywordSpotting;
    }
    
    public int[] getHasilNilaiEmosi() {
        return hasilNilaiEmosi;
    }
    
    public void setHashMap (HashMap hm) {
        hmKeywordSpotting = hm;
    }
    
    public void setHasilNilaiEmosi (int[] hasil) {
        hasilNilaiEmosi = hasil;
    }
    
    // baca teks per line lalu dimasukkan ke hashmap
    public HashMap readTextToHashmap(String fileName) throws FileNotFoundException, IOException {
        HashMap hm = new HashMap();
        int sum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null; ) {
                String[] strOneLine = line.split(" ");
                String temp = "";
                int index = 0;
                for (String str : strOneLine) {
                    if (!str.equals("0") && !str.equals("1")) {
                        if (index > 0 ) {
                            temp = temp + " " + str;
                        } else {
                            temp += str;
                        }
                        index++;
                    } else {
                        break;
                    }
                }
//                System.out.println("temp: " + temp);
                ArrayList<Integer> nilaiWord = new ArrayList<>();
                for (int idxStrOneLine=index; idxStrOneLine<strOneLine.length; idxStrOneLine++) {
                    nilaiWord.add(parseInt(strOneLine[idxStrOneLine]));
                }
                sum++;
                hm.put(temp, nilaiWord);
            }
            System.out.println("sum: " + sum);
        }
        
        return hm;
    }
    
    public HashMap readtAntonimToHashMap(String fileName) throws FileNotFoundException, IOException {
        HashMap hm = new HashMap();
        int sum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null; ) {
                String[] strOneLine = line.split(" ");
                hm.put(strOneLine[0], strOneLine[1]);
            }
        }
        
        return hm;
    }
    
    //mengembalikan nilai dari suatu kata, ex: 1 1 0 0 0 0
    public ArrayList<String> getArrayNilaiKata (String word) {
        return (ArrayList<String>) (hmKeywordSpotting.get(word));
    }
    
    // menghasilkan nilai emosi tertentu
    public int getNilaiEmosi(String word, int input) {
        int nilai;
        nilai = ((ArrayList<Integer>)hmKeywordSpotting.get(word)).get(input);
        return nilai;
    }
    
    // mengecek apa sebuah word ada di hashmap
    public boolean isAvailable(String word) {
//        System.out.println("hm di isAvailable: " + hmKeywordSpotting);
        return hmKeywordSpotting.containsKey(word);
    }
    
    //menghasilkan array of integer yang berisi nilai dari masing-masing emosi berdasarkan arraylist words
    public int[] getHasilEmosi(ArrayList<String> words){
        int[] nilaiEmosi = new int[6];
        
        for (String word : words) {
            if (isAvailable(word)) {
                ArrayList<Integer> hmKS = (ArrayList<Integer>) (hmKeywordSpotting.get(word));
                for (int i=0; i<hmKS.size(); i++) {
                    nilaiEmosi[i] = nilaiEmosi[i] + hmKS.get(i);
                }
            }
        }
        
        return nilaiEmosi;
    }
    
    public void printHashMap () throws IOException {
        // Get a set of the entries
        Set set = hmKeywordSpotting.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        
        int sum =0;
        String temp = "";
        // Display elements
        while(i.hasNext()) {
           Map.Entry me = (Map.Entry)i.next();
           temp = temp + me.getKey() + ": " + me.getValue() + "\n";
           System.out.print(me.getKey() + ": ");
           System.out.println(me.getValue());
           sum++;
        }
//        writeToFile(temp, "keywordSpotting/hujanDalamGelap/hashMap.txt");
//        System.out.println("sum: " + sum);
    }
    
    public static void main (String[] args) throws IOException {
        Scanner scanner = new Scanner (System.in);
        System.out.print("judul cerpen: ");
        String judul_cerpen = scanner.nextLine();
        
        String text_cerpen = readText("cerpen/" + judul_cerpen + ".txt");
        ArrayList<String> listKata_cerpen = splitTextIntoWords(text_cerpen);
        writeListToFile(listKata_cerpen, "keywordSpotting/" + judul_cerpen + "/listKata_cerpen.txt");
        
        // remove punctuation
        StopWords stopword = new StopWords();
        stopword.setWordList(listKata_cerpen);
        stopword.setPunctuation(stopword.readPunctuation("StopWords/punctuation.txt"));
        stopword.removePunctuation();
        listKata_cerpen = stopword.getWordList();
        String kata_cerpen = "";
        for (String str : listKata_cerpen) {
            kata_cerpen = kata_cerpen + str + "\n";
        }
        writeToFile(kata_cerpen, "keywordSpotting/" + judul_cerpen + "/listKata_cerpen.txt");
        
        /*** FORMALISASI ***/
        ArrayList<String> listKata_after_formalization = new ArrayList<>();
        Formalization formalisasi = new Formalization();
        for (String str : listKata_cerpen) {
            if (!str.contains("-")) {
                listKata_after_formalization.add(formalisasi.formalize(str).trim());
            } else {
                listKata_after_formalization.add(str.trim());
            }
        }
        System.out.println("size list kata formalisasi: " + listKata_after_formalization.size());
        
        
        for (int i=listKata_after_formalization.size()-1; i>=0; i--) {
            if (listKata_after_formalization.get(i).equals("tidak")) {
                if (listKata_after_formalization.get(i+1).equals("akan")) {
                    listKata_after_formalization.remove(i+1);
                }
            }
        } 
        
        KeywordSpotting ks_antonim1 = new KeywordSpotting();
        ks_antonim1.setHashMap(ks_antonim1.readtAntonimToHashMap("keywordSpotting/antonim/result_antonim.txt"));
        
        KeywordSpotting ks_antonim2 = new KeywordSpotting();
        ks_antonim2.setHashMap(ks_antonim2.readtAntonimToHashMap("keywordSpotting/antonim/result_antonim_reverse.txt"));
        for (int i=listKata_after_formalization.size()-1; i>=0; i--) {
            if (listKata_after_formalization.get(i).equals("tidak") || listKata_cerpen.get(i).equals("tak")) {
                String kata_setelah_tidak = listKata_after_formalization.get(i+1);
                System.out.println("kata setelah tidak/tak: " + kata_setelah_tidak);
                if (ks_antonim1.isAvailable(kata_setelah_tidak)) {
                    listKata_after_formalization.set(i+1, ks_antonim1.getHashMap().get(kata_setelah_tidak).toString());
                    listKata_after_formalization.remove(i);
                } else if (ks_antonim2.isAvailable(kata_setelah_tidak)) {
                    listKata_after_formalization.set(i+1, ks_antonim2.getHashMap().get(kata_setelah_tidak).toString());
                    listKata_after_formalization.remove(i);
                } else {
                    for (int k=0; k<2; k++) {
                        listKata_after_formalization.remove(i);
                    }
                }
            }
        }
        
        String listKata_cerpen_new = "";
        for (String str : listKata_after_formalization) {
            listKata_cerpen_new = listKata_cerpen_new + str + "\n";
        }
        System.out.println("listKata cerpen baru: " + listKata_cerpen_new);
        
         /*** remove stop word ***/
//        StopWords stopword = new StopWords();
        stopword.setWordList(listKata_after_formalization);
        stopword.setStopWords(stopword.readStopWords("StopWords/stopwords_id_ks.txt"));
        stopword.setPunctuation(stopword.readPunctuation("StopWords/punctuation.txt"));
        
        ArrayList<String> list_kata_after_stopword = stopword.removeStopWords();
        System.out.println("listkata_after_stopword: ");
        for (String str : list_kata_after_stopword) {
            System.out.println(str.toLowerCase());
        }
        
//        ArrayList<String> words_noNeed_stemming = readTextPerLine("keywordSpotting/no_need_stemming.txt");
//        ArrayList<String> list_kataBerulang = readTextPerLine("keywordSpotting/kata_berulang.txt");
//        ArrayList<String> listKata_after_stemming = new ArrayList<>();
//        Stemming stemmer = new Stemming();
//        for (String str : list_kata_after_stopword) {
//            boolean found_noStem = false;
//            for (String no_stem : words_noNeed_stemming) {
//                if (no_stem.equals(str)) {
//                    found_noStem = true;
//                    break;
//                }
//            }
//            
//            boolean found_kataBerulang = false;
//            for (String kataBerulang : list_kataBerulang) {
//                if (kataBerulang.equals(str)) {
//                    System.out.println("kataBerulang: " + str);
//                    found_kataBerulang = true;
//                    break;
//                }
//            }
//            if ((!found_noStem) && (!found_kataBerulang)) {
//                listKata_after_stemming.add(stemmer.stemWord(str));
//            } else {
//                listKata_after_stemming.add(str);
//            }
//        }
//        
//        System.out.println("listKata_after_stemming: ");
//        for (String str : listKata_after_stemming) {
//            System.out.println(str);
//        }
        
//        String text_cerpen_after_stemming = stemmer.stemSentences(text_cerpen.toLowerCase());
//        System.out.println(text_cerpen_after_stemming);
//        ArrayList<String> listKata_after_stemming = splitTextIntoWords(text_cerpen_after_stemming, "keywordSpotting/" + judul_cerpen + "/listKata_after_stemming.txt");
//        ArrayList<String> listKata_after_stemming_dummy = readTextPerLine("keywordSpotting/hujanDalamGelap/listKata_after_stemming_dummy.txt");

        
        /*** SPLIT TEXT INTO TWO WORDS ***/        
        ArrayList<String> two_words = new ArrayList<>();
        int i=0;
        while (i < list_kata_after_stopword.size()-1) {
            String temp_1 = list_kata_after_stopword.get(i);
            String temp_2 = "";
            if (i <= list_kata_after_stopword.size()-1) {
                temp_2 = list_kata_after_stopword.get(i+1);
            }
            String temp = temp_1 + " " + temp_2;
            two_words.add(temp);
            i++;
        }
//        for (String str : two_words) {
//            System.out.println(str);
//        }        
 
        KeywordSpotting ks = new KeywordSpotting();
        ks.setHashMap(ks.readTextToHashmap("keywordSpotting/result_lexicon_noStemming.txt"));
        
        System.out.println("hashmap: \n");
//        ks.printHashMap();
        System.out.println("size hm: " + ks.getHashMap().size());
        
        int jumlah_twoWords = 0;
        for (int j=two_words.size()-1; j>=0; j--) {
            if (ks.isAvailable(two_words.get(j))) {
                jumlah_twoWords++;
//                System.out.println(jumlah_twoWords);
            } else {
                two_words.remove(j);
            }
        }
        System.out.println("jumlah two words final: " + jumlah_twoWords);
        System.out.println("isi two_words yang ada di lexicon: " + two_words);
        String str_twoWords = "";
        for (String str : two_words) {
            str_twoWords = str_twoWords + str + "\n";
        }
        writeToFile (str_twoWords, "keywordSpotting/" + judul_cerpen + "/two_words_availabe_inLexicon.txt");
        
        if (jumlah_twoWords > 0) {
//            System.out.println("masuk jumlah two words");
            int k=list_kata_after_stopword.size()-1;
            while (k >= 0 ) {
                String temp_1 = "";
                if (k > 0) {
                    temp_1 = list_kata_after_stopword.get(k-1);
                }
                String temp_2 = list_kata_after_stopword.get(k);
                String temp = temp_1 + " " + temp_2;
                if (two_words.contains(temp)) {
                    if (!temp_1.isEmpty()) {
                        for (int idx=0; idx<2; idx++) {
                            list_kata_after_stopword.remove(k);
                            k--;
                        }
                    } else {
                        list_kata_after_stopword.remove(k);
                        k--;
                    }
                } else {
                    k--;
                }
            }
        }
        for (String str : two_words) {
            list_kata_after_stopword.add(str);
        }
        System.out.println("listKata baru " + list_kata_after_stopword);
        String listKataBaru = "";
        for (String str : list_kata_after_stopword) {
            listKataBaru = listKataBaru + str + "\n";
        }
        writeToFile(listKataBaru, "keywordSpotting/" + judul_cerpen + "/listKata_after_stemming_new.txt");
        
//        int jumlah_oneWord = 0;
        ArrayList<String> arrayWord_available_inLexicon = new ArrayList<>();
        for (int j=list_kata_after_stopword.size()-1; j>=0; j--) {
//            System.out.println("kata ke j: " + listKata_after_stemming.get(j));
            if (ks.isAvailable(list_kata_after_stopword.get(j))) {
                boolean found = false;
                for (String str : arrayWord_available_inLexicon) {
                    if(str.equals(list_kata_after_stopword.get(j))) {
                        found = true;
                        break;
                    }
                }
//                if (!found) {
                    arrayWord_available_inLexicon.add(list_kata_after_stopword.get(j));
//                }
//                word_available_inLexicon = word_available_inLexicon + listKata_after_stemming.get(j) + "\n";
//                jumlah_oneWord++;
            }
        }
        
        System.out.println("jumlah one_word: " + arrayWord_available_inLexicon.size());
        
        System.out.println("word_available_inLexicon: \n");
        String word_available_inLexicon = "";
        for (String str : arrayWord_available_inLexicon) {
            System.out.println(str);
            word_available_inLexicon = word_available_inLexicon + str + "\n";
        }
        
        writeToFile(word_available_inLexicon, "keywordSpotting/" + judul_cerpen + "/word_available_inLexicon.txt");
        
        ks.setHasilNilaiEmosi(ks.getHasilEmosi(arrayWord_available_inLexicon));
        for (int nilai : ks.getHasilNilaiEmosi()) {
            System.out.println(nilai);
        }
    }
}
