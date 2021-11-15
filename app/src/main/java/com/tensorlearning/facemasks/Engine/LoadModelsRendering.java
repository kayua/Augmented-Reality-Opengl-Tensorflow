package com.tensorlearning.facemasks.Engine;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class LoadModelsRendering {

    BufferedReader bufferReaderObject = null;
    Context context;
    String fileObjectModel;
    InputStreamReader fileInputReference;
    String stringBufferReadLine;


    public LoadModelsRendering(Context context, String file) {

    }

    public void readFileObject(){

        try {

            fileInputReference = new InputStreamReader(context.getAssets().open("filename.txt"));
            bufferReaderObject = new BufferedReader(fileInputReference);

            while ((stringBufferReadLine = bufferReaderObject.readLine()) != null) {

            }

        } catch (IOException e) {

        } finally {

            if (bufferReaderObject != null) {

                try {
                    bufferReaderObject.close();
                } catch (IOException e) { }


            }
        }

    }





}