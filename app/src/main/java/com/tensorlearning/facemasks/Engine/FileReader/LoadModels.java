package com.tensorlearning.facemasks.Engine.FileReader;

import android.content.Context;

import com.tensorlearning.facemasks.Engine.Buffering.MemoryAllocation;
import com.tensorlearning.facemasks.Engine.Formats.WavefrontFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;


public final class LoadModels {


    private String stringBufferReadLine;
    private BufferedReader bufferReaderObject = null;
    private InputStreamReader fileInputReference;
    private Context context;
    private WavefrontFormat objectModelWavefront;

    private float modelVerticesCoordinateX;
    private float modelVerticesCoordinateY;
    private float modelVerticesCoordinateZ;

    private float modelTextureCoordinateX;
    private float modelTextureCoordinateY;
    private float modelTextureCoordinateZ;
    
    private short modelSurfaceIndexPlaneFirst;
    private short modelSurfaceIndexPlaneSecond;
    private short modelSurfaceIndexPlaneThird;


    private MemoryAllocation memoryAllocationModel;


    public LoadModels(Context context) {

        this.context = context;
        allocationMemoryNewObject();

    }


    public void allocationMemoryNewObject(){

        this.objectModelWavefront = new WavefrontFormat();

    }


    public void readFileModelObject(int typeObjectModel, String fileNameModelObject){


        if(typeObjectModel==0){

            try {

                fileInputReference = new InputStreamReader(context.getAssets().open(fileNameModelObject));
                bufferReaderObject = new BufferedReader(fileInputReference);

                while ((stringBufferReadLine = bufferReaderObject.readLine()) != null) {

                    decomposeFileModelObjectWavefront(stringBufferReadLine.split("\\s"));

                }

            } catch (IOException ignored) {

            } finally {

                objectModelWavefront.dynamicAllocationObject();
                allocationByteBufferModel();

                if (bufferReaderObject != null) {

                    try { bufferReaderObject.close(); } catch (IOException ignored) { }

                }
            }

        }


    }


    public void decomposeFileModelObjectWavefront(String[] readeLineModelFile){


        if(readeLineModelFile[0].equals("v")){

            modelVerticesCoordinateX = Float.parseFloat(readeLineModelFile[1]);
            modelVerticesCoordinateY = Float.parseFloat(readeLineModelFile[2]);
            modelVerticesCoordinateZ = Float.parseFloat(readeLineModelFile[3]);
            objectModelWavefront.addVerticesComponents(modelVerticesCoordinateX, modelVerticesCoordinateY, modelVerticesCoordinateZ);

        }

        if(readeLineModelFile[0].equals("vt")){

            modelTextureCoordinateX = Float.parseFloat(readeLineModelFile[1]);
            modelTextureCoordinateY = Float.parseFloat(readeLineModelFile[2]);
            modelTextureCoordinateZ = Float.parseFloat(readeLineModelFile[3]);
            objectModelWavefront.addTexturesComponents(modelTextureCoordinateX, modelTextureCoordinateY, modelTextureCoordinateZ);

        }

        if(readeLineModelFile[0].equals("f")){

            modelSurfaceIndexPlaneFirst = Short.parseShort(readeLineModelFile[1]);
            modelSurfaceIndexPlaneSecond = Short.parseShort(readeLineModelFile[2]);
            modelSurfaceIndexPlaneThird = Short.parseShort(readeLineModelFile[3]);
            objectModelWavefront.addObjectIndexComponents(modelSurfaceIndexPlaneFirst, modelSurfaceIndexPlaneSecond, modelSurfaceIndexPlaneThird);


        }


    }

    public void allocationByteBufferModel(){

        memoryAllocationModel = new MemoryAllocation();
        memoryAllocationModel.setObjectModelSurfaceIndexVectorFlattenBuffer(objectModelWavefront.getObjectModelSurfaceIndexVectorFlattenBuffer());
        memoryAllocationModel.setObjectModelVerticesComponentsFlattenBuffer(objectModelWavefront.getObjectModelVerticesComponentsFlattenBuffer());
        memoryAllocationModel.setObjectModelTextureCoordinatesFlattenBuffer(objectModelWavefront.getObjectModelTextureCoordinatesFlattenBuffer());
        memoryAllocationModel.allocationBufferSurfaceIndexModel();
        memoryAllocationModel.allocationBufferTextureModel();
        memoryAllocationModel.allocationBufferVerticesModel();

    }

    public ByteBuffer getByteBufferVertices(){

        return memoryAllocationModel.getByteBufferVertices();

    }

    public ByteBuffer getByteBufferIndex(){

        return memoryAllocationModel.getByteBufferIndex();

    }

    public ByteBuffer getByteBufferTexture(){

        return memoryAllocationModel.getByteBufferTexture();

    }

}