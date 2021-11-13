package com.tensorlearning.facemasks.Buffering;

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


public class ByteBufferModel {


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

    public ByteArrayOutputStream faceTrackerBufferStreamOutput = null;
    public ByteArrayOutputStream personalModelBufferStreamOutput = null;
    public ByteArrayOutputStream spatialEstimationBufferStreamOutput = null;
    public ByteArrayOutputStream identificationFacialPointsBufferStreamOutput = null;

    private String faceTrackerFileModel;
    private String personalModelFileModel;
    private String spatialEstimationFileModel;
    private String identificationFacialFileModel;

    Context foreignContext;


    public ByteBufferModel(Context context) {

        this.foreignContext = context;

    }


    private void FaceTrackerCastBitmapToByteBuffer(Bitmap bitmap) {

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


    private void PersonalModelCastBitmapToByteBuffer(Bitmap bitmap) {

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
        Log.d("1", "Timecost to put values into ByteBuffer: " + Long.toString(endTime - startTime));
    }


    private void IdentificationFacialPointsCastBitmapToByteBuffer(Bitmap bitmap) {

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
        Log.d("1", "Timecost to put values into ByteBuffer: " + Long.toString(endTime - startTime));
    }


    private void SpatialEstimationCastBitmapToByteBuffer(Bitmap bitmap) {

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
        Log.d("1", "Timecost to put values into ByteBuffer: " + Long.toString(endTime - startTime));
    }




    private MappedByteBuffer FaceTrackerLoadModelFile(String file) throws IOException
    {
        AssetFileDescriptor assetFileDescriptor = this.foreignContext.getAssets().openFd(file);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();

        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);
    }


}



