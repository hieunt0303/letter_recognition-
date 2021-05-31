package com.example.gesture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Gesture_Long extends AppCompatActivity {
    ImageView img,img2;
    int mode=0;
    int drag =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture__long);
        img = findViewById(R.id.imageviewhinh);
        img2 = findViewById(R.id.imageviewhinh2);
        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(Gesture_Long.this, new MyGesture());;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(250,250);
        layoutParams.leftMargin = 50;
        layoutParams.topMargin = 50;
        img.setLayoutParams(layoutParams);

        img.setOnTouchListener(new View.OnTouchListener() {
            RelativeLayout.LayoutParams params ;
            float dx=0,dy=0,x=0,y=0;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                ImageView imageView = (ImageView) view;

                switch (event.getAction() & event.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN:
                        params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                        dx= event.getRawX() - params.leftMargin;
                        dy= event.getRawY() - params.topMargin;
                        mode = drag;
                        Toast.makeText(Gesture_Long.this,"onDown",Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Toast.makeText(Gesture_Long.this,"onMove",Toast.LENGTH_SHORT).show();
                        if(mode == drag)
                        {
                            x = event.getRawX();
                            y = event.getRawY();

                            params.leftMargin = (int)(x-dx);
                            params.topMargin = (int)(y-dy);
                            params.rightMargin = 0;
                            params.bottomMargin = 0;
                            params.rightMargin =params.leftMargin + (5*params.width);
                            params.bottomMargin = params.topMargin + (5*params.height);
                            imageView.setLayoutParams(params);
                        }
                }


                return true;
            }
        });
        img2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }
    class MyGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        float scale = 1.0F , onScaleStart = 0 , onScaleEnd=0;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            img2.setScaleX(scale);
            img2.setScaleY(scale);
            return super.onScale(detector);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Toast.makeText(Gesture_Long.this,"onScale Start", Toast.LENGTH_SHORT).show();
            onScaleStart = scale;


            return super.onScaleBegin(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Toast.makeText(Gesture_Long.this,"onScale End", Toast.LENGTH_SHORT).show();
            onScaleEnd = scale;

            super.onScaleEnd(detector);
        }
    }

}