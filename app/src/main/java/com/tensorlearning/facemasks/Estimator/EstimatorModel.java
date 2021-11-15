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

    }

    public void estimatorAllocationBuffer(){


        this.estimatorBufferFirstFrameSequencesAxisX = new float[estimatorNumberPointsPerFace];
        this.estimatorBufferFirstFrameSequencesAxisY = new float[estimatorNumberPointsPerFace];
        this.estimatorBufferSecondFrameSequencesAxisX = new float[estimatorNumberPointsPerFace];
        this.estimatorBufferSecondFrameSequencesAxisY = new float[estimatorNumberPointsPerFace];
        this.estimatorBufferThirdFrameSequencesAxisX = new float[estimatorNumberPointsPerFace];
        this.estimatorBufferThirdFrameSequencesAxisY = new float[estimatorNumberPointsPerFace];
        this.estimatorBufferPredictionFrameSequencesAxisX = new float[estimatorNumberPointsPerFace];
        this.estimatorBufferPredictionFrameSequencesAxisY = new float[estimatorNumberPointsPerFace];


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

                estimatorBufferFirstFrameSequencesAxisX[i] = coordinatesPredicted[2*i];
                estimatorBufferFirstFrameSequencesAxisX[i] =  coordinatesPredicted[2*i+1];

            }

        }

        if(estimatorNumberSequences>= 1){

            for(int i = 0; i < estimatorNumberPointsPerFace; i++) {

                estimatorBufferSecondFrameSequencesAxisX[i] = coordinatesPredicted[2*i];
                estimatorBufferSecondFrameSequencesAxisY[i] = coordinatesPredicted[2*i+1];

            }
        }

        if(estimatorNumberSequences>= 2){


            estimatorBufferThirdFrameSequencesAxisX = estimatorBufferSecondFrameSequencesAxisX;
            estimatorBufferThirdFrameSequencesAxisY = estimatorBufferSecondFrameSequencesAxisY;
            estimatorBufferSecondFrameSequencesAxisX = estimatorBufferFirstFrameSequencesAxisX;
            estimatorBufferSecondFrameSequencesAxisY = estimatorBufferFirstFrameSequencesAxisY;


            for(int i = 0; i < estimatorNumberPointsPerFace; i++) {

                estimatorBufferFirstFrameSequencesAxisX[i] = coordinatesPredicted[2*i];
                estimatorBufferFirstFrameSequencesAxisY[i] = coordinatesPredicted[2*i+1];

            }

            estimatorBufferLoaded = true;

        }

        estimatorNumberSequences++;

    }


}



