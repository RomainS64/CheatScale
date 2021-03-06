/***
 * @Author:Romain_Salha
 * @Date:16/01/2021
 *
 * ManagerNote est une classe permettant de savoir si une fréquence(double) donné en parametre,
 *
 * 1)Est une note: public boolean estUneNote(double frequence);
 * 2)Savoir la note la plus proche: public int getNoteLaPlusProche(double frequence);
 * 3)Savoir la distance (en Hz) de la note la plus proche: public double getDistanceNoteLaPlusProche(double frequence);
 */
package com.example.scalefinder.detectionnote;


import android.util.Log;


import java.util.ArrayList;


import static java.lang.Math.abs;
import static java.lang.Math.pow;
import com.example.Note;
public class ManagerNote {

    //différentes notes
    final int DO=1;
    final int REB=2;
    final int RE=3;
    final int MIB=4;
    final int MI=5;
    final int FA=6;
    final int SOLB=7;
    final int SOL=8;
    final int LAB=9;
    final int LA=10;
    final int SIB=11;
    final int SI=12;

    //fréquence des différentes notes
    final double DO_FQ=32.70;
    final double REB_FQ=34.65;
    final double RE_FQ=36.71;
    final double MIB_FQ=38.89;
    final double MI_FQ=41.20;
    final double FA_FQ=43.65;
    final double SOLB_FQ=46.25;
    final double SOL_FQ=49.00;
    final double LAB_FQ=51.91;
    final double LA_FQ=55.00;
    final double SIB_FQ=58.27;
    final double SI_FQ=61.74;


    ArrayList<Note> notes;
    private double margeErreur = 1.5;

    // Paramètres de sensibilité de la recherche de note
    private final int NOMBRE_DE_NOTES_A_ANALYSER = 10;
    private final double PROPORTION_DE_NOTES_REQUISE = 0.9;

    private Note[] dernieresNotes = new Note[NOMBRE_DE_NOTES_A_ANALYSER];
    private double frequence;
    private Note noteProche = new Note();
    private int nombreOccurence;
    private ManagerAudio audioManager = new ManagerAudio();
    private boolean continuerRechercheNote;

    public ManagerNote() { definirNotes(); audioManager.run(); }
    public ManagerNote(double margeErreur){
        this.margeErreur=margeErreur;
        definirNotes();
    }

    public void arreterRecherche() { continuerRechercheNote = false; }

    public Note getNote() {
        continuerRechercheNote = true;
        while (continuerRechercheNote) {
            try {
                frequence = audioManager.getFrequenceForte();

                if (estUneNote(frequence)) {
                    noteProche = getNoteLaPlusProche(frequence);
                    nombreOccurence = 0;

                    // décalage des dernières notes
                    for (int i = dernieresNotes.length - 1; i > 0; i--) {
                        dernieresNotes[i] = dernieresNotes[i - 1];
                    }
                    // Insertion de la nouvelle note
                    dernieresNotes[0] = noteProche;

                    // On compte le nombre d'occurence de la nouvelle note dans le tableau
                    for (int j = 0; j < dernieresNotes.length; j++) {
                        try {
                            if (dernieresNotes[j].toString().compareTo(noteProche.toString()) == 0) {
                                nombreOccurence++;
                            }
                        } catch (Throwable t) {}
                    }

                    // Si la noteCourante est en proportion satisfaisante dans le tableau, on valide la note et on la renvoie
                    if (nombreOccurence / dernieresNotes.length >= PROPORTION_DE_NOTES_REQUISE) {
                        return noteProche;
                    }
                }
            } catch (Throwable t) {}
        }
        return noteProche;
    }

    public Note getNoteLaPlusProche(double frequence){

        int gammeTemperee=getGammeTemperee(frequence);

        int noteProche=DO;

        double distMin=abs(frequence-DO_FQ*pow(2,gammeTemperee));


        for(int i=REB;i<=SI;i++){
            double distI=abs(frequence-notes.get(i-1).getFrequence()*pow(2,gammeTemperee));
            if(distMin>distI){
                distMin=distI;
                noteProche=i;
            }
        }
        return notes.get(noteProche-1);
    }
    public double getDistanceNoteLaPlusProche(double frequence) {
        int note = getNoteLaPlusProche(frequence).getNote();
        int gammeTemperee = getGammeTemperee(frequence);

        return (frequence - notes.get(note-1).getFrequence()*pow(2,gammeTemperee));
    }
    public boolean estUneNote(double frequence){
        //Log.e("estUneNotes", "freq:"+frequence);
        //Log.e("estUneNotes",getDistanceNoteLaPlusProche(frequence)+">"+margeErreur*getGammeTemperee(frequence));
        if (abs(getDistanceNoteLaPlusProche(frequence))>margeErreur*getGammeTemperee(frequence)){

            return false;
        }

        //Log.e("estUneNotes", "C UNE NOTEUH");
        return true;
    }

    //Lier toutes les notes avec les bonnes fréquence dans laMap margeErreur
    private void definirNotes(){

        notes = new ArrayList<>();
        notes.add(new Note(DO,DO_FQ));
        notes.add(new Note(REB,REB_FQ));
        notes.add(new Note(RE,RE_FQ));
        notes.add(new Note(MIB,MIB_FQ));
        notes.add(new Note(MI,MI_FQ));
        notes.add(new Note(FA,FA_FQ));
        notes.add(new Note(SOLB,SOLB_FQ));
        notes.add(new Note(SOL,SOL_FQ));
        notes.add(new Note(LAB,LAB_FQ));
        notes.add(new Note(LA,LA_FQ));
        notes.add(new Note(SIB,SIB_FQ));
        notes.add(new Note(SI,SI_FQ));

    }

    private int getGammeTemperee(double frequence){
        int gammeTemperee=1;
        /**
         * SI_FQ*pow(2,gammeTemperee) = Limite de la gamme temperee
         * gammeTemperee*margeErreur = marge d'erreur, plus la gamme temperee est grande, plus la marge est permissive
         */
        while(frequence > SI_FQ*pow(2,gammeTemperee)+gammeTemperee*margeErreur){
            gammeTemperee++;
        }
        return gammeTemperee;
    }
}
