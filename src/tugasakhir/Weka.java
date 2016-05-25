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
import static tugasakhir.TugasAkhir.*;
import weka.classifiers.Classifier;
import weka.classifiers.misc.SerializedClassifier;
import weka.core.Instances;

/**
 *
 * @author Windy Amelia
 */
public class Weka {
    private Classifier m_classifier;
    
    
    public Weka() {
        
    }
    
    public void loadModel(String fileName) {
        SerializedClassifier classifier = new SerializedClassifier();
        classifier.setModelFile(new File(fileName));
        
        m_classifier = classifier.getCurrentModel();
    }
    
    public void classifyInstance(String fileInput, String fileOutput) throws FileNotFoundException, IOException, Exception {
        Instances unlabeled = new Instances(new BufferedReader(new FileReader(fileInput)));
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
        
        // Create a copy
        Instances labeled = new Instances(unlabeled);
        
        String temp = "";
        for(int i=0; i<unlabeled.numInstances(); i++) {
            double clsLabel = m_classifier.classifyInstance(unlabeled.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
            if (clsLabel == 0.0) {
                temp = temp + "marah";
            } else if (clsLabel == 1.0) {
                temp = temp + "jijik";
            } else if (clsLabel == 2.0) {
                temp = temp + "takut";
            } else if (clsLabel == 3.0) {
                temp = temp + "senang";
            } else if (clsLabel == 4.0) {
                temp = temp + "sedih";
            } else if (clsLabel == 5.0) {
                temp = temp + "kaget";
            } else if (clsLabel == 6.0) {
                temp = temp + "netral";
            }
            temp = temp + "\n";
        }
        writeToFile(temp, fileOutput);
    }
    
    public static void main(String[] args) throws Exception {
        Weka weka = new Weka();
        weka.loadModel("machine_learning/model/revisi_tidak/Resample_555%/logistic.model");
        weka.classifyInstance("data_test/revisi_tidak/data_test_hujanDalamGelap.arff", "machine_learning/hasil_prediksi/hasil_weka_java.txt");
    }
}
