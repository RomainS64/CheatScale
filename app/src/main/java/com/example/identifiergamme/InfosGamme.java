package com.example.identifiergamme;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import com.example.R;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class InfosGamme extends AppCompatActivity {
    protected TextView texteNomGammeChoisie;
    protected TextView texteRelativeMineure;
    protected TextView texteNotesDeLaGamme;
    protected TextView texteAccord1;
    protected TextView texteAccord2;
    protected TextView texteAccord3;
    protected TextView texteAccord4;
    protected TextView texteAccord5;
    protected TextView texteAccord6;
    protected TextView texteAccord7;
    protected TextView texteAccordRelativeMineure1;
    protected TextView texteAccordRelativeMineure2;
    protected TextView texteAccordRelativeMineure3;
    protected TextView texteAccordRelativeMineure4;
    protected TextView texteAccordRelativeMineure5;
    protected TextView texteAccordRelativeMineure6;
    protected TextView texteAccordRelativeMineure7;
    protected String notesDeLaGamme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.infos_gamme);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        texteNomGammeChoisie = (TextView)this.findViewById(R.id.texte_nom_gamme_choisie);
        texteRelativeMineure = (TextView)this.findViewById(R.id.texte_relative_mineure);
        texteNotesDeLaGamme = (TextView)this.findViewById(R.id.texte_notes_de_la_gamme);
        texteAccord1 = (TextView)this.findViewById(R.id.texte_accord_1);
        texteAccord2 = (TextView)this.findViewById(R.id.texte_accord_2);
        texteAccord3 = (TextView)this.findViewById(R.id.texte_accord_3);
        texteAccord4 = (TextView)this.findViewById(R.id.texte_accord_4);
        texteAccord5 = (TextView)this.findViewById(R.id.texte_accord_5);
        texteAccord6 = (TextView)this.findViewById(R.id.texte_accord_6);
        texteAccord7 = (TextView)this.findViewById(R.id.texte_accord_7);
        texteAccordRelativeMineure1 = (TextView)this.findViewById(R.id.texte_accord_relative_mineure_1);
        texteAccordRelativeMineure2 = (TextView)this.findViewById(R.id.texte_accord_relative_mineure_2);
        texteAccordRelativeMineure3 = (TextView)this.findViewById(R.id.texte_accord_relative_mineure_3);
        texteAccordRelativeMineure4 = (TextView)this.findViewById(R.id.texte_accord_relative_mineure_4);
        texteAccordRelativeMineure5 = (TextView)this.findViewById(R.id.texte_accord_relative_mineure_5);
        texteAccordRelativeMineure6 = (TextView)this.findViewById(R.id.texte_accord_relative_mineure_6);
        texteAccordRelativeMineure7 = (TextView)this.findViewById(R.id.texte_accord_relative_mineure_7);

        texteNomGammeChoisie.setText("Gamme de " + IdentifierGamme.gammeChoisie.nom());

        notesDeLaGamme = IdentifierGamme.gammeChoisie.notesDeLaGamme().get(0)
                + "  -  " + IdentifierGamme.gammeChoisie.notesDeLaGamme().get(1)
                + "  -  " + IdentifierGamme.gammeChoisie.notesDeLaGamme().get(2)
                + "  -  " + IdentifierGamme.gammeChoisie.notesDeLaGamme().get(3)
                + "  -  " + IdentifierGamme.gammeChoisie.notesDeLaGamme().get(4)
                + "  -  " + IdentifierGamme.gammeChoisie.notesDeLaGamme().get(5)
                + "  -  " + IdentifierGamme.gammeChoisie.notesDeLaGamme().get(6);
        texteNotesDeLaGamme.setText(notesDeLaGamme);

        texteAccord1.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(0));
        texteAccord2.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(1)+"m");
        texteAccord3.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(2)+"m");
        texteAccord4.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(3));
        texteAccord5.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(4));
        texteAccord6.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(5)+"m");
        texteAccord7.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(6)+"m5b");

        texteAccordRelativeMineure1.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(5)+"m");
        texteAccordRelativeMineure2.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(6)+"m5b");
        texteAccordRelativeMineure3.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(0));
        texteAccordRelativeMineure4.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(1)+"m");
        texteAccordRelativeMineure5.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(2)+"m");
        texteAccordRelativeMineure6.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(3));
        texteAccordRelativeMineure7.setText(IdentifierGamme.gammeChoisie.notesDeLaGamme().get(4));

        texteRelativeMineure.setText("Gamme relative mineure : " + IdentifierGamme.gammeChoisie.relativeMineure());
    }
}