package com.tensorlearning.facemasks.Settings.SettingsSpatialEstimation;



public class SpatialEstimationSettings {



    private final String spatialEstimationFileModel = "model_face.tflite";
    private final int spatialEstimationSizeImageHeight = 256;
    private final int spatialEstimationSizeImageWidth = 512;
    private final int spatialEstimationCoordinateMeanImage = 128;
    private final int spatialEstimationFlattenAllocationBuffer = spatialEstimationSizeImageHeight  * spatialEstimationSizeImageWidth*4;
    private final int spatialEstimationBufferOutput = 40;
    private final float spatialEstimationCoordinateMeanStandard = 128f;
    private final boolean spatialEstimationAllowBufferHandleOutput = true;
    private final int spatialEstimationNumberThreads = 2;
    private final boolean spatialEstimationAllowFp16PrecisionForFp32 = true;



    public boolean getSpatialEstimationAllowFp16PrecisionForFp32() {

        return spatialEstimationAllowFp16PrecisionForFp32;

    }

    public boolean getSpatialEstimationAllowBufferHandleOutput() {

        return spatialEstimationAllowBufferHandleOutput;

    }

    public int getSpatialEstimationNumberThreads() {

        return spatialEstimationNumberThreads;

    }

    public int getSpatialEstimationSizeImageHeight() {

        return spatialEstimationSizeImageHeight;

    }

    public int getSpatialEstimationSizeImageWidth() {

        return spatialEstimationSizeImageWidth;

    }

    public int getSpatialEstimationCoordinateMeanImage() {

        return spatialEstimationCoordinateMeanImage;

    }

    public int getSpatialEstimationFlattenAllocationBuffer() {

        return spatialEstimationFlattenAllocationBuffer;

    }

    public int getSpatialEstimationBufferOutput() {

        return spatialEstimationBufferOutput;

    }

    public float getSpatialEstimationCoordinateMeanStandard() {

        return spatialEstimationCoordinateMeanStandard;

    }

    public String getSpatialEstimationFileModel() {

        return spatialEstimationFileModel;

    }

}



