package com.tensorlearning.facemasks.Engine.Formats;
import java.util.Vector;

public class WavefrontFormat {

    private int objectNumberComponentsPerPlane;



    private final Vector<Float> objectModelVerticesComponents;
    private final Vector<Float> objectModelTextureCoordinates;
    private final Vector<Short> objectModelObjectPositions;

    private float[] objectModelVerticesComponentsFlattenBuffer;
    private float[] objectModelTextureCoordinatesFlattenBuffer;
    private short[] objectModelIndexComponentsFlattenBuffer;



    public WavefrontFormat() {

        objectModelVerticesComponents = new Vector<>(0);
        objectModelTextureCoordinates = new Vector<>(0);
        objectModelObjectPositions = new Vector<>(0);

    }

    public void dynamicAllocationObject(){

        objectModelVerticesComponentsFlattenBuffer = new float[objectModelVerticesComponents.size()];
        objectModelTextureCoordinatesFlattenBuffer = new float[objectModelTextureCoordinates.size()];
        objectModelIndexComponentsFlattenBuffer = new short[objectModelObjectPositions.size()];


        for(int iterator = 0; iterator < objectModelVerticesComponents.size(); iterator++){

            objectModelVerticesComponentsFlattenBuffer[iterator] = objectModelVerticesComponents.get(iterator);

        }

        for(int iterator = 0; iterator < objectModelTextureCoordinates.size(); iterator++){

            objectModelTextureCoordinatesFlattenBuffer[iterator] = objectModelTextureCoordinates.get(iterator);

        }

        for(int iterator = 0; iterator < objectModelObjectPositions.size(); iterator++){

            objectModelIndexComponentsFlattenBuffer[iterator] = objectModelObjectPositions.get(iterator);

        }

    }

    public int getObjectNumberComponentsPerPlane() {

        return objectNumberComponentsPerPlane;

    }


    public void addVerticesComponents(float coordinateAxisX, float coordinateAxisY, float coordinateAxisZ){

        objectModelVerticesComponents.add(coordinateAxisX);
        objectModelVerticesComponents.add(coordinateAxisY);
        objectModelVerticesComponents.add(coordinateAxisZ);

    }

    public void addTexturesComponents(float coordinateAxisX, float coordinateAxisY, float coordinateAxisZ){

        objectModelTextureCoordinates.add(coordinateAxisX);
        objectModelTextureCoordinates.add(coordinateAxisY);
        objectModelTextureCoordinates.add(coordinateAxisZ);

    }

    public void addObjectIndexComponents(int numberComponents, short coordinateAxisX, short coordinateAxisY, short coordinateAxisZ){

        objectModelObjectPositions.add(coordinateAxisX);
        objectModelObjectPositions.add(coordinateAxisY);
        objectModelObjectPositions.add(coordinateAxisZ);
        objectNumberComponentsPerPlane = numberComponents;

    }


    public float[] getObjectModelVerticesComponentsFlattenBuffer() {

        return objectModelVerticesComponentsFlattenBuffer;

    }

    public float[] getObjectModelTextureCoordinatesFlattenBuffer() {

        return objectModelTextureCoordinatesFlattenBuffer;

    }

    public short[] getObjectModelIndexComponentsFlattenBuffer() {

        return objectModelIndexComponentsFlattenBuffer;

    }

}