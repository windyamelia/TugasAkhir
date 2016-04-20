/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasakhir;

import IndonesianNLP.IndonesianSentenceTokenizer;
import IndonesianNLP.IndonesianStemmer;
import java.util.ArrayList;

/**
 *
 * @author Windy Amelia
 */
public class Stemming {
    public Stemming() {
        
    }
    
    public String stemWord(String word) {
        IndonesianStemmer stemmer = new IndonesianStemmer();
        return stemmer.stem(word);
    }
    
    public String stemSentences(String sentence) {
        IndonesianStemmer stemmer = new IndonesianStemmer();
        return stemmer.stemSentence(sentence);
    }
    
    public static void main(String[] args) {
        String str = "berkedip-kedip";
        Stemming stem = new Stemming();
        System.out.println(stem.stemWord(str));
        
        /*** TEST hapus two words di dalam list kata 1 word ***/
//        ArrayList<String> two_words = new ArrayList<>();
//        two_words.add("tari jemari");
//        two_words.add("jemari aku");
//        
//        ArrayList<String> listKata_after_stemming = new ArrayList<>();
//        listKata_after_stemming.add("rintik");
//        listKata_after_stemming.add("air");
//        listKata_after_stemming.add("tari");
//        listKata_after_stemming.add("jemari");
//        listKata_after_stemming.add("aku");
//        listKata_after_stemming.add("pandang");
//        listKata_after_stemming.add("langit");
//        listKata_after_stemming.add("santai");
//        
//        int k=listKata_after_stemming.size()-1;
//            while (k >= 0 ) {
//                String temp_1 = "";
//                if (k > 0) {
//                    temp_1 = listKata_after_stemming.get(k-1);
//                }
//                String temp_2 = listKata_after_stemming.get(k);
//                String temp = temp_1 + " " + temp_2;
//                if (two_words.contains(temp)) {
//                    if (!temp_1.isEmpty()) {
//                        for (int idx=0; idx<2; idx++) {
//                            listKata_after_stemming.remove(k);
//                            k--;
//                        }
//                    } else {
//                        listKata_after_stemming.remove(k);
//                        k--;
//                    }
//                } else {
//                    k--;
//                }
//            }
//            System.out.println(listKata_after_stemming);
    }
}
