package com.example.identifiergamme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.R;
import com.example.Note;

import androidx.appcompat.app.AppCompatActivity;

public class IdentifierGamme extends AppCompatActivity {

    Button start_button_identifier_tonalite, stop_button_identifier_tonalite;
    TextView texte_ecoute, texte_jouez, texte_tonalites_compatibles, texte_note;
    ProgressBar progress_bar;
    View barre_horizontale_3, barre_horizontale_2;
    ImageView img_check;

    com.example.scalefinder.detectionnote.ManagerAudio audioManager;
    com.example.scalefinder.detectionnote.ManagerNote noteManager;

    Thread AfficherNote;
    boolean stopAff = false;


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
        texte_note = (TextView)findViewById(R.id.texte_note);
        img_check = (ImageView)findViewById(R.id.img_check);


        // Déclenchement de la fonctionnalité
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

                //Initialisation des managers
                audioManager = new com.example.scalefinder.detectionnote.ManagerAudio();
                noteManager = new com.example.scalefinder.detectionnote.ManagerNote();

                // Initialisation du thread d'affichage de note
                AfficherNote = new Thread(new Runnable() { public void run() { afficherNote(); } });

                // On lance l'écoute
                audioManager.run();
                try {
                    stopAff = false;
                    AfficherNote.start();
                } catch (Throwable t) {
                    // Message d'erreur à dev
                }
            }
        });


        // Arrêt de la fonctionnalité
        stop_button_identifier_tonalite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Arrêt du thread
                stopAff = true;
                try {
                    AfficherNote.join();
                }
                catch(InterruptedException e) {
                    // Message d'erreur à dev
                }

                // Retrait des éléments inutiles
                texte_ecoute.setVisibility(View.INVISIBLE);
                texte_jouez.setVisibility(View.INVISIBLE);
                progress_bar.setVisibility(View.INVISIBLE);
                barre_horizontale_3.setVisibility(View.INVISIBLE);
                stop_button_identifier_tonalite.setVisibility(View.INVISIBLE);
                texte_tonalites_compatibles.setVisibility(View.INVISIBLE);
                img_check.setVisibility(View.INVISIBLE);
                texte_note.setVisibility(View.INVISIBLE);

                // Réapparition des éléments utiles
                start_button_identifier_tonalite.setVisibility(View.VISIBLE);
                barre_horizontale_2.setVisibility(View.VISIBLE);
            }
        });
    }

    private void afficherNote() {
        double frequence;

        while(!stopAff) {
            try {
                frequence = audioManager.getFrequenceForte();

                // txt.setText("" + frequence);

                if (noteManager.estUneNote(frequence)) {

                    Note noteProche = noteManager.getNoteLaPlusProche(frequence);

                    // Disparition des indications de recherche
                    texte_ecoute.setVisibility(View.INVISIBLE);
                    progress_bar.setVisibility(View.INVISIBLE);


                    // Affichage de la note
                    texte_note.setVisibility(View.VISIBLE);
                    img_check.setVisibility(View.VISIBLE);
                    texte_note.setText(noteProche.toString());


                } else {
                    // Apparition des indications de recherche
                    texte_ecoute.setVisibility(View.VISIBLE);
                    progress_bar.setVisibility(View.VISIBLE);


                    // Disparition de la note
                    texte_note.setVisibility(View.INVISIBLE);
                    img_check.setVisibility(View.INVISIBLE);
                    texte_note.setText("");
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
}