package com.example.metronome;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import static android.view.View.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.R;



public class PageMetronome extends AppCompatActivity {

    TextView txtBpm;
    SeekBar barBpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
