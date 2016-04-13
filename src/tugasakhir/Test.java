/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasakhir;

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
        IndonesianSentenceTokenizer tokenizer = new IndonesianSentenceTokenizer();
        String str = "Selain itu, sore hari sudah jelas menjelang, dan sang surya tak mungkin untuk menyalahi kodratnya.\n" +
"Pikiranku semu dan tak tentu arah.";
        ArrayList<String> test = tokenizer.tokenizeSentenceWithCompositeWords(str);
        writeListToFile(test, "test_splittext.txt");

        System.out.println(test);
    }
}
