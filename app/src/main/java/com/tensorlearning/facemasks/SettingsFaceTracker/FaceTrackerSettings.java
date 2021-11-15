package com.tensorlearning.facemasks.SettingsFaceTracker;



public class FaceTrackerSettings {


    private final String faceTrackerFileModel = "lite";
    private final int faceTrackerSizeImageHeight = 256;
    private final int faceTrackerSizeImageWidth = 512;
    private final int faceTrackerCoordinateMeanImage = 128;
    private final int faceTrackerFlattenAllocationBuffer = faceTrackerSizeImageHeight * faceTrackerSizeImageWidth;
    private final int faceTrackerBufferOutput = 40;
    private final float faceTrackerCoordinateMeanStandard = 128f;
    private final boolean faceTrackerAllowBufferHandleOutput = true;
    private final boolean faceTrackerAllowFp16PrecisionForFp32 = true;
    private final int faceTrackerNumberThreads = 2;


    public int getFaceTrackerNumberThreads() {

        return faceTrackerNumberThreads;

    }

    public int getFaceTrackerSizeImageHeight() {

        return faceTrackerSizeImageHeight;

    }

    public int getFaceTrackerSizeImageWidth() {

        return faceTrackerSizeImageWidth;

    }

    public int getFaceTrackerCoordinateMeanImage() {

        return faceTrackerCoordinateMeanImage;

    }

    public int getFaceTrackerFlattenAllocationBuffer() {

        return faceTrackerFlattenAllocationBuffer;

    }

    public int getFaceTrackerBufferOutput() {

        return faceTrackerBufferOutput;

    }

    public float getFaceTrackerCoordinateMeanStandard() {

        return faceTrackerCoordinateMeanStandard;

    }

    public boolean getFaceTrackerAllowBufferHandleOutput() {

        return faceTrackerAllowBufferHandleOutput;

    }

    public boolean getFaceTrackerAllowFp16PrecisionForFp32() {

        return faceTrackerAllowFp16PrecisionForFp32;

    }

    public String getFaceTrackerFileModel() {

        return faceTrackerFileModel;

    }


}


