package com.tensorlearning.facemasks.Engine;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class SurfaceComponent extends GLSurfaceView {

    public EngineRenderer cr;


    public SurfaceComponent(Context context, android.hardware.Camera camera) {
        super(context, (AttributeSet) camera);

        Interpreter interpreter = null;

        cr = new EngineRenderer(getContext(), interpreter, camera);
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.setRenderer(cr);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        this.getHolder().setFormat(PixelFormat.TRANSPARENT);

    }

    private MappedByteBuffer loadModelFile(String file) throws IOException {

        AssetFileDescriptor assetFileDescriptor = getContext().getAssets().openFd(file);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);
    }


}