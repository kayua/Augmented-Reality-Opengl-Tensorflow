package com.tensorlearning.facemasks.Engine;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class LoadModelsRendering {


    String stringBufferReadLine;
    String[] fileTemporaryProcessingLine;
    String fileObjectModel;

    BufferedReader bufferReaderObject = null;
    InputStreamReader fileInputReference;
    ObjectModel objectModelStruct;
    Context context;

    private float modelObjectFloatAxisX;
    private float modelObjectFloatAxisY;
    private float modelObjectFloatAxisZ;

    public LoadModelsRendering(Context context, String file) {

        this.objectModelStruct = new ObjectModel();

    }

    public void readFileObject(){

        try {

            fileInputReference = new InputStreamReader(context.getAssets().open("filename.txt"));
            bufferReaderObject = new BufferedReader(fileInputReference);

            while ((stringBufferReadLine = bufferReaderObject.readLine()) != null) {
                decomposeFileObject(stringBufferReadLine);
            }

        } catch (IOException ignored) {

        } finally {

            if (bufferReaderObject != null) {

                try { bufferReaderObject.close(); } catch (IOException ignored) { }

            }
        }

    }

    public void decomposeFileObject(String stringBufferLine){


        fileTemporaryProcessingLine = stringBufferLine.split("\\s");

        if(fileTemporaryProcessingLine[0].equals("v")){

            modelObjectFloatAxisX = Float.parseFloat(fileTemporaryProcessingLine[1]);
            modelObjectFloatAxisY = Float.parseFloat(fileTemporaryProcessingLine[2]);
            modelObjectFloatAxisZ = Float.parseFloat(fileTemporaryProcessingLine[3]);
            objectModelStruct.addVerticesComponents(modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);

        }

        if(fileTemporaryProcessingLine[0].equals("vt")){

            modelObjectFloatAxisX = Float.parseFloat(fileTemporaryProcessingLine[1]);
            modelObjectFloatAxisY = Float.parseFloat(fileTemporaryProcessingLine[2]);
            modelObjectFloatAxisZ = Float.parseFloat(fileTemporaryProcessingLine[3]);
            objectModelStruct.addTexturesComponents(modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);

        }

        if(fileTemporaryProcessingLine[0].equals("f")){

            for (String s : fileTemporaryProcessingLine) {

                modelObjectFloatAxisX = Float.parseFloat(s.split("/")[0]);
                modelObjectFloatAxisY = Float.parseFloat(s.split("/")[1]);
                modelObjectFloatAxisZ = Float.parseFloat(s.split("/")[2]);

                objectModelStruct.addObjectIndexComponents(fileTemporaryProcessingLine.length, modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);


            }

        }



    }


}