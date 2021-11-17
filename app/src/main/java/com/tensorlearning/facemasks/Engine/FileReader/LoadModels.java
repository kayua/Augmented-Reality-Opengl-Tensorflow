package com.tensorlearning.facemasks.Engine.FileReader;

import android.content.Context;
import com.tensorlearning.facemasks.Engine.ObjectModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public final class LoadModels {


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


    public LoadModels(Context context) {

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

                modelObjectFloatAxisXByte = Short.parseShort(fileTemporaryProcessingLine[1]);
                modelObjectFloatAxisYByte = Short.parseShort(fileTemporaryProcessingLine[2]);
                modelObjectFloatAxisZByte = Short.parseShort(fileTemporaryProcessingLine[3]);

                objectModelStruct.get(numberSequenceModels).addObjectIndexComponents(fileTemporaryProcessingLine.length, modelObjectFloatAxisXByte, modelObjectFloatAxisYByte, modelObjectFloatAxisZByte);
                modelObjectFloatAxisX = Float.parseFloat("0.6");
                modelObjectFloatAxisY = Float.parseFloat("0.6");
                modelObjectFloatAxisZ = Float.parseFloat("0.6");
                objectModelStruct.get(numberSequenceModels).addTexturesComponents(modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);


        }



    }



}