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
        /*** SPLIT SENTENCE INTO WORDS ***/
//        IndonesianSentenceTokenizer tokenizer = new IndonesianSentenceTokenizer();
//        String str = "Selain itu, sore hari sudah jelas menjelang, dan sang surya tak mungkin untuk menyalahi kodratnya.\n" +
//"Pikiranku semu dan tak tentu arah.";
        String text_cerpen = readText("cerpen/hujanDalamGelap.txt");
        ArrayList<String> test = splitTextIntoWords(text_cerpen);
        System.out.println(test);
        System.out.println(test.get(1));
//        writeListToFile(test, "test_splittext.txt");
//
//        System.out.println(test);
        
        /*** SPLIT TEXT INTO SENTENCES ***/
//        IndonesianSentenceDetector isd = new IndonesianSentenceDetector();
//        ArrayList<String> list_sentences = isd.splitSentence(readText("cerpen/hujanDalamGelap.txt"));
//        for (String str : list_sentences) {
//            System.out.println(str);
//        }
        
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
//        ArrayList<String> judul_cerpen = new ArrayList<>();
//        judul_cerpen.add("hasil_knn_hujanDalamGelap.txt");
//        judul_cerpen.add("hasil_knn_keberuntunganRemi.txt");
//        judul_cerpen.add("hasil_knn_lemariRahasia.txt");
//        judul_cerpen.add("hasil_knn_priaBerjubahHitam.txt");
//        judul_cerpen.add("hasil_knn_janganMarahDongPutri.txt");
//        judul_cerpen.add("hasil_knn_pisauBerkarat.txt");
//        judul_cerpen.add("hasil_knn_peluangEmasBerharga.txt");
//        judul_cerpen.add("hasil_knn_kembalikanSenyumku.txt");
//        
//        String temp_emotion = "";
//        for (String title : judul_cerpen) {
//            ArrayList<String> hasil_prediksi = readTextPerLine("machine_learning/hasil_prediksi/KNN/" + title);
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
//        writeToFile(temp_emotion, "machine_learning/hasil_emosi_tiap_cerpen/KNN/hasil_emosi_knn.txt");
    }
}
