package com.example.misstherec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private Ball ball;
    private Element rec1;
    private Element rec2;
    private Element rec3;
    private Element rec4;
    private Element rec5;
    private Element rec6;
    private Element target;

    private int points = 0;

    SensorEventListener moveListener;
    SensorEventListener blindListener;

    private int x_accele = 0;
    private int y_accele = 0;


    public GameView(Context context, SensorManager sensorManager) {
        super(context);

        Sensor move = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor blind = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        blindListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] < 10) {
                    points = 0
                    ;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        moveListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                x_accele=(int)event.values[0]*5;
                y_accele=(int)event.values[1]*5;


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(moveListener, move, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(blindListener, blind, SensorManager.SENSOR_DELAY_FASTEST);



        thread = new MainThread(getHolder(),this);
        setFocusable(true);

        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Bitmap ball_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ball),
                100, 100, false);
        ball = new Ball(ball_image);

        Bitmap rec1_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rec1),
                300, 300, false);
        rec1 = new Element(rec1_image);

        Bitmap rec2_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rec2),
                350, 400, false);
        rec2 = new Element(rec2_image);

        Bitmap rec3_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rec3),
                150, 500, false);
        rec3 = new Element(rec3_image);

        Bitmap rec4_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rec1),
                500, 150, false);
        rec4 = new Element(rec4_image);

        Bitmap rec5_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rec2),
                700, 250, false);
        rec5 = new Element(rec5_image);

        Bitmap rec6_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rec3),
                500, 200, false);
        rec6 = new Element(rec6_image);

        Bitmap target_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.target),
                150, 150, false);
        target = new Element(target_image);


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry  = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e) {e.printStackTrace();}
            retry=false;
        }


    }
    public void update(){
        ball.update(x_accele,y_accele);

        if(ball.hit){
            points+=1;
        }

        if(ball.lost){
            points=0;
        }

        ball.hit=false;
        ball.lost=false;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        if(canvas!=null) {

            canvas.drawColor(Color.WHITE);
            ball.draw(canvas);
            rec1.draw(canvas,45,1000);
            rec2.draw(canvas,700,200);
            rec3.draw(canvas,500,800);
            rec4.draw(canvas,800,750);
            rec5.draw(canvas,200,1500);
            rec6.draw(canvas,100,200);
            target.draw(canvas,900, 10);



            Paint score = new Paint();
            score.setColor(Color.BLACK);
            score.setTextSize(100);


            canvas.drawText(Integer.toString(points), 15, 100, score);



        }

    }





}
