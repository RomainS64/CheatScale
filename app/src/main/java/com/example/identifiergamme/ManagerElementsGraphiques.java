package com.example.identifiergamme;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ManagerElementsGraphiques {
    // ---------- Eléments graphiques ---------- \\
    // Boutons
    protected Button boutonStart;
    protected Button boutonStop;
    protected Button boutonGamme1;

    // Textes
    protected TextView texteEcouteEnCours;
    protected TextView texteIndiquationJouez;
    protected TextView texteTonalitesCompatibles;
    protected TextView texteNoteCourante;
    protected TextView texteCompatibiliteGamme1;

    // Autre
    protected ProgressBar progress_bar;
    protected View barre_horizontale_3;
    protected View barre_horizontale_2;
    protected ImageView img_check;
    // ------------------------------------------ \\

    private IdentifierGamme page;

    protected ManagerElementsGraphiques(IdentifierGamme identifierGamme) {
        this.page = identifierGamme;
    }

    protected void initialiserElementsGraphiques() {
        // ---------- Initialisation des éléments graphiques ---------- \\
        // Boutons
        boutonStart = (Button)page.findViewById(R.id.start_button_identifier_tonalite);
        boutonStop = (Button)page.findViewById(R.id.stop_button_identifier_tonalite);
        boutonGamme1 = (Button)page.findViewById(R.id.button_gamme1);

        // Textes
        texteEcouteEnCours = (TextView)page.findViewById(R.id.texte_ecoute);
        texteIndiquationJouez = (TextView)page.findViewById(R.id.texte_jouez);
        texteTonalitesCompatibles = (TextView)page.findViewById(R.id.texte_tonalites_compatibles);
        texteNoteCourante = (TextView)page.findViewById(R.id.texte_note);
        texteCompatibiliteGamme1 = (TextView)page.findViewById(R.id.texte_gamme1);

        // Autre
        progress_bar = (ProgressBar)page.findViewById(R.id.progress_bar);
        barre_horizontale_2 = (View)page.findViewById(R.id.barre_horizontale_2);
        barre_horizontale_3 = (View)page.findViewById(R.id.barre_horizontale_3);
        img_check = (ImageView)page.findViewById(R.id.img_check);
        // -------------------------------------------------------------- \\
    }

    protected void lancerEcoute() {
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
    }

    protected void arreterEcoute() {
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
}
