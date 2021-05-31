package com.example.gesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class PaintView  extends View {

    // LINK XEM CHI TIẾT CÁC THUỘC TÍNH TRONG PAINT
    // https://viblo.asia/p/canvas-trong-android-phan-1-bJzKm1eDK9N
    public static int BRUSH_SIZE = 10;
    public  static final int DEFAULT_COLOR = Color.RED;
    public  static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;
    private float mX,mY;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<FingerPath> paths= new ArrayList<>();
    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private  int strokeWidth;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint((Paint.DITHER_FLAG));

    public PaintView(Context context) {
        this(context,null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint= new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);

        Toast.makeText(getContext(),"PaintView",Toast.LENGTH_SHORT).show();
    }
    public  void init(DisplayMetrics metrics){
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor= DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
        Toast.makeText(getContext(),"init",Toast.LENGTH_LONG).show();
    }
    public  void normal(){
        Toast.makeText(getContext(),"normal",Toast.LENGTH_LONG).show();
    }
    public  void clear(){
        backgroundColor= DEFAULT_BG_COLOR;
        paths.clear();
        normal();
        invalidate();
        Toast.makeText(getContext(),"clear",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(backgroundColor);

        for(FingerPath fp:paths){
            mPaint.setColor(fp.color);
            //Set giá trị độ rộng của net vẽ.
            mPaint.setStrokeWidth(fp.stokeWidth);

            mCanvas.drawPath( fp.path,mPaint);
        }

        canvas.drawBitmap(mBitmap,0,0,mBitmapPaint);
        //Gọi canvas.Restore(); để đưa canvas về trạng thái nó đã save trước đó.
        //Lưu ý. canvas.Restore() chỉ có thể gọi được nếu bạn đã call canvas.Save();.
        // Nếu bạn cố tình gọi canvas.Restore() mà chưa gọi canvas.Save() ở trước thì Android sẽ ném ra ngoại lệ.
        canvas.restore();
        Toast.makeText(getContext(),"onDraw",Toast.LENGTH_SHORT).show();
    }


    private  void touchStart(float x, float y){
        mPath= new Path();
        FingerPath fp= new FingerPath(currentColor,strokeWidth,  mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x,y);
        mX= x;
        mY=y;
        Toast.makeText(getContext(),"touchStart",Toast.LENGTH_SHORT).show();
    }
    private void touchMove(float x, float y){
        float dx= Math.abs(x-mX);
        float dy= Math.abs(y-mY);
        if(dx >=TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            mPath.quadTo(mX,mY,(x+mX)/2,(y+mY)/2);
            mX=x;
            mY=y;
        }
        Toast.makeText(getContext(),"touchMove",Toast.LENGTH_SHORT).show();
    }
    private  void touchUp(){
        mPath.lineTo(mX,mY);
        Toast.makeText(getContext(),"touchUp",Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x= event.getX();
        float y= event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        Toast.makeText(getContext(),"onTouchEvent",Toast.LENGTH_SHORT).show();
        return true;
    }
}
