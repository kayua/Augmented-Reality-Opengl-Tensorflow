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
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.tensorlearning.facemasks.R;

import org.tensorflow.lite.Interpreter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.time.Duration;
import java.time.Instant;


public class AugmentedActivity extends Activity implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    Interpreter interpreter = null;

    int size_image_x = 128;
    int size_image_y = 256;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    float[][] output_signal_return = new float[1][40];
    private final int[] intValues = new int[size_image_x * size_image_y];
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    private SurfaceComponent mGLSurfaceView;
    ByteBuffer imgData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            interpreter = new Interpreter(loadModelFile("model_face.tflite"), setConfig());
            interpreter.allocateTensors();
            imgData = ByteBuffer.allocateDirect(size_image_x * size_image_y*4);
            imgData.order(ByteOrder.nativeOrder());

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void convertBitmapToByteBuffer(Bitmap bitmap) {
        if (imgData == null) {
            return;
        }
        imgData.rewind();
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, size_image_x, size_image_y);
        // Convert the image to floating point.
        int pixel = 0;
        long startTime = SystemClock.uptimeMillis();
        for (int i = 0; i < size_image_x; ++i) {
            for (int j = 0; j < size_image_y; ++j) {
                final int val = intValues[pixel++];
                imgData.put((byte) ((((val >> 16) & 0xFF)-IMAGE_MEAN)/IMAGE_STD));
                imgData.put((byte) ((((val >> 8) & 0xFF)-IMAGE_MEAN)/IMAGE_STD));
                imgData.put((byte) ((((val) & 0xFF)-IMAGE_MEAN)/IMAGE_STD));
            }
        }
        long endTime = SystemClock.uptimeMillis();
        Log.d("1", "Timecost to put values into ByteBuffer: " + Long.toString(endTime - startTime));
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


    public Interpreter.Options setConfig(){

        Interpreter.Options options = new Interpreter.Options();

        options.setNumThreads(2);
        options.setAllowBufferHandleOutput(true);
        options.setAllowFp16PrecisionForFp32(true);

        return options;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        camera = Camera.open();
        setCameraDisplayOrientation(this, 0,camera);
        Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();


                YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
                yuvImage.compressToJpeg(new Rect(0, 0, size_image_x, size_image_y), 90, out);
                byte[] imageBytes = out.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                convertBitmapToByteBuffer(bitmap);
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
        Instant start = Instant.now();

        interpreter.run(imgData, output_signal_return);

        Instant end = Instant.now();
        Log.i("TIME:     ",Duration.between(start, end).toString());
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



