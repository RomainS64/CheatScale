package com.example.metronome;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import static android.view.View.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.R;



public class PageMetronome extends AppCompatActivity {

    private TextView txtBpm;
    private SeekBar barBpm;
    private Metronome metronome;
    private Button plus,moins,startStop;

    private MediaPlayer tic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //metre en plein ecran

        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.metronome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtBpm = findViewById(R.id.txtbpm);
        barBpm = findViewById(R.id.seekbpm);
        plus = (Button) findViewById(R.id.plus);
        moins = (Button) findViewById(R.id.moins);
        startStop = (Button) findViewById(R.id.startstop);


        tic = MediaPlayer.create(this, R.raw.tic);
        metronome = new Metronome(tic,barBpm.getProgress());

        startStop.setText("start");
        barBpm.setProgress(120);
        txtBpm.setText(120+"");


        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = barBpm.getProgress();
                if (progress<barBpm.getMax()){
                    barBpm.setProgress(progress+1);
                }
            }
        });
        moins.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = barBpm.getProgress();
                if (progress>1){
                    barBpm.setProgress(progress-1);
                }
            }
        });

        startStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               if(startStop.getText() == "start"){
                   metronome = new Metronome(tic,barBpm.getProgress());
                   metronome.run();
                   startStop.setText("stop");
               }
               else{
                   metronome.stop();
                   startStop.setText("start");
               }
            }
        });



        barBpm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                metronome.bpm = progress;
                txtBpm.setText(progress+"bpm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        metronome.stop();

    }
}
