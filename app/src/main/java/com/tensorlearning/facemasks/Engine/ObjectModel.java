package com.tensorlearning.facemasks.Engine;

import java.util.Vector;

public final class ObjectModel {

    private int objectModelNumberFaces;
    private int objectModelNumberNormal;
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

        objectModelNormalComponents.add(coordinateAxisX);
        objectModelNormalComponents.add(coordinateAxisY);
        objectModelNormalComponents.add(coordinateAxisZ);
        objectModelNumberNormal = objectModelNumberNormal + 1;

    }

    public void addTextureComponents(float coordinateAxisX, float coordinateAxisY, float coordinateAxisZ){

        objectModelTextureCoordinates.add(coordinateAxisX);
        objectModelTextureCoordinates.add(coordinateAxisY);
        objectModelTextureCoordinates.add(coordinateAxisZ);
        objectModelNumberNormal = objectModelNumberNormal + 1;

    }

    public void addObjectPositionsComponents(float coordinateAxisX, float coordinateAxisY, float coordinateAxisZ){

        objectModelObjectPositions.add(coordinateAxisX);
        objectModelObjectPositions.add(coordinateAxisY);
        objectModelObjectPositions.add(coordinateAxisZ);
        objectModelNumberNormal = objectModelNumberNormal + 1;

    }




    public ObjectModel() {

        objectModelNumberFaces = 0;
        objectModelNumberNormal = 0;
        objectModelIdObject = 0;
        objectModelMaterialReference = "";
        objectModelNormalComponents = new Vector<>();
        objectModelTextureCoordinates = new Vector<>();
        objectModelObjectPositions = new Vector<>();

    }


    public void createBufferObject(){

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


    public float[] getObjectModelNormalComponentsFlattenBuffer() {

        return objectModelNormalComponentsFlattenBuffer;

    }


    public float[] getObjectModelTextureCoordinatesFlattenBuffer() {

        return objectModelTextureCoordinatesFlattenBuffer;

    }


    public float[] getObjectModelObjectPositionsFlattenBuffer() {

        return objectModelObjectPositionsFlattenBuffer;

    }



}