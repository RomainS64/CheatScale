package com.example.identifiergamme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.R;
import com.example.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class IdentifierGamme extends AppCompatActivity {

    Button start_button_identifier_tonalite, stop_button_identifier_tonalite;
    TextView texte_ecoute, texte_jouez, texte_tonalites_compatibles;
    ProgressBar progress_bar;
    View barre_horizontale_3, barre_horizontale_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identifier_gamme);

        // Initialisation des éléments graphiques
        start_button_identifier_tonalite = (Button)findViewById(R.id.start_button_identifier_tonalite);
        stop_button_identifier_tonalite = (Button)findViewById(R.id.stop_button_identifier_tonalite);
        texte_ecoute = (TextView)findViewById(R.id.texte_ecoute);
        texte_jouez = (TextView)findViewById(R.id.texte_jouez);
        texte_tonalites_compatibles = (TextView)findViewById(R.id.texte_tonalites_compatibles);
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        barre_horizontale_3 = (View)findViewById(R.id.barre_horizontale_3);
        barre_horizontale_2 = (View)findViewById(R.id.barre_horizontale_2);

        start_button_identifier_tonalite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Apparition des éléments à ajouter
                texte_ecoute.setVisibility(View.VISIBLE);
                texte_jouez.setVisibility(View.VISIBLE);
                progress_bar.setVisibility(View.VISIBLE);
                barre_horizontale_3.setVisibility(View.VISIBLE);
                stop_button_identifier_tonalite.setVisibility(View.VISIBLE);
                texte_tonalites_compatibles.setVisibility(View.VISIBLE);

                // Retrait des éléments inutiles
                start_button_identifier_tonalite.setVisibility(View.INVISIBLE);
                barre_horizontale_2.setVisibility(View.INVISIBLE);

                stop_button_identifier_tonalite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrait des éléments inutiles
                        texte_ecoute.setVisibility(View.INVISIBLE);
                        texte_jouez.setVisibility(View.INVISIBLE);
                        progress_bar.setVisibility(View.INVISIBLE);
                        barre_horizontale_3.setVisibility(View.INVISIBLE);
                        stop_button_identifier_tonalite.setVisibility(View.INVISIBLE);
                        texte_tonalites_compatibles.setVisibility(View.INVISIBLE);

                        // Réapparition des éléments utiles
                        start_button_identifier_tonalite.setVisibility(View.VISIBLE);
                        barre_horizontale_2.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}