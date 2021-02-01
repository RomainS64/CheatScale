package com.example.accordeur;

import android.os.Bundle;
import static android.view.View.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.R;


public class Accordeur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.accordeur);
    }

}
