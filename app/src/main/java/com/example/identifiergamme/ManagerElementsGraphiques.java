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
    protected Button boutonGamme2;
    protected Button boutonGamme3;
    protected Button boutonGamme4;
    protected Button boutonGamme5;
    protected Button boutonGamme6;

    // Textes
    protected TextView texteEcouteEnCours;
    protected TextView texteIndiquationJouez;
    protected TextView texteTonalitesCompatibles;
    protected TextView texteNoteCourante;
    protected TextView texteCompatibiliteGamme1;
    protected TextView texteCompatibiliteGamme2;
    protected TextView texteCompatibiliteGamme3;
    protected TextView texteCompatibiliteGamme4;
    protected TextView texteCompatibiliteGamme5;
    protected TextView texteCompatibiliteGamme6;

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
        boutonGamme2 = (Button)page.findViewById(R.id.button_gamme2);
        boutonGamme3 = (Button)page.findViewById(R.id.button_gamme3);
        boutonGamme4 = (Button)page.findViewById(R.id.button_gamme4);
        boutonGamme5 = (Button)page.findViewById(R.id.button_gamme5);
        boutonGamme6 = (Button)page.findViewById(R.id.button_gamme6);

        // Textes
        texteEcouteEnCours = (TextView)page.findViewById(R.id.texte_ecoute);
        texteIndiquationJouez = (TextView)page.findViewById(R.id.texte_jouez);
        texteTonalitesCompatibles = (TextView)page.findViewById(R.id.texte_tonalites_compatibles);
        texteNoteCourante = (TextView)page.findViewById(R.id.texte_note);
        texteCompatibiliteGamme1 = (TextView)page.findViewById(R.id.texte_gamme1);
        texteCompatibiliteGamme2 = (TextView)page.findViewById(R.id.texte_gamme2);
        texteCompatibiliteGamme3 = (TextView)page.findViewById(R.id.texte_gamme3);
        texteCompatibiliteGamme4 = (TextView)page.findViewById(R.id.texte_gamme4);
        texteCompatibiliteGamme5 = (TextView)page.findViewById(R.id.texte_gamme5);
        texteCompatibiliteGamme6 = (TextView)page.findViewById(R.id.texte_gamme6);

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
        boutonGamme2.setVisibility(View.VISIBLE);
        boutonGamme3.setVisibility(View.VISIBLE);
        boutonGamme4.setVisibility(View.VISIBLE);
        boutonGamme5.setVisibility(View.VISIBLE);
        boutonGamme6.setVisibility(View.VISIBLE);
        texteCompatibiliteGamme1.setVisibility(VISIBLE);
        texteCompatibiliteGamme2.setVisibility(VISIBLE);
        texteCompatibiliteGamme3.setVisibility(VISIBLE);
        texteCompatibiliteGamme4.setVisibility(VISIBLE);
        texteCompatibiliteGamme5.setVisibility(VISIBLE);
        texteCompatibiliteGamme6.setVisibility(VISIBLE);
        texteNoteCourante.setVisibility(View.VISIBLE);

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
        boutonGamme1.setVisibility(View.INVISIBLE);
        boutonGamme2.setVisibility(View.INVISIBLE);
        boutonGamme3.setVisibility(View.INVISIBLE);
        boutonGamme4.setVisibility(View.INVISIBLE);
        boutonGamme5.setVisibility(View.INVISIBLE);
        boutonGamme6.setVisibility(View.INVISIBLE);
        texteCompatibiliteGamme1.setVisibility(View.INVISIBLE);
        texteCompatibiliteGamme2.setVisibility(View.INVISIBLE);
        texteCompatibiliteGamme3.setVisibility(View.INVISIBLE);
        texteCompatibiliteGamme4.setVisibility(View.INVISIBLE);
        texteCompatibiliteGamme5.setVisibility(View.INVISIBLE);
        texteCompatibiliteGamme6.setVisibility(View.INVISIBLE);

        // Réapparition des éléments utiles
        boutonStart.setVisibility(View.VISIBLE);
        barre_horizontale_2.setVisibility(View.VISIBLE);
        // ---------------------------------------------------------- \\
    }

    protected void afficherSurUIThread(final TextView elementAModifier, final String texte) {
        try {
            page.runOnUiThread(new Runnable() {
                @Override
                    public void run() {
                        elementAModifier.setText(texte);
                    }
                });
            } catch (Throwable e) {}
    }

    protected void reinitialiserAffichage() {
        texteNoteCourante.setText("NOTE");
        boutonGamme1.setText("GAMME 1");
        boutonGamme2.setText("GAMME 2");
        boutonGamme3.setText("GAMME 3");
        boutonGamme4.setText("GAMME 4");
        boutonGamme5.setText("GAMME 5");
        boutonGamme6.setText("GAMME 6");
        texteCompatibiliteGamme1.setText("");
        texteCompatibiliteGamme2.setText("");
        texteCompatibiliteGamme3.setText("");
        texteCompatibiliteGamme4.setText("");
        texteCompatibiliteGamme5.setText("");
        texteCompatibiliteGamme6.setText("");
    }
}
