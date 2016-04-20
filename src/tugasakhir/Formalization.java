/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasakhir;

import IndonesianNLP.IndonesianSentenceFormalization;
import java.io.IOException;
import static tugasakhir.TugasAkhir.*;


/**
 *
 * @author Windy Amelia
 */
public class Formalization {
    public Formalization() {
        
    }
    
    public String formalize(String word) {
        IndonesianSentenceFormalization formalizer = new IndonesianSentenceFormalization();
        return (formalizer.formalizeSentence(word));
    }
    
    public String formalizeSentence(String sentence) {
        IndonesianSentenceFormalization formalizer = new IndonesianSentenceFormalization();
        return (formalizer.formalizeSentence(sentence));
    }
    
    public static void main (String[] args) throws IOException {
        Formalization frm = new Formalization();
        System.out.println(frm.formalize("ngerjain"));
    }
}
