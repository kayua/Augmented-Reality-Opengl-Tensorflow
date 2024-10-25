package com.tensorlearning.facemasks.Settings.SettingsPersonalModels;



public class PersonalModelsSettings {



    private final String personalModelFileModel = "model_face.tflite";
    private final int personalModelsSizeImageHeight = 128;
    private final int personalModelsSizeImageWidth = 256;
    private final int personalModelsCoordinateMeanImage = 128;
    private final int personalModelsFlattenAllocationBuffer = personalModelsSizeImageHeight * personalModelsSizeImageWidth*4;
    private final int personalModelsBufferOutput = 40;
    private final float personalModelsCoordinateMeanStandard = 128f;
    private final boolean personalModelsAllowBufferHandleOutput = true;
    private final int personalModelsNumberThreads = 2;
    private final boolean personalModelsAllowFp16PrecisionForFp32 = true;


    public boolean getPersonalModelsAllowFp16PrecisionForFp32() {

        return personalModelsAllowFp16PrecisionForFp32;

    }

    public boolean getPersonalModelsAllowBufferHandleOutput() {

        return personalModelsAllowBufferHandleOutput;

    }

    public int getPersonalModelsNumberThreads() {

        return personalModelsNumberThreads;

    }

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

    public int getPersonalModelsBufferOutput() {

        return personalModelsBufferOutput;

    }

    public float getPersonalModelsCoordinateMeanStandard() {

        return personalModelsCoordinateMeanStandard;

    }

    public String getPersonalModelFileModel() {

        return personalModelFileModel;

    }

}



