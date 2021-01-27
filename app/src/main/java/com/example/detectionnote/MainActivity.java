package com.example.detectionnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button start,stop,analyser;
    TextView txt,note;

    ManagerAudio audioManager;
    ManagerNote noteManager;

    Thread AfficherNote;
    boolean stopAff=false;//tant que false, le thread afficherNote continue

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Demander la permition d'utiliser le microphone
        demanderPermission();

        //Initialisation des managers
        audioManager = new ManagerAudio();
        noteManager = new ManagerNote();

        //Initialisation des textes
        txt = findViewById(R.id.txt);
        note = findViewById(R.id.note);


        //initialisation des boutons
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        analyser = findViewById(R.id.analyser);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("Recording...");
                audioManager.run();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAff=true;
            }
        });
        analyser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MAIN", "Lancement Affichage");
                try {
                    stopAff = false;
                    AfficherNote.start();
                }catch (Throwable t) {
                    Log.e("MAIN", "erreurAffichage");
                }
            }
        });


        //Initialisation du thread d'affichage de note
        AfficherNote = new Thread(
                new Runnable() {
                    public void run() {
                        afficherNote();
                    }
                });
    }

    private void afficherNote(){
        double frequence;

        while(!stopAff) {
            try {
                frequence = audioManager.getFrequenceForte();

                txt.setText("" + frequence);

                if (noteManager.estUneNote(frequence)) {
                    Log.e("AFFICHAGE NOTE", "coucou j'affiche");
                    Note noteProche = noteManager.getNoteLaPlusProche(frequence);
                    note.setText(noteProche.toString() + "/" + noteManager.getDistanceNoteLaPlusProche(noteProche.getFrequence()));


                } else {
                    note.setText("");
                }
            }catch(Throwable t){
                Log.e("MAIN", "erreur tour de boucle Affichage");
                t.printStackTrace();
            }
        }
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