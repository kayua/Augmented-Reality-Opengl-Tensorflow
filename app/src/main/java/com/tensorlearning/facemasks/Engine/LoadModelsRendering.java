package com.tensorlearning.facemasks.Engine;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public final class LoadModelsRendering {


    private String stringBufferReadLine;
    private String[] fileTemporaryProcessingLine;
    private ArrayList<String> fileObjectModel;
    private BufferedReader bufferReaderObject = null;
    private InputStreamReader fileInputReference;
    private int numberObjectsRendered=0;
    public ArrayList<ObjectModel> objectModelStruct;
    private Context context;

    private float modelObjectFloatAxisX;
    private float modelObjectFloatAxisY;
    private float modelObjectFloatAxisZ;

    private short modelObjectFloatAxisXByte;
    private short modelObjectFloatAxisYByte;
    private short modelObjectFloatAxisZByte;


    public LoadModelsRendering(Context context) {

        this.context = context;
        this.objectModelStruct= new ArrayList<>(0);

    }

    public void allocationMemoryObjects(){

        for(int i=0; i<fileObjectModel.size(); i++ ){

            this.objectModelStruct.add(new ObjectModel());

        }


    }

    public void readFileObject(int sequenceObjectId){


        try {

            fileInputReference = new InputStreamReader(context.getAssets().open(fileObjectModel.get(sequenceObjectId)));
            bufferReaderObject = new BufferedReader(fileInputReference);

            while ((stringBufferReadLine = bufferReaderObject.readLine()) != null) {

                decomposeFileObject(stringBufferReadLine, sequenceObjectId);

            }

        } catch (IOException ignored) {

        } finally {

            if (bufferReaderObject != null) {

                try { bufferReaderObject.close(); } catch (IOException ignored) { }

            }
        }

    }

    public void decomposeFileObject(String stringBufferLine, int numberSequenceModels){


        fileTemporaryProcessingLine = stringBufferLine.split("\\s");

        if(fileTemporaryProcessingLine[0].equals("v")){

            modelObjectFloatAxisX = Float.parseFloat(fileTemporaryProcessingLine[1]);
            modelObjectFloatAxisY = Float.parseFloat(fileTemporaryProcessingLine[2]);
            modelObjectFloatAxisZ = Float.parseFloat(fileTemporaryProcessingLine[3]);
            objectModelStruct.get(numberSequenceModels).addVerticesComponents(modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);
        }

        if(fileTemporaryProcessingLine[0].equals("vt")){

            modelObjectFloatAxisX = Float.parseFloat(fileTemporaryProcessingLine[1]);
            modelObjectFloatAxisY = Float.parseFloat(fileTemporaryProcessingLine[2]);
            modelObjectFloatAxisZ = Float.parseFloat(fileTemporaryProcessingLine[3]);
            objectModelStruct.get(numberSequenceModels).addTexturesComponents(modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);

        }

        if(fileTemporaryProcessingLine[0].equals("f")){

                modelObjectFloatAxisXByte = Integer.parseInt(fileTemporaryProcessingLine[1]);
                modelObjectFloatAxisYByte = Integer.parseInt(fileTemporaryProcessingLine[2]);
                modelObjectFloatAxisZByte = Integer.parseInt(fileTemporaryProcessingLine[3]);

                objectModelStruct.get(numberSequenceModels).addObjectIndexComponents(fileTemporaryProcessingLine.length, modelObjectFloatAxisXByte, modelObjectFloatAxisYByte, modelObjectFloatAxisZByte);
                modelObjectFloatAxisX = Float.parseFloat("0.6");
                modelObjectFloatAxisY = Float.parseFloat("0.6");
                modelObjectFloatAxisZ = Float.parseFloat("0.6");
                objectModelStruct.get(numberSequenceModels).addTexturesComponents(modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);


        }



    }

    public void setFileObjectModel(ArrayList<String> fileObjectModel) {

        this.fileObjectModel = fileObjectModel;
        this.numberObjectsRendered = this.fileObjectModel.size();

    }

    public void loadModels(){

        for(int i=0; i< fileObjectModel.size(); i++){

            readFileObject(i);
            objectModelStruct.get(i).dynamicAllocationObject();
            objectModelStruct.get(i).allocationBufferVerticesModel();
            objectModelStruct.get(i).allocationBufferTextureModel();
            objectModelStruct.get(i).allocationBufferIndexModel();


        }


    }

    public void draw(GL10 gl) {

        gl.glFrontFace(GL10.GL_CW);

        for(int i = 0; i < this.numberObjectsRendered; i++){

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, objectModelStruct.get(i).byteBufferVertices);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, objectModelStruct.get(i).byteBufferTexture);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        if(objectModelStruct.get(i).getObjectNumberComponentsPerPlane()==4){

            gl.glDrawElements(GL10.GL_TRIANGLES, 35, GL10.GL_SHORT, objectModelStruct.get(i).byteBufferIndex);
        }

        if(objectModelStruct.get(i).getObjectNumberComponentsPerPlane()==5){

                gl.glDrawElements(GL10.GL_TRIANGLES, 35, GL10.GL_SHORT, objectModelStruct.get(i).byteBufferIndex);
            }


        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);


        }

    }

}