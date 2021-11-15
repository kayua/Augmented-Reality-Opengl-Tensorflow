package com.tensorlearning.facemasks.SettingsSpatialEstimation;



public class SpatialEstimationSettings {

    private final int spatialEstimationSizeImageHeight = 256;
    private final int spatialEstimationSizeImageWidth = 512;
    private final int spatialEstimationCoordinateMeanImage = 128;
    private final int spatialEstimationFlattenAllocationBuffer = spatialEstimationSizeImageHeight  * spatialEstimationSizeImageWidth;
    private final int spatialEstimationBufferOutput = 40;
    private final float spatialEstimationCoordinateMeanStandard = 128f;
    private final boolean spatialEstimationAllowBufferHandleOutput = true;
    private final int spatialEstimationNumberThreads = 2;

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


}



