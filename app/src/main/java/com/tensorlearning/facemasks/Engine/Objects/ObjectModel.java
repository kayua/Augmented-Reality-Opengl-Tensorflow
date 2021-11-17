package com.tensorlearning.facemasks.Engine.Objects;


import android.opengl.GLES30;

import com.tensorlearning.facemasks.Engine.Core.myRenderer;
import com.tensorlearning.facemasks.Engine.Settings.SettingsEngine;
import com.tensorlearning.facemasks.Engine.Texture.myColor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

class ObjectModel{

    private int mProgramObject;
    private int mMVPMatrixHandle;
    private int mColorHandle;
    private FloatBuffer mVertices;
    int vertexShader;
    int fragmentShader;
    int programObject;
    int[] linked = new int[1];
    float size = 0.4f;
    private SettingsEngine settingsEngine = new SettingsEngine();

    float[] mVerticesData = new float[]{};

    float colorcyan[] = myColor.cyan();
    float colorblue[] = myColor.blue();
    float colorred[] = myColor.red();
    float colorgray[] = myColor.gray();
    float colorgreen[] = myColor.green();
    float coloryellow[] = myColor.yellow();

    String vShaderStr =
            "#version 300 es 			  \n"
                    + "uniform mat4 uMVPMatrix;     \n"
                    + "in vec4 vPosition;           \n"
                    + "void main()                  \n"
                    + "{                            \n"
                    + "   gl_Position = uMVPMatrix * vPosition;  \n"
                    + "}                            \n";


    String fShaderStr =
            "#version 300 es		 			          	\n"
                    + "precision mediump float;					  	\n"
                    + "uniform vec4 vColor;	 			 		  	\n"
                    + "out vec4 fragColor;	 			 		  	\n"
                    + "void main()                                  \n"
                    + "{                                            \n"
                    + "  fragColor = vColor;                    	\n"
                    + "}                                            \n";


    public ObjectModel() {

        mVertices = ByteBuffer
                .allocateDirect(mVerticesData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVerticesData);
        mVertices.position(0);

        initializeEngine();

    }

    public void initializeEngine(){

        vertexShader = myRenderer.LoadShader(settingsEngine.getSettingVertexShader(), vShaderStr);
        fragmentShader = myRenderer.LoadShader(settingsEngine.getSettingFragmentShader(), fShaderStr);
        programObject = GLES30.glCreateProgram();

        if (programObject == 0) { return; }

        GLES30.glAttachShader(programObject, vertexShader);
        GLES30.glAttachShader(programObject, fragmentShader);
        GLES30.glBindAttribLocation(programObject, 0, settingsEngine.getSettingVerticesPosition());
        GLES30.glLinkProgram(programObject);
        GLES30.glGetProgramiv(programObject, settingsEngine.getSettingColorBuffer(), linked, 0);

        if (linked[0] == 0) { GLES30.glDeleteProgram(programObject); return; }

        mProgramObject = programObject;

    }

    public void draw(float[] mvpMatrix) {


        GLES30.glUseProgram(mProgramObject);
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgramObject, settingsEngine.getSettingUniformLocation());
        myRenderer.checkGlError(settingsEngine.getSettingCheckGlError());
        mColorHandle = GLES30.glGetUniformLocation(mProgramObject, settingsEngine.getSettingUniformLocationColor());
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        myRenderer.checkGlError(settingsEngine.getSettingCheckGlErrorMatrix());

        int VERTEX_POS_INDX = 0;
        mVertices.position(VERTEX_POS_INDX);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, 3, GLES30.GL_FLOAT, false, 0, mVertices);
        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);
        int startPos =0;
        int verticesPerface = 6;

        GLES30.glUniform4fv(mColorHandle, 1, colorblue, 0);
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, 36, GLES30.GL_UNSIGNED_SHORT, indices);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;


    }

}

