package com.banjodayo.a3dgraphicwithopengl.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import com.banjodayo.a3dgraphicwithopengl.R;
import com.banjodayo.a3dgraphicwithopengl.glViews.GraphicView;
import com.banjodayo.a3dgraphicwithopengl.glViews.Lines;

/*public class MainActivity extends AppCompatActivity {

    private  GraphicView graphicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        graphicView = new GraphicView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (graphicView != null) {
            graphicView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        graphicView.onPause();
        super.onDestroy();
    }
}*/

public class MainActivity extends Activity {
    private GLSurfaceView mView;
    private Lines mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new GLSurfaceView(this);
        mRenderer = new Lines(this);
        mView.setRenderer(mRenderer);
        setContentView(mView);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return mRenderer.onTouchEvent(event);
    }
}