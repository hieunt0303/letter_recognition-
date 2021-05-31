package com.example.gesture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;

public class MainActivity extends AppCompatActivity {

    //https://eitguide.net/nhung-kien-thuc-ve-view-trong-android-ma-cac-ban-nen-biet/
    //
    //https://qastack.vn/programming/4743116/get-screen-width-and-height-in-android
    //
    //https://viblo.asia/p/canvas-trong-android-phan-2-3Q75wggD5Wb
    //
    //https://developers.google.com/ml-kit/vision/text-recognition/android
    //
    //https://viblo.asia/p/android-customview-paint-su-dung-canvas-Qpmle2Xr5rd
    TextView txt;
    Button btn;
    ImageView img;

    // paint view
    private PaintView paintView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.textView);
        btn= findViewById(R.id.button);
        img = findViewById(R.id.imageView);

        //bắt sự kiện nhận dạng chữ
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectText();
            }
        });

        // paint view
        paintView = findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();

        // LINK : https://qastack.vn/programming/4743116/get-screen-width-and-height-in-android
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.normal1:
                paintView.normal();
                return true;
            case R.id.clear:
                paintView.clear();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public  void  detectText(){
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.word);

        Bitmap bitmap = getBitmapFromView(paintView);
        img.setImageBitmap(bitmap);

        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap1 = drawable.getBitmap();
        Toast.makeText(MainActivity.this,"truyen dc img",Toast.LENGTH_SHORT).show();

        InputImage image = InputImage.fromBitmap(bitmap1,0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // Task completed successfully
                                // ...
                                txt.setText(visionText.getText());
                                Toast.makeText(MainActivity.this,"done",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                        Toast.makeText(MainActivity.this,"khong thanh cong",Toast.LENGTH_SHORT).show();
                                    }
                                });


    }

}