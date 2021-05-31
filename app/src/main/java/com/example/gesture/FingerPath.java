package com.example.gesture;


import android.graphics.Path;

public class FingerPath {

    public int color;

    public  int stokeWidth;
    public Path path;

    public FingerPath(int color, int stokeWidth, Path path) {
        this.color = color;
        this.stokeWidth = stokeWidth;
        this.path = path;
    }
}
