package com.example.identifiergamme;

import android.os.Bundle;
import static android.view.View.*;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.R;
import com.example.Note;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class IdentifierGamme extends AppCompatActivity {

    Button start_button_identifier_tonalite, stop_button_identifier_tonalite, button_gamme_1;
    TextView texte_ecoute, texte_jouez, texte_tonalites_compatibles, texte_note, texte_gamme1;
    ProgressBar progress_bar;
    View barre_horizontale_3, barre_horizontale_2;
    ImageView img_check;

    com.example.scalefinder.detectionnote.ManagerAudio audioManager;
    com.example.scalefinder.detectionnote.ManagerNote noteManager;

    ManagerGamme gammeManager;

    Thread AfficherNote;
    boolean stopAff = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.identifier_gamme);

        // Initialisation des éléments graphiques

        start_button_identifier_tonalite = (Button)findViewById(R.id.start_button_identifier_tonalite);
        stop_button_identifier_tonalite = (Button)findViewById(R.id.stop_button_identifier_tonalite);
        button_gamme_1 = (Button)findViewById(R.id.button_gamme1);
        texte_ecoute = (TextView)findViewById(R.id.texte_ecoute);
        texte_jouez = (TextView)findViewById(R.id.texte_jouez);
        texte_tonalites_compatibles = (TextView)findViewById(R.id.texte_tonalites_compatibles);
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        barre_horizontale_3 = (View)findViewById(R.id.barre_horizontale_3);
        barre_horizontale_2 = (View)findViewById(R.id.barre_horizontale_2);
        texte_note = (TextView)findViewById(R.id.texte_note);
        texte_gamme1 = (TextView)findViewById(R.id.texte_gamme1);
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
                gammeManager = new ManagerGamme();

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
        final int NOMBRE_DE_NOTES_A_ANALYSER = 10;
        final double PROPORTION_DE_NOTES_REQUISE = 0.9;

        Note[] dernieresNotes = new Note[NOMBRE_DE_NOTES_A_ANALYSER];
        double frequence;
        Note noteProche;
        int nombreOccurence;


        while(!stopAff) {
            try {
                frequence = audioManager.getFrequenceForte();

                if (noteManager.estUneNote(frequence)) {
                    noteProche = noteManager.getNoteLaPlusProche(frequence);
                    nombreOccurence = 0;

                    // décalage des dernières notes
                    for (int i = dernieresNotes.length-1; i > 0; i--) {
                        dernieresNotes[i] = dernieresNotes[i-1];
                    }
                    // Insertion de la nouvelle note
                    dernieresNotes[0] = noteProche;

                    // On compte le nombre d'occurence de la nouvelle note dans le tableau
                    for (int j = 0; j <dernieresNotes.length; j++) {
                        if (dernieresNotes[j].toString().compareTo(noteProche.toString()) == 0) {
                            nombreOccurence++;
                        }
                    }

                    // Si la noteCourante est en proportion satisfaisante dans le tableau, on valide la note
                    if (nombreOccurence / dernieresNotes.length >= PROPORTION_DE_NOTES_REQUISE) {
                        // Affichage de la note
                        if (noteProche.toString().compareTo(texte_note.getText().toString()) != 0) {
                            texte_note.setVisibility(View.INVISIBLE);
                            texte_note.setText(noteProche.toString());
                            texte_note.setVisibility(View.VISIBLE);
                        }
                    }

                    // On actualise maintenant les gammes
                    afficherGammes(noteProche);
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }

    private void afficherGammes(Note note) {
        if (texte_gamme1.getVisibility() == INVISIBLE || button_gamme_1.getVisibility() == INVISIBLE) {
            texte_gamme1.setVisibility(VISIBLE);
            button_gamme_1.setVisibility(VISIBLE);
        }
        List<Gamme> gammes = gammeManager.ajouterOccurenceDeNote(note.toString());
        texte_gamme1.setText(gammes.get(0).nom());
    }
}