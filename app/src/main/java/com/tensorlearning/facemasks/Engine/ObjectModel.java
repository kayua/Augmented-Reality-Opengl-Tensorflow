package com.tensorlearning.facemasks.Engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

public class ObjectModel {

    private final int objectModelNumberFaces;
    private final int objectModelNumberNormals;
    private final int objectModelIdObject;

    private int objectNumberComponentsPerPlane;

    private String  objectModelMaterialReference;

    private Vector<Float> objectModelVerticesComponents;
    private Vector<Float> objectModelTextureCoordinates;
    private Vector<Byte> objectModelObjectPositions;

    private float[] objectModelVerticesComponentsFlattenBuffer;
    private float[] objectModelTextureCoordinatesFlattenBuffer;
    private byte[] objectModelIndexFlattenBuffer;

    public FloatBuffer floatBufferVerticesComponents;
    public FloatBuffer floatBufferTextureComponents;
    public ByteBuffer floatBufferIndexComponents;

    public ByteBuffer byteBufferVertices;
    public ByteBuffer byteBufferTexture;
    public ByteBuffer byteBufferIndex;


    public ObjectModel() {

        objectModelNumberFaces = 0;
        objectModelNumberNormals = 0;
        objectModelIdObject = 0;
        objectModelMaterialReference = "";
        objectModelVerticesComponents = new Vector<>(0);
        objectModelTextureCoordinates = new Vector<>(0);
        objectModelObjectPositions = new Vector<>(0);

    }

    public void createBufferObject(){

        objectModelVerticesComponentsFlattenBuffer = new float[objectModelVerticesComponents.size()];
        objectModelTextureCoordinatesFlattenBuffer = new float[objectModelTextureCoordinates.size()];
        objectModelIndexFlattenBuffer = new byte[objectModelObjectPositions.size()];

        for(int i = 0; i < objectModelVerticesComponents.size(); i++){

            objectModelVerticesComponentsFlattenBuffer[i] = objectModelVerticesComponents.get(i);

        }

        for(int i = 0; i < objectModelTextureCoordinates.size(); i++){

            objectModelTextureCoordinatesFlattenBuffer[i] = objectModelTextureCoordinates.get(i);

        }

        for(int i = 0; i < objectModelObjectPositions.size(); i++){

            objectModelIndexFlattenBuffer[i] = objectModelObjectPositions.get(i);

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


    public void bufferedVerticesModel() {

        byteBufferVertices = ByteBuffer.allocateDirect(objectModelVerticesComponentsFlattenBuffer.length * 4);
        byteBufferVertices.order(ByteOrder.nativeOrder());
        floatBufferVerticesComponents = byteBufferVertices.asFloatBuffer();
        floatBufferVerticesComponents.put(objectModelVerticesComponentsFlattenBuffer);
        floatBufferVerticesComponents.position(0);

    }

    public void bufferedTextureModel() {

        byteBufferTexture = ByteBuffer.allocateDirect(objectModelTextureCoordinatesFlattenBuffer.length * 4);
        byteBufferTexture.order(ByteOrder.nativeOrder());
        floatBufferTextureComponents = byteBufferTexture.asFloatBuffer();
        floatBufferTextureComponents.put(objectModelTextureCoordinatesFlattenBuffer);
        floatBufferTextureComponents.position(0);

    }

    public void bufferedIndexModel() {

        byteBufferIndex = ByteBuffer.allocateDirect(objectModelIndexFlattenBuffer.length);
        floatBufferIndexComponents.put(objectModelIndexFlattenBuffer);
        floatBufferIndexComponents.position(0);


    }



}