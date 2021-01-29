package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.R;
import com.example.detectionnote.*;
import com.example.accordeur.*;
import com.example.metronome.*;

public class MainActivity extends AppCompatActivity {

    private Button boutonScaleFinder;
    private Button boutonAccordeur;
    private Button boutonMetronome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Demander la permition d'utiliser le microphone
        demanderPermission();

        boutonScaleFinder = (Button) findViewById(R.id.bouton_scaleFinder);
        boutonAccordeur = (Button) findViewById(R.id.bouton_accordeur);
        boutonMetronome = (Button) findViewById(R.id.bouton_metronome);

        //Fonctionnement des boutons

        boutonScaleFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openScaleFinder = new Intent(MainActivity.this, ScaleFinder.class); //Declaration de l'activité pour ouvrir la page ScaleFinder
                startActivity(openScaleFinder); //Ouverture de la page ScaleFinder

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