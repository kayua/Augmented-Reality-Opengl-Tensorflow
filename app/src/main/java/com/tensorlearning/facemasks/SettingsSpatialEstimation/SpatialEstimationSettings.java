package com.tensorlearning.facemasks.SettingsSpatialEstimation;



public class SpatialEstimationSettings {

    private final int spatialEstimationSizeImageHeight = 256;
    private final int spatialEstimationSizeImageWidth = 512;
    private final int spatialEstimationCoordinateMeanImage = 128;
    private final int spatialEstimationFlattenAllocationBuffer = spatialEstimationSizeImageHeight  * spatialEstimationSizeImageWidth;
    private final float spatialEstimationBufferOutput = 40;
    private final float spatialEstimationCoordinateMeanStandard = 128f;

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

    public float getSpatialEstimationBufferOutput() {

        return spatialEstimationBufferOutput;

    }

    public float getSpatialEstimationCoordinateMeanStandard() {

        return spatialEstimationCoordinateMeanStandard;

    }


}



