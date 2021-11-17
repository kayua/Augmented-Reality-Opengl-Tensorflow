package com.tensorlearning.facemasks.Engine.Buffering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


public class MemoryAllocation {

    private float[] objectModelVerticesComponentsFlattenBuffer;
    private float[] objectModelTextureCoordinatesFlattenBuffer;
    private short[] objectModelSurfaceIndexVectorFlattenBuffer;

    private FloatBuffer bufferVerticesCoordinatesComponents;
    private FloatBuffer bufferTextureCoordinatesComponents;
    private ShortBuffer bufferSurfaceIndexComponents;

    private ByteBuffer byteBufferVertices;
    private ByteBuffer byteBufferTexture;
    private ByteBuffer byteBufferIndex;


    public MemoryAllocation() {


    }

    public void allocationBufferVerticesModel() {

        byteBufferVertices = ByteBuffer.allocateDirect(objectModelVerticesComponentsFlattenBuffer.length * 4);
        byteBufferVertices.order(ByteOrder.nativeOrder());
        bufferVerticesCoordinatesComponents = byteBufferVertices.asFloatBuffer();
        bufferVerticesCoordinatesComponents.put(objectModelVerticesComponentsFlattenBuffer);
        bufferVerticesCoordinatesComponents.position(0);

    }

    public void allocationBufferTextureModel() {

        byteBufferTexture = ByteBuffer.allocateDirect(objectModelTextureCoordinatesFlattenBuffer.length * 4);
        byteBufferTexture.order(ByteOrder.nativeOrder());
        bufferTextureCoordinatesComponents = byteBufferTexture.asFloatBuffer();
        bufferTextureCoordinatesComponents.put(objectModelTextureCoordinatesFlattenBuffer);
        bufferTextureCoordinatesComponents.position(0);

    }

    public void allocationBufferSurfaceIndexModel() {

        byteBufferIndex = ByteBuffer.allocateDirect(objectModelSurfaceIndexVectorFlattenBuffer.length*2);
        byteBufferTexture.order(ByteOrder.nativeOrder());
        bufferSurfaceIndexComponents = byteBufferIndex.asShortBuffer();
        bufferSurfaceIndexComponents.put(objectModelSurfaceIndexVectorFlattenBuffer);
        bufferSurfaceIndexComponents.position(0);


    }

    public void setObjectModelVerticesComponentsFlattenBuffer(float[] objectModelVerticesComponentsFlattenBuffer) {

        this.objectModelVerticesComponentsFlattenBuffer = objectModelVerticesComponentsFlattenBuffer;

    }

    public void setObjectModelTextureCoordinatesFlattenBuffer(float[] objectModelTextureCoordinatesFlattenBuffer) {

        this.objectModelTextureCoordinatesFlattenBuffer = objectModelTextureCoordinatesFlattenBuffer;

    }

    public void setObjectModelSurfaceIndexVectorFlattenBuffer(short[] objectModelSurfaceIndexVectorFlattenBuffer) {

        this.objectModelSurfaceIndexVectorFlattenBuffer = objectModelSurfaceIndexVectorFlattenBuffer;

    }

    public ByteBuffer getByteBufferVertices() {

        return byteBufferVertices;

    }

    public ByteBuffer getByteBufferTexture() {

        return byteBufferTexture;

    }

    public ByteBuffer getByteBufferIndex() {

        return byteBufferIndex;

    }

}