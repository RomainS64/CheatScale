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
    private com.example.scalefinder.detectionnote.ManagerNote noteManager;
    private ManagerGamme gammeManager;
    private ManagerElementsGraphiques graph;
    private Thread GererNotesEtGammes;
    private boolean arreterFonctionnalite = false;
    private Note derniereNoteTrouvee = new Note();

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
                graph.lancerEcoute();
                noteManager = new com.example.scalefinder.detectionnote.ManagerNote();
                gammeManager = new ManagerGamme();
                arreterFonctionnalite = false;
                GererNotesEtGammes = new Thread(new Runnable() { public void run() { identifierGammes(); } });
                try { GererNotesEtGammes.start(); }
                catch (Throwable t){}
            }
        });

        // Arrêt de la fonctionnalité
        graph.boutonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arreterFonctionnalite = true;
                noteManager.arreterRecherche();
                try { GererNotesEtGammes.join(); }
                catch(InterruptedException e){}
                graph.arreterEcoute();
            }
        });
    }

    private void identifierGammes() {
        while(!arreterFonctionnalite) {
            Note noteTrouvee = noteManager.getNote();

            if (noteTrouvee.toString().compareTo(derniereNoteTrouvee.toString()) != 0) {
                derniereNoteTrouvee = noteTrouvee;
                graph.afficherSurUIThread(graph.texteNoteCourante, noteTrouvee.toString());

                List<Gamme> gammes = gammeManager.ajouterOccurenceDeNote(noteTrouvee.toString());
                double pourcentageDeCompatibiliteDeLaGamme1 = Math.ceil(((double)gammes.get(0).scoreGamme() / (double)gammeManager.nombreDeNotesAjoutees()) * 100);
                double pourcentageDeCompatibiliteDeLaGamme2 = Math.ceil(((double)gammes.get(1).scoreGamme() / (double)gammeManager.nombreDeNotesAjoutees()) * 100);
                double pourcentageDeCompatibiliteDeLaGamme3 = Math.ceil(((double)gammes.get(2).scoreGamme() / (double)gammeManager.nombreDeNotesAjoutees()) * 100);
                double pourcentageDeCompatibiliteDeLaGamme4 = Math.ceil(((double)gammes.get(3).scoreGamme() / (double)gammeManager.nombreDeNotesAjoutees()) * 100);
                double pourcentageDeCompatibiliteDeLaGamme5 = Math.ceil(((double)gammes.get(4).scoreGamme() / (double)gammeManager.nombreDeNotesAjoutees()) * 100);
                double pourcentageDeCompatibiliteDeLaGamme6 = Math.ceil(((double)gammes.get(5).scoreGamme() / (double)gammeManager.nombreDeNotesAjoutees()) * 100);

                graph.afficherSurUIThread(graph.boutonGamme1, gammes.get(0).nom());
                graph.afficherSurUIThread(graph.boutonGamme2, gammes.get(1).nom());
                graph.afficherSurUIThread(graph.boutonGamme3, gammes.get(2).nom());
                graph.afficherSurUIThread(graph.boutonGamme4, gammes.get(3).nom());
                graph.afficherSurUIThread(graph.boutonGamme5, gammes.get(4).nom());
                graph.afficherSurUIThread(graph.boutonGamme6, gammes.get(5).nom());
                graph.afficherSurUIThread(graph.texteCompatibiliteGamme1, pourcentageDeCompatibiliteDeLaGamme1 + "%");
                graph.afficherSurUIThread(graph.texteCompatibiliteGamme2, pourcentageDeCompatibiliteDeLaGamme2 + "%");
                graph.afficherSurUIThread(graph.texteCompatibiliteGamme3, pourcentageDeCompatibiliteDeLaGamme3 + "%");
                graph.afficherSurUIThread(graph.texteCompatibiliteGamme4, pourcentageDeCompatibiliteDeLaGamme4 + "%");
                graph.afficherSurUIThread(graph.texteCompatibiliteGamme5, pourcentageDeCompatibiliteDeLaGamme5 + "%");
                graph.afficherSurUIThread(graph.texteCompatibiliteGamme6, pourcentageDeCompatibiliteDeLaGamme6 + "%");
            }
        }
    }
}