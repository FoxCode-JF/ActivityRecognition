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
                setValue(attributeAx, standardize(input[0], 1.191, 4.143));
                setValue(attributeAy, standardize(input[1], -7.598, 5.479));
                setValue(attributeAz, standardize(input[2], -2.813, 4.198));
                setValue(attributeGx, standardize(input[3], 0, 1.406));
                setValue(attributeGy, standardize(input[4], 0.045, 1.254));
                setValue(attributeGz, standardize(input[5], -0.025, 0.962));
                setValue(attributeLx, standardize(input[6], 0.048, 2.927));
                setValue(attributeLy, standardize(input[7], -0.248, 4.081));
                setValue(attributeLz, standardize(input[8], 0.059, 2.896);
                setValue(attributeMx, standardize(input[9], -6.32, 21.482));
                setValue(attributeMy, standardize(input[20], 30.169, 19.561));
                setValue(attributeMz, standardize(input[11], 6.811, 20.643));
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
    private double standardize(double dataPoint, double mean, double stdDev)
    {
        return (dataPoint - mean) / stdDev;
    }
}
