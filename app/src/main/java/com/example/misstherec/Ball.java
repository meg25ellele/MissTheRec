package com.example.misstherec;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

class Ball {

    private Bitmap image;
    private int x,y;

    boolean lost =false;
    boolean hit = false;


    private int screenWith = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    Ball(Bitmap bitmap){
        image=bitmap;
        x=screenWith-image.getWidth();
        y=screenHeight-image.getHeight();
    }

    void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);

    }
    void update(int x_accele, int y_accele){

        x-=x_accele;
        y+=y_accele;

        if (x>screenWith-image.getWidth()){
            x= screenWith-image.getWidth();
        }
        if(x<0){
            x=0;
        }
        if(y>screenHeight-image.getHeight()){
            y=screenHeight-image.getHeight();
        }
        if(y<0){
            y=0;
        }

        checkHit();

        if(lost || hit){
            x=screenWith-image.getWidth();
            y=screenHeight-image.getHeight();

        }
    }

    private void checkHit(){

        int resize = 15;


        int ballXend = x + 100;
        int ballYend = y + 100;

        int targetCenterX = 900 + 150 /2;
        int targetCenterY = 10 + 150 /2;

        if(ballXend >45 + resize && x<345 - resize&& ballYend> 1000  + resize& y < 1300 - resize){
            lost =true;
        }

        if(ballXend >700 + resize && x<1050 - resize&& ballYend> 200 + resize & y < 600 - resize){
            lost =true;
        }

        if(ballXend >500 + resize && x<650 - resize && ballYend> 800 + resize & y < 1300 - resize){
            lost =true;
        }

        if(ballXend >800 + resize && x<1300 - resize && ballYend> 750 + resize & y < 900 - resize){
            lost =true;
        }

        if(ballXend >200 + resize && x<900 - resize && ballYend> 1500 + resize & y < 1750 - resize){
            lost =true;
        }
        if(ballXend >100 + resize && x<600 - resize && ballYend> 200 + resize & y < 400- resize){
            lost =true;
        }


        if(ballXend >targetCenterX && x<1050 && ballYend> targetCenterY & y < 160){
            hit=true;

        }

    }



}
