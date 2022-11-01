package com.google.dkClock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextClock;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    TextClock time, seconds, amPm, date, year;
    LinearLayout mainLayout;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        fullScreen();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fullScreen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = (TextClock)findViewById(R.id.textClockTime);
        seconds = (TextClock)findViewById(R.id.textClockSeconds);
        amPm = (TextClock)findViewById(R.id.textClockAmPm);
        date = (TextClock)findViewById(R.id.textClockDate);
        year = (TextClock)findViewById(R.id.textClockYear);
        mainLayout = (LinearLayout)findViewById(R.id.linearLayoutMain);

        Typeface myFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/digital.ttf");

        time.setTypeface(myFont);
        seconds.setTypeface(myFont);
        amPm.setTypeface(myFont);
        date.setTypeface(myFont);
        year.setTypeface(myFont);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        fullScreen();

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);

            }
        });





    }
    void fullScreen()
    {

        View decorView = getWindow().getDecorView();
        int uiOptions = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
            uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        else
            uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Drawable image = Drawable.createFromStream(inputStream, selectedImage.toString());
            mainLayout.setBackground(image);
        }
    }
}