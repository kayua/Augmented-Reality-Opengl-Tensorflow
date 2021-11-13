package com.tensorlearning.facemasks.Models;


import org.tensorflow.lite.Interpreter;

public class NeuralModels {


    private String faceTrackerFileModel;
    private String personalModelFileModel;
    private String spatialEstimationFileModel;
    private String identificationFacialFileModel;


    public Interpreter.Options faceTrackerSettings(){

        Interpreter.Options modelOptions = new Interpreter.Options();
        modelOptions.setNumThreads(2);
        modelOptions.setAllowBufferHandleOutput(true);
        modelOptions.setAllowFp16PrecisionForFp32(true);
        return modelOptions;

    }

    public Interpreter.Options personalModelSettings(){

        Interpreter.Options modelOptions = new Interpreter.Options();
        modelOptions.setNumThreads(2);
        modelOptions.setAllowBufferHandleOutput(true);
        modelOptions.setAllowFp16PrecisionForFp32(true);
        return modelOptions;

    }

    public Interpreter.Options spatialEstimationModelSettings(){

        Interpreter.Options modelsOptions = new Interpreter.Options();
        modelsOptions.setNumThreads(2);
        modelsOptions.setAllowBufferHandleOutput(true);
        modelsOptions.setAllowFp16PrecisionForFp32(true);
        return modelsOptions;

    }

    public Interpreter.Options identificationFacialModelSettings(){

        Interpreter.Options modelsOptions = new Interpreter.Options();
        modelsOptions.setNumThreads(2);
        modelsOptions.setAllowBufferHandleOutput(true);
        modelsOptions.setAllowFp16PrecisionForFp32(true);
        return modelsOptions;

    }


}



