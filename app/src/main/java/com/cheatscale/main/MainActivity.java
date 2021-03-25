package com.cheatscale.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import static android.view.View.*;
import android.widget.Button;

import com.cheatscale.R;
import com.cheatscale.accordeur.*;
import com.cheatscale.metronome.*;
import com.cheatscale.identifiergamme.*;

public class MainActivity extends AppCompatActivity {

    private Button boutonScaleFinder;
    private Button boutonAccordeur;
    private Button boutonMetronome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Demander la permission d'utiliser le microphone
        demanderPermission();

        boutonScaleFinder = (Button) findViewById(R.id.bouton_scaleFinder);
        boutonAccordeur = (Button) findViewById(R.id.bouton_accordeur);
        boutonMetronome = (Button) findViewById(R.id.bouton_metronome);

        //Fonctionnement des boutons

        boutonScaleFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openIdentifierGamme = new Intent(MainActivity.this, IdentifierGamme.class); //Declaration de l'activité pour ouvrir la page ScaleFinder
                startActivity(openIdentifierGamme); //Ouverture de la page ScaleFinder

            }
        });

        boutonAccordeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openAccordeur = new Intent(MainActivity.this, Accordeur.class); //Declaration de l'activité pour ouvrir la page Accordeur
                startActivity(openAccordeur); //Ouverture de la page Accordeur

            }
        });

        boutonMetronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openMetronome = new Intent(MainActivity.this, PageMetronome.class); //Declaration de l'activité pour ouvrir la page Metronome
                startActivity(openMetronome); //Ouverture de la page Metronome

            }
        });
    }


    private void demanderPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1234);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    1234);
        }

    }
}