package com.tensorlearning.facemasks.SettingsIdentificationFacialPoints;



public class FacialPointsSettings {

    private final int identificationFacialPointsSizeImageHeight = 256;
    private final int identificationFacialPointsSizeImageWidth = 512;
    private final int identificationFacialPointsCoordinateMeanImage = 128;
    private final int identificationFacialPointsFlattenAllocationBuffer = identificationFacialPointsSizeImageHeight * identificationFacialPointsSizeImageWidth;
    private final float identificationFacialPointsBufferOutput = 40;
    private final float identificationFacialPointsCoordinateMeanStandard = 128f;

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

    public float getIdentificationFacialPointsBufferOutput() {

        return identificationFacialPointsBufferOutput;

    }

    public float getIdentificationFacialPointsCoordinateMeanStandard() {

        return identificationFacialPointsCoordinateMeanStandard;

    }


}



