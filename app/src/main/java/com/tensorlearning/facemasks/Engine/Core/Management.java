package com.tensorlearning.facemasks.Engine.Core;

import android.content.Context;

import com.tensorlearning.facemasks.Engine.Formats.WavefrontFormat;
import com.tensorlearning.facemasks.Engine.Settings.SettingsEngine;
import java.util.ArrayList;


class Management {

    private final SettingsEngine engineSettings;
    private final ArrayList<WavefrontFormat> objectsWavefrontFormat;


    public Management(Context context) {

        this.objectsWavefrontFormat = new ArrayList<>();
        this.engineSettings = new SettingsEngine();

    }













}
