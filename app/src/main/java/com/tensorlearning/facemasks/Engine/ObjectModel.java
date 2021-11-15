package com.tensorlearning.facemasks.Engine;

import java.util.Vector;

public final class ObjectModel {

    private int objectModelNumberFaces;
    private int objectModelIdObject;
    private String  objectModelMaterialReference;

    private Vector<Float> objectModelNormalComponents;
    private Vector<Float> objectModelTextureCoordinates;
    private Vector<Float> objectModelObjectPositions;

    private float[] objectModelNormalComponentsFlattenBuffer;
    private float[] objectModelTextureCoordinatesFlattenBuffer;
    private float[] objectModelObjectPositionsFlattenBuffer;


    public int getObjectModelNumberFaces() {

        return objectModelNumberFaces;

    }

    public int getObjectModelIdObject() {

        return objectModelIdObject;

    }

    public String getObjectModelMaterialReference() {

        return objectModelMaterialReference;

    }

    public void addNormalComponents(float coordinateAxisX, float coordinateAxisY, float coordinateAxisZ){



    }

    public ObjectModel() {

        objectModelNumberFaces = 0;
        objectModelIdObject = 0;
        objectModelMaterialReference = "";
        objectModelNormalComponents = new Vector<>();
        objectModelTextureCoordinates = new Vector<>();
        objectModelObjectPositions = new Vector<>();

    }

    public void getBufferObject(){

        objectModelNormalComponentsFlattenBuffer = new float[objectModelNormalComponents.size()];
        objectModelTextureCoordinatesFlattenBuffer = new float[objectModelTextureCoordinates.size()];
        objectModelObjectPositionsFlattenBuffer  = new  float[objectModelObjectPositions.size()];

        for(int i = 0; i < objectModelNormalComponents.size(); i++){

            objectModelNormalComponentsFlattenBuffer[i] = objectModelNormalComponents.get(i);

        }

        for(int i = 0; i < objectModelNormalComponents.size(); i++){

            objectModelTextureCoordinatesFlattenBuffer[i] = objectModelTextureCoordinates.get(i);

        }

        for(int i = 0; i < objectModelObjectPositions.size(); i++){

            objectModelObjectPositionsFlattenBuffer[i] = objectModelObjectPositions.get(i);

        }



    }





}