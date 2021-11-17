package com.tensorlearning.facemasks.Engine.Settings;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLES31;
import android.opengl.GLES32;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


class EngineSettings {

    private final int settingsDepthFunction = GLES32.GL_LEQUAL;
    private final int settingsEnable = GLES32.GL_DEPTH_TEST;
    private final float settingsClearDepthFunction = 1.0f;
    private final int settingsHintPerspective = GLES32.GL_FRAGMENT_SHADER_DERIVATIVE_HINT;
    private final int settingsHintNicest = GLES32.GL_NICEST;
    private final int settingsColorBuffer = GLES32.GL_COLOR_BUFFER_BIT;
    private final int settingsDepthBuffer = GLES32.GL_DEPTH_BUFFER_BIT;

    public EngineSettings(Context context) {


    }



    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -10.0f);

        gl.glRotatef(mCubeRotation, 1.0f, 1.0f, 1.0f);

        this.renderModels.draw(gl);

        gl.glLoadIdentity();

        mCubeRotation -= 2.90f;



    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 65.0f, (float)width / (float)height, 0.1f, 100.0f);
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

}
