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
    private float[] objectModelTextureCoordinatesFlattenBuffer;;
    private float[] objectModelObjectPositionsFlattenBuffer;;


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







}