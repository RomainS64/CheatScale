package com.example.metronome;


import android.media.MediaPlayer;
import android.util.Log;


public class Metronome {
    public MediaPlayer tic;
    public float bpm;

    private Thread battement;
    private boolean finBattement=false;
    private float timeStart;



    Metronome(MediaPlayer tic){

        this.tic = tic;
        bpm = 90;
        battement = new Thread(
                new Runnable() {
                    public void run() {
                        battement();
                    }
                });

    }

    void run(){
        battement.start();
    }


    void battement(){
        while(!finBattement){
            timeStart =(int)System.currentTimeMillis();
            while((int)System.currentTimeMillis()-timeStart < 60000/bpm){
                Log.e("Metronome", ""+((int)System.currentTimeMillis()-(int)timeStart));
            }
            tic.start();
        }
    }
}
