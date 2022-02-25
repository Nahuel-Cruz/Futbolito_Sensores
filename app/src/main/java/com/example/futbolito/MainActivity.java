package com.example.futbolito;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Vista vista= new Vista(this);
        setContentView(vista);
    }
    class Vista extends View {
        public Vista(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas){
            Paint cancha = new Paint();
            Paint centro= new Paint();
            centro.setStyle(Paint.Style.STROKE);
            centro.setStrokeWidth(5);
            centro.setColor(Color.WHITE);
            cancha.setColor(Color.GREEN);

            int anchoCancha=canvas.getWidth();
            int largoCancha=canvas.getHeight();
            canvas.drawRect(10,largoCancha-10,anchoCancha-10,10,cancha);

            canvas.drawCircle(anchoCancha/2,largoCancha/2,120,centro);




        }
    }
}