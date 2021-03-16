package com.example.identifiergamme;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import com.example.R;

public class InfosGamme extends AppCompatActivity {
    protected TextView texteNomGammeChoisie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infos_gamme);

        texteNomGammeChoisie = (TextView)this.findViewById(R.id.texte_nom_gamme_choisie);

        texteNomGammeChoisie.setText(IdentifierGamme.gammeChoisie.nom());

    }
}