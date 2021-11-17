package com.tensorlearning.facemasks.Engine.Texture;

import java.util.Vector;


public class TextureMTLFormat {


    private final Vector<Float> objectModelVerticesComponents;
    private final Vector<Float> objectModelTextureCoordinates;
    private final Vector<Short> objectModelSurfaceIndexVector;

    private int objectModelNumberVerticesComponents;
    private int objectModelNumberTextureCoordinates;
    private int objectModelNumberSurfaceIndexVector;

    private float[] objectModelVerticesComponentsFlattenBuffer;
    private float[] objectModelTextureCoordinatesFlattenBuffer;
    private short[] objectModelSurfaceIndexVectorFlattenBuffer;


    public TextureMTLFormat() {

        objectModelVerticesComponents = new Vector<>(0);
        objectModelTextureCoordinates = new Vector<>(0);
        objectModelSurfaceIndexVector = new Vector<>(0);

    }

    public void dynamicAllocationObject(){

        objectModelVerticesComponentsFlattenBuffer = new float[objectModelVerticesComponents.size()];
        objectModelTextureCoordinatesFlattenBuffer = new float[objectModelTextureCoordinates.size()];
        objectModelSurfaceIndexVectorFlattenBuffer = new short[objectModelSurfaceIndexVector.size()];


        for(int iterator = 0; iterator < objectModelVerticesComponents.size(); iterator++){

            objectModelVerticesComponentsFlattenBuffer[iterator] = objectModelVerticesComponents.get(iterator);

        }

        for(int iterator = 0; iterator < objectModelTextureCoordinates.size(); iterator++){

            objectModelTextureCoordinatesFlattenBuffer[iterator] = objectModelTextureCoordinates.get(iterator);

        }

        for(int iterator = 0; iterator < objectModelSurfaceIndexVector.size(); iterator++){

            objectModelSurfaceIndexVectorFlattenBuffer[iterator] = objectModelSurfaceIndexVector.get(iterator);

        }

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

    public void addObjectIndexComponents(short coordinateAxisX, short coordinateAxisY, short coordinateAxisZ){

        objectModelSurfaceIndexVector.add(coordinateAxisX);
        objectModelSurfaceIndexVector.add(coordinateAxisY);
        objectModelSurfaceIndexVector.add(coordinateAxisZ);

    }


    public float[] getObjectModelVerticesComponentsFlattenBuffer() {

        return objectModelVerticesComponentsFlattenBuffer;

    }

    public float[] getObjectModelTextureCoordinatesFlattenBuffer() {

        return objectModelTextureCoordinatesFlattenBuffer;

    }

    public short[] getObjectModelSurfaceIndexVectorFlattenBuffer() {

        return objectModelSurfaceIndexVectorFlattenBuffer;

    }


    public int getObjectModelNumberVerticesComponents() {

        return objectModelNumberVerticesComponents;

    }

    public int getObjectModelNumberTextureCoordinates() {

        return objectModelNumberTextureCoordinates;

    }

    public int getObjectModelNumberSurfacesVector() {

        return objectModelNumberSurfaceIndexVector;

    }


}