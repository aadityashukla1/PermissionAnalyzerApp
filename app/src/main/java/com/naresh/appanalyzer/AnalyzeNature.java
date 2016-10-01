package com.naresh.appanalyzer;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Random;


import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;

public class AnalyzeNature {

    public String testResults;
    public String analyzeApp(String permissions, InputStream inputStream) throws IOException, Exception
    {
        Reader dataReader=null;
        //training data
        dataReader=new BufferedReader(new InputStreamReader(inputStream));
        Instances classifierTrainer=new Instances(dataReader);
        //to convert string attribute to numeric(naive bayes cannot accept string attributes)
        StringToWordVector filter=new StringToWordVector();
        filter.setInputFormat(classifierTrainer);
        Instances filteredTrainer=Filter.useFilter(classifierTrainer, filter);
        //to set the class index-class attribute will be put first by the StringToWordVector class
        filteredTrainer.setClassIndex(0);
        NaiveBayes classifier=new NaiveBayes();
        classifier.buildClassifier(filteredTrainer);
        //test data
        StringReader stringReader=new StringReader(permissions);
        Instances classifierTester=new Instances(stringReader);
        //to convert string attribute to numeric(naive bayes cannot accept string attributes)
        StringToWordVector filterTester=new StringToWordVector();
        filterTester.setInputFormat(classifierTester);
        Instances filteredTester=Filter.useFilter(classifierTester, filterTester);
        //to set the class index-class attribute will be put first by the StringToWordVector class
        filteredTester.setClassIndex(0);
        //evaluate the model
        Evaluation evaluate=new Evaluation(filteredTrainer);
        evaluate.evaluateModel(classifier,filteredTester);
        testResults=evaluate.toSummaryString();
        //System.out.println(testResults);
        //System.out.println(evaluate.toSummaryString("\n The test results\n", false));
        dataReader.close();
        stringReader.close();

        return testResults;
    }
}
