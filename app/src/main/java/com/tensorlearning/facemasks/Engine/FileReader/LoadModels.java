package com.tensorlearning.facemasks.Engine.FileReader;

import android.content.Context;

import com.tensorlearning.facemasks.Engine.Formats.WavefrontFormat;
import com.tensorlearning.facemasks.Engine.ObjectModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public final class LoadModels {


    private String stringBufferReadLine;
    private String[] fileTemporaryProcessingLine;
    private BufferedReader bufferReaderObject = null;
    private InputStreamReader fileInputReference;
    private int numberObjectsRendered=0;
    public ArrayList<ObjectModel> objectModelStruct;
    private Context context;
    private WavefrontFormat objectModelWavefront;

    private float modelObjectFloatAxisX;
    private float modelObjectFloatAxisY;
    private float modelObjectFloatAxisZ;

    private float modelObjectFloatAxisX;
    private float modelObjectFloatAxisY;
    private float modelObjectFloatAxisZ;

    private short modelObjectFloatAxisXByte;
    private short modelObjectFloatAxisYByte;
    private short modelObjectFloatAxisZByte;


    public LoadModels(Context context) {

        this.context = context;
        allocationMemoryNewObject();

    }

    public void allocationMemoryNewObject(){

        this.objectModelWavefront = new WavefrontFormat();

    }


    public void readFileModelObject(int typeObject, String fileNameModelObject){

        if(typeObject==0){

            try {

                fileInputReference = new InputStreamReader(context.getAssets().open(fileNameModelObject));
                bufferReaderObject = new BufferedReader(fileInputReference);

                while ((stringBufferReadLine = bufferReaderObject.readLine()) != null) {

                    decomposeFileModelObject(stringBufferReadLine.split("\\s"));

                }

            } catch (IOException ignored) {

            } finally {

                if (bufferReaderObject != null) {

                    try { bufferReaderObject.close(); } catch (IOException ignored) { }

                }
            }

        }


    }

    public void decomposeFileModelObject(String[] stringBufferLine){


        if(fileTemporaryProcessingLine[0].equals("v")){

            modelObjectFloatAxisX = Float.parseFloat(fileTemporaryProcessingLine[1]);
            modelObjectFloatAxisY = Float.parseFloat(fileTemporaryProcessingLine[2]);
            modelObjectFloatAxisZ = Float.parseFloat(fileTemporaryProcessingLine[3]);
            objectModelWavefront.addVerticesComponents(modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);
        }

        if(fileTemporaryProcessingLine[0].equals("vt")){

            modelObjectFloatAxisX = Float.parseFloat(fileTemporaryProcessingLine[1]);
            modelObjectFloatAxisY = Float.parseFloat(fileTemporaryProcessingLine[2]);
            modelObjectFloatAxisZ = Float.parseFloat(fileTemporaryProcessingLine[3]);
            objectModelWavefront.addTexturesComponents(modelObjectFloatAxisX, modelObjectFloatAxisY, modelObjectFloatAxisZ);

        }

        if(fileTemporaryProcessingLine[0].equals("f")){

                modelObjectFloatAxisXByte = Short.parseShort(fileTemporaryProcessingLine[1]);
                modelObjectFloatAxisYByte = Short.parseShort(fileTemporaryProcessingLine[2]);
                modelObjectFloatAxisZByte = Short.parseShort(fileTemporaryProcessingLine[3]);
                objectModelWavefront.addObjectIndexComponents(modelObjectFloatAxisXByte, modelObjectFloatAxisYByte, modelObjectFloatAxisZByte);


        }



    }



}