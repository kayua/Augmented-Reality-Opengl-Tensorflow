package com.tensorlearning.facemasks.Estimator;



public class EstimatorModel {


    private final float estimatorConstantFirstAcceleration = 128;
    private final float estimatorConstantSecondAcceleration = 128;

    private final int estimatorNumberPointsPerFace = 40;
    private final int estimatorNumberMaxSimultaneousFace = 3;

    public float[] estimatorBufferFirstFrameSequencesAxisX;
    public float[] estimatorBufferFirstFrameSequencesAxisY;
    public float[] estimatorBufferSecondFrameSequencesAxisX;
    public float[] estimatorBufferSecondFrameSequencesAxisY;



}



