/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasakhir;

import IndonesianNLP.IndonesianSentenceDetector;
import IndonesianNLP.IndonesianSentenceTokenizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static tugasakhir.TugasAkhir.*;

/**
 *
 * @author Windy Amelia
 */
public class Test {
    public Test() {
        
    }
    
    public void matchText (String text) {
        Pattern pattern = Pattern.compile("[A-Za-z].*?([^\\w\\s]|(?=\\n)|$)", Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher match = pattern.matcher(text);
        while (match.find()) {
            System.out.println(match.group());
        }
    }
    
    public static void main(String[] args) throws IOException {
        /** tes split **/
//        String str = "berkumpul";
//        String[] list = str.split("ku");
//        for (String s : list) {
//            System.out.println(s);
//        }
        
        /** remove two_words **/
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
        
        /*** tes remove three words ***/
//        ArrayList<String> listKata_after_stemming = new ArrayList<>();
//        listKata_after_stemming.add("aku");
//        listKata_after_stemming.add("belajar");
//        listKata_after_stemming.add("di");
//        listKata_after_stemming.add("rumah");
//        listKata_after_stemming.add("sekolah");
//        listKata_after_stemming.add("kuliah");
//        listKata_after_stemming.add("orang");
//        listKata_after_stemming.add("laptop");
//        listKata_after_stemming.add("tas");
//        
//        ArrayList<String> three_words = new ArrayList<>();
//        three_words.add("aku belajar di");
//        int k=listKata_after_stemming.size()-1;
//            while (k >= 2 ) {
//                String temp_1 = listKata_after_stemming.get(k-2);
//                String temp_2 = listKata_after_stemming.get(k-1);
//                String temp_3 = listKata_after_stemming.get(k);
//                String temp = temp_1 + " " + temp_2 + " " + temp_3;
//                if (isAvailableWordInList(temp, three_words)) {
//                    for (int idx=0; idx<3; idx++) {
//                        listKata_after_stemming.remove(k);
//                        k--;
//                    }
//                } else {
//                    k--;
//                }
//            }
//            System.out.println("hasil list: " + listKata_after_stemming);
        
        /** list kata lexicon "-" **/
//        KeywordSpotting ks = new KeywordSpotting();
//        ks.setHashMap(ks.readTextToHashmap("keywordSpotting/result_lexicon_noStemming.txt"));
//        Set set = ks.getHashMap().entrySet();
//        Iterator i = set.iterator();
//        
//        int sum =0;
//        String temp = "";
//        while(i.hasNext()) {
//            Map.Entry me = (Map.Entry)i.next();
//            if (me.getKey().toString().contains("-")) {
//                temp = temp + me.getKey().toString() + "\n";
//            }
//        }
//        writeToFile(temp, "keywordSpotting/lexicon_karakter-strip.txt");
        
        /** test replace all **/
//        String str = "a-apa";
//        System.out.println(str.replaceAll(".*-", ""));
        
        /*** SPLIT SENTENCE INTO WORDS ***/
//        IndonesianSentenceTokenizer tokenizer = new IndonesianSentenceTokenizer();
//        String str = "Selain itu, sore hari sudah jelas menjelang, dan sang surya tak mungkin untuk menyalahi kodratnya.\n" +
//"Pikiranku semu dan tak tentu arah.";
//        String text_cerpen = readText("cerpen/hujanDalamGelap.txt");
//        ArrayList<String> test = splitTextIntoWords(text_cerpen);
//        System.out.println(test);
//        System.out.println(test.get(1));
//        writeListToFile(test, "test_splittext.txt");
//
//        System.out.println(test);
        
        /*** SPLIT TEXT INTO SENTENCES INANLP ***/
//        IndonesianSentenceDetector isd = new IndonesianSentenceDetector();
//        ArrayList<String> list_sentences = isd.splitSentence(readText("cerpen/priaBerjubahHitam.txt"));
//        for (String str : list_sentences) {
//            System.out.println(str);
//        }
        
        /*** SPLIT TEXT INTO SENTENCES DARI INTERNET ***/
        ArrayList<String> list_sentences = splitTextIntoSentences(readText("cerpen/priaBerjubahHitam.txt"));
        System.out.println("length list sentences: " + list_sentences.size());
        for (String str : list_sentences) {
//            System.out.println("tes");
//            System.out.println("masuk: " + str);
        }
        
        /*** COBA TOKENIZER BU AYU KE TEXT CERPEN (HASIL: LIST KATA) ***/
//        IndonesianSentenceTokenizer tokenizer = new IndonesianSentenceTokenizer();
//        String text_cerpen = readText("cerpen/hujanDalamGelap.txt");
//        ArrayList<String> list_words = tokenizer.tokenizeSentence(text_cerpen);
//        
//        StopWords sw = new StopWords();
//        sw.setWordList(list_words);
//        sw.setPunctuation(sw.readPunctuation("StopWords/punctuation_ks.txt"));
//        sw.removePunctuation();
//        writeListToFile(sw.getWordList(), "keywordSpotting/hujanDalamGelap/listKata_cerpen_tokenizer.txt");
        
        /** NULIS HASIL EMOSI SEMUA CERPEN DARI METODE ML **/
//        Scanner scanner = new Scanner (System.in);
//        System.out.print("algoritma: ");
//        String algoritma = scanner.nextLine();
//        ArrayList<String> judul_cerpen = new ArrayList<>();
//        judul_cerpen.add("hasil_" + algoritma + "_hujanDalamGelap.txt");
//        judul_cerpen.add("hasil_" + algoritma + "_keberuntunganRemi.txt");
//        judul_cerpen.add("hasil_" + algoritma + "_lemariRahasia.txt");
//        judul_cerpen.add("hasil_" + algoritma + "_priaBerjubahHitam.txt");
//        judul_cerpen.add("hasil_" + algoritma + "_janganMarahDongPutri.txt");
//        judul_cerpen.add("hasil_" + algoritma + "_pisauBerkarat.txt");
//        judul_cerpen.add("hasil_" + algoritma + "_peluangEmasBerharga.txt");
//        judul_cerpen.add("hasil_" + algoritma + "_kembalikanSenyumku.txt");
//        
//        String temp_emotion = "";
//        for (String title : judul_cerpen) {
//            ArrayList<String> hasil_prediksi = readTextPerLine("machine_learning/hasil_prediksi/Naive_Bayes_Multinomial/revisi_tidak/" + title);
//            int[] nilai_emosi = new int[7];
//            for (int n : nilai_emosi) {
//                n = 0;
//            }
//            for (String str : hasil_prediksi) {
//                if (str.equals("marah")) {
//                    nilai_emosi[0]++;
//                } else if (str.equals("jijik")) {
//                    nilai_emosi[1]++;
//                } else if (str.equals("takut")) {
//                    nilai_emosi[2]++;
//                } else if (str.equals("senang")) {
//                    nilai_emosi[3]++;
//                } else if (str.equals("sedih")) {
//                    nilai_emosi[4]++;
//                } else if (str.equals("kaget"))  {
//                    nilai_emosi[5]++;
//                } else if (str.equals("netral"))  {
//                    nilai_emosi[6]++;
//                } else {
//                    System.out.println("ada typo");
//                    break;
//                }
//            }
//            
//            temp_emotion = temp_emotion + "/*** " + title + " ***/" + "\n";
//            for (int n : nilai_emosi) {
//                System.out.println("nilai emosi: " + n);
//                temp_emotion = temp_emotion + "jumlah emosi: " + n + "\n";
//            }
//            temp_emotion = temp_emotion + "\n";
//        }
//        writeToFile(temp_emotion, "machine_learning/hasil_emosi_tiap_cerpen/Naive_Bayes_Multinomial/revisi_tidak/hasil_emosi_" + algoritma + ".txt");
    }
}
