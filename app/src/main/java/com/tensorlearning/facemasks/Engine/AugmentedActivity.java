package com.tensorlearning.facemasks.Engine;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.tensorlearning.facemasks.Engine.SurfaceComponent;
import com.tensorlearning.facemasks.R;
import android.hardware.Camera;

import org.tensorflow.lite.Interpreter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class AugmentedActivity extends Activity implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    Interpreter interpreter = null;
    private SurfaceComponent mGLSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.tests);

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
                Camera.Parameters parameters = camera.getParameters();

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
                yuvImage.compressToJpeg(new Rect(0, 0, 60, 60), 90, out);
                byte[] imageBytes = out.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                reshape()
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("Callback Camera", "asdasd");
                try {
                    doInference();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        };

        camera.setPreviewCallback(previewCallback);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }

    private MappedByteBuffer loadModelFile(String file) throws IOException
    {
        AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd(file);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();

        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);
    }

    public void doInference() throws IOException {

        float[][][][] a = new float [1][60][60][1];
        interpreter = new Interpreter(loadModelFile("0.tflite"), null);
        Log.i("New Model", "-----");

        float[][] output_signal_return = new float[1][1];

        interpreter.run(a, output_signal_return);
        String s = Float.toString(output_signal_return[0][0]);
        Log.i("----->", s);


    }
    public float[][][][] reshape(Bitmap bitmap){

        int x = bitmap.getWidth();
        int y = bitmap.getHeight();
        int[] intArray = new int[x * y];
        bitmap.getPixels(intArray, 0, x, 0, 0, x, y);

        float[][][][] f = new float[1][60][60][1];
        int i;
        int j;
        for(i=0; i< 60; i++){

            for(j=0; j< 60; j++){
                int t = intArray[0];
                f[0][i][j][0]= (float) t;

            }

        }

        return f;

    }


    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
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



