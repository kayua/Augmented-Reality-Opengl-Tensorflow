package com.tensorlearning.facemasks.Engine;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class LoadModelsRendering {

    BufferedReader reader = null;
    Context context;
    String fileObjectModel;
    InputStreamReader fileInputReference;
    String bufferReadLine;


    public LoadModelsRendering(Context context, String file) {

    }

    public void readFileObject(){

        try {

            fileInputReference = new InputStreamReader(context.getAssets().open("filename.txt"));
            reader = new BufferedReader(fileInputReference);

            while ((bufferReadLine = reader.readLine()) != null) {

            }

        } catch (IOException e) {

        } finally {

            if (reader != null) {

                try {
                    reader.close();
                } catch (IOException e) { }


            }
        }

    }





}