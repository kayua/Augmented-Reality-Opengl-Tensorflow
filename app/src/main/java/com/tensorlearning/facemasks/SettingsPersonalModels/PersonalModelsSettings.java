package com.tensorlearning.facemasks.SettingsPersonalModels;



public class PersonalModelsSettings {

    private final int personalModelsSizeImageHeight = 256;
    private final int personalModelsSizeImageWidth = 512;
    private final int personalModelsCoordinateMeanImage = 128;
    private final int personalModelsFlattenAllocationBuffer = personalModelsSizeImageHeight * personalModelsSizeImageWidth;
    private final float personalModelsBufferOutput = 40;
    private final float personalModelsCoordinateMeanStandard = 128f;

    public int getPersonalModelsSizeImageHeight() {

        return personalModelsSizeImageHeight;

    }

    public int getPersonalModelsSizeImageWidth() {

        return personalModelsSizeImageWidth;

    }

    public int getPersonalModelsCoordinateMeanImage() {

        return personalModelsCoordinateMeanImage;

    }

    public int getPersonalModelsFlattenAllocationBuffer() {

        return personalModelsFlattenAllocationBuffer;

    }

    public float getPersonalModelsBufferOutput() {

        return personalModelsBufferOutput;

    }

    public float getPersonalModelsCoordinateMeanStandard() {

        return personalModelsCoordinateMeanStandard;

    }


}



