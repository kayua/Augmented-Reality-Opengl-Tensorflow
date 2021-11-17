package com.tensorlearning.facemasks.Engine.Objects;


import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

class ObjectModel {

    private int mProgramObject;
    private int mMVPMatrixHandle;
    private int mColorHandle;
    private FloatBuffer mVertices;

    float size = 0.4f;

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

    String TAG = "Cube";


    public Cube() {

        mVertices = ByteBuffer
                .allocateDirect(mVerticesData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVerticesData);
        mVertices.position(0);

        //setup the shaders
        int vertexShader;
        int fragmentShader;
        int programObject;
        int[] linked = new int[1];

        // Load the vertex/fragment shaders
        vertexShader = myRenderer.LoadShader(GLES30.GL_VERTEX_SHADER, vShaderStr);
        fragmentShader = myRenderer.LoadShader(GLES30.GL_FRAGMENT_SHADER, fShaderStr);

        // Create the program object
        programObject = GLES30.glCreateProgram();

        if (programObject == 0) {
            Log.e(TAG, "So some kind of error, but what?");
            return;
        }

        GLES30.glAttachShader(programObject, vertexShader);
        GLES30.glAttachShader(programObject, fragmentShader);

        // Bind vPosition to attribute 0
        GLES30.glBindAttribLocation(programObject, 0, "vPosition");

        // Link the program
        GLES30.glLinkProgram(programObject);

        // Check the link status
        GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linked, 0);

        if (linked[0] == 0) {
            Log.e(TAG, "Error linking program:");
            Log.e(TAG, GLES30.glGetProgramInfoLog(programObject));
            GLES30.glDeleteProgram(programObject);
            return;
        }

        // Store the program object
        mProgramObject = programObject;

        //now everything is setup and ready to draw.
    }

    public void draw(float[] mvpMatrix) {

        GLES30.glUseProgram(mProgramObject);
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgramObject, "uMVPMatrix");
        myRenderer.checkGlError("glGetUniformLocation");
        mColorHandle = GLES30.glGetUniformLocation(mProgramObject, "vColor");
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        myRenderer.checkGlError("glUniformMatrix4fv");

        int VERTEX_POS_INDX = 0;
        mVertices.position(VERTEX_POS_INDX);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, 3, GLES30.GL_FLOAT, false, 0, mVertices);
        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);
        int startPos =0;
        int verticesPerface = 6;

        GLES30.glUniform4fv(mColorHandle, 1, colorblue, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorcyan, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorred, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, colorgreen, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        GLES30.glUniform4fv(mColorHandle, 1, coloryellow, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);

    }

}

