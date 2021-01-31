package com.example.metronome;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.R;

public class PageMetronome extends AppCompatActivity {

    TextView txtBpm;
    SeekBar barBpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.metronome);

        txtBpm = findViewById(R.id.txtbpm);
        barBpm = findViewById(R.id.seekbpm);


        final Metronome metronome = new Metronome(MediaPlayer.create(this, R.raw.tic));
        barBpm.setProgress(120);
        txtBpm.setText(120+"");
        metronome.run();

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


}
