package com.tensorlearning.facemasks.Engine.Core;

import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.tensorlearning.facemasks.Engine.Formats.StlModel;
import com.tensorlearning.facemasks.Engine.Objects.Model;
import com.tensorlearning.facemasks.R;


import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    private static final String[] SAMPLE_MODELS = new String[] {"lucy.stl" };


    private ModelViewerApplication app;
    @Nullable
    private ModelSurfaceView modelView;
    private ViewGroup containerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = ModelViewerApplication.getInstance();

        containerView = findViewById(R.id.container_view);


    }

    @Override
    protected void onStart() {
        super.onStart();
        createNewModelView(app.getCurrentModel());
        if (app.getCurrentModel() != null) {
            setTitle(app.getCurrentModel().getTitle());
        }
        loadSampleModel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (modelView != null) {
            modelView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (modelView != null) {
            modelView.onResume();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void createNewModelView(@Nullable Model model) {
        if (modelView != null) {
            containerView.removeView(modelView);
        }
        ModelViewerApplication.getInstance().setCurrentModel(model);
        modelView = new ModelSurfaceView(this, model);
        containerView.addView(modelView, 0);
    }


    private void setCurrentModel(@NonNull Model model) {
        createNewModelView(model);
        setTitle(model.getTitle());

    }



    private void loadSampleModel() {
        try {
            InputStream stream = getApplicationContext().getAssets()
                    .open(SAMPLE_MODELS[0]);
            setCurrentModel(new StlModel(stream));
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
