package com.example.activityrecognition;

import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class ModelLoader {
    private Classifier mClassifier;
    final Attribute attributeAx = new Attribute("Ax");
    final Attribute attributeAy = new Attribute("Ay");
    final Attribute attributeAz = new Attribute("Az");
    final Attribute attributeLx = new Attribute("Lx");
    final Attribute attributeLy = new Attribute("Ly");
    final Attribute attributeLz = new Attribute("Lz");
    final Attribute attributeGx = new Attribute("Gx");
    final Attribute attributeGy = new Attribute("Gy");
    final Attribute attributeGz = new Attribute("Gz");
    final Attribute attributeMx = new Attribute("Mx");
    final Attribute attributeMy = new Attribute("My");
    final Attribute attributeMz = new Attribute("Mz");
    final List<String> classes = new ArrayList<String>(){
        {
            add("walking");
            add("standing");
            add("jogging");
            add("sitting");
            add("biking");
            add("upstairs");
            add("downstairs");
        }
    };
    ArrayList<Attribute> attributesList = new ArrayList<Attribute>(2){
        {
            add(attributeAx);
            add(attributeAy);
            add(attributeAz);
            add(attributeGx);
            add(attributeGy);
            add(attributeGz);
            add(attributeLx);
            add(attributeLy);
            add(attributeLz);
            add(attributeMx);
            add(attributeMy);
            add(attributeMz);
            Attribute attributeClass = new Attribute("@@class@@", classes);
            add(attributeClass);
        }
    };
    Instances dataUnpredicted = new Instances("TestInstances", attributesList, 1);

    public ModelLoader() {

    }

    public void loadModel(){
        try{
            this.mClassifier = (Classifier) weka.core.SerializationHelper.read("");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public String predict(){
        if(mClassifier == null) {
            loadModel();
        }

        dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes()-1);
        DenseInstance testInstance = new DenseInstance(dataUnpredicted.numAttributes()){
            {
                setValue(attributeAx, 0);
                setValue(attributeAy, 0);
                setValue(attributeAz, 0);
                setValue(attributeGx, 0);
                setValue(attributeGy, 0);
                setValue(attributeGz, 0);
                setValue(attributeLx, 0);
                setValue(attributeLy, 0);
                setValue(attributeLz, 0);
                setValue(attributeMx, 0);
                setValue(attributeMy, 0);
                setValue(attributeMz, 0);
            }
        };

        DenseInstance newInstace = testInstance;
        newInstace.setDataset(dataUnpredicted);
        String output = "";
        try{
            double result = mClassifier.classifyInstance(testInstance);
            output = "Index: " + result + "\nClass:" + classes.get(Double.valueOf(result).intValue());
            System.out.println(output);
        }catch (Exception e){
            e.printStackTrace();
        }




        return output;
    }
}
