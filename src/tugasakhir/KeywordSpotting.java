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
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
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
    
    // menghasilkan nilai emosi tertentu daru key kata tertentu
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
    public int[] getHasilEmosi(ArrayList<String> words, String judul_cerpen) throws IOException{
        int[] nilaiEmosi = new int[6];
        ArrayList<String> kata_marah = new ArrayList<>();
        ArrayList<String> kata_jijik = new ArrayList<>();
        ArrayList<String> kata_takut = new ArrayList<>();
        ArrayList<String> kata_senang = new ArrayList<>();
        ArrayList<String> kata_sedih = new ArrayList<>();
        ArrayList<String> kata_kaget = new ArrayList<>();        
        
        for (String word : words) {
            if (isAvailable(word)) {
                ArrayList<Integer> hmKS = (ArrayList<Integer>) (hmKeywordSpotting.get(word));
                for (int i=0; i<hmKS.size(); i++) {
                    nilaiEmosi[i] = nilaiEmosi[i] + hmKS.get(i);
                    if (i == 0) {
                        if (hmKS.get(i) == 1) {
                            kata_marah.add(word);
                        }
                    } else if (i == 1) {
                        if (hmKS.get(i) == 1) {
                            kata_jijik.add(word);
                        }
                    } else if (i == 2) {
                        if (hmKS.get(i) == 1) {
                            kata_takut.add(word);
                        }
                    } else if (i == 3) {
                        if (hmKS.get(i) == 1) {
                            kata_senang.add(word);
                        }
                    } else if (i == 4) {
                        if (hmKS.get(i) == 1) {
                            kata_sedih.add(word);
                        }
                    } else if (i == 5) {
                        if (hmKS.get(i) == 1) {
                            kata_kaget.add(word);
                        }
                    }
                }
            }
        }
        
        String temp = "";
        temp = temp + "/*** kata marah ***/" + "\n";
        for (String str : kata_marah) {
            temp = temp + str + "\n";
        }
        System.out.println("");
        
        temp = temp + "/*** kata jijik ***/" + "\n";
        for (String str : kata_jijik) {
            temp = temp + str + "\n";
        }
        System.out.println("");
        
        temp = temp + "/*** kata takut ***/" + "\n";
        for (String str : kata_takut) {
            temp = temp + str + "\n";
        }
        System.out.println("");
        
        temp = temp + "/*** kata senang ***/" + "\n";
        for (String str : kata_senang) {
            temp = temp + str + "\n";
        }        
        System.out.println("");
        
        temp = temp + "/*** kata sedih ***/" + "\n";
        for (String str : kata_sedih) {
            temp = temp + str + "\n";
        }
        System.out.println("");
        
        temp = temp + "/*** kata kaget ***/" + "\n";
        for (String str : kata_kaget) {
            temp = temp + str + "\n";
        }
        
        writeToFile(temp, "keywordSpotting/" + judul_cerpen + "/kata_per_emosi.txt");
        
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
        System.out.print("jenis lexicon: ");
        String input = scanner.nextLine();
        
        ArrayList<String> judul_cerpen = new ArrayList<>();
//        judul_cerpen.add("diTempatPembuanganAkhir");
//        judul_cerpen.add("suaraAneh");
//        judul_cerpen.add("menembusLangit");
//        judul_cerpen.add("liburanBersamaSahabat");
//        judul_cerpen.add("ballerina");
//        judul_cerpen.add("pohonBesar");
//        judul_cerpen.add("mataSebeningKristal");
        judul_cerpen.add("hujanDalamGelap");
        judul_cerpen.add("keberuntunganRemi");
        judul_cerpen.add("lemariRahasia");
        judul_cerpen.add("priaBerjubahHitam");
        judul_cerpen.add("janganMarahDongPutri");
        judul_cerpen.add("pisauBerkarat");
        judul_cerpen.add("peluangEmasBerharga");
        judul_cerpen.add("kembalikanSenyumku");
        
        String temp_hasilEmosi = "";
        for (String judulCerpen : judul_cerpen) {
            String text_cerpen = readText("cerpen/" + judulCerpen + ".txt");
            ArrayList<String> listKata_cerpen = splitTextIntoWords(text_cerpen);
            writeListToFile(listKata_cerpen, "keywordSpotting/" + judulCerpen + "/listKata_cerpen.txt");

            // remove punctuation
            StopWords stopword = new StopWords();
            stopword.setWordList(listKata_cerpen);
            stopword.setPunctuation(stopword.readPunctuation("StopWords/punctuation_ks.txt"));
            stopword.removePunctuation();
            listKata_cerpen = stopword.getWordList();
            String kata_cerpen = "";
            for (String str : listKata_cerpen) {
                kata_cerpen = kata_cerpen + str + "\n";
            }
            writeToFile(kata_cerpen, "keywordSpotting/" + judulCerpen + "/listKata_cerpen.txt");

            /*** FORMALISASI ***/
            ArrayList<String> listKata_after_formalization = new ArrayList<>();
            Formalization formalisasi = new Formalization();
            for (String str : listKata_cerpen) {
                if (!str.contains("-") && !str.contains("-")) {
                    listKata_after_formalization.add(formalisasi.formalize(str).trim());
                } else {
                    listKata_after_formalization.add(str.trim());
                }
            }
            writeListToFile(listKata_after_formalization, "keywordSpotting/" + judulCerpen + "/listKata_after_formalization.txt");
            System.out.println("size list kata formalisasi: " + listKata_after_formalization.size());
            for (String str : listKata_after_formalization) {
                System.out.println("kata form: " + str);
            }

            String kata_setelah_akan = "";
            for (int i=listKata_after_formalization.size()-1; i>=0; i--) {
                if ((listKata_after_formalization.get(i).equals("tidak")) || (listKata_cerpen.get(i).equals("tak"))) {
                    if (listKata_after_formalization.get(i+1).equals("akan")) {
                        kata_setelah_akan = kata_setelah_akan + listKata_after_formalization.remove(i+1) + "\n"; 
                    }
                }
            }
            writeToFile(kata_setelah_akan, "keywordSpotting/" + judulCerpen + "/kata_setelah_akan.txt");

            KeywordSpotting ks_antonim1 = new KeywordSpotting();
            ks_antonim1.setHashMap(ks_antonim1.readtAntonimToHashMap("keywordSpotting/antonim/result_antonim.txt"));

            KeywordSpotting ks_antonim2 = new KeywordSpotting();
            ks_antonim2.setHashMap(ks_antonim2.readtAntonimToHashMap("keywordSpotting/antonim/result_antonim_reverse.txt"));
            String afterNot_available_inAntonym = "";
            String afterNot_notAvailable_inAntonym = "";
            for (int i=listKata_after_formalization.size()-1; i>=0; i--) {
                if (listKata_after_formalization.get(i).equals("tidak") || listKata_cerpen.get(i).equals("tak")) {
                    String kata_setelah_tidak = listKata_after_formalization.get(i+1);
                    System.out.println("kata setelah tidak/tak: " + kata_setelah_tidak);
                    if (ks_antonim1.isAvailable(kata_setelah_tidak)) {
                        listKata_after_formalization.set(i+1, ks_antonim1.getHashMap().get(kata_setelah_tidak).toString());
                        listKata_after_formalization.remove(i);
                        afterNot_available_inAntonym = afterNot_available_inAntonym +  ks_antonim1.getHashMap().get(kata_setelah_tidak).toString() + "\n";
                    } else if (ks_antonim2.isAvailable(kata_setelah_tidak)) {
                        listKata_after_formalization.set(i+1, ks_antonim2.getHashMap().get(kata_setelah_tidak).toString());
                        listKata_after_formalization.remove(i);
                        afterNot_available_inAntonym = afterNot_available_inAntonym + ks_antonim2.getHashMap().get(kata_setelah_tidak).toString() + "\n";
                    } else {
                        for (int k=0; k<2; k++) {
                            afterNot_notAvailable_inAntonym = afterNot_notAvailable_inAntonym + listKata_after_formalization.remove(i) + "\n";
                        }
                    }
                }
            writeToFile(afterNot_available_inAntonym, "keywordSpotting/" + judulCerpen + "/afterNot_available_inAntonym.txt");
            writeToFile(afterNot_notAvailable_inAntonym, "keywordSpotting/" + judulCerpen + "/afterNot_notAvailable_inAntonym.txt");

            String listKata_cerpen_new = "";
            for (String str : listKata_after_formalization) {
                listKata_cerpen_new = listKata_cerpen_new + str + "\n";
            }
            System.out.println("listKata cerpen baru: " + listKata_cerpen_new);

             /*** remove stop word ***/
    //        StopWords stopword = new StopWords();
            stopword.setWordList(listKata_after_formalization);
            stopword.setStopWords(stopword.readStopWords("StopWords/stopwords_id_ks.txt"));
            stopword.setPunctuation(stopword.readPunctuation("StopWords/punctuation_ks.txt"));

            ArrayList<String> list_kata_after_stopword = stopword.removeStopWords();
            System.out.println("listkata_after_stopword: ");
            for (String str : list_kata_after_stopword) {
                System.out.println(str.toLowerCase());
            }
            String file = "keywordSpotting/" + judulCerpen + "/listKata_after_stopword.txt";
            writeListToFile(list_kata_after_stopword, file);
    //        ArrayList<String> words_noNeed_stemming = readTextPerLine("keywordSpotting/no_need_stemming.txt");
            ArrayList<String> list_kataBerulang = readTextPerLine("keywordSpotting/kata_berulang.txt");
            KeywordSpotting ks = new KeywordSpotting();
            ks.setHashMap(ks.readTextToHashmap("keywordSpotting/result_lexicon_" + input + ".txt"));
            /** new: stemming kata mengandung "-" yang berarti jamak, ex: teman-teman **/
            ArrayList<String> listKata_after_stemming = new ArrayList<>();
            Stemming stemmer = new Stemming();
            PrintWriter pw_stem = new PrintWriter("keywordSpotting/" + judulCerpen + "/kataYangDiStem.txt");
            PrintWriter pw_strip = new PrintWriter("keywordSpotting/" + judulCerpen + "/temp_setelah_replace_strip.txt");
            PrintWriter pw_kata_ku = new PrintWriter("keywordSpotting/" + judulCerpen + "/kataDiSplit_ku.txt");
            pw_stem.close();
            pw_strip.close();
            pw_kata_ku.close();
            ArrayList<String> keys_hashMap = new ArrayList<>(ks.getHashMap().keySet());
            for (String str : list_kata_after_stopword) {
                String temp = "";
                if (!isAvailableWordInList(str, list_kataBerulang) && !isAvailableWordInList(str, keys_hashMap) && str.contains("-")) {
                    temp = stemmer.stemWord(str);
                    System.out.println("kata yg distem: " + temp);
                    writeToAppend(temp + "\n", "keywordSpotting/" + judulCerpen + "/kataYangDiStem.txt");
                    while (temp.contains("-")) {
                        temp = temp.replaceAll(".*-", "");
                        System.out.println("temp setelah direplace: " + temp);
                        writeToAppend(temp + "\n", "keywordSpotting/" + judulCerpen + "/temp_setelah_replace_strip.txt");
                    }
                } else {
                    temp = str;
                }
    //            listKata_after_stemming.add(temp);
                if (temp.matches(".*ku")) {
                    String[] kata_ku = temp.split("ku");
                    if(kata_ku.length != 0) {
                        listKata_after_stemming.add(kata_ku[0]);
                        System.out.println("kata yg displit ku: " + temp);
                        writeToAppend(temp + "\n", "keywordSpotting/" + judulCerpen + "/kataDiSplit_ku.txt");
                    }
                } else {
                    listKata_after_stemming.add(temp);
                }
            }
            for (String str : listKata_after_stemming) {
    //            System.out.println("tes kata after stemming: " + str);
            }
            writeListToFile(listKata_after_stemming, "keywordSpotting/" + judulCerpen + "/listKata_after_stemming.txt");

            System.out.println("hashmap: \n");
    //        ks.printHashMap();
            System.out.println("size hm: " + ks.getHashMap().size());

            /*** SPLIT TEXT INTO THREE WORDS ***/
            ArrayList<String> three_words = new ArrayList<>();
            int idx_threewords=0;
            while (idx_threewords < listKata_after_stemming.size()-2) {
                String temp_1 = listKata_after_stemming.get(idx_threewords);
                String temp_2 = listKata_after_stemming.get(idx_threewords+1);
                String temp_3 = listKata_after_stemming.get(idx_threewords+2);;
    //            if (i <= listKata_after_stemming.size()-1) {
    //                temp_2 = listKata_after_stemming.get(i+1);
    //            }
                String temp = temp_1 + " " + temp_2 + " " + temp_3;
                three_words.add(temp);
                idx_threewords++;
            }
            System.out.println("three_words: " + three_words);

            /*** ambil 3 words yang ada di lexicon, buang kalo ga ada ***/
            int jumlah_threeWords = 0;
            for (int j=three_words.size()-1; j>=0; j--) {
                if (ks.isAvailable(three_words.get(j))) {
                    jumlah_threeWords++;
    //                System.out.println(jumlah_twoWords);
                } else {
                    three_words.remove(j);
                }
            }
            System.out.println("jumlah three words final: " + jumlah_threeWords);
            System.out.println("isi three_words yang ada di lexicon: " + three_words);
            String str_threeWords = "";
            for (String str : three_words) {
                str_threeWords = str_threeWords + str + "\n";
            }

            if (jumlah_threeWords > 0) {
                int k=listKata_after_stemming.size()-1;
                while (k >= 2 ) {
                    String temp_1 = listKata_after_stemming.get(k-2);
                    String temp_2 = listKata_after_stemming.get(k-1);
                    String temp_3 = listKata_after_stemming.get(k);
                    String temp = temp_1 + " " + temp_2 + " " + temp_3;
                    if (isAvailableWordInList(temp, three_words)) {
                        for (int idx=0; idx<3; idx++) {
                            listKata_after_stemming.remove(k);
                            k--;
                        }
                    } else {
                        k--;
                    }
                }
            }

            /*** SPLIT TEXT INTO TWO WORDS ***/        
            ArrayList<String> two_words = new ArrayList<>();
            int i=0;
            while (i < listKata_after_stemming.size()-1) {
                String temp_1 = listKata_after_stemming.get(i);
                String temp_2 = "";
                if (i <= listKata_after_stemming.size()-1) {
                    temp_2 = listKata_after_stemming.get(i+1);
                }
                String temp = temp_1 + " " + temp_2;
                two_words.add(temp);
                i++;
            }
    //        for (String str : two_words) {
    //            System.out.println(str);
    //        }        

            /** ambil jumlah two words yang ada di lexicon, buang yang ga ada **/
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
            writeToFile (str_twoWords, "keywordSpotting/" + judulCerpen + "/two_words_availabe_inLexicon_" + input + ".txt");

            /** hapus kata-kata yang ada di two_words_available_inLexicon di list 1 kata **/
            if (jumlah_twoWords > 0) {
    //            System.out.println("masuk jumlah two words");
                int k=listKata_after_stemming.size()-1;
                while (k >= 1 ) {
                    String temp_1 = listKata_after_stemming.get(k-1);
                    String temp_2 = listKata_after_stemming.get(k);
                    String temp = temp_1 + " " + temp_2;
                    if (isAvailableWordInList(temp, two_words)) {
                        for (int idx=0; idx<3; idx++) {
                            listKata_after_stemming.remove(k);
                            k--;
                        }
                    } else {
                        k--;
                    }
                }
            }
            for (String str : two_words) {
                listKata_after_stemming.add(str);
            }
            System.out.println("listKata baru " + listKata_after_stemming);
            String listKataBaru = "";
            for (String str : listKata_after_stemming) {
                listKataBaru = listKataBaru + str + "\n";
            }
            writeToFile(listKataBaru, "keywordSpotting/" + judulCerpen + "/listKata_after_stemming_new.txt");

    //        int jumlah_oneWord = 0;
            ArrayList<String> arrayWord_available_inLexicon = new ArrayList<>();
            for (int j=listKata_after_stemming.size()-1; j>=0; j--) {
    //            System.out.println("kata ke j: " + listKata_after_stemming.get(j));
                if (ks.isAvailable(listKata_after_stemming.get(j))) {
                      arrayWord_available_inLexicon.add(listKata_after_stemming.get(j));
                }
            }

            System.out.println("jumlah one_word: " + arrayWord_available_inLexicon.size());

            System.out.println("word_available_inLexicon: \n");
            String word_available_inLexicon = "";
            for (String str : arrayWord_available_inLexicon) {
                System.out.println(str + " " + ks.getArrayNilaiKata(str));
                word_available_inLexicon = word_available_inLexicon + str + " " + ks.getArrayNilaiKata(str) + "\n";
            }

            writeToFile(word_available_inLexicon, "keywordSpotting/" + judulCerpen + "/word_available_inLexicon_" + input + ".txt");

            ks.setHasilNilaiEmosi(ks.getHasilEmosi(arrayWord_available_inLexicon, judulCerpen));
            
            temp_hasilEmosi = temp_hasilEmosi + "/*** " + judulCerpen + " ***/" + "\n";
            for (int nilai : ks.getHasilNilaiEmosi()) {
                System.out.println(nilai);
                temp_hasilEmosi = temp_hasilEmosi + String.valueOf(nilai) + "\n";
            }
            writeToFile(temp_hasilEmosi, "keywordSpotting/" + judulCerpen + "/hasil_emosi_" + input + ".txt");
            temp_hasilEmosi = temp_hasilEmosi + "\n";
        }
        
        writeToFile(temp_hasilEmosi, "keywordSpotting/hasilGabungan/hasil_emosi_partOf_dataTraining_" + input + ".txt");
    }
}
