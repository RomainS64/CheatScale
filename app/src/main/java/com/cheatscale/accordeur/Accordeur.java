package com.cheatscale.accordeur;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.view.View.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cheatscale.Note;
import com.cheatscale.R;
import com.cheatscale.identifiernotes.ManagerNote;


public class Accordeur extends AppCompatActivity {

    ManagerNote managerNote;
    Button startStop;
    TextView noteJouee;
    SeekBar distanceNote;

    boolean ecoute = false;
    int max = 100;
    int min = -100;


    Thread ecouter;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.accordeur);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        startStop = (Button) findViewById(R.id.startstopAccordeur);
        startStop.setText("start");
        noteJouee = findViewById(R.id.noteAccordeur);
        distanceNote = findViewById(R.id.distanceAccordeur);
        distanceNote.setMax(max);
        distanceNote.setMin(min);
        managerNote = new ManagerNote();

        startStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startStop.getText() == "start"){

                    ecoute = true;
                    ecouter = new Thread(new Runnable() {
                                public void run() {
                                    ecouter();
                                }
                            });
                    startStop.setText("stop");
                    try {
                        ecouter.start();
                    }catch (Throwable t) {
                        t.printStackTrace();
                    }

                }
                else{
                    ecoute = false;
                    managerNote.arreterRecherche();
                    startStop.setText("start");
                }
            }
        });

    }
    Note note;
    public void ecouter(){
        while(ecoute){
            note = managerNote.getNoteLaPlusProche(managerNote.getAudioManager().getFrequenceForte());

            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        noteJouee.setText(note.toString());

                        distanceNote.setProgress((int)
                                (managerNote.getDistanceNoteLaPlusProche
                                        (managerNote.getAudioManager().getFrequenceForte())*10));
                    }
                });
            } catch (Throwable e) {}

        }

    }

}
