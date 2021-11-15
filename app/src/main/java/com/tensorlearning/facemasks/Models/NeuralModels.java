package com.tensorlearning.facemasks.Models;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import com.tensorlearning.facemasks.Buffering.ByteBufferModel;
import com.tensorlearning.facemasks.SettingsFaceTracker.FaceTrackerSettings;
import com.tensorlearning.facemasks.SettingsIdentificationFacialPoints.FacialPointsSettings;
import com.tensorlearning.facemasks.SettingsPersonalModels.PersonalModelsSettings;
import com.tensorlearning.facemasks.SettingsSpatialEstimation.SpatialEstimationSettings;

import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;

public class NeuralModels {

    private Interpreter interpreterFaceTracker = null;
    private Interpreter interpreterPersonalModel = null;
    private Interpreter interpreterSpatialEstimation = null;
    private Interpreter interpreterIdentificationFacialPoints = null;

    private final FaceTrackerSettings faceTrackerSettings = new FaceTrackerSettings();
    private final FacialPointsSettings facialPointsSettings = new FacialPointsSettings();
    private final PersonalModelsSettings personalModelSettings = new PersonalModelsSettings();
    private final SpatialEstimationSettings spatialEstimationSettings = new SpatialEstimationSettings();

    private final ByteBufferModel byteBuffer;

    private Camera.Parameters faceTrackerCameraParameters = null;
    private Camera.Parameters personalModelsCameraParameters = null;
    private Camera.Parameters spatialEstimationCameraParameters = null;
    private Camera.Parameters identificationFacialPointsCameraParameters = null;




    public NeuralModels(Context context) {

        this.byteBuffer = new ByteBufferModel(context);

    }

    public void createByteBufferModel(){

        byteBuffer.setFaceTrackerSizeImageHeight(faceTrackerSettings.getFaceTrackerSizeImageHeight());
        byteBuffer.setFaceTrackerSizeImageWidth(faceTrackerSettings.getFaceTrackerSizeImageWidth());
        byteBuffer.setFaceTrackerCoordinateMeanImage(faceTrackerSettings.getFaceTrackerCoordinateMeanImage());
        byteBuffer.setFaceTrackerCoordinateMeanStandard(faceTrackerSettings.getFaceTrackerCoordinateMeanStandard());
        byteBuffer.setFaceTrackerFlattenAllocationBuffer(new int[faceTrackerSettings.getFaceTrackerFlattenAllocationBuffer()]);
        byteBuffer.setFaceTrackerBufferOutput(new float[1][faceTrackerSettings.getFaceTrackerBufferOutput()]);
        byteBuffer.setFaceTrackerByteBufferStreamInput(ByteBuffer.allocateDirect(faceTrackerSettings.getFaceTrackerFlattenAllocationBuffer()*4));
        byteBuffer.setFaceTrackerFileModelNeural(faceTrackerSettings.getFaceTrackerFileModel());


        byteBuffer.setIdentificationFacialPointsSizeImageHeight(facialPointsSettings.getIdentificationFacialPointsSizeImageHeight());
        byteBuffer.setIdentificationFacialPointsSizeImageWidth(facialPointsSettings.getIdentificationFacialPointsSizeImageWidth());
        byteBuffer.setIdentificationFacialPointsCoordinateMeanImage(facialPointsSettings.getIdentificationFacialPointsCoordinateMeanImage());
        byteBuffer.setIdentificationFacialPointsCoordinateMeanStandard(facialPointsSettings.getIdentificationFacialPointsCoordinateMeanStandard());
        byteBuffer.setIdentificationFacialPointsFlattenAllocationBuffer(new int[facialPointsSettings.getIdentificationFacialPointsFlattenAllocationBuffer()]);
        byteBuffer.setIdentificationFacialPointsBufferOutput(new float[1][facialPointsSettings.getIdentificationFacialPointsBufferOutput()]);
        byteBuffer.setIdentificationFacialPointsByteBufferStreamInput(ByteBuffer.allocateDirect(facialPointsSettings.getIdentificationFacialPointsFlattenAllocationBuffer()*4));
        byteBuffer.setIdentificationFacialPointsFileModelNeural(facialPointsSettings.getIdentificationFacialPointsFileModel());


        byteBuffer.setPersonalModelSizeImageHeight(personalModelSettings.getPersonalModelsSizeImageHeight());
        byteBuffer.setPersonalModelSizeImageWidth(personalModelSettings.getPersonalModelsSizeImageWidth());
        byteBuffer.setPersonalModelCoordinateMeanImage(personalModelSettings.getPersonalModelsCoordinateMeanImage());
        byteBuffer.setPersonalModelCoordinateMeanStandard(personalModelSettings.getPersonalModelsCoordinateMeanStandard());
        byteBuffer.setPersonalModelFlattenAllocationBuffer(new int[personalModelSettings.getPersonalModelsFlattenAllocationBuffer()]);
        byteBuffer.setPersonalModelBufferOutput(new float[1][personalModelSettings.getPersonalModelsBufferOutput()]);
        byteBuffer.setPersonalModelByteBufferStreamInput(ByteBuffer.allocateDirect(personalModelSettings.getPersonalModelsFlattenAllocationBuffer()*4));
        byteBuffer.setPersonalModelFileModelNeural(personalModelSettings.getPersonalModelFileModel());


        byteBuffer.setSpatialEstimationSizeImageHeight(spatialEstimationSettings.getSpatialEstimationSizeImageHeight());
        byteBuffer.setSpatialEstimationSizeImageWidth(spatialEstimationSettings.getSpatialEstimationSizeImageWidth());
        byteBuffer.setSpatialEstimationCoordinateMeanImage(spatialEstimationSettings.getSpatialEstimationCoordinateMeanImage());
        byteBuffer.setSpatialEstimationCoordinateMeanStandard(spatialEstimationSettings.getSpatialEstimationCoordinateMeanStandard());
        byteBuffer.setSpatialEstimationFlattenAllocationBuffer(new int[spatialEstimationSettings.getSpatialEstimationFlattenAllocationBuffer()]);
        byteBuffer.setSpatialEstimationBufferOutput(new float[1][spatialEstimationSettings.getSpatialEstimationBufferOutput()]);
        byteBuffer.setSpatialEstimationByteBufferStreamInput(ByteBuffer.allocateDirect(spatialEstimationSettings.getSpatialEstimationFlattenAllocationBuffer()*4));
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

        YuvImage yuvImage = new YuvImage(data, faceTrackerCameraParameters.getPreviewFormat(), faceTrackerCameraParameters.getPreviewSize().width, faceTrackerCameraParameters.getPreviewSize().height, null);
        yuvImage.compressToJpeg(new Rect(0, 0, faceTrackerSettings.getFaceTrackerSizeImageHeight(), faceTrackerSettings.getFaceTrackerSizeImageWidth()), 90, byteBuffer.faceTrackerBufferStreamOutput);
        byte[] imageBytes = byteBuffer.faceTrackerBufferStreamOutput.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        byteBuffer.FaceTrackerCastBitmapToByteBuffer(bitmap);
        inferenceFaceTracker();

    }

    public void predictPersonalModel(byte[] data, Camera camera){

        personalModelsCameraParameters = camera.getParameters();

        YuvImage yuvImage = new YuvImage(data, personalModelsCameraParameters.getPreviewFormat(), personalModelsCameraParameters.getPreviewSize().width, personalModelsCameraParameters.getPreviewSize().height, null);
        yuvImage.compressToJpeg(new Rect(0, 0, personalModelSettings.getPersonalModelsSizeImageHeight(), personalModelSettings.getPersonalModelsSizeImageWidth()), 90, byteBuffer.personalModelBufferStreamOutput);
        byte[] imageBytes = byteBuffer.personalModelBufferStreamOutput.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        byteBuffer.PersonalModelCastBitmapToByteBuffer(bitmap);
        inferencePersonalModel();

    }

    public void predictSpatialEstimation(byte[] data, Camera camera){

        spatialEstimationCameraParameters = camera.getParameters();

        YuvImage yuvImage = new YuvImage(data, spatialEstimationCameraParameters.getPreviewFormat(), spatialEstimationCameraParameters.getPreviewSize().width, spatialEstimationCameraParameters.getPreviewSize().height, null);
        yuvImage.compressToJpeg(new Rect(0, 0, spatialEstimationSettings.getSpatialEstimationSizeImageHeight(), spatialEstimationSettings.getSpatialEstimationSizeImageWidth()), 90, byteBuffer.spatialEstimationBufferStreamOutput);
        byte[] imageBytes = byteBuffer.spatialEstimationBufferStreamOutput.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        byteBuffer.SpatialEstimationCastBitmapToByteBuffer(bitmap);
        inferenceSpatialEstimation();

    }

    public void predictIdentificationFacialPoints(byte[] data, Camera camera){

        identificationFacialPointsCameraParameters = camera.getParameters();

        YuvImage yuvImage = new YuvImage(data, identificationFacialPointsCameraParameters.getPreviewFormat(), identificationFacialPointsCameraParameters.getPreviewSize().width, identificationFacialPointsCameraParameters.getPreviewSize().height, null);
        yuvImage.compressToJpeg(new Rect(0, 0,facialPointsSettings.getIdentificationFacialPointsSizeImageHeight(), facialPointsSettings.getIdentificationFacialPointsSizeImageWidth()), 90, byteBuffer.identificationFacialPointsBufferStreamOutput);
        byte[] imageBytes = byteBuffer.identificationFacialPointsBufferStreamOutput.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        byteBuffer.IdentificationFacialPointsCastBitmapToByteBuffer(bitmap);
        inferenceIdentificationFacialPoints();

    }


    public void inferenceFaceTracker(){

        Instant start = Instant.now();
        interpreterFaceTracker.run(byteBuffer.faceTrackerByteBufferStreamInput, byteBuffer.faceTrackerBufferOutput);
        Instant end = Instant.now();
        Log.i("TIME:     ", Duration.between(start, end).toString());
    }

    public void inferencePersonalModel() {

        Instant start = Instant.now();
        interpreterPersonalModel.run(byteBuffer.personalModelByteBufferStreamInput, byteBuffer.personalModelBufferOutput);
        Instant end = Instant.now();
        Log.i("TIME:     ", Duration.between(start, end).toString());
    }

    public void inferenceSpatialEstimation() {

        Instant start = Instant.now();
        interpreterSpatialEstimation.run(byteBuffer.spatialEstimationByteBufferStreamInput, byteBuffer.spatialEstimationBufferOutput);
        Instant end = Instant.now();
        Log.i("TIME:     ", Duration.between(start, end).toString());
    }

    public void inferenceIdentificationFacialPoints() {

        Instant start = Instant.now();
        interpreterIdentificationFacialPoints.run(byteBuffer.identificationFacialPointsByteBufferStreamInput, byteBuffer.identificationFacialPointsBufferOutput);
        Instant end = Instant.now();
        Log.i("TIME:     ", Duration.between(start, end).toString());
    }



}



