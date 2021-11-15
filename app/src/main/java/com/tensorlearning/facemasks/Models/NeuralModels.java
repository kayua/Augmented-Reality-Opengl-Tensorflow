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

    public void createByteBufferModel(){

        byteBuffer.setFaceTrackerSizeImageHeight(faceTrackerSettings.getFaceTrackerSizeImageHeight());
        byteBuffer.setFaceTrackerSizeImageWidth(faceTrackerSettings.getFaceTrackerSizeImageWidth());
        byteBuffer.setFaceTrackerCoordinateMeanImage(faceTrackerSettings.getFaceTrackerCoordinateMeanImage());
        byteBuffer.setFaceTrackerCoordinateMeanStandard(faceTrackerSettings.getFaceTrackerCoordinateMeanStandard());
        byteBuffer.setFaceTrackerFlattenAllocationBuffer(new int[faceTrackerSettings.getFaceTrackerFlattenAllocationBuffer()]);
        byteBuffer.setFaceTrackerBufferOutput(new float[1][faceTrackerSettings.getFaceTrackerBufferOutput()]);
        byteBuffer.setFaceTrackerByteBufferStreamInput(ByteBuffer.allocateDirect(faceTrackerSettings.getFaceTrackerFlattenAllocationBuffer()*4));


        byteBuffer.setIdentificationFacialPointsSizeImageHeight(facialPointsSettings.getIdentificationFacialPointsSizeImageHeight());
        byteBuffer.setIdentificationFacialPointsSizeImageWidth(facialPointsSettings.getIdentificationFacialPointsSizeImageWidth());
        byteBuffer.setIdentificationFacialPointsCoordinateMeanImage(facialPointsSettings.getIdentificationFacialPointsCoordinateMeanImage());
        byteBuffer.setIdentificationFacialPointsCoordinateMeanStandard(facialPointsSettings.getIdentificationFacialPointsCoordinateMeanStandard());
        byteBuffer.setIdentificationFacialPointsFlattenAllocationBuffer(new int[facialPointsSettings.getIdentificationFacialPointsFlattenAllocationBuffer()]);
        byteBuffer.setIdentificationFacialPointsBufferOutput(new float[1][facialPointsSettings.getIdentificationFacialPointsBufferOutput()]);
        byteBuffer.setIdentificationFacialPointsByteBufferStreamInput(ByteBuffer.allocateDirect(facialPointsSettings.getIdentificationFacialPointsFlattenAllocationBuffer()*4));


        byteBuffer.setPersonalModelSizeImageHeight(personalModelSettings.getPersonalModelsSizeImageHeight());
        byteBuffer.setPersonalModelSizeImageWidth(personalModelSettings.getPersonalModelsSizeImageWidth());
        byteBuffer.setPersonalModelCoordinateMeanImage(personalModelSettings.getPersonalModelsCoordinateMeanImage());
        byteBuffer.setPersonalModelCoordinateMeanStandard(personalModelSettings.getPersonalModelsCoordinateMeanStandard());
        byteBuffer.setPersonalModelFlattenAllocationBuffer(new int[personalModelSettings.getPersonalModelsFlattenAllocationBuffer()]);
        byteBuffer.setPersonalModelBufferOutput(new float[1][personalModelSettings.getPersonalModelsBufferOutput()]);
        byteBuffer.setPersonalModelByteBufferStreamInput(ByteBuffer.allocateDirect(personalModelSettings.getPersonalModelsFlattenAllocationBuffer()*4));


        byteBuffer.setSpatialEstimationSizeImageHeight(spatialEstimationSettings.getSpatialEstimationSizeImageHeight());
        byteBuffer.setSpatialEstimationSizeImageWidth(spatialEstimationSettings.getSpatialEstimationSizeImageWidth());
        byteBuffer.setSpatialEstimationCoordinateMeanImage(spatialEstimationSettings.getSpatialEstimationCoordinateMeanImage());
        byteBuffer.setSpatialEstimationCoordinateMeanStandard(spatialEstimationSettings.getSpatialEstimationCoordinateMeanStandard());
        byteBuffer.setSpatialEstimationFlattenAllocationBuffer(new int[spatialEstimationSettings.getSpatialEstimationFlattenAllocationBuffer()]);
        byteBuffer.setSpatialEstimationBufferOutput(new float[1][spatialEstimationSettings.getSpatialEstimationBufferOutput()]);
        byteBuffer.setSpatialEstimationByteBufferStreamInput(ByteBuffer.allocateDirect(spatialEstimationSettings.getSpatialEstimationFlattenAllocationBuffer()*4));



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
        modelOptions.setAllowBufferHandleOutput(true);
        modelOptions.setAllowFp16PrecisionForFp32(true);
        return modelOptions;

    }

    public Interpreter.Options personalModelSettings(){

        Interpreter.Options modelOptions = new Interpreter.Options();
        modelOptions.setNumThreads(personalModelSettings.getPersonalModelsNumberThreads());
        modelOptions.setAllowBufferHandleOutput(true);
        modelOptions.setAllowFp16PrecisionForFp32(true);
        return modelOptions;

    }

    public Interpreter.Options spatialEstimationModelSettings(){

        Interpreter.Options modelsOptions = new Interpreter.Options();
        modelsOptions.setNumThreads(spatialEstimationSettings.getSpatialEstimationNumberThreads());
        modelsOptions.setAllowBufferHandleOutput(true);
        modelsOptions.setAllowFp16PrecisionForFp32(true);
        return modelsOptions;

    }

    public Interpreter.Options identificationFacialModelSettings(){

        Interpreter.Options modelsOptions = new Interpreter.Options();
        modelsOptions.setNumThreads(facialPointsSettings.getIdentificationFacialPointsNumberThreads());
        modelsOptions.setAllowBufferHandleOutput(true);
        modelsOptions.setAllowFp16PrecisionForFp32(true);
        return modelsOptions;

    }


    public void predictFaceTracker(byte[] data, Camera camera){

        Camera.Parameters parameters = camera.getParameters();

        YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
        yuvImage.compressToJpeg(new Rect(0, 0, faceTrackerSettings.getFaceTrackerSizeImageHeight(), faceTrackerSettings.getFaceTrackerSizeImageWidth()), 90, byteBuffer.faceTrackerBufferStreamOutput);
        byte[] imageBytes = byteBuffer.faceTrackerBufferStreamOutput.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        byteBuffer.FaceTrackerCastBitmapToByteBuffer(bitmap);

        try {

            inferenceFaceTracker();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void predictPersonalModel(byte[] data, Camera camera){

        Camera.Parameters parameters = camera.getParameters();

        YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
        yuvImage.compressToJpeg(new Rect(0, 0, personalModelSettings.getPersonalModelsSizeImageHeight(), personalModelSettings.getPersonalModelsSizeImageWidth()), 90, byteBuffer.personalModelBufferStreamOutput);
        byte[] imageBytes = byteBuffer.personalModelBufferStreamOutput.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        byteBuffer.PersonalModelCastBitmapToByteBuffer(bitmap);

        try {

            inferencePersonalModel();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void predictSpatialEstimation(byte[] data, Camera camera){

        Camera.Parameters parameters = camera.getParameters();

        YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
        yuvImage.compressToJpeg(new Rect(0, 0, spatialEstimationSettings.getSpatialEstimationSizeImageHeight(), spatialEstimationSettings.getSpatialEstimationSizeImageWidth()), 90, byteBuffer.spatialEstimationBufferStreamOutput);
        byte[] imageBytes = byteBuffer.spatialEstimationBufferStreamOutput.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        byteBuffer.SpatialEstimationCastBitmapToByteBuffer(bitmap);

        try {

            inferenceSpatialEstimation();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void predictIdentificationFacialPoints(byte[] data, Camera camera){

        Camera.Parameters parameters = camera.getParameters();

        YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
        yuvImage.compressToJpeg(new Rect(0, 0,facialPointsSettings.getIdentificationFacialPointsSizeImageHeight(), facialPointsSettings.getIdentificationFacialPointsSizeImageWidth()), 90, byteBuffer.identificationFacialPointsBufferStreamOutput);
        byte[] imageBytes = byteBuffer.identificationFacialPointsBufferStreamOutput.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        byteBuffer.IdentificationFacialPointsCastBitmapToByteBuffer(bitmap);

        try {

            inferenceIdentificationFacialPoints();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    public void inferenceFaceTracker() throws IOException {

        Instant start = Instant.now();
        interpreterFaceTracker.run(byteBuffer.faceTrackerByteBufferStreamInput, byteBuffer.faceTrackerBufferOutput);
        Instant end = Instant.now();
        Log.i("TIME:     ", Duration.between(start, end).toString());
    }

    public void inferencePersonalModel() throws IOException {

        Instant start = Instant.now();
        interpreterPersonalModel.run(byteBuffer.personalModelByteBufferStreamInput, byteBuffer.personalModelBufferOutput);
        Instant end = Instant.now();
        Log.i("TIME:     ", Duration.between(start, end).toString());
    }

    public void inferenceSpatialEstimation() throws IOException {

        Instant start = Instant.now();
        interpreterSpatialEstimation.run(byteBuffer.spatialEstimationByteBufferStreamInput, byteBuffer.spatialEstimationBufferOutput);
        Instant end = Instant.now();
        Log.i("TIME:     ", Duration.between(start, end).toString());
    }

    public void inferenceIdentificationFacialPoints() throws IOException {

        Instant start = Instant.now();
        interpreterIdentificationFacialPoints.run(byteBuffer.identificationFacialPointsByteBufferStreamInput, byteBuffer.identificationFacialPointsBufferOutput);
        Instant end = Instant.now();
        Log.i("TIME:     ", Duration.between(start, end).toString());
    }



}



