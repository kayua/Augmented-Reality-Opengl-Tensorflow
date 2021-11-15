package com.tensorlearning.facemasks.Settings.EstimatorSettings;



public class EstimatorSettings {


    private float estimatorConstantFirstAcceleration = 0.25f;
    private float estimatorConstantSecondAcceleration = 0.25f;
    private float estimatorConstantThirdAcceleration = 0.25f;

    private int estimatorNumberPointsPerFace = 40;
    private int estimatorNumberMaxSimultaneousFace = 40;

    public float getEstimatorConstantFirstAcceleration() {

        return estimatorConstantFirstAcceleration;

    }

    public float getEstimatorConstantSecondAcceleration() {

        return estimatorConstantSecondAcceleration;

    }

    public float getEstimatorConstantThirdAcceleration() {

        return estimatorConstantThirdAcceleration;

    }

    public int getEstimatorNumberPointsPerFace() {

        return estimatorNumberPointsPerFace;

    }

    public int getEstimatorNumberMaxSimultaneousFace() {

        return estimatorNumberMaxSimultaneousFace;

    }



}



