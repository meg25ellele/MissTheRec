package com.example.misstherec;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Element {

    public Bitmap image;


    public Element(Bitmap bitmap){
        image=bitmap;

    }

    public void draw(Canvas canvas, int x_pos, int y_pos) {

        canvas.drawBitmap(image,x_pos,y_pos,null);

    }
}
