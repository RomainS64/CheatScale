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
                noteManager.arreterRecherche();
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
            graph.afficherSurUIThread(graph.texteNoteCourante, noteTrouvee.toString());
            actualiserGammes(noteTrouvee);
        }
    }

    private void actualiserGammes(Note note) {
        List<Gamme> gammes = gammeManager.ajouterOccurenceDeNote(note.toString());
        int pourcentageDeCompatibiliteDeLaGamme1 = (gammes.get(0).scoreGamme() / gammeManager.nombreDeNotesAjoutees()) * 100;
        graph.afficherSurUIThread(graph.boutonGamme1, gammes.get(0).nom());
        graph.afficherSurUIThread(graph.texteCompatibiliteGamme1, pourcentageDeCompatibiliteDeLaGamme1 + "%");
    }
}