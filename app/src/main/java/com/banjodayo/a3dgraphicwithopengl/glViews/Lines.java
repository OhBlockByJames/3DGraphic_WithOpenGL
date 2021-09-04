package com.banjodayo.a3dgraphicwithopengl.glViews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

public class Lines implements Renderer{
    private Context mContext;
    private FloatBuffer mVertexBuffer = null;
    private ShortBuffer mTriangleBorderIndicesBuffer = null;
    private int mNumOfTriangleBorderIndices = 0;

    public float mAngleX = 0.0f;
    public float mAngleY = 0.0f;
    public float mAngleZ = 0.0f;
    private float mPreviousX;
    private float mPreviousY;
    private final float TOUCH_SCALE_FACTOR = 0.6f;
    private final float AXIS_SCALE_FACTOR = 1.5f;
    private final int AXIS_WIDTH = 10;
    private final float POINT_WIDTH = 10f;

    public Lines(Context context) {
        mContext = context;
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -3.0f);
        gl.glRotatef(mAngleX, 1, 0, 0);
        gl.glRotatef(mAngleY, 0, 1, 0);
        gl.glRotatef(mAngleZ, 0, 0, 1);
        gl.glScalef(AXIS_SCALE_FACTOR, AXIS_SCALE_FACTOR, AXIS_SCALE_FACTOR);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);

        gl.glLineWidth(AXIS_WIDTH);

        // Draw x line
        // Set line color to green
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_LINES, 0, 2);
        gl.glDrawArrays(GL10.GL_LINES, 9, 2);
        gl.glDrawArrays(GL10.GL_LINES, 11, 2);

        // Draw y line
        // Set line color to red
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_LINES, 2, 2);
        gl.glDrawArrays(GL10.GL_LINES, 13, 2);
        gl.glDrawArrays(GL10.GL_LINES, 15, 2);

        // Draw z lines
        // Set line color to blue
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_LINES, 4, 2);
        gl.glDrawArrays(GL10.GL_LINES, 17, 2);
        gl.glDrawArrays(GL10.GL_LINES, 19, 2);

        gl.glPointSize(POINT_WIDTH);
        gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_POINTS, 5, 3);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Get all the buffers ready
        setAllBuffers();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float aspect = (float)width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-aspect, aspect, -1.0f, 1.0f, 1.0f, 10.0f);
    }

    private void setAllBuffers(){
        // Set vertex buffer
        float vertexlist[] = {
                -1.0f, 0.0f, 0.0f,   //0 X-axis
                1.0f, 0.0f, 0.0f,   //1 X-axis
                0.0f, 1.0f, 0.0f,   //2 Y-axis
                0.0f,-1.0f, 0.0f,   //3 Y-axis
                0.0f, 0.0f, 1.0f,   //4 Z-axis
                0.0f, 0.0f,-1.0f,   //5 Z-axis
                -0.5f , -0.5f, 0.0f ,   //6 point 1
                0.5f, -0.5f, 0.0f ,    //7 point 2
                0.0f , 0.5f, 0.0f ,      //8 point 3
                0.9f, 0.1f, 0.0f,    //9  X-axis
                1.0f, 0.0f, 0.0f,    //10 X-axis
                0.9f,-0.1f, 0.0f,    //11 X-axis
                1.0f, 0.0f, 0.0f,    //12 X-axis
                -0.1f, 0.9f, 0.0f,   //13 Y-axis
                0.0f, 1.0f, 0.0f,    //14 Y-axis
                0.1f, 0.9f, 0.0f,     //15 Y-axis
                0.0f, 1.0f, 0.0f,    //16 Y-axis
                0.1f, 0.0f, 0.9f,    //17 Z-axis
                0.0f, 0.0f, 1.0f,    //18 Z-axis
                -0.1f,0.0f, 0.9f,    //19 Z-axis
                0.0f, 0.0f, 1.0f,    //20 Z-axis
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexlist.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertexlist);
        mVertexBuffer.position(0);
    }

    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                mAngleY = (mAngleY + (int)(dx * TOUCH_SCALE_FACTOR) + 360) % 360;
                mAngleX = (mAngleX + (int)(dy * TOUCH_SCALE_FACTOR) + 360) % 360;
                break;
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
