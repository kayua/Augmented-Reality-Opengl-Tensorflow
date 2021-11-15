package com.tensorlearning.facemasks.Estimator;



public class EstimatorModel {


    private float estimatorConstantFirstAcceleration;
    private float estimatorConstantSecondAcceleration;
    private float estimatorConstantThirdAcceleration;

    private int estimatorNumberPointsPerFace;

    public float[] estimatorBufferFirstFrameSequencesAxisX;
    public float[] estimatorBufferFirstFrameSequencesAxisY;
    public float[] estimatorBufferSecondFrameSequencesAxisX;
    public float[] estimatorBufferSecondFrameSequencesAxisY;
    public float[] estimatorBufferThirdFrameSequencesAxisX;
    public float[] estimatorBufferThirdFrameSequencesAxisY;

    public float[] estimatorBufferPredictionFrameSequencesAxisX;
    public float[] estimatorBufferPredictionFrameSequencesAxisY;

    public boolean estimatorBufferLoaded;
    private int estimatorNumberSequences;

    public EstimatorModel() {

        estimatorBufferLoaded = false;
        estimatorNumberSequences = 0;

        estimatorBufferFirstFrameSequencesAxisX = new float[estimatorNumberPointsPerFace];
        estimatorBufferFirstFrameSequencesAxisY = new float[estimatorNumberPointsPerFace];
        estimatorBufferSecondFrameSequencesAxisX = new float[estimatorNumberPointsPerFace];
        estimatorBufferSecondFrameSequencesAxisY = new float[estimatorNumberPointsPerFace];
        estimatorBufferThirdFrameSequencesAxisX = new float[estimatorNumberPointsPerFace];
        estimatorBufferThirdFrameSequencesAxisY = new float[estimatorNumberPointsPerFace];
        estimatorBufferPredictionFrameSequencesAxisX = new float[estimatorNumberPointsPerFace];
        estimatorBufferPredictionFrameSequencesAxisY = new float[estimatorNumberPointsPerFace];


    }

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

    }

    public void generateEstimating(){



    }

    public void addEstimationBufferPredictions(float[] coordinatesPredicted){

        if(estimatorNumberSequences>= 0){

            for(int i = 0; i < estimatorNumberPointsPerFace; i++) {

            }

        }

        if(estimatorNumberSequences>= 1){

            for(int i = 0; i < estimatorNumberPointsPerFace; i++) {


            }
        }

        if(estimatorNumberSequences>= 2){


            for(int i = 0; i < estimatorNumberPointsPerFace; i++) {


            }

            estimatorBufferLoaded = true;

        }

        estimatorNumberSequences++;

    }


}



