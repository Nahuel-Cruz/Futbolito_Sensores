package com.example.futbolito;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private static int x, y;
    private int height, width;

    ShapeDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Display display = getWindowManager().getDefaultDisplay();
        height = display.getHeight();
        width = display.getWidth();
        Log.d("Tamaño pantalla", "x "+ width +"y "+height);

        Ball ball = new Ball(this);
        setContentView(ball);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this,
                    accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //When the values of 'x' and 'y' axis change I update global variables
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            //Bounds for X axis
            if(x + 100 - (int) sensorEvent.values[0] >= width){
                x = width - 100;
            }else if(x - (int) sensorEvent.values[0] <= 1){
                x = 0;
            }else{
                x -= (int) sensorEvent.values[0];
            }

            //Bounds for Y axis
            if(y + 100 + (int) sensorEvent.values[1] >= height){
                y = height - 100;
            }else if(y + (int) sensorEvent.values[1] <= 1){
                y = 0;
            }else{
                y += (int) sensorEvent.values[1];
            }
            Log.d("Valores de los ejes", "x "+ x +", y "+y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public class Ball extends View{
        static final int width = 100;
        static final int height = 100;

        public Ball(Context context) {
            super(context);
            drawable = new ShapeDrawable(new OvalShape());
            //drawable.getPaint().setColor(0xff74AC23);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint balon = new Paint();
            balon.setColor(Color.WHITE);
            Paint cancha = new Paint();
            Paint centro= new Paint();
            Paint porteria = new Paint();
            centro.setStyle(Paint.Style.STROKE);
            centro.setStrokeWidth(5);
            centro.setColor(Color.WHITE);
            cancha.setColor(Color.GREEN);
            porteria.setColor(Color.BLACK);

            int anchoCancha=canvas.getWidth();
            int largoCancha=canvas.getHeight();
            canvas.drawRect(10,largoCancha-10,anchoCancha-10,10,cancha);

            canvas.drawRect(1,largoCancha/2,anchoCancha,1,centro);
            canvas.drawCircle(anchoCancha/2,largoCancha/2,120,centro);
            int anchop=anchoCancha/5;
            canvas.drawRect(anchop*2,10,anchop*3,150,porteria);
            canvas.drawRect(anchop*2,largoCancha-150,anchop*3,largoCancha-10,porteria);



            RectF oval = new RectF(MainActivity.x, MainActivity.y, MainActivity.x + width, MainActivity.y
                    + height); // set bounds of rectangle


            //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.field),0,0,null); <--- Draw a background

            //Ball
            canvas.drawOval(oval,balon);

            invalidate();
        }
    }
}