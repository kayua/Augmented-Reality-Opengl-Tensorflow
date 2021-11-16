package com.tensorlearning.facemasks.Engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

public class ObjectModel {

    private int objectNumberComponentsPerPlane;

    private final Vector<Float> objectModelVerticesComponents;
    private final Vector<Float> objectModelTextureCoordinates;
    private final Vector<Byte> objectModelObjectPositions;

    private float[] objectModelVerticesComponentsFlattenBuffer;
    private float[] objectModelTextureCoordinatesFlattenBuffer;
    private byte[] objectModelIndexFlattenBuffer;

    public FloatBuffer floatBufferVerticesComponents;
    public FloatBuffer floatBufferTextureComponents;

    public ByteBuffer byteBufferVertices;
    public ByteBuffer byteBufferTexture;
    public ByteBuffer byteBufferIndex;


    public ObjectModel() {

        objectModelVerticesComponents = new Vector<>(0);
        objectModelTextureCoordinates = new Vector<>(0);
        objectModelObjectPositions = new Vector<>(0);

    }

    public void dynamicAllocationObject(){

        objectModelVerticesComponentsFlattenBuffer = new float[objectModelVerticesComponents.size()];
        objectModelTextureCoordinatesFlattenBuffer = new float[objectModelTextureCoordinates.size()];
        objectModelIndexFlattenBuffer = new byte[objectModelObjectPositions.size()];

        for(int iterator = 0; iterator < objectModelVerticesComponents.size(); iterator++){

            objectModelVerticesComponentsFlattenBuffer[iterator] = objectModelVerticesComponents.get(iterator);

        }

        for(int iterator = 0; iterator < objectModelTextureCoordinates.size(); iterator++){

            objectModelTextureCoordinatesFlattenBuffer[iterator] = objectModelTextureCoordinates.get(iterator);

        }

        for(int iterator = 0; iterator < objectModelObjectPositions.size(); iterator++){

            objectModelIndexFlattenBuffer[iterator] = objectModelObjectPositions.get(iterator);

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

    public void addObjectIndexComponents(int numberComponents, byte coordinateAxisX, byte coordinateAxisY, byte coordinateAxisZ){

        objectModelObjectPositions.add(coordinateAxisX);
        objectModelObjectPositions.add(coordinateAxisY);
        objectModelObjectPositions.add(coordinateAxisZ);
        objectNumberComponentsPerPlane = numberComponents;

    }


    public void allocationBufferVerticesModel() {

        byteBufferVertices = ByteBuffer.allocateDirect(objectModelVerticesComponentsFlattenBuffer.length * 4);
        byteBufferVertices.order(ByteOrder.nativeOrder());
        floatBufferVerticesComponents = byteBufferVertices.asFloatBuffer();
        floatBufferVerticesComponents.put(objectModelVerticesComponentsFlattenBuffer);
        floatBufferVerticesComponents.position(0);

    }

    public void allocationBufferTextureModel() {

        byteBufferTexture = ByteBuffer.allocateDirect(objectModelTextureCoordinatesFlattenBuffer.length * 4);
        byteBufferTexture.order(ByteOrder.nativeOrder());
        floatBufferTextureComponents = byteBufferTexture.asFloatBuffer();
        floatBufferTextureComponents.put(objectModelTextureCoordinatesFlattenBuffer);
        floatBufferTextureComponents.position(0);

    }

    public void allocationBufferIndexModel() {

        byteBufferIndex = ByteBuffer.allocateDirect(objectModelIndexFlattenBuffer.length);
        byteBufferIndex.put(objectModelIndexFlattenBuffer);
        byteBufferIndex.position(0);


    }



}