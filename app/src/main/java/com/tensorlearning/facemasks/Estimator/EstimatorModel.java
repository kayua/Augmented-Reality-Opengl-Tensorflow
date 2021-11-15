package com.tensorlearning.facemasks.Estimator;



public class EstimatorModel {


    private float estimatorConstantFirstAcceleration;
    private float estimatorConstantSecondAcceleration;
    private float estimatorConstantThirdAcceleration;

    private int estimatorNumberPointsPerFace;
    private int estimatorNumberMaxSimultaneousFace;

    public float[] estimatorBufferFirstFrameSequencesAxisX;
    public float[] estimatorBufferFirstFrameSequencesAxisY;
    public float[] estimatorBufferSecondFrameSequencesAxisX;
    public float[] estimatorBufferSecondFrameSequencesAxisY;
    public float[] estimatorBufferThirdFrameSequencesAxisX;
    public float[] estimatorBufferThirdFrameSequencesAxisY;


    public void setEstimatorConstantFirstAcceleration(float estimatorConstantFirstAcceleration) {

        this.estimatorConstantFirstAcceleration = estimatorConstantFirstAcceleration;

    }

    public void setEstimatorConstantSecondAcceleration(float estimatorConstantSecondAcceleration) {

        this.estimatorConstantSecondAcceleration = estimatorConstantSecondAcceleration;

    }

    public void setEstimatorConstantThirdAcceleration(float estimatorConstantThirdAcceleration) {

        this.estimatorConstantThirdAcceleration = estimatorConstantThirdAcceleration;

    }

    public void setEstimatorNumberPointsPerFace(int estimatorNumberPointsPerFace) {

        this.estimatorNumberPointsPerFace = estimatorNumberPointsPerFace;

    }

    public void setEstimatorNumberMaxSimultaneousFace(int estimatorNumberMaxSimultaneousFace) {

        this.estimatorNumberMaxSimultaneousFace = estimatorNumberMaxSimultaneousFace;

    }





}



