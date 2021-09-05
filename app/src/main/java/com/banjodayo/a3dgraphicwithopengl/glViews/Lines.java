package com.banjodayo.a3dgraphicwithopengl.glViews;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.view.MotionEvent;

import static javax.microedition.khronos.opengles.GL10.GL_COLOR_ARRAY;
import static javax.microedition.khronos.opengles.GL10.GL_FLOAT;
import static javax.microedition.khronos.opengles.GL10.GL_VERTEX_ARRAY;

public class Lines implements Renderer{
    private Context mContext;
    private FloatBuffer mVertexBuffer = null;

    private FloatBuffer mVertexBuffer2 = null;

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
    private final float POINT_WIDTH = 20f;
    private int program;

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

        float Color[] = {
                1.0f, 0.0f, 0.0f,   //0 rgb
                1.0f, 0.0f, 0.0f,   //1 rgb
                0.0f, 1.0f, 0.0f,   //2 rgb
                0.0f,1.0f, 0.0f,   //3 rgb
                0.0f, 0.0f, 1.0f,   //4 rgb
                0.0f, 0.0f,1.0f,   //5 rgb
                0.5f , 0.5f, 0.0f ,  //6 rgb
                0.5f, 0.5f, 0.0f ,   //7 rgb
                0.0f , 0.5f, 0.0f ,  //8 rgb
                0.9f, 0.1f, 0.0f,    //9  rgb
                1.0f, 0.0f, 0.0f,    //10 rgb
                0.9f,0.1f, 0.0f,    //11 rgb
                1.0f, 0.0f, 0.0f,    //12 rgb
                0.1f, 0.9f, 0.0f,   //13 rgb
                0.0f, 1.0f, 0.0f,    //14 rgb
                0.1f, 0.9f, 0.0f,     //15 rgb
                0.0f, 1.0f, 0.0f,    //16 rgb
                0.1f, 0.0f, 0.9f,    //17 rgb
                0.0f, 0.0f, 1.0f,    //18 rgb
                0.1f,0.0f, 0.9f,    //19 rgb
                0.0f, 0.0f, 1.0f,    //20 rgb
                0.5f, 0.5f, 0.5f,    //21 rgb
                0.5f, 0.5f, 0.5f, //22 rgb
                0.5f, 0f, 0.5f, //23 rgb
                0.5f, 0.5f, 0f, //24 rgb
        };

        int C_Length=(Color.length/3)-1;


        // Draw x line
        // Set line color to green
        /*gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
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
        gl.glDrawArrays(GL10.GL_LINES, 19, 2);*/

        for(int index=0;index<=C_Length;index++){
            gl.glPointSize(20);
            gl.glColor4f(Color[3*index], Color[3*index+1], Color[3*index+2], 1.0f);
            gl.glDrawArrays(GL10.GL_POINTS, index, C_Length);
        }

        //從vertexlist[0]開始數2個element進行繪製，換言之這邊會繪製vertexlist[0]與vertexlist[1]
        /*gl.glPointSize(20);
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_POINTS, 0, 20);

        gl.glPointSize(50);
        gl.glColor4f(1.0f, 0f, 0.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_POINTS, 15, 5);*/
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

                0.5f, 0.5f, 0.5f,    //point22
                -0.5f, -0.5f, -0.5f, //point23
                -0.5f, -0f, -0.5f, //point24
                -0.5f, -0.5f, -0f, //point25
        };

        Log.d("Array length: ",""+(vertexlist.length)/3);

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

    //黑屏
    public void paint(GL11 gl)
    {
        gl.glEnableClientState(GL_COLOR_ARRAY);
        gl.glEnableClientState(GL_VERTEX_ARRAY);

        float vtx[]=
                {
                        15.0f, 15.0f, 20.0f,
                        10.0f,20.0f, 30.0f,
                        -10.0f,-20.0f, 10.0f
                };
        FloatBuffer vtxBuffer;
        //不可直接wrap
        ByteBuffer bb = ByteBuffer.allocateDirect(vtx.length*4);
        bb.order(ByteOrder.nativeOrder());
        vtxBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vtxBuffer.put(vtx);
        // set the buffer to read the first coordinate
        vtxBuffer.position(0);

        float color[]=
                {
                        1.0f,0.0f,0.0f,1.0f,
                        0.0f,1.0f,0.0f,1.0f,
                        1.0f,1.0f,0.0f,1.0f
                };
        FloatBuffer colorBuffer;
        //colorBuffer = FloatBuffer.wrap(color);
        ByteBuffer cb = ByteBuffer.allocateDirect(color.length*4);
        cb.order(ByteOrder.nativeOrder());
        colorBuffer = cb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        colorBuffer.put(color);
        // set the buffer to read the first coordinate
        colorBuffer.position(0);
        gl.glPointSize(50);
        gl.glVertexPointer(3,GL_FLOAT,0,vtxBuffer);
        gl.glColorPointer(4,GL_FLOAT,0,colorBuffer);
        gl.glDisableClientState(GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL_COLOR_ARRAY);
    }

    public void points(){
        int vertex = createShader(GLES20.GL_VERTEX_SHADER, "" +
                    "void main() {" +
                    "   gl_Position = vec4(0.5, 0, 0, 1);" +
                    "   gl_PointSize = 50.0;" +
                    "}");

        //片段著色器(控制顏色OUTPUT)
        int fragment = createShader(GLES20.GL_FRAGMENT_SHADER, "" +
                "precision mediump float;" +
                "void main() {" +
                "   gl_FragColor = vec4(1, 0, 0, 1);" +
                "}");

        //建立OpenGL ES Program
        program = GLES20.glCreateProgram();
        checkError("glCreateProgram");
        GLES20.glAttachShader(program, vertex);
        checkError("glAttachShader(vertex)");
        GLES20.glAttachShader(program, fragment);
        checkError("glAttachShader(fragment)");
        GLES20.glLinkProgram(program);
        checkError("glLinkProgram");
    }

    private void checkError(String message) {
        int err = GLES20.glGetError();
        if (err != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(message + " -> " + err);
        }
    }

    private int createShader(int type, String source) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, source);
        checkError("glShaderSource");
        GLES20.glCompileShader(shader);
        checkError("glCompileShader");
        return shader;
    }
}
