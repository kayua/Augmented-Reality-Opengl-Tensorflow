package com.tensorlearning.facemasks.Recognize.Buffering;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class MemoryAllocationBuffer {


    private int faceTrackerSizeImageHeight;
    private int faceTrackerSizeImageWidth;
    private int faceTrackerCoordinateMeanImage;

    private int spatialEstimationSizeImageHeight;
    private int spatialEstimationSizeImageWidth;
    private int spatialEstimationCoordinateMeanImage;

    private int personalModelSizeImageHeight;
    private int personalModelSizeImageWidth;
    private int personalModelCoordinateMeanImage;

    private int identificationFacialPointsSizeImageHeight;
    private int identificationFacialPointsSizeImageWidth;
    private int identificationFacialPointsCoordinateMeanImage;


    private float faceTrackerCoordinateMeanStandard;
    private float spatialEstimationCoordinateMeanStandard;
    private float personalModelCoordinateMeanStandard;
    private float identificationFacialPointsCoordinateMeanStandard;


    public int[] faceTrackerFlattenAllocationBuffer = null;
    public int[] personalModelFlattenAllocationBuffer = null;
    public int[] spatialEstimationFlattenAllocationBuffer = null;
    public int[] identificationFacialPointsFlattenAllocationBuffer = null;


    public float [][] faceTrackerBufferOutput;
    public float [][] personalModelBufferOutput;
    public float [][] spatialEstimationBufferOutput;
    public float [][] identificationFacialPointsBufferOutput;


    public ByteBuffer faceTrackerByteBufferStreamInput;
    public ByteBuffer personalModelByteBufferStreamInput;
    public ByteBuffer spatialEstimationByteBufferStreamInput;
    public ByteBuffer identificationFacialPointsByteBufferStreamInput;

    public ByteArrayOutputStream faceTrackerBufferStreamOutput = new ByteArrayOutputStream();;
    public ByteArrayOutputStream personalModelBufferStreamOutput = new ByteArrayOutputStream();;
    public ByteArrayOutputStream spatialEstimationBufferStreamOutput = new ByteArrayOutputStream();;
    public ByteArrayOutputStream identificationFacialPointsBufferStreamOutput = new ByteArrayOutputStream();;

    private String faceTrackerFileModel;
    private String personalModelFileModel;
    private String spatialEstimationFileModel;
    private String identificationFacialFileModel;

    Context foreignContext;


    public MemoryAllocationBuffer(Context context) {

        this.foreignContext = context;

    }


    public void FaceTrackerCastBitmapToByteBuffer(Bitmap bitmap) {

        if (faceTrackerByteBufferStreamInput == null) { return; }
        faceTrackerByteBufferStreamInput.rewind();
        bitmap.getPixels(faceTrackerFlattenAllocationBuffer, 0, bitmap.getWidth(), 0, 0, faceTrackerSizeImageHeight, faceTrackerSizeImageWidth);
        int pixelShift = 0;
        long startTime = SystemClock.uptimeMillis();
        for (int i = 0; i < faceTrackerSizeImageHeight; ++i) {
            for (int j = 0; j < faceTrackerSizeImageWidth; ++j) {
                final int val = faceTrackerFlattenAllocationBuffer[pixelShift++];
                faceTrackerByteBufferStreamInput.put((byte) ((((val >> 16) & 0xFF)- faceTrackerCoordinateMeanImage)/ faceTrackerCoordinateMeanStandard));
                faceTrackerByteBufferStreamInput.put((byte) ((((val >> 8) & 0xFF)- faceTrackerCoordinateMeanImage)/ faceTrackerCoordinateMeanStandard));
                faceTrackerByteBufferStreamInput.put((byte) ((((val) & 0xFF)- faceTrackerCoordinateMeanImage)/ faceTrackerCoordinateMeanStandard));
            }
        }
        long endTime = SystemClock.uptimeMillis();
        Log.d("1", "Timecost to put values into ByteBuffer: " + Long.toString(endTime - startTime));
    }

    public void PersonalModelCastBitmapToByteBuffer(Bitmap bitmap) {

        if (personalModelByteBufferStreamInput == null) { return; }
        personalModelByteBufferStreamInput.rewind();
        bitmap.getPixels(personalModelFlattenAllocationBuffer, 0, bitmap.getWidth(), 0, 0, personalModelSizeImageHeight, personalModelSizeImageWidth);
        int pixelShift = 0;
        long startTime = SystemClock.uptimeMillis();
        for (int i = 0; i < personalModelSizeImageHeight; ++i) {
            for (int j = 0; j < personalModelSizeImageWidth; ++j) {
                final int val = personalModelFlattenAllocationBuffer[pixelShift++];
                personalModelByteBufferStreamInput.put((byte) ((((val >> 16) & 0xFF)- personalModelCoordinateMeanImage)/ personalModelCoordinateMeanStandard));
                personalModelByteBufferStreamInput.put((byte) ((((val >> 8) & 0xFF)- personalModelCoordinateMeanImage)/ personalModelCoordinateMeanStandard));
                personalModelByteBufferStreamInput.put((byte) ((((val) & 0xFF)- personalModelCoordinateMeanImage)/ personalModelCoordinateMeanStandard));
            }
        }
        long endTime = SystemClock.uptimeMillis();
        Log.d("1", "Timecost  ByteBuffer: " + Long.toString(endTime - startTime));
    }

    public void IdentificationFacialPointsCastBitmapToByteBuffer(Bitmap bitmap) {

        if (identificationFacialPointsByteBufferStreamInput == null) { return; }
        identificationFacialPointsByteBufferStreamInput.rewind();
        bitmap.getPixels(identificationFacialPointsFlattenAllocationBuffer, 0, bitmap.getWidth(), 0, 0, identificationFacialPointsSizeImageHeight, identificationFacialPointsSizeImageWidth);
        int pixelShift = 0;
        long startTime = SystemClock.uptimeMillis();
        for (int i = 0; i < identificationFacialPointsSizeImageHeight; ++i) {
            for (int j = 0; j < identificationFacialPointsSizeImageWidth; ++j) {
                final int val = identificationFacialPointsFlattenAllocationBuffer[pixelShift++];
                identificationFacialPointsByteBufferStreamInput.put((byte) ((((val >> 16) & 0xFF)- identificationFacialPointsCoordinateMeanImage)/ identificationFacialPointsCoordinateMeanStandard));
                identificationFacialPointsByteBufferStreamInput.put((byte) ((((val >> 8) & 0xFF)- identificationFacialPointsCoordinateMeanImage)/ identificationFacialPointsCoordinateMeanStandard));
                identificationFacialPointsByteBufferStreamInput.put((byte) ((((val) & 0xFF)- identificationFacialPointsCoordinateMeanImage)/ identificationFacialPointsCoordinateMeanStandard));
            }
        }
        long endTime = SystemClock.uptimeMillis();
        Log.d("1", "Timecost ByteBuffer: " + Long.toString(endTime - startTime));
    }

    public void SpatialEstimationCastBitmapToByteBuffer(Bitmap bitmap) {

        if (spatialEstimationByteBufferStreamInput == null) { return; }
        spatialEstimationByteBufferStreamInput.rewind();
        bitmap.getPixels(spatialEstimationFlattenAllocationBuffer, 0, bitmap.getWidth(), 0, 0, spatialEstimationSizeImageHeight, spatialEstimationSizeImageWidth);
        int pixelShift = 0;
        long startTime = SystemClock.uptimeMillis();
        for (int i = 0; i < spatialEstimationSizeImageHeight; ++i) {
            for (int j = 0; j < spatialEstimationSizeImageWidth; ++j) {
                final int val = spatialEstimationFlattenAllocationBuffer[pixelShift++];
                spatialEstimationByteBufferStreamInput.put((byte) ((((val >> 16) & 0xFF)- spatialEstimationCoordinateMeanImage)/ spatialEstimationCoordinateMeanStandard));
                spatialEstimationByteBufferStreamInput.put((byte) ((((val >> 8) & 0xFF)- spatialEstimationCoordinateMeanImage)/ spatialEstimationCoordinateMeanStandard));
                spatialEstimationByteBufferStreamInput.put((byte) ((((val) & 0xFF)- spatialEstimationCoordinateMeanImage)/ spatialEstimationCoordinateMeanStandard));
            }
        }
        long endTime = SystemClock.uptimeMillis();
        Log.d("1", "Timecost  ByteBuffer: " + Long.toString(endTime - startTime));
    }


    public MappedByteBuffer FaceTrackerLoadModelFile() throws IOException {

        AssetFileDescriptor assetFileDescriptor = this.foreignContext.getAssets().openFd(faceTrackerFileModel);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);

    }

    public MappedByteBuffer PersonalModelLoadModelFile() throws IOException {

        AssetFileDescriptor assetFileDescriptor = this.foreignContext.getAssets().openFd(personalModelFileModel);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);

    }

    public MappedByteBuffer IdentificationFacialPointsLoadModelFile() throws IOException {

        AssetFileDescriptor assetFileDescriptor = this.foreignContext.getAssets().openFd(identificationFacialFileModel);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);

    }

    public MappedByteBuffer SpatialEstimationLoadModelFile() throws IOException {

        AssetFileDescriptor assetFileDescriptor = this.foreignContext.getAssets().openFd(spatialEstimationFileModel);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);

    }


    public void setFaceTrackerSizeImageHeight(int faceTrackerSizeImageHeight) {

        this.faceTrackerSizeImageHeight = faceTrackerSizeImageHeight;

    }

    public void setFaceTrackerSizeImageWidth(int faceTrackerSizeImageWidth) {

        this.faceTrackerSizeImageWidth = faceTrackerSizeImageWidth;

    }

    public void setFaceTrackerCoordinateMeanImage(int faceTrackerCoordinateMeanImage) {

        this.faceTrackerCoordinateMeanImage = faceTrackerCoordinateMeanImage;

    }

    public void setSpatialEstimationSizeImageHeight(int spatialEstimationSizeImageHeight) {

        this.spatialEstimationSizeImageHeight = spatialEstimationSizeImageHeight;

    }

    public void setSpatialEstimationSizeImageWidth(int spatialEstimationSizeImageWidth) {

        this.spatialEstimationSizeImageWidth = spatialEstimationSizeImageWidth;

    }

    public void setSpatialEstimationCoordinateMeanImage(int spatialEstimationCoordinateMeanImage) {

        this.spatialEstimationCoordinateMeanImage = spatialEstimationCoordinateMeanImage;

    }

    public void setPersonalModelSizeImageHeight(int personalModelSizeImageHeight) {

        this.personalModelSizeImageHeight = personalModelSizeImageHeight;

    }

    public void setPersonalModelSizeImageWidth(int personalModelSizeImageWidth) {

        this.personalModelSizeImageWidth = personalModelSizeImageWidth;

    }

    public void setPersonalModelCoordinateMeanImage(int personalModelCoordinateMeanImage) {

        this.personalModelCoordinateMeanImage = personalModelCoordinateMeanImage;

    }

    public void setIdentificationFacialPointsSizeImageHeight(int identificationFacialPointsSizeImageHeight) {

        this.identificationFacialPointsSizeImageHeight = identificationFacialPointsSizeImageHeight;

    }

    public void setIdentificationFacialPointsSizeImageWidth(int identificationFacialPointsSizeImageWidth) {

        this.identificationFacialPointsSizeImageWidth = identificationFacialPointsSizeImageWidth;

    }

    public void setIdentificationFacialPointsCoordinateMeanImage(int identificationFacialPointsCoordinateMeanImage) {

        this.identificationFacialPointsCoordinateMeanImage = identificationFacialPointsCoordinateMeanImage;

    }

    public void setFaceTrackerCoordinateMeanStandard(float faceTrackerCoordinateMeanStandard) {

        this.faceTrackerCoordinateMeanStandard = faceTrackerCoordinateMeanStandard;

    }

    public void setSpatialEstimationCoordinateMeanStandard(float spatialEstimationCoordinateMeanStandard) {

        this.spatialEstimationCoordinateMeanStandard = spatialEstimationCoordinateMeanStandard;

    }

    public void setPersonalModelCoordinateMeanStandard(float personalModelCoordinateMeanStandard) {

        this.personalModelCoordinateMeanStandard = personalModelCoordinateMeanStandard;

    }

    public void setIdentificationFacialPointsCoordinateMeanStandard(float identificationFacialPointsCoordinateMeanStandard) {

        this.identificationFacialPointsCoordinateMeanStandard = identificationFacialPointsCoordinateMeanStandard;

    }

    public void setFaceTrackerFlattenAllocationBuffer(int[] faceTrackerFlattenAllocationBuffer) {

        this.faceTrackerFlattenAllocationBuffer = faceTrackerFlattenAllocationBuffer;

    }

    public void setPersonalModelFlattenAllocationBuffer(int[] personalModelFlattenAllocationBuffer) {

        this.personalModelFlattenAllocationBuffer = personalModelFlattenAllocationBuffer;

    }

    public void setSpatialEstimationFlattenAllocationBuffer(int[] spatialEstimationFlattenAllocationBuffer) {

        this.spatialEstimationFlattenAllocationBuffer = spatialEstimationFlattenAllocationBuffer;

    }

    public void setIdentificationFacialPointsFlattenAllocationBuffer(int[] identificationFacialPointsFlattenAllocationBuffer) {

        this.identificationFacialPointsFlattenAllocationBuffer = identificationFacialPointsFlattenAllocationBuffer;

    }

    public void setFaceTrackerBufferOutput(float[][] faceTrackerBufferOutput) {

        this.faceTrackerBufferOutput = faceTrackerBufferOutput;

    }

    public void setPersonalModelBufferOutput(float[][] personalModelBufferOutput) {

        this.personalModelBufferOutput = personalModelBufferOutput;

    }

    public void setSpatialEstimationBufferOutput(float[][] spatialEstimationBufferOutput) {

        this.spatialEstimationBufferOutput = spatialEstimationBufferOutput;

    }

    public void setIdentificationFacialPointsBufferOutput(float[][] identificationFacialPointsBufferOutput) {

        this.identificationFacialPointsBufferOutput = identificationFacialPointsBufferOutput;

    }

    public void setFaceTrackerByteBufferStreamInput(ByteBuffer faceTrackerByteBufferStreamInput) {

        this.faceTrackerByteBufferStreamInput = faceTrackerByteBufferStreamInput;

    }

    public void setPersonalModelByteBufferStreamInput(ByteBuffer personalModelByteBufferStreamInput) {

        this.personalModelByteBufferStreamInput = personalModelByteBufferStreamInput;

    }

    public void setSpatialEstimationByteBufferStreamInput(ByteBuffer spatialEstimationByteBufferStreamInput) {

        this.spatialEstimationByteBufferStreamInput = spatialEstimationByteBufferStreamInput;

    }

    public void setIdentificationFacialPointsByteBufferStreamInput(ByteBuffer identificationFacialPointsByteBufferStreamInput) {

        this.identificationFacialPointsByteBufferStreamInput = identificationFacialPointsByteBufferStreamInput;

    }

    public void setFaceTrackerFileModelNeural(String faceTrackerFileModel) {

        this.faceTrackerFileModel = faceTrackerFileModel;

    }

    public void setPersonalModelFileModelNeural(String personalModelFileModel) {

        this.personalModelFileModel = personalModelFileModel;

    }

    public void setSpatialEstimationFileModelNeural(String spatialEstimationFileModel) {

        this.spatialEstimationFileModel = spatialEstimationFileModel;

    }

    public void setIdentificationFacialPointsFileModelNeural(String identificationFacialFileModel) {

        this.identificationFacialFileModel = identificationFacialFileModel;

    }


}



