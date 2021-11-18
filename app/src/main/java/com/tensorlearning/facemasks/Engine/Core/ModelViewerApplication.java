package com.tensorlearning.facemasks.Engine.Core;

import android.app.Application;


public class ModelViewerApplication extends Application
{
    private static com.dmitrybrant.modelviewer.ModelViewerApplication INSTANCE;

    // Store the current model globally, so that we don't have to re-decode it upon
    // relaunching the main or VR activities.
    // TODO: handle this a bit better.
    @Nullable private com.dmitrybrant.modelviewer.Model currentModel;

    public static com.dmitrybrant.modelviewer.ModelViewerApplication getInstance() {
        return INSTANCE;
    }

    @Nullable
    public com.dmitrybrant.modelviewer.Model getCurrentModel() {
        return currentModel;
    }

    public void setCurrentModel(@Nullable com.dmitrybrant.modelviewer.Model model) {
        currentModel = model;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        INSTANCE = this;
    }
}
