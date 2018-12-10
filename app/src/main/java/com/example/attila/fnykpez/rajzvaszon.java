package com.example.attila.fnykpez;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.Random;

public class rajzvaszon extends View{
    Paint paint = new Paint();
    //int i;
    Random rnd = new Random();
    Path path = new Path();
    Path path2 = new Path();

    public rajzvaszon(Context context){
        super(context);
        rnd.setSeed(600);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        //canvas.drawColor(Color.argb(255,255,255,255));

        //pontrajzolás
        paint.setColor(Color.parseColor("#005500"));
        //for (i<1;i<100;++i){
            canvas.drawPoint(rnd.nextInt(300),rnd.nextInt(300),paint);
        //}
    }
}
