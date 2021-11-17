package com.tensorlearning.facemasks.Engine.Settings;

import android.content.Context;
import android.opengl.GLES30;


class EngineSettings {

    private final int settingDepthFunction = GLES30.GL_LEQUAL;
    private final int settingEnable = GLES30.GL_DEPTH_TEST;
    private final int settingHintPerspective = GLES30.GL_FRAGMENT_SHADER_DERIVATIVE_HINT;
    private final int settingHintNicest = GLES30.GL_NICEST;
    private final int settingColorBuffer = GLES30.GL_COLOR_BUFFER_BIT;
    private final int settingDepthBuffer = GLES30.GL_DEPTH_BUFFER_BIT;
    private final int settingVertexShader = GLES30.GL_VERTEX_SHADER;
    private final int settingFragmentShader = GLES30.GL_FRAGMENT_SHADER;
    private final int settingLinkStatus = GLES30.GL_LINK_STATUS;
    private final int settingTriangles = GLES30.GL_TRIANGLES;

    private final String settingUniformLocation = "uMVPMatrix";
    private final String settingCheckGlError = "glGetUniformLocation";
    private final String settingUniformLocationColor = "vColor";
    private final String settingCheckGlErrorMatrix = "glUniformMatrix4fv";

    private final float settingsClearDepthFunction = 1.0f;

    private final String settingVerticesShaderStr = "#version 300 es 			  \n"

                                        + "uniform mat4 uMVPMatrix;     \n"
                                        + "in vec4 vPosition;           \n"
                                        + "void main()                  \n"
                                        + "{                            \n"
                                        + "   gl_Position = uMVPMatrix * vPosition;  \n"
                                        + "}                            \n";

    private final String fShaderStr =  "#version 300 es		 			          	\n"
                                        + "precision mediump float;					  	\n"
                                        + "uniform vec4 vColor;	 			 		  	\n"
                                        + "out vec4 fragColor;	 			 		  	\n"
                                        + "void main()                                  \n"
                                        + "{                                            \n"
                                        + "  fragColor = vColor;                    	\n"
                                        + "}                                            \n";


    public EngineSettings(Context context) {


    }



}
