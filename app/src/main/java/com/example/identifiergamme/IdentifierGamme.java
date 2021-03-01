package com.example.identifiergamme;

import android.os.Bundle;
import static android.view.View.*;

import android.util.Log;
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

    // ---------- Eléments graphiques ---------- \\
    // Boutons
    private Button boutonStart;
    private Button boutonStop;
    private Button boutonGamme1;

    // Textes
    private TextView texteEcouteEnCours;
    private TextView texteIndiquationJouez;
    private TextView texteTonalitesCompatibles;
    private TextView texteNoteCourante;
    private TextView texteCompatibiliteGamme1;

    // Autre
    private ProgressBar progress_bar;
    private View barre_horizontale_3;
    private View barre_horizontale_2;
    private ImageView img_check;
    // ------------------------------------------ \\


    // ---------- Managers ---------- \\
    com.example.scalefinder.detectionnote.ManagerAudio audioManager;
    com.example.scalefinder.detectionnote.ManagerNote noteManager;
    ManagerGamme gammeManager;
    // ------------------------------ \\


    // ---------- Threads & Gestion des Threads ---------- \\
    Thread GererNotesEtGammes;
    boolean stopAff = false;
    // --------------------------------------------------- \\


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.identifier_gamme);

        // ---------- Initialisation des éléments graphiques ---------- \\
        // Boutons
        boutonStart = (Button)findViewById(R.id.start_button_identifier_tonalite);
        boutonStop = (Button)findViewById(R.id.stop_button_identifier_tonalite);
        boutonGamme1 = (Button)findViewById(R.id.button_gamme1);

        // Textes
        texteEcouteEnCours = (TextView)findViewById(R.id.texte_ecoute);
        texteIndiquationJouez = (TextView)findViewById(R.id.texte_jouez);
        texteTonalitesCompatibles = (TextView)findViewById(R.id.texte_tonalites_compatibles);
        texteNoteCourante = (TextView)findViewById(R.id.texte_note);
        texteCompatibiliteGamme1 = (TextView)findViewById(R.id.texte_gamme1);

        // Autre
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        barre_horizontale_2 = (View)findViewById(R.id.barre_horizontale_2);
        barre_horizontale_3 = (View)findViewById(R.id.barre_horizontale_3);
        img_check = (ImageView)findViewById(R.id.img_check);
        // -------------------------------------------------------------- \\


        // Déclenchement de la fonctionnalité
        boutonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ---------- Mise à jour des éléments graphiques ---------- \\
                // Apparition des éléments à ajouter
                texteEcouteEnCours.setVisibility(View.VISIBLE);
                texteIndiquationJouez.setVisibility(View.VISIBLE);
                progress_bar.setVisibility(View.VISIBLE);
                barre_horizontale_3.setVisibility(View.VISIBLE);
                boutonStop.setVisibility(View.VISIBLE);
                texteTonalitesCompatibles.setVisibility(View.VISIBLE);
                boutonGamme1.setVisibility(View.VISIBLE);
                texteCompatibiliteGamme1.setVisibility(VISIBLE);

                // Retrait des éléments inutiles
                boutonStart.setVisibility(View.INVISIBLE);
                barre_horizontale_2.setVisibility(View.INVISIBLE);
                // --------------------------------------------------------- \\


                // ---------- Initialisation des éléments requis ---------- \\
                //Initialisation des managers
                audioManager = new com.example.scalefinder.detectionnote.ManagerAudio();
                noteManager = new com.example.scalefinder.detectionnote.ManagerNote();
                gammeManager = new ManagerGamme();

                // Initialisation du thread d'affichage de note
                GererNotesEtGammes = new Thread(new Runnable() { public void run() { gererNotesCourantes(); } });
                // -------------------------------------------------------- \\


                // ---------- Lancement du Thread et du manager audio ---------- \\
                audioManager.run();
                try {
                    stopAff = false;
                    GererNotesEtGammes.start();
                } catch (Throwable t) {
                    // Message d'erreur à dev
                }
                // -------------------------------------------------------------- \\
            }
        });


        // Arrêt de la fonctionnalité
        boutonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ---------- Arrêt du thread ---------- \\
                stopAff = true;
                try {
                    GererNotesEtGammes.join();
                }
                catch(InterruptedException e) {
                    // Message d'erreur à dev
                }
                // -------------------------------------- \\


                // ---------- Mise à jour des éléments graphiques ---------- \\
                // Retrait des éléments inutiles
                texteEcouteEnCours.setVisibility(View.INVISIBLE);
                texteIndiquationJouez.setVisibility(View.INVISIBLE);
                progress_bar.setVisibility(View.INVISIBLE);
                barre_horizontale_3.setVisibility(View.INVISIBLE);
                boutonStop.setVisibility(View.INVISIBLE);
                texteTonalitesCompatibles.setVisibility(View.INVISIBLE);
                img_check.setVisibility(View.INVISIBLE);
                texteNoteCourante.setVisibility(View.INVISIBLE);
                boutonGamme1.setVisibility(INVISIBLE);
                texteCompatibiliteGamme1.setVisibility(INVISIBLE);

                // Réapparition des éléments utiles
                boutonStart.setVisibility(View.VISIBLE);
                barre_horizontale_2.setVisibility(View.VISIBLE);
                // ---------------------------------------------------------- \\
            }
        });
    }

    private void gererNotesCourantes() {
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
                        try {
                            if (dernieresNotes[j].toString().compareTo(noteProche.toString()) == 0) {
                                nombreOccurence++;
                            }
                        }
                        catch(Throwable t){
                            Log.e("Dectective CONAN" , "tiens,tiens,tiens");
                        }
                    }

                    // Si la noteCourante est en proportion satisfaisante dans le tableau, on valide la note et on actualise les gammes
                    if (nombreOccurence / dernieresNotes.length >= PROPORTION_DE_NOTES_REQUISE) {

                        // Affichage de la note si elle n'est pas déjà affichée
                        if (noteProche.toString().compareTo(texteNoteCourante.getText().toString()) != 0) {
                            //RunOnUIThread
                            afficherNote(noteProche);
                        }
                        gererGammes(noteProche);
                    }

                }
            } catch(Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void gererGammes(Note note) {
        //RunOnUIThread
        //if (texteCompatibiliteGamme1.getVisibility() == INVISIBLE) { texteCompatibiliteGamme1.setVisibility(VISIBLE); }
        //if (boutonGamme1.getVisibility() == INVISIBLE) { boutonGamme1.setVisibility(VISIBLE); }

        List<Gamme> gammes = gammeManager.ajouterOccurenceDeNote(note.toString());
        int pourcentageDeCompatibiliteDeLaGamme1 = (gammes.get(0).scoreGamme() / gammeManager.nombreDeNotesAjoutees()) * 100;

        if (gammes.get(0).nom().compareTo(boutonGamme1.getText().toString()) != 0) {
            //RunOnUIThread
           afficherNomGamme(gammes);
        }

        if (Integer.toString(pourcentageDeCompatibiliteDeLaGamme1).compareTo(texteCompatibiliteGamme1.getText().toString()) != 0) {
            //RunOnUIThread
            afficherPourcentage(pourcentageDeCompatibiliteDeLaGamme1);
        }
    }

    Note noteAAfficher;
    private void afficherNote(Note note){
        try {
            noteAAfficher = note;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    texteNoteCourante.setVisibility(View.INVISIBLE);
                    texteNoteCourante.setText(noteAAfficher.toString());
                    texteNoteCourante.setVisibility(View.VISIBLE);
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    List<Gamme> gammesAAfficher;
    private void afficherNomGamme(List<Gamme> gammes){
        try {
            gammesAAfficher = gammes;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    boutonGamme1.setText(gammesAAfficher.get(0).nom());
                    boutonGamme1.setVisibility(View.VISIBLE);
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    int pourcentageAAfficher;
    private void afficherPourcentage(int pourcentage){
        pourcentageAAfficher = pourcentage;
        try {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    texteCompatibiliteGamme1.setText(Integer.toString(pourcentageAAfficher)+"%");
                    texteCompatibiliteGamme1.setVisibility(View.VISIBLE);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}