package com.tensorlearning.facemasks.Engine;

import android.content.Context;
import android.util.Log;

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


    public LoadModelsRendering(Context context) {

        this.context = context;
        this.objectModelStruct= new ArrayList<>(0);
        this.objectModelStruct.add(new ObjectModel());
        this.objectModelStruct.add(new ObjectModel());
        this.objectModelStruct.add(new ObjectModel());
        this.objectModelStruct.add(new ObjectModel());


    }

    public void readFileObject(int sequenceObjectId){


        try {

            fileInputReference = new InputStreamReader(context.getAssets().open("male.obj"));
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
            Log.i(String.valueOf(objectModelStruct.get(numberSequenceModels).objectModelVerticesComponents.size()), "---<><><><>---");
        }

        if(fileTemporaryProcessingLine[0].equals("vt")){

            modelObjectFloatAxisX = Float.parseFloat(fileTemporaryProcessingLine[1]);
            modelObjectFloatAxisY = Float.parseFloat(fileTemporaryProcessingLine[2]);
            modelObjectFloatAxisZ = Float.parseFloat(fileTemporaryProcessingLine[3]);
            objectModelStruct.get(numberSequenceModels).addTexturesComponents(modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);

        }

        if(fileTemporaryProcessingLine[0].equals("f")){

            for (String s : fileTemporaryProcessingLine) {

                modelObjectFloatAxisX = Float.parseFloat(s.split("/")[1]);
                modelObjectFloatAxisY = Float.parseFloat(s.split("/")[2]);
                modelObjectFloatAxisZ = Float.parseFloat(s.split("/")[3]);

                objectModelStruct.get(numberSequenceModels).addObjectIndexComponents(fileTemporaryProcessingLine.length, modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);


            }

        }



    }

    public void setFileObjectModel(ArrayList<String> fileObjectModel) {

        this.fileObjectModel = fileObjectModel;
        this.numberObjectsRendered = this.fileObjectModel.size();

    }

    public void loadModels(){

        for(int i=0; i< fileObjectModel.size(); i++){

            readFileObject(i);
            objectModelStruct.get(i).createBufferObject();
            objectModelStruct.get(i).bufferedVerticesModel();
            objectModelStruct.get(i).bufferedTextureModel();
            objectModelStruct.get(i).bufferedIndexModel();

        }


    }


    public void draw(GL10 gl) {

        gl.glFrontFace(GL10.GL_CW);

        for(int i = 0; i < this.numberObjectsRendered; i++){

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, objectModelStruct.get(i).byteBufferVertices);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, objectModelStruct.get(i).byteBufferTexture);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        if(objectModelStruct.get(i).getObjectNumberComponentsPerPlane()==3){

            gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, objectModelStruct.get(i).byteBufferIndex);
        }

        if(objectModelStruct.get(i).getObjectNumberComponentsPerPlane()==4){

                gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 36, GL10.GL_UNSIGNED_BYTE, objectModelStruct.get(i).byteBufferIndex);
            }


        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);


        }

    }

}