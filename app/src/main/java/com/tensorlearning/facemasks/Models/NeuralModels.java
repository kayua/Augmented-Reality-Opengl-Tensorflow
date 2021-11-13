package com.tensorlearning.facemasks.Models;


import android.content.Context;

import com.tensorlearning.facemasks.Buffering.ByteBufferModel;
import com.tensorlearning.facemasks.SettingsFaceTracker.FaceTrackerSettings;
import com.tensorlearning.facemasks.SettingsIdentificationFacialPoints.FacialPointsSettings;
import com.tensorlearning.facemasks.SettingsPersonalModels.PersonalModelsSettings;
import com.tensorlearning.facemasks.SettingsSpatialEstimation.SpatialEstimationSettings;

import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NeuralModels {


    private String faceTrackerFileModel;
    private String personalModelFileModel;
    private String spatialEstimationFileModel;
    private String identificationFacialFileModel;

    private Interpreter interpreterFaceTracker = null;
    private Interpreter interpreterPersonalModel = null;
    private Interpreter interpreterSpatialEstimation = null;
    private Interpreter interpreterIdentificationFacialPoints = null;


    private FaceTrackerSettings faceTrackerSettings = new FaceTrackerSettings();
    private FacialPointsSettings facialPointsSettings = new FacialPointsSettings();
    private PersonalModelsSettings personalModelSettings = new PersonalModelsSettings();
    private SpatialEstimationSettings spatialEstimationSettings = new SpatialEstimationSettings();




    private Context context;

    private ByteBufferModel byteBuffer = new ByteBufferModel(context);



    public void createInterpreter(){

        try {

            interpreterFaceTracker = new Interpreter(byteBuffer.FaceTrackerLoadModelFile(),byteBuffer. setConfig());

            interpreterPersonalModel = new Interpreter(byteBuffer.PersonalModelLoadModelFile(), setConfig());

            interpreterSpatialEstimation = new Interpreter(byteBuffer.SpatialEstimationLoadModelFile(), setConfig());

            interpreterIdentificationFacialPoints = new Interpreter(byteBuffer.IdentificationFacialPointsLoadModelFile(), setConfig());


            interpreterFaceTracker.allocateTensors();
            interpreterPersonalModel.allocateTensors();
            interpreterSpatialEstimation.allocateTensors();
            interpreterIdentificationFacialPoints.allocateTensors();


            imgData = ByteBuffer.allocateDirect(size_image_x * size_image_y*4);
            imgData.order(ByteOrder.nativeOrder());

        } catch (IOException e) {
            e.printStackTrace();
        }




    }



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



