package com.example.activityrecognition;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void loadModel() {
        try{
            this.mClassifier = (Classifier) weka.core.SerializationHelper.read("/storage/emulated/0/bluetooth/Model right pocket.model");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String predict(final double[] input) {
        if(this.mClassifier == null) {
            loadModel();
        }
        dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes()-1);
        DenseInstance testInstance = new DenseInstance(dataUnpredicted.numAttributes()){
            {
                setValue(attributeAx, input[0]);
                setValue(attributeAy, input[1]);
                setValue(attributeAz, input[2]);
                setValue(attributeGx, input[3]);
                setValue(attributeGy, input[4]);
                setValue(attributeGz, input[5]);
                setValue(attributeLx, input[6]);
                setValue(attributeLy, input[7]);
                setValue(attributeLz, input[8]);
                setValue(attributeMx, input[9]);
                setValue(attributeMy, input[10]);
                setValue(attributeMz, input[11]);
            }
        };

        DenseInstance newInstace = testInstance;
        newInstace.setDataset(dataUnpredicted);
        String output = "";
        try{
            double result = mClassifier.classifyInstance(testInstance);
            output = classes.get(Double.valueOf(result).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
        return output;
    }
}
