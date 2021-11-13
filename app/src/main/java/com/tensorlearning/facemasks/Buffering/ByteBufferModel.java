package com.tensorlearning.facemasks.Buffering;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class ByteBufferModel {


    private final int modelFaceTrackerSizeImageHeight = 128;
    private final int modelFaceTrackerSizeImageWidth = 256;

    private final int modelFaceTrackerSizeImageHeight = 128;
    private final int modelFaceTrackerSizeImageWidth = 256;
    
    
    
    
    
    
    
    
    
    
    
    private final int valueSizeImageHeight = 128;
    private final int valueSizeImageWidth = 256;
    private final int coordinateMeanImage = 128;
    private final float coordinateMeanStandard = 128.0f;
    public  float [][] bufferOutputModel;
    ByteArrayOutputStream bufferStreamOutputModel = new ByteArrayOutputStream();
    private final int[] flattenAllocationBuffer = new int[valueSizeImageHeight * size_image_y];
    java.nio.ByteBuffer byteBufferStreamInputModel;
    Context foreignContext;

    public ByteBufferModel(Context context) {

        this.foreignContext = context;

    }

    private void convertBitmapToByteBuffer(Bitmap bitmap) {

        if (byteBufferStreamInputModel == null) { return; }
        byteBufferStreamInputModel.rewind();
        bitmap.getPixels(flattenAllocationBuffer, 0, bitmap.getWidth(), 0, 0, valueSizeImageHeight, size_image_y);
        int pixel = 0;
        long startTime = SystemClock.uptimeMillis();
        for (int i = 0; i < valueSizeImageHeight; ++i) {
            for (int j = 0; j < size_image_y; ++j) {
                final int val = flattenAllocationBuffer[pixel++];
                byteBufferStreamInputModel.put((byte) ((((val >> 16) & 0xFF)-IMAGE_MEAN)/IMAGE_STD));
                byteBufferStreamInputModel.put((byte) ((((val >> 8) & 0xFF)-IMAGE_MEAN)/IMAGE_STD));
                byteBufferStreamInputModel.put((byte) ((((val) & 0xFF)-IMAGE_MEAN)/IMAGE_STD));
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



