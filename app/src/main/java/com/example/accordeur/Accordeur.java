package com.example.accordeur;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.view.View.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Note;
import com.example.R;
import com.example.identifiernotes.ManagerNote;
import com.example.metronome.Metronome;

import org.w3c.dom.Text;


public class Accordeur extends AppCompatActivity {

    ManagerNote managerNote;
    Button startStop;
    TextView noteJouee;
    SeekBar distanceNote;

    boolean ecoute = false;
    int max = 10;
    int min = -10;


    Thread ecouter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.accordeur);

        startStop = (Button) findViewById(R.id.startstopAccordeur);
        startStop.setText("start");
        noteJouee = findViewById(R.id.noteAccordeur);
        distanceNote = findViewById(R.id.distanceAccordeur);
        distanceNote.setMax(max);
        distanceNote.setMax(min);
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
                        distanceNote.setProgress((int)managerNote.getDistanceNoteLaPlusProche(managerNote.getAudioManager().getFrequenceForte()));
                    }
                });
            } catch (Throwable e) {}

        }

    }

}
