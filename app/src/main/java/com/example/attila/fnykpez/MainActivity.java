package com.example.attila.fnykpez;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    rajzvaszon rajzlap;
    private final int CAMERA_IMAGE_REQUEST = 101;
    Random rnd = new Random();
    int n = rnd.nextInt(20000);
    Long tsLong = System.currentTimeMillis()/1000;
    String ts = tsLong.toString();
    private final String IMAGEPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Image" + ts + ".jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnTakePhoto = (Button)findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String str;
                                                File imageFile = new File(IMAGEPATH);
                                                Uri imageFileUri = Uri.fromFile(imageFile);
                                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                                                startActivityForResult(cameraIntent,CAMERA_IMAGE_REQUEST);
                                            }
                                        }
        );
    }
    //Visszatérés a kamerától
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
                if (requestCode == CAMERA_IMAGE_REQUEST){
            if (resultCode == RESULT_OK) {
                try{
                    File imageFile = new File(IMAGEPATH);
                    FileInputStream fis = new FileInputStream(imageFile);
                    Bitmap img = BitmapFactory.decodeStream(fis);

                    //Arcfelismerés aktiválása
                    //FaceView fv= new FaceView(this, img);
                    //setContentView(fv);
                    //rajzlap = new rajzvaszon(this);
                    //setContentView(rajzlap);
                    ImageView ivPhoto =(ImageView)findViewById(R.id.ivPhoto);
                    ivPhoto.setImageBitmap(img);
                    long time = System.currentTimeMillis();
                    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy.MM.dd G 'án' HH:mm:ss z");
                    String str = dayTime.format(new Date(time));
                    Toast.makeText(this,"Íme az elkészült fénykép!",Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "A készítés időpontja: " + str, Toast.LENGTH_LONG).show();
                    }
                 catch (Exception e){}
            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this,"Nem készült fénykép!",Toast.LENGTH_LONG).show();
            }
        }

    }
}
