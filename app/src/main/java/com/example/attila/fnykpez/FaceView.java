package com.example.attila.fnykpez;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.FaceDetector;
import android.support.constraint.solver.widgets.Rectangle;
import android.view.View;

public class FaceView extends View {
    //max 64
    private static final int MAX_FACES = 10;
    private Bitmap sourceImg;
    private FaceDetector myFaceDetector;
    private FaceDetector.Face allFaces[] = new FaceDetector.Face[MAX_FACES];
    private FaceDetector.Face getFace = null;
    private PointF eyesMidPts[] = new PointF[MAX_FACES];
    private float eyesDistance[] = new float[MAX_FACES];
    private Paint tempPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint eyePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int imgWidth, imgHeight;
    private float xRat, yRat;

    public FaceView(Context context, Bitmap aSurceImage){
        super(context);

        eyePaint.setStyle(Paint.Style.STROKE);
        eyePaint.setColor(Color.GREEN);

        tempPaint.setStyle(Paint.Style.STROKE);
        tempPaint.setTextAlign(Paint.Align.CENTER);

        sourceImg = aSurceImage;

        imgWidth = sourceImg.getWidth();
        imgHeight = sourceImg.getHeight();

        myFaceDetector = new FaceDetector(imgWidth, imgHeight, MAX_FACES);
        // ez a hívás sokáig tarthat
        myFaceDetector.findFaces(sourceImg, allFaces);

        for (int i=0; i< allFaces.length; i++){
            getFace = allFaces[i];
            try{
                PointF eyesMP = new PointF();
                getFace.getMidPoint(eyesMP);
                eyesDistance[i] = getFace.eyesDistance();
                eyesMidPts[i] = eyesMP;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas){
        xRat = getWidth()*1.0f / imgWidth;
        yRat = getHeight()*1.0f / imgHeight;
        //teljes kép kirajzolása
        //public new Rect(0,0, getWidth(), getHeight());
        //canvas.drawBitmap(sourceImg,null, new Rect(0,0, getWidth(), getHeight(), android.graphics.Paint));
        //körök kirajzolása az arcokra
        for (int i =0; i<eyesMidPts.length; i++){
            if (eyesMidPts[i] != null){
                eyePaint.setStrokeWidth(eyesDistance[i]/6);
                canvas.drawCircle(eyesMidPts[i].x*xRat,eyesMidPts[i].y*yRat, eyesDistance[i]/2, eyePaint);
            }
        }
    }

}
