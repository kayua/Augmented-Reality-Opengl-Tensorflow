package com.tensorlearning.facemasks.Models;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import com.tensorlearning.facemasks.Buffering.MemoryAllocationBuffer;
import com.tensorlearning.facemasks.Estimator.EstimatorModel;
import com.tensorlearning.facemasks.Settings.EstimatorSettings.EstimatorSettings;
import com.tensorlearning.facemasks.Settings.SettingsFaceTracker.FaceTrackerSettings;
import com.tensorlearning.facemasks.Settings.SettingsIdentificationFacialPoints.FacialPointsSettings;
import com.tensorlearning.facemasks.Settings.SettingsPersonalModels.PersonalModelsSettings;
import com.tensorlearning.facemasks.Settings.SettingsSpatialEstimation.SpatialEstimationSettings;

import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;

public class NeuralModel {

    private Interpreter interpreterFaceTracker = null;
    private Interpreter interpreterPersonalModel = null;
    private Interpreter interpreterSpatialEstimation = null;
    private Interpreter interpreterIdentificationFacialPoints = null;

    private final FaceTrackerSettings faceTrackerSettings = new FaceTrackerSettings();
    private final FacialPointsSettings facialPointsSettings = new FacialPointsSettings();
    private final PersonalModelsSettings personalModelSettings = new PersonalModelsSettings();
    private final SpatialEstimationSettings spatialEstimationSettings = new SpatialEstimationSettings();
    private final EstimatorSettings estimatorSettings = new EstimatorSettings();

    private final MemoryAllocationBuffer byteBuffer;
    private final EstimatorModel estimatorModel;
    private boolean estimationCycle =true;

    private Camera.Parameters faceTrackerCameraParameters = null;
    private Camera.Parameters personalModelsCameraParameters = null;
    private Camera.Parameters spatialEstimationCameraParameters = null;
    private Camera.Parameters identificationFacialPointsCameraParameters = null;

    private YuvImage faceTrackerCompressionYuvImage;
    private YuvImage personalModelsCompressionYuvImage;
    private YuvImage spatialEstimationCompressionYuvImage;
    private YuvImage identificationFacialPointsCompressionYuvImage;

    private byte[] faceTrackerImageByte;
    private byte[] personalModelsImageByte;
    private byte[] spatialEstimationImageByte;
    private byte[] identificationFacialPointsImageByte;

    private Bitmap faceTrackerBitmapImage;
    private Bitmap personalModelBitmapImage;
    private Bitmap spatialEstimationBitmapImage;
    private Bitmap identificationFacialPointsBitmapImage;


    public NeuralModel(Context context) {

        this.byteBuffer = new MemoryAllocationBuffer(context);
        this.estimatorModel = new EstimatorModel();

    }

    public void createByteBufferModel(){

        byteBuffer.setFaceTrackerSizeImageHeight(faceTrackerSettings.getFaceTrackerSizeImageHeight());
        byteBuffer.setFaceTrackerSizeImageWidth(faceTrackerSettings.getFaceTrackerSizeImageWidth());
        byteBuffer.setFaceTrackerCoordinateMeanImage(faceTrackerSettings.getFaceTrackerCoordinateMeanImage());
        byteBuffer.setFaceTrackerCoordinateMeanStandard(faceTrackerSettings.getFaceTrackerCoordinateMeanStandard());
        byteBuffer.setFaceTrackerFlattenAllocationBuffer(new int[faceTrackerSettings.getFaceTrackerFlattenAllocationBuffer()]);
        byteBuffer.setFaceTrackerBufferOutput(new float[1][faceTrackerSettings.getFaceTrackerBufferOutput()]);
        byteBuffer.setFaceTrackerByteBufferStreamInput(ByteBuffer.allocateDirect(faceTrackerSettings.getFaceTrackerFlattenAllocationBuffer()));
        byteBuffer.setFaceTrackerFileModelNeural(faceTrackerSettings.getFaceTrackerFileModel());


        byteBuffer.setIdentificationFacialPointsSizeImageHeight(facialPointsSettings.getIdentificationFacialPointsSizeImageHeight());
        byteBuffer.setIdentificationFacialPointsSizeImageWidth(facialPointsSettings.getIdentificationFacialPointsSizeImageWidth());
        byteBuffer.setIdentificationFacialPointsCoordinateMeanImage(facialPointsSettings.getIdentificationFacialPointsCoordinateMeanImage());
        byteBuffer.setIdentificationFacialPointsCoordinateMeanStandard(facialPointsSettings.getIdentificationFacialPointsCoordinateMeanStandard());
        byteBuffer.setIdentificationFacialPointsFlattenAllocationBuffer(new int[facialPointsSettings.getIdentificationFacialPointsFlattenAllocationBuffer()]);
        byteBuffer.setIdentificationFacialPointsBufferOutput(new float[1][facialPointsSettings.getIdentificationFacialPointsBufferOutput()]);
        byteBuffer.setIdentificationFacialPointsByteBufferStreamInput(ByteBuffer.allocateDirect(facialPointsSettings.getIdentificationFacialPointsFlattenAllocationBuffer()));
        byteBuffer.setIdentificationFacialPointsFileModelNeural(facialPointsSettings.getIdentificationFacialPointsFileModel());


        byteBuffer.setPersonalModelSizeImageHeight(personalModelSettings.getPersonalModelsSizeImageHeight());
        byteBuffer.setPersonalModelSizeImageWidth(personalModelSettings.getPersonalModelsSizeImageWidth());
        byteBuffer.setPersonalModelCoordinateMeanImage(personalModelSettings.getPersonalModelsCoordinateMeanImage());
        byteBuffer.setPersonalModelCoordinateMeanStandard(personalModelSettings.getPersonalModelsCoordinateMeanStandard());
        byteBuffer.setPersonalModelFlattenAllocationBuffer(new int[personalModelSettings.getPersonalModelsFlattenAllocationBuffer()]);
        byteBuffer.setPersonalModelBufferOutput(new float[1][personalModelSettings.getPersonalModelsBufferOutput()]);
        byteBuffer.setPersonalModelByteBufferStreamInput(ByteBuffer.allocateDirect(personalModelSettings.getPersonalModelsFlattenAllocationBuffer()));
        byteBuffer.setPersonalModelFileModelNeural(personalModelSettings.getPersonalModelFileModel());


        byteBuffer.setSpatialEstimationSizeImageHeight(spatialEstimationSettings.getSpatialEstimationSizeImageHeight());
        byteBuffer.setSpatialEstimationSizeImageWidth(spatialEstimationSettings.getSpatialEstimationSizeImageWidth());
        byteBuffer.setSpatialEstimationCoordinateMeanImage(spatialEstimationSettings.getSpatialEstimationCoordinateMeanImage());
        byteBuffer.setSpatialEstimationCoordinateMeanStandard(spatialEstimationSettings.getSpatialEstimationCoordinateMeanStandard());
        byteBuffer.setSpatialEstimationFlattenAllocationBuffer(new int[spatialEstimationSettings.getSpatialEstimationFlattenAllocationBuffer()]);
        byteBuffer.setSpatialEstimationBufferOutput(new float[1][spatialEstimationSettings.getSpatialEstimationBufferOutput()]);
        byteBuffer.setSpatialEstimationByteBufferStreamInput(ByteBuffer.allocateDirect(spatialEstimationSettings.getSpatialEstimationFlattenAllocationBuffer()));
        byteBuffer.setSpatialEstimationFileModelNeural(spatialEstimationSettings.getSpatialEstimationFileModel());


    }

    public void createInterpreter(){

        try {

            interpreterFaceTracker = new Interpreter(byteBuffer.FaceTrackerLoadModelFile(), faceTrackerSettings());
            interpreterPersonalModel = new Interpreter(byteBuffer.PersonalModelLoadModelFile(), personalModelSettings());
            interpreterSpatialEstimation = new Interpreter(byteBuffer.SpatialEstimationLoadModelFile(), spatialEstimationModelSettings());
            interpreterIdentificationFacialPoints = new Interpreter(byteBuffer.IdentificationFacialPointsLoadModelFile(), identificationFacialModelSettings());

            interpreterFaceTracker.allocateTensors();
            interpreterPersonalModel.allocateTensors();
            interpreterSpatialEstimation.allocateTensors();
            interpreterIdentificationFacialPoints.allocateTensors();

            byteBuffer.faceTrackerByteBufferStreamInput = ByteBuffer.allocateDirect(faceTrackerSettings.getFaceTrackerSizeImageHeight() *faceTrackerSettings.getFaceTrackerSizeImageWidth()*4);
            byteBuffer.faceTrackerByteBufferStreamInput.order(ByteOrder.nativeOrder());

            byteBuffer.personalModelByteBufferStreamInput= ByteBuffer.allocateDirect(personalModelSettings.getPersonalModelsSizeImageHeight() *personalModelSettings.getPersonalModelsSizeImageWidth()*4);
            byteBuffer.personalModelByteBufferStreamInput.order(ByteOrder.nativeOrder());

            byteBuffer.spatialEstimationByteBufferStreamInput = ByteBuffer.allocateDirect(spatialEstimationSettings.getSpatialEstimationSizeImageHeight() *spatialEstimationSettings.getSpatialEstimationSizeImageWidth()*4);
            byteBuffer.spatialEstimationByteBufferStreamInput.order(ByteOrder.nativeOrder());

            byteBuffer.identificationFacialPointsByteBufferStreamInput = ByteBuffer.allocateDirect(facialPointsSettings.getIdentificationFacialPointsSizeImageHeight() *facialPointsSettings.getIdentificationFacialPointsSizeImageWidth()*4);
            byteBuffer.identificationFacialPointsByteBufferStreamInput.order(ByteOrder.nativeOrder());


        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void createEstimator(){

        estimatorModel.setEstimatorConstantFirstAcceleration(estimatorSettings.getEstimatorConstantFirstAcceleration());
        estimatorModel.setEstimatorConstantSecondAcceleration(estimatorSettings.getEstimatorConstantSecondAcceleration());
        estimatorModel.setEstimatorConstantThirdAcceleration(estimatorSettings.getEstimatorConstantThirdAcceleration());
        estimatorModel.setEstimatorNumberMaxSimultaneousFace(estimatorSettings.getEstimatorNumberMaxSimultaneousFace());
        estimatorModel.setEstimatorNumberPointsPerFace(estimatorSettings.getEstimatorNumberPointsPerFace());

    }

    public Interpreter.Options faceTrackerSettings(){

        Interpreter.Options modelOptions = new Interpreter.Options();
        modelOptions.setNumThreads(faceTrackerSettings.getFaceTrackerNumberThreads());
        modelOptions.setAllowBufferHandleOutput(faceTrackerSettings.getFaceTrackerAllowBufferHandleOutput());
        modelOptions.setAllowFp16PrecisionForFp32(faceTrackerSettings.getFaceTrackerAllowFp16PrecisionForFp32());
        return modelOptions;

    }

    public Interpreter.Options personalModelSettings(){

        Interpreter.Options modelOptions = new Interpreter.Options();
        modelOptions.setNumThreads(personalModelSettings.getPersonalModelsNumberThreads());
        modelOptions.setAllowBufferHandleOutput(personalModelSettings.getPersonalModelsAllowBufferHandleOutput());
        modelOptions.setAllowFp16PrecisionForFp32(personalModelSettings.getPersonalModelsAllowFp16PrecisionForFp32());
        return modelOptions;

    }

    public Interpreter.Options spatialEstimationModelSettings(){

        Interpreter.Options modelsOptions = new Interpreter.Options();
        modelsOptions.setNumThreads(spatialEstimationSettings.getSpatialEstimationNumberThreads());
        modelsOptions.setAllowBufferHandleOutput(spatialEstimationSettings.getSpatialEstimationAllowBufferHandleOutput());
        modelsOptions.setAllowFp16PrecisionForFp32(spatialEstimationSettings.getSpatialEstimationAllowFp16PrecisionForFp32());
        return modelsOptions;

    }

    public Interpreter.Options identificationFacialModelSettings(){

        Interpreter.Options modelsOptions = new Interpreter.Options();
        modelsOptions.setNumThreads(facialPointsSettings.getIdentificationFacialPointsNumberThreads());
        modelsOptions.setAllowBufferHandleOutput(facialPointsSettings.getIdentificationFacialPointsAllowBufferHandleOutput());
        modelsOptions.setAllowFp16PrecisionForFp32(facialPointsSettings.getIdentificationFacialPointsAllowFp16PrecisionForFp32());
        return modelsOptions;

    }


    public void predictFaceTracker(byte[] data, Camera camera){

        faceTrackerCameraParameters = camera.getParameters();
        faceTrackerCompressionYuvImage = new YuvImage(data, faceTrackerCameraParameters.getPreviewFormat(), faceTrackerCameraParameters.getPreviewSize().width, faceTrackerCameraParameters.getPreviewSize().height, null);
        faceTrackerCompressionYuvImage.compressToJpeg(new Rect(0, 0, faceTrackerSettings.getFaceTrackerSizeImageHeight(), faceTrackerSettings.getFaceTrackerSizeImageWidth()), 90, byteBuffer.faceTrackerBufferStreamOutput);
        faceTrackerImageByte = byteBuffer.faceTrackerBufferStreamOutput.toByteArray();
        faceTrackerBitmapImage = BitmapFactory.decodeByteArray(faceTrackerImageByte, 0, faceTrackerImageByte.length);
        byteBuffer.FaceTrackerCastBitmapToByteBuffer(faceTrackerBitmapImage);
        inferenceFaceTracker();

    }

    public void predictPersonalModel(byte[] data, Camera camera){

        personalModelsCameraParameters = camera.getParameters();
        personalModelsCompressionYuvImage = new YuvImage(data, personalModelsCameraParameters.getPreviewFormat(), personalModelsCameraParameters.getPreviewSize().width, personalModelsCameraParameters.getPreviewSize().height, null);
        personalModelsCompressionYuvImage.compressToJpeg(new Rect(0, 0, personalModelSettings.getPersonalModelsSizeImageHeight(), personalModelSettings.getPersonalModelsSizeImageWidth()), 90, byteBuffer.personalModelBufferStreamOutput);
        personalModelsImageByte = byteBuffer.personalModelBufferStreamOutput.toByteArray();
        personalModelBitmapImage = BitmapFactory.decodeByteArray(personalModelsImageByte, 0, personalModelsImageByte.length);
        byteBuffer.PersonalModelCastBitmapToByteBuffer(personalModelBitmapImage);
        inferencePersonalModel();

    }

    public void predictSpatialEstimation(byte[] data, Camera camera){

        spatialEstimationCameraParameters = camera.getParameters();
        spatialEstimationCompressionYuvImage = new YuvImage(data, spatialEstimationCameraParameters.getPreviewFormat(), spatialEstimationCameraParameters.getPreviewSize().width, spatialEstimationCameraParameters.getPreviewSize().height, null);
        spatialEstimationCompressionYuvImage.compressToJpeg(new Rect(0, 0, spatialEstimationSettings.getSpatialEstimationSizeImageHeight(), spatialEstimationSettings.getSpatialEstimationSizeImageWidth()), 90, byteBuffer.spatialEstimationBufferStreamOutput);
        spatialEstimationImageByte = byteBuffer.spatialEstimationBufferStreamOutput.toByteArray();
        spatialEstimationBitmapImage = BitmapFactory.decodeByteArray(spatialEstimationImageByte, 0, spatialEstimationImageByte.length);
        byteBuffer.SpatialEstimationCastBitmapToByteBuffer(spatialEstimationBitmapImage);
        inferenceSpatialEstimation();

    }

    public void predictIdentificationFacialPoints(byte[] data, Camera camera){

        if( estimationCycle && estimatorModel.estimatorBufferLoaded){

            Log.i("CYCLE TESTE --------------------", "---");

            estimationCycle = true;
        }else{

            identificationFacialPointsCameraParameters = camera.getParameters();
            identificationFacialPointsCompressionYuvImage = new YuvImage(data, identificationFacialPointsCameraParameters.getPreviewFormat(), identificationFacialPointsCameraParameters.getPreviewSize().width, identificationFacialPointsCameraParameters.getPreviewSize().height, null);
            identificationFacialPointsCompressionYuvImage.compressToJpeg(new Rect(0, 0,facialPointsSettings.getIdentificationFacialPointsSizeImageHeight(), facialPointsSettings.getIdentificationFacialPointsSizeImageWidth()), 90, byteBuffer.identificationFacialPointsBufferStreamOutput);
            identificationFacialPointsImageByte = byteBuffer.identificationFacialPointsBufferStreamOutput.toByteArray();
            identificationFacialPointsBitmapImage = BitmapFactory.decodeByteArray(identificationFacialPointsImageByte, 0, identificationFacialPointsImageByte.length);
            byteBuffer.IdentificationFacialPointsCastBitmapToByteBuffer(identificationFacialPointsBitmapImage);
            inferenceIdentificationFacialPoints();
            estimatorModel.addEstimationBufferPredictions(byteBuffer.identificationFacialPointsBufferOutput[0]);


        }


    }


    public void estimationIdentificationFacialPoints(){

        estimatorModel.generateEstimating();

        for(int i = 0; i < estimatorSettings.getEstimatorNumberPointsPerFace(); i++) {

            byteBuffer.faceTrackerBufferOutput[0][2*i] = estimatorModel.estimatorBufferPredictionFrameSequencesAxisX[i];
            byteBuffer.faceTrackerBufferOutput[0][2*i+1] = estimatorModel.estimatorBufferPredictionFrameSequencesAxisY[i];

        }

    }



    public void inferenceFaceTracker(){

        Instant start = Instant.now();
        interpreterFaceTracker.run(byteBuffer.faceTrackerByteBufferStreamInput, byteBuffer.faceTrackerBufferOutput);
        Instant end = Instant.now();
        Log.i("------- TIME:     ", Duration.between(start, end).toString());
    }

    public void inferencePersonalModel() {

        Instant start = Instant.now();
        interpreterPersonalModel.run(byteBuffer.personalModelByteBufferStreamInput, byteBuffer.personalModelBufferOutput);
        Instant end = Instant.now();
        Log.i("------- TIME:     ", Duration.between(start, end).toString());
    }

    public void inferenceSpatialEstimation() {

        Instant start = Instant.now();
        interpreterSpatialEstimation.run(byteBuffer.spatialEstimationByteBufferStreamInput, byteBuffer.spatialEstimationBufferOutput);
        Instant end = Instant.now();
        Log.i("------- TIME:     ", Duration.between(start, end).toString());
    }

    public void inferenceIdentificationFacialPoints() {

        Instant start = Instant.now();
        interpreterIdentificationFacialPoints.run(byteBuffer.identificationFacialPointsByteBufferStreamInput, byteBuffer.identificationFacialPointsBufferOutput);
        Instant end = Instant.now();
        Log.i("------- TIME:     ", Duration.between(start, end).toString());
    }



}



