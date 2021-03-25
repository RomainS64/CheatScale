package com.example.identifiergamme;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import static android.view.View.*;
import static androidx.core.content.ContextCompat.startActivity;

import android.view.View;
import com.example.R;
import com.example.Note;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class IdentifierGamme extends AppCompatActivity {
    private com.example.identifiernotes.ManagerNote noteManager;
    private ManagerGamme gammeManager;
    private ManagerElementsGraphiques graph;
    private Thread GererNotesEtGammes;
    private boolean arreterFonctionnalite = false;
    private Note derniereNoteTrouvee = new Note();
    protected static Gamme gammeChoisie;
    List<Gamme> gammes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.identifier_gamme);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialisation des éléments graphiques
        graph = new ManagerElementsGraphiques(this);
        graph.initialiserElementsGraphiques();

        graph.boutonGamme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gammeChoisie = gammes.get(0);
                    arreterRecherche();
                    Intent openInfosGamme = new Intent(IdentifierGamme.this, InfosGamme.class); //Declaration de l'activité pour ouvrir la page InfosGamme
                    startActivity(openInfosGamme); //Ouverture de la page InfosGammes
                }
                catch (Exception e) {}
            }
        });
        graph.boutonGamme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gammeChoisie = gammes.get(1);
                    arreterRecherche();
                    Intent openInfosGamme = new Intent(IdentifierGamme.this, InfosGamme.class); //Declaration de l'activité pour ouvrir la page InfosGamme
                    startActivity(openInfosGamme); //Ouverture de la page InfosGammes
                }
                catch (Exception e) {}
            }
        });
        graph.boutonGamme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gammeChoisie = gammes.get(2);
                    arreterRecherche();
                    Intent openInfosGamme = new Intent(IdentifierGamme.this, InfosGamme.class); //Declaration de l'activité pour ouvrir la page InfosGamme
                    startActivity(openInfosGamme); //Ouverture de la page InfosGammes
                }
                catch (Exception e) {}
            }
        });

        // Déclenchement de la fonctionnalité
        graph.boutonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graph.lancerEcoute();
                graph.reinitialiserAffichage();
                noteManager = new com.example.identifiernotes.ManagerNote();
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
                arreterRecherche();
            }
        });
    }
    private void arreterRecherche() {
        arreterFonctionnalite = true;
        noteManager.arreterRecherche();
        try { GererNotesEtGammes.join(); }
        catch(InterruptedException e){}
        graph.arreterEcoute();
    }

    private void identifierGammes() {
        while(!arreterFonctionnalite) {
            Note noteTrouvee = noteManager.getNote();

            if (noteTrouvee.toString().compareTo(derniereNoteTrouvee.toString()) != 0) {
                derniereNoteTrouvee = noteTrouvee;
                graph.afficherSurUIThread(graph.texteNoteCourante, noteTrouvee.toString());

                gammes = gammeManager.ajouterOccurenceDeNote(noteTrouvee.toString());

                double pourcentageDeCompatibiliteDeLaGamme1 = Math.ceil(((double)gammes.get(0).scoreGamme() / (double)gammeManager.nombreDeNotesAjoutees()) * 100);
                double pourcentageDeCompatibiliteDeLaGamme2 = Math.ceil(((double)gammes.get(1).scoreGamme() / (double)gammeManager.nombreDeNotesAjoutees()) * 100);
                double pourcentageDeCompatibiliteDeLaGamme3 = Math.ceil(((double)gammes.get(2).scoreGamme() / (double)gammeManager.nombreDeNotesAjoutees()) * 100);

                graph.afficherSurUIThread(graph.boutonGamme1, gammes.get(0).nom());
                graph.afficherSurUIThread(graph.boutonGamme2, gammes.get(1).nom());
                graph.afficherSurUIThread(graph.boutonGamme3, gammes.get(2).nom());
                graph.afficherSurUIThread(graph.texteCompatibiliteGamme1, pourcentageDeCompatibiliteDeLaGamme1 + "%");
                graph.afficherSurUIThread(graph.texteCompatibiliteGamme2, pourcentageDeCompatibiliteDeLaGamme2 + "%");
                graph.afficherSurUIThread(graph.texteCompatibiliteGamme3, pourcentageDeCompatibiliteDeLaGamme3 + "%");
            }
        }
    }
}