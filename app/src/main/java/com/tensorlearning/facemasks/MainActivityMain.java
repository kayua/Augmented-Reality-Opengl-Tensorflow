package com.tensorlearning.facemasks;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.tensorlearning.facemasks.Engine.SurfaceComponent;
import com.tensorlearning.facemasks.Models.NeuralModel;

import java.io.IOException;


public class MainActivityMain extends Activity implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceComponent mGLSurfaceView;
    private NeuralModel model = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tests);

        model = new NeuralModel(getApplicationContext());
        model.createByteBufferModel();
        model.createInterpreter();

        mSurfaceView = findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        mGLSurfaceView = new SurfaceComponent(this, camera);
        mGLSurfaceView.setAlpha(0.2f);
        addContentView(mGLSurfaceView, new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));


    }



    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        Camera.Parameters p = camera.getParameters();

        p.setPreviewSize(arg2, arg3);

        try {
            camera.setPreviewDisplay(arg0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        camera.startPreview();

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        camera = Camera.open();
        setCameraDisplayOrientation(this, 0,camera);
        Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                model.predictFaceTracker(data, camera);
            }


        };

        camera.setPreviewCallback(previewCallback);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
}



