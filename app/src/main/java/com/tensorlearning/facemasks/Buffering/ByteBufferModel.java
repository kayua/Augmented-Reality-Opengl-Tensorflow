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
    private float faceTrackerCoordinateMeanStandard;
    public int[] faceTrackerFlattenAllocationBuffer = null;
    public float [][] faceTrackerBufferOutput;
    public ByteArrayOutputStream faceTrackerBufferStreamOutput = null;
    public ByteBuffer faceTrackerByteBufferStreamInput;


    private int personalModelsSizeImageHeight;
    private int personalModelsSizeImageWidth;
    private int personalModelsCoordinateMeanImage;
    private float personalModelsCoordinateMeanStandard;
    public float [][] personalModelsBufferOutput;
    public int[] personalFlattenAllocationBuffer = null;
    public ByteArrayOutputStream personalBufferStreamOutput = null;
    public ByteBuffer personalByteBufferStreamInput;


    private int identificationFacialPointsSizeImageHeight;
    private int identificationFacialPointsSizeImageWidth;
    private int identificationFacialPointsCoordinateMeanImage;
    private float identificationFacialPointsCoordinateMeanStandard;
    public  float [][] identificationFacialPointsBufferOutput;
    public final int[] identificationFacialPointsFlattenAllocationBuffer = null;
    public ByteArrayOutputStream identificationFacialPointsBufferStreamOutput = null;
    public ByteBuffer identificationFacialPointsByteBufferStreamInput;


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
































    private MappedByteBuffer loadModelFile(String file) throws IOException
    {
        AssetFileDescriptor assetFileDescriptor = this.foreignContext.getAssets().openFd(file);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();

        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);
    }


}



