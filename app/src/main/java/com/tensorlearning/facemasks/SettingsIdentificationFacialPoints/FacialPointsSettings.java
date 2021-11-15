package com.tensorlearning.facemasks.SettingsIdentificationFacialPoints;



public class FacialPointsSettings {


    private final String identificationFacialPointsFileModel = "lite";
    private final int identificationFacialPointsSizeImageHeight = 256;
    private final int identificationFacialPointsSizeImageWidth = 512;
    private final int identificationFacialPointsCoordinateMeanImage = 128;
    private final int identificationFacialPointsFlattenAllocationBuffer = identificationFacialPointsSizeImageHeight * identificationFacialPointsSizeImageWidth;
    private final int identificationFacialPointsBufferOutput = 40;
    private final float identificationFacialPointsCoordinateMeanStandard = 128f;
    private final boolean identificationFacialPointsAllowBufferHandleOutput = true;
    private final boolean identificationFacialPointsAllowFp16PrecisionForFp32 = true;
    private final int identificationFacialPointsNumberThreads = 2;



    public boolean getIdentificationFacialPointsAllowFp16PrecisionForFp32() {

        return identificationFacialPointsAllowFp16PrecisionForFp32;

    }

    public boolean getIdentificationFacialPointsAllowBufferHandleOutput() {

        return identificationFacialPointsAllowBufferHandleOutput;

    }

    public int getIdentificationFacialPointsNumberThreads() {

        return identificationFacialPointsNumberThreads;

    }

    public int getIdentificationFacialPointsSizeImageHeight() {

        return identificationFacialPointsSizeImageHeight;

    }

    public int getIdentificationFacialPointsSizeImageWidth() {

        return identificationFacialPointsSizeImageWidth;

    }

    public int getIdentificationFacialPointsCoordinateMeanImage() {

        return identificationFacialPointsCoordinateMeanImage;

    }

    public int getIdentificationFacialPointsFlattenAllocationBuffer() {

        return identificationFacialPointsFlattenAllocationBuffer;

    }

    public int getIdentificationFacialPointsBufferOutput() {

        return identificationFacialPointsBufferOutput;

    }

    public float getIdentificationFacialPointsCoordinateMeanStandard() {

        return identificationFacialPointsCoordinateMeanStandard;

    }

    public String getIdentificationFacialPointsFileModel() {

        return identificationFacialPointsFileModel;

    }


}



