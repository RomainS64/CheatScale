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
import com.example.scalefinder.detectionnote.ManagerNote;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class IdentifierGamme extends AppCompatActivity {
    // ---------- Managers ---------- \\
    com.example.scalefinder.detectionnote.ManagerNote noteManager;
    ManagerGamme gammeManager;
    ManagerElementsGraphiques graph;

    // ---------- Threads & Gestion des Threads ---------- \\
    Thread GererNotesEtGammes;
    boolean arreterFonctionnalite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.identifier_gamme);

        // Initialisation des éléments graphiques
        graph = new ManagerElementsGraphiques(this);
        graph.initialiserElementsGraphiques();

        // Déclenchement de la fonctionnalité
        graph.boutonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ---------- Initialisation des éléments requis ---------- \\
                // Apparition des éléments graphiques
                graph.lancerEcoute();

                //Initialisation des managers
                noteManager = new com.example.scalefinder.detectionnote.ManagerNote();
                gammeManager = new ManagerGamme();

                // Initialisation du thread d'affichage de note
                GererNotesEtGammes = new Thread(new Runnable() { public void run() { identifierGammes(); } });

                // ---------- Lancement du Thread et du manager audio ---------- \\
                arreterFonctionnalite = false;
                try { GererNotesEtGammes.start(); }
                catch (Throwable t){}
            }
        });

        // Arrêt de la fonctionnalité
        graph.boutonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ---------- Arrêt du thread ---------- \\
                arreterFonctionnalite = true;
                try { GererNotesEtGammes.join(); }
                catch(InterruptedException e){}

                // Suppression des éléments graphiques
                graph.arreterEcoute();
            }
        });
    }

    private void identifierGammes() {
        while(!arreterFonctionnalite) {
            Note noteTrouvee = noteManager.getNote();
            afficherNote(noteTrouvee);
            actualiserGammes(noteTrouvee);
        }
    }

    private void actualiserGammes(Note note) {
        //RunOnUIThread
        //if (texteCompatibiliteGamme1.getVisibility() == INVISIBLE) { texteCompatibiliteGamme1.setVisibility(VISIBLE); }
        //if (boutonGamme1.getVisibility() == INVISIBLE) { boutonGamme1.setVisibility(VISIBLE); }

        List<Gamme> gammes = gammeManager.ajouterOccurenceDeNote(note.toString());
        int pourcentageDeCompatibiliteDeLaGamme1 = (gammes.get(0).scoreGamme() / gammeManager.nombreDeNotesAjoutees()) * 100;

        if (gammes.get(0).nom().compareTo(graph.boutonGamme1.getText().toString()) != 0) {
            //RunOnUIThread
           afficherNomGamme(gammes);
        }

        if (Integer.toString(pourcentageDeCompatibiliteDeLaGamme1).compareTo(graph.texteCompatibiliteGamme1.getText().toString()) != 0) {
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
                    graph.texteNoteCourante.setVisibility(View.INVISIBLE);
                    graph.texteNoteCourante.setText(noteAAfficher.toString());
                    graph.texteNoteCourante.setVisibility(View.VISIBLE);
                }
            });

        } catch (Throwable e) {}
    }

    List<Gamme> gammesAAfficher;
    private void afficherNomGamme(List<Gamme> gammes){
        try {
            gammesAAfficher = gammes;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    graph.boutonGamme1.setText(gammesAAfficher.get(0).nom());
                    graph.boutonGamme1.setVisibility(View.VISIBLE);
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
                    graph.texteCompatibiliteGamme1.setText(Integer.toString(pourcentageAAfficher)+"%");
                    graph.texteCompatibiliteGamme1.setVisibility(View.VISIBLE);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}