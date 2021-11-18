package com.tensorlearning.facemasks.Engine.Core;

import android.app.Application;

import androidx.annotation.Nullable;

import com.tensorlearning.facemasks.Engine.Objects.Model;


public class ModelViewerApplication extends Application
{
    private static ModelViewerApplication INSTANCE;

    @Nullable
    private Model currentModel;

    public static ModelViewerApplication getInstance() {
        return INSTANCE;
    }

    @Nullable
    public Model getCurrentModel() {
        return currentModel;
    }

    public void setCurrentModel(@Nullable Model model) {
        currentModel = model;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        INSTANCE = this;
    }
}
